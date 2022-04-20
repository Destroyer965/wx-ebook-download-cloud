package com.yangjiahai.ebookdownload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangjiahai.ebookdownload.VO.CommentsVO;
import com.yangjiahai.ebookdownload.VO.InterviewCommentsVO;
import com.yangjiahai.ebookdownload.VO.Reviewer;
import com.yangjiahai.ebookdownload.constants.Result;
import com.yangjiahai.ebookdownload.entity.BookComments;
import com.yangjiahai.ebookdownload.entity.InterviewComments;
import com.yangjiahai.ebookdownload.feignClient.UserClient;
import com.yangjiahai.ebookdownload.mapper.InterviewCommentsMapper;
import com.yangjiahai.ebookdownload.service.InterviewCommentsService;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author yangjiahai
 * @description: TODO
 * @date 2022/4/1213:53
 */
@Slf4j
@Service
public class InterviewCommentsServiceImpl extends ServiceImpl<InterviewCommentsMapper, InterviewComments> implements InterviewCommentsService {
    @Resource
    private InterviewCommentsMapper interviewCommentsMapper;
    @Resource
    private UserClient userClient;

    private Logger logger = LoggerFactory.getLogger(InterviewCommentsServiceImpl.class);

    @Override
    public Map<String, Object> selectInterviewComments(Long interviewid, Integer pageNo, Integer pageSize) {
        Page<InterviewComments> page = new Page<>(pageNo, pageSize);
        //分页获取一级父评论
        QueryWrapper<InterviewComments> wrapper = new QueryWrapper<>();
        wrapper.eq("interviewid", interviewid);
        wrapper.isNull("parentid");
        wrapper.orderByDesc("gmt_creat");
        Page<InterviewComments> commentsPage = interviewCommentsMapper.selectPage(page, wrapper);
        List<InterviewComments> records = commentsPage.getRecords();
        //以及评论记录数
        long parentTotal = commentsPage.getTotal();

        //获取评论总数量
        Integer total = interviewCommentsMapper.selectCount(new QueryWrapper<InterviewComments>().eq("interviewid", interviewid));

        List<InterviewCommentsVO> commentsVOList = new ArrayList<>();

        records.forEach(record -> {
            //将评论信息转为InterviewCommentsVO对象
            InterviewCommentsVO commentsVO = new InterviewCommentsVO();
            //属性拷贝
            BeanUtils.copyProperties(record, commentsVO);
            //获取评论者的信息
            Reviewer reviewer = new Reviewer();
            Result userInfo = userClient.getUserInfo(record.getUserid());
            LinkedHashMap user = (LinkedHashMap) userInfo.getData();
            reviewer.setId((Integer) user.get("id")).setUsername((String) user.get("username")).setAvatar((String) user.get("avatar"));
            //设置评论者信息
            commentsVO.setReviewer(reviewer);

            logger.info(record.getId().toString());
            //根据父评论id查询子评论
            List<InterviewComments> commentsChild = interviewCommentsMapper.selectList(new QueryWrapper<InterviewComments>().eq("parentid", record.getId()).orderByDesc("gmt_creat"));

            List<InterviewCommentsVO> childSubComments = this.interviewChildFun(commentsChild);
            //设置子评论
            commentsVO.setSubComments(childSubComments);
            commentsVOList.add(commentsVO);
        });

        Map<String, Object> map = new HashMap<>(16);
        map.put("total", total);
        map.put("parentTotal", parentTotal);
        map.put("data", commentsVOList);
        return map;
    }

    /**
     * 根据评论id查询评论信息子评论
     *
     * @param commentid
     * @return
     */
    @Override
    public Map<String, Object> selectInterviewCommentsById(Long commentid) {
        InterviewComments comments = interviewCommentsMapper.selectOne(new QueryWrapper<InterviewComments>().eq("id", commentid));
        //将评论信息转为InterviewCommentsVO对象
        InterviewCommentsVO commentsVO = new InterviewCommentsVO();
        //属性拷贝
        BeanUtils.copyProperties(comments, commentsVO);
        //获取评论者的信息
        Reviewer reviewer = new Reviewer();
        Result userInfo = userClient.getUserInfo(comments.getUserid());
        LinkedHashMap user = (LinkedHashMap) userInfo.getData();
        reviewer.setId((Integer) user.get("id")).setUsername((String) user.get("username")).setAvatar((String) user.get("avatar"));
        //设置评论者信息
        commentsVO.setReviewer(reviewer);
        //根据父评论id查询子评论
        List<InterviewComments> commentsChild = interviewCommentsMapper.selectList(new QueryWrapper<InterviewComments>().eq("parentid", commentid).orderByDesc("gmt_creat"));
        //设置子评论
        List<InterviewCommentsVO> childSubComments = this.interviewChildFun(commentsChild);
        commentsVO.setSubComments(childSubComments);
        //查询子评论数量
        Integer total = interviewCommentsMapper.selectCount(new QueryWrapper<InterviewComments>().eq("parentid", commentid));
        Map<String, Object> map = new HashMap<>(16);
        map.put("data", commentsVO);
        map.put("total", total);
        return map;
    }

