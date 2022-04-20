package com.yangjiahai.ebookdownload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangjiahai.ebookdownload.constants.Result;
import com.yangjiahai.ebookdownload.entity.BookComments;
import com.yangjiahai.ebookdownload.VO.CommentsVO;
import com.yangjiahai.ebookdownload.VO.Reviewer;
import com.yangjiahai.ebookdownload.feignClient.UserClient;
import com.yangjiahai.ebookdownload.mapper.BookCommentsMapper;
import com.yangjiahai.ebookdownload.service.BookCommentsService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/2/28 13:41
 */
@Slf4j
@Service
public class BookCommentsServiceImpl extends ServiceImpl<BookCommentsMapper, BookComments> implements BookCommentsService {
    @Resource
    private BookCommentsMapper bookCommentsMapper;
    @Resource
    private UserClient userClient;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    private Logger logger = LoggerFactory.getLogger(BookCommentsServiceImpl.class);

    /**
     * 根据图书id查询图书评论列表
     *
     * @param bookid
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Map<String, Object> selectBookCommentsByBookId(Long bookid, Long pageNo, Long pageSize) {
        Page<BookComments> page1 = new Page<>(pageNo, pageSize);
        QueryWrapper<BookComments> wrapper = new QueryWrapper<>();
        wrapper.eq("bookid", bookid);
        //获取评论数量
        Integer count = bookCommentsMapper.selectCount(wrapper);
        //分页获取父评论
        QueryWrapper<BookComments> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("bookid", bookid);
        wrapper2.isNull("parentid");
        wrapper2.orderByDesc("gmt_creat");
        Page<BookComments> commentsPage = bookCommentsMapper.selectPage(page1, wrapper2);
        List<BookComments> records = commentsPage.getRecords();
        long total = commentsPage.getTotal();

        List<CommentsVO> commentsVOList = new ArrayList<>();
        records.forEach(record -> {
            //将评论信息转为commentVo对象
            CommentsVO commentsVO = new CommentsVO();
            //属性拷贝
            BeanUtils.copyProperties(record, commentsVO);
            //获取评论者的信息
            Reviewer reviewer = new Reviewer();
            Result userInfo = userClient.getUserInfo(record.getUserid());
            LinkedHashMap user = (LinkedHashMap) userInfo.getData();
            reviewer.setId((Integer) user.get("id"))
                    .setUsername((String) user.get("username"))
                    .setAvatar((String) user.get("avatar"));
            //设置评论者信息
            commentsVO.setReviewer(reviewer);

            //设置子评论
            logger.info(record.getId().toString());
            //根据父评论id查询子评论
            List<BookComments> commentsChild = bookCommentsMapper.selectList(new QueryWrapper<BookComments>().eq("parentid", record.getId()).orderByDesc("gmt_creat"));
            List<CommentsVO> childCommentsVOS = new ArrayList<>();
            commentsChild.forEach(comment -> {
                CommentsVO commentsVOS = new CommentsVO();
                //属性拷贝
                BeanUtils.copyProperties(comment, commentsVOS);

                //查询子评论用户信息
                //获取评论者的信息
                Reviewer reviewerChild = new Reviewer();
                Result childUserInfo = userClient.getUserInfo(comment.getUserid());
                LinkedHashMap childUser = (LinkedHashMap) childUserInfo.getData();
                reviewerChild.setId((Integer) childUser.get("id"))
                        .setUsername((String) childUser.get("username"))
                        .setAvatar((String) childUser.get("avatar"));
                //设置评论者信息
                commentsVOS.setReviewer(reviewerChild);
                childCommentsVOS.add(commentsVOS);
            });
            // 设置子评论
            commentsVO.setSubComments(childCommentsVOS);
            commentsVOList.add(commentsVO);
        });

        Map<String, Object> result = new HashMap<>(32);
        result.put("count", count);
        result.put("total", total);
        result.put("data", commentsVOList);
        return result;
    }

    /**
     * 添加评论
     *
     * @param userid
     * @param bookid
     * @param content
     * @return
     */
    @Override
    public CommentsVO addComment(Long userid, Long bookid, String content) {
        BookComments bookComments = new BookComments();
        bookComments
                .setId(UUID.randomUUID().toString())
                .setBookid(bookid)
                .setContent(content)
                .setGmtCreat(LocalDateTime.now())
                .setGmtModified(LocalDateTime.now())
                .setUserid(userid);
        bookCommentsMapper.insert(bookComments);

        CommentsVO commentsVO = new CommentsVO();
        Reviewer reviewer = new Reviewer();
        Result userInfo = userClient.getUserInfo(userid);
        LinkedHashMap user = (LinkedHashMap) userInfo.getData();
        reviewer.setId((Integer) user.get("id"))
                .setUsername((String) user.get("username"))
                .setAvatar((String) user.get("avatar"));
        commentsVO
                .setId(bookComments.getId())
                .setBookid(bookid)
                .setContent(content)
                .setLikeNum(0)
                .setReviewer(reviewer)
                .setGmtCreat(LocalDateTime.now())
                .setGmtModified(LocalDateTime.now());
        ;
        return commentsVO;
    }

    /**
     * 根据评论id获取评论信息
     *
     * @param id
     * @return
     */
    @Override
    public BookComments getCommentById(String id) {
        BookComments records = bookCommentsMapper.selectOne(new QueryWrapper<BookComments>().eq("id", id));
        return records;
    }

    /**
     * 回复评论
     *
     * @param userid
     * @param bookid
     * @param content
     * @param parentid
     * @return
     */
    @Override
    public CommentsVO replayComment(Long userid, Long bookid, String content, String parentid) {
        BookComments comments = new BookComments();
        comments.setId(UUID.randomUUID().toString())
                .setUserid(userid)
                .setBookid(bookid)
                .setParentid(parentid)
                .setContent(content)
                .setGmtCreat(LocalDateTime.now())
                .setGmtModified(LocalDateTime.now());
        System.out.println("comments = " + comments);
        bookCommentsMapper.insert(comments);
        CommentsVO commentsVO = new CommentsVO();
        Reviewer reviewer = new Reviewer();
        //获取用户信息
        Result userInfo = userClient.getUserInfo(userid);
        LinkedHashMap user = (LinkedHashMap) userInfo.getData();
        reviewer.setId((Integer) user.get("id"))
                .setUsername((String) user.get("username"))
                .setAvatar((String) user.get("avatar"));
        commentsVO
                .setId(comments.getId())
                .setBookid(bookid)
                .setParentid(parentid)
                .setContent(content)
                .setLikeNum(0)
                .setReviewer(reviewer)
                .setGmtCreat(LocalDateTime.now())
                .setGmtModified(LocalDateTime.now());

        return commentsVO;
    }

    /**
     * 根据评论id获取评论点赞数
     *
     * @param id
     * @return
     */
    @Override
    public Integer selectCommentsLikeById(String id) {
        String num = stringRedisTemplate.opsForValue().get("book_comment_count" + id);
       Integer count = 0;
        if (!StringUtils.isEmpty(num)){
            count= Integer.valueOf(num);
        }
        return count;
    }
}
