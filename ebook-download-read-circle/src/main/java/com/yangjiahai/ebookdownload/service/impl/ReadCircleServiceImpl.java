package com.yangjiahai.ebookdownload.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangjiahai.ebookdownload.VO.CommentsVO;
import com.yangjiahai.ebookdownload.constants.Result;
import com.yangjiahai.ebookdownload.entity.CircleFriends;
import com.yangjiahai.ebookdownload.entity.CircleFriendsImg;
import com.yangjiahai.ebookdownload.VO.CircleFriendsVO;
import com.yangjiahai.ebookdownload.VO.Reviewer;
import com.yangjiahai.ebookdownload.VO.UserVO;

import com.yangjiahai.ebookdownload.entity.User;
import com.yangjiahai.ebookdownload.feignCilent.UserClient;
import com.yangjiahai.ebookdownload.mapper.ReadCircleMapper;
import com.yangjiahai.ebookdownload.service.ReadCircleImgService;
import com.yangjiahai.ebookdownload.service.ReadCircleService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/3/8 20:12
 */
@Slf4j
@Service
public class ReadCircleServiceImpl extends ServiceImpl<ReadCircleMapper, CircleFriends> implements ReadCircleService {
    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private ReadCircleMapper readCircleMapper;

    @Resource
    private ReadCircleImgService readCircleImgService;

    private Logger logger = LoggerFactory.getLogger(ReadCircleServiceImpl.class);
    @Resource
    private UserClient userClient;

    /**
     * 添加读友圈
     *
     * @param id
     * @param imgList
     * @param circleFriends
     * @return
     */
    @Override
    public int addReadCircle(Long id, String imgList, CircleFriends circleFriends) {
        CircleFriends circleFriends1 = new CircleFriends();
        circleFriends1.setUserid(id)
                .setLocation(circleFriends.getLocation())
                .setContentText(circleFriends.getContentText())
                .setLatitude(circleFriends.getLatitude())
                .setLongitude(circleFriends.getLongitude())
                .setGmtCreat(LocalDateTime.now())
                .setGmtModified(LocalDateTime.now());
        //将用户发表的数据存入数据库
        int insert = readCircleMapper.insert(circleFriends1);
        //将图片信息存入数据库
        readCircleImgService.addReadCircleImgByCid(circleFriends1.getId(), imgList);
        return insert;
    }

    /**
     * 分页查询读友圈列表
     *
     * @param pageNo
     * @param pageSize
     */
    @Override
    public Map<String, Object> selectAllReadCircle(Long pageNo, Long pageSize) {
        //分页获取父评论
        QueryWrapper<CircleFriends> wrapper2 = new QueryWrapper<>();
        wrapper2.isNull("parentid");
        wrapper2.orderByDesc("gmt_creat");
        Page<CircleFriends> page = new Page<>(pageNo, pageSize);

        Page<CircleFriends> circleFriendsPage = readCircleMapper.selectPage(page, wrapper2);
        List<CircleFriends> records = circleFriendsPage.getRecords();
        long total = circleFriendsPage.getTotal();
        List<CircleFriendsVO> commentsVOList = new ArrayList<>();
        records.forEach(record -> {
            //将评论信息转为circleFriendsVO对象
            CircleFriendsVO circleFriendsVO = new CircleFriendsVO();
            Long id = record.getId();
            //根据读友圈id查询图片列表
            logger.info("{id}", id);
            CircleFriendsImg circleFriendsImg = readCircleImgService.selectCircleImg(id);

            if (!ObjectUtils.isEmpty(circleFriendsImg)) {
                circleFriendsVO.setImgList(JSON.parseArray(circleFriendsImg.getImgUrl(), String.class));
            }
            //属性拷贝
            BeanUtils.copyProperties(record, circleFriendsVO);
            //获取点赞用户列表
            List<UserVO> userVOS = selectLikedList(record.getId().intValue());

            //设置点赞用户列表
            circleFriendsVO.setUserVOS(userVOS);
            //获取评论者的信息
            Reviewer reviewer = new Reviewer();
            Result userInfo = userClient.getUserInfo(record.getUserid());
            LinkedHashMap user = (LinkedHashMap) userInfo.getData();
            reviewer.setId((Integer) user.get("id"))
                    .setUsername((String) user.get("username"))
                    .setAvatar((String) user.get("avatar"));
            //设置评论者信息
            circleFriendsVO.setReviewer(reviewer);
            //设置子评论
            logger.info(record.getId().toString());
            //根据父评论id查询子评论
            List<CircleFriends> CircleFriendsChild = readCircleMapper.selectList(new QueryWrapper<CircleFriends>().eq("parentid", record.getId()).orderByDesc("gmt_creat"));
            List<CommentsVO> childCommentsVOS = new ArrayList<>();
            CircleFriendsChild.forEach(circleFriend -> {
                CommentsVO commentsVOS = new CommentsVO();
                //属性拷贝
                BeanUtils.copyProperties(circleFriend, commentsVOS);

                //查询子评论用户信息
                //获取评论者的信息
                Reviewer reviewerChild = new Reviewer();
                Result childUserInfo = userClient.getUserInfo(circleFriend.getUserid());
                LinkedHashMap childUser = (LinkedHashMap) childUserInfo.getData();
                reviewerChild.setId((Integer) childUser.get("id"))
                        .setUsername((String) childUser.get("username"))
                        .setAvatar((String) childUser.get("avatar"));
                //设置评论者信息
                commentsVOS
                        .setUsername((String) childUser.get("username"))
                ;
                childCommentsVOS.add(commentsVOS);
            });
            // 设置子评论
            circleFriendsVO.setSubComments(childCommentsVOS);
            commentsVOList.add(circleFriendsVO);
        });
        Map<String, Object> result = new HashMap<>(32);
        result.put("total", total);
        result.put("data", commentsVOList);
        return result;
    }