    /**
     * 添加评论
     *
     * @param id
     * @param interviewComments
     */
    @Override
    public InterviewCommentsVO insertInterviewComments(Long id, InterviewComments interviewComments) {
        InterviewComments comments = new InterviewComments();
        comments.setId(UUID.randomUUID().toString())
                .setContent(interviewComments.getContent())
                .setInterviewid(interviewComments.getInterviewid())
                .setParentid(interviewComments.getParentid())
                .setLevel(interviewComments.getLevel())
                .setUserid(id)
                .setGmtCreat(LocalDateTime.now())
                .setGmtModified(LocalDateTime.now());
        interviewCommentsMapper.insert(comments);
        InterviewCommentsVO commentsVO = new InterviewCommentsVO();
        //设置评论者信息
        Reviewer reviewer = new Reviewer();
        Result userInfo = userClient.getUserInfo(id);
        LinkedHashMap user = (LinkedHashMap) userInfo.getData();
        reviewer.setId((Integer) user.get("id"))
                .setUsername((String) user.get("username"))
                .setAvatar((String) user.get("avatar"));
        //根据子评论获取父评论

        if (!StringUtils.isEmpty(comments.getParentid())) {
            InterviewComments parentComments = interviewCommentsMapper
                    .selectOne(new QueryWrapper<InterviewComments>()
                            .eq("id", comments.getParentid()));
            //从父评论中获取父评论用户信息
            LinkedHashMap parentCommentUser = (LinkedHashMap) userClient.getUserInfo(parentComments.getUserid()).getData();
            commentsVO.setReplayUserName(parentCommentUser.get("username").toString());
        }

        commentsVO
                .setId(comments.getId())
                .setInterviewid(comments.getInterviewid())
                .setContent(comments.getContent())
                .setLevel(comments.getLevel())
                .setParentid(comments.getParentid())
                .setReviewer(reviewer)
                .setGmtCreat(LocalDateTime.now())
                .setGmtModified(LocalDateTime.now());
        ;
        return commentsVO;
    }

    /**
     * 根据父评论获取子评论
     *
     * @param commentsChild
     * @return
     */
    public List<InterviewCommentsVO> interviewChildFun(List<InterviewComments> commentsChild) {
        List<InterviewCommentsVO> childCommentsVOS = new ArrayList<>();
        commentsChild.forEach(comment -> {
            InterviewCommentsVO commentsVOS = new InterviewCommentsVO();
            //属性拷贝
            System.out.println("comment = " + comment);
            BeanUtils.copyProperties(comment, commentsVOS);
            //查询子评论用户信息
            //获取评论者的信息
            Reviewer reviewerChild = new Reviewer();
            Result childUserInfo = userClient.getUserInfo(comment.getUserid());
            LinkedHashMap childUser = (LinkedHashMap) childUserInfo.getData();
            reviewerChild.setId((Integer) childUser.get("id")).setUsername((String) childUser.get("username")).setAvatar((String) childUser.get("avatar"));
            //设置评论者信息
            commentsVOS.setReviewer(reviewerChild);

            //根据父评论id查询父评论信息
            InterviewComments parentComment = interviewCommentsMapper
                    .selectOne(
                            new QueryWrapper<InterviewComments>().
                                    eq("id", comment.getParentid()));
            //从父评论中获取用户id，查询用户信息
            LinkedHashMap replayUser = (LinkedHashMap) userClient.getUserInfo(parentComment.getUserid()).getData();
            String username = (String) replayUser.get("username");
            //设置回复者用户名
            commentsVOS.setReplayUserName(username);
            //获取回复者用户信息
            childCommentsVOS.add(commentsVOS);
            //查询二级评论是否有子评论
            List<InterviewComments> child = interviewCommentsMapper.selectList(new QueryWrapper<InterviewComments>().eq("parentid", comment.getId()).orderByDesc("gmt_creat"));
            if (!CollectionUtils.isEmpty(child)) {
                //递归调用
                List<InterviewCommentsVO> voList = interviewChildFun(child);
                //设置自评论
                commentsVOS.setSubComments(voList);
            }
        });

        return childCommentsVOS;
    }
}
