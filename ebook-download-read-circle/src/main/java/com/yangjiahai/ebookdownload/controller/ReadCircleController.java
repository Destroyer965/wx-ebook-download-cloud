package com.yangjiahai.ebookdownload.controller;

import com.yangjiahai.ebookdownload.VO.CommentsVO;
import com.yangjiahai.ebookdownload.VO.UserVO;
import com.yangjiahai.ebookdownload.annotation.RequiredToken;
import com.yangjiahai.ebookdownload.constants.Result;
import com.yangjiahai.ebookdownload.entity.CircleFriends;
import com.yangjiahai.ebookdownload.entity.User;
import com.yangjiahai.ebookdownload.service.ReadCircleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/3/8 20:09
 */
@RefreshScope
@Slf4j
@Api("读友圈服务")
@RestController
@RequestMapping("/readcircle")
public class ReadCircleController {
    @Resource
    private ReadCircleService readCircleService;
    private Logger logger = LoggerFactory.getLogger(ReadCircleController.class);
    @Resource
    private RedisTemplate redisTemplate;

    @RequiredToken
    @ApiOperation("添加读友圈")
    @PostMapping(value = "/add")
    public Result addReadCircle(
            HttpServletRequest request,
            @RequestParam("imgList") String imgList,
            CircleFriends circleFriends
    ) {
        logger.info("imgList：", imgList);
        logger.info("circleFriends：", circleFriends);
        User user = (User) request.getAttribute("user");
        Long id = user.getId();
        int i = readCircleService.addReadCircle(id, imgList, circleFriends);
        return Result.ok().data(i);
    }

    @ApiOperation("分页查询读友圈列表")
    @GetMapping("/selectpage")
    public Result selectPage(Long pageNo,
                             Long pageSize) {
        Map<String, Object> map = readCircleService.selectAllReadCircle(pageNo, pageSize);
        return Result.ok().data(map);
    }
    @ApiOperation("根据读友圈id查询")
    @GetMapping("/select/{circleid}")
    public Result selectCircleById(@PathVariable Long circleid){
        CircleFriends circleFriend = readCircleService.selectCircleById(circleid);
        return Result.ok().data(circleFriend);
    }
    @RequiredToken
    @ApiOperation("根据id删除读友圈")
    @DeleteMapping("/del/{circleid}")
    public Result delCircle(HttpServletRequest request, @PathVariable Long circleid) {
        int i = readCircleService.deleteCircleById(circleid);
        return Result.ok().data(i);
    }

    @RequiredToken
    @ApiOperation("查看用户是否对该读友圈点赞")
    @GetMapping("/liked/{circleid}")
    public Result isLiked(HttpServletRequest request, @PathVariable Long circleid) {
        User user = (User) request.getAttribute("user");
        Long id = user.getId();
        Boolean isMember = redisTemplate.opsForSet().isMember("circle_friend_user_" + id, circleid);
        logger.info("isMember{}",isMember);
        return Result.ok().data(isMember);
    }

    @RequiredToken
    @ApiOperation("用户对读友圈点击点赞，若用户以及点赞，则取消点赞，否则点赞数量加一")
    @PutMapping("/liked/{circleid}")
    public Result liked(HttpServletRequest request, @PathVariable Long circleid
    ) {
        User user = (User) request.getAttribute("user");
        Long id = user.getId();
        logger.info("{id}", id);
        logger.info("circleid", circleid);
        Boolean isMember = redisTemplate.opsForSet().isMember("circle_friend_user_" + id, circleid);
        String info = null;
        if (isMember) {
            //用户点赞列表
            redisTemplate.opsForSet().remove("circle_friend_user_" + id, circleid);
            //读友圈点赞用户列表
            redisTemplate.opsForSet().remove("circle_friend_" + circleid, id);
            info = "取消点赞";
        } else {
            redisTemplate.opsForSet().add("circle_friend_user_" + id, circleid);
            //读友圈点赞用户列表
            redisTemplate.opsForSet().add("circle_friend_" + circleid, id);
            info = "点赞成功";
        }
        return Result.ok().data(info);
    }

    @ApiOperation("根据读友圈id查询点赞列表")
    @GetMapping("/liked/users/{circleid}")
    public Result likedUsers(@PathVariable Integer circleid) {
        logger.info("circleid：", circleid);
        List<UserVO> users = readCircleService.selectLikedList(circleid);
        return Result.ok().data(users);
    }

    @RequiredToken
    @ApiOperation("对读友圈进行评论")
    @PostMapping("/comment")
    public Result comment(
            HttpServletRequest request,
            @RequestParam Long circleid,
            @RequestParam String content){
        logger.info("circleid：", circleid);
        User user = (User) request.getAttribute("user");
        Long id = user.getId();
        int i = readCircleService.addCommentByCircleId(id, circleid, content);
        return Result.ok().data(i);
    }

    @ApiOperation("根据读友圈id查询评论列表")
    @GetMapping("/comment/{circleid}")
    public Result commentChild(@PathVariable Long circleid){
        List<CommentsVO> comments = readCircleService.selectCommentsByCircleId(circleid);
        return Result.ok().data(comments);
    }

}