    /**
     * 根据读友圈id查询点赞列表
     *
     * @param circleid
     * @return
     */
    @Override
    public List<UserVO> selectLikedList(Integer circleid) {
        Set<Integer> members = redisTemplate.opsForSet().members("circle_friend_" + circleid);
        List<UserVO> users = new ArrayList<>();
        members.forEach(member -> {
            User user = readCircleMapper.selectListById(member);
            UserVO userVOS = new UserVO();
            userVOS.setUserId(user.getId().intValue());
            userVOS.setUserName(user.getUsername());
            users.add(userVOS);
        });
        return users;
    }

    /**
     * 根据读友圈id删除
     *
     * @param circleid
     * @return
     */
    @Override
    public int deleteCircleById(Long circleid) {
        int i = readCircleMapper.deleteById(circleid);
        readCircleImgService.delReadCircleImgByCid(circleid);
        return i;
    }

    /**
     * 根据读友圈id查询
     *
     * @param circleid
     * @return
     */
    @Override
    public CircleFriends selectCircleById(Long circleid) {
        CircleFriends circleFriend = readCircleMapper.selectOne(new QueryWrapper<CircleFriends>().eq("id", circleid));
        return circleFriend;
    }

    /**
     * 对读友圈进行评论
     *
     * @param id
     * @param circleid
     * @param content
     * @return
     */
    @Override
    public int addCommentByCircleId(Long id, Long circleid, String content) {
        CircleFriends circleFriends = new CircleFriends();
        circleFriends.setUserid(id)
                .setParentid(circleid)
                .setContentText(content)
                .setGmtCreat(LocalDateTime.now())
                .setGmtModified(LocalDateTime.now());
        int insert = readCircleMapper.insert(circleFriends);
        return insert;
    }

    /**
     * 根据读友圈id查询评论列表
     *
     * @param circleid
     * @return
     */
    @Override
    public List<CommentsVO> selectCommentsByCircleId(Long circleid) {
        List<CircleFriends> comments = readCircleMapper.selectList(new QueryWrapper<CircleFriends>().eq("parentid", circleid));
        System.out.println("comments = " + comments);
        List<CommentsVO> commentsVOS = new ArrayList<>();
        comments.forEach(comment -> {
            CommentsVO commentsVO = new CommentsVO();
            Result userInfo = userClient.getUserInfo(comment.getUserid());
            LinkedHashMap data = (LinkedHashMap) userInfo.getData();
            String username = (String) data.get("username");
            commentsVO.setId(comment.getId())
                    .setUsername(username)
                    .setContentText(comment.getContentText());
            commentsVOS.add(commentsVO);
        });
        return commentsVOS;
    }
}
