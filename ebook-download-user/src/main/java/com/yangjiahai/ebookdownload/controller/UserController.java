package com.yangjiahai.ebookdownload.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.yangjiahai.ebookdownload.annotation.RequiredToken;
import com.yangjiahai.ebookdownload.constants.Result;
import com.yangjiahai.ebookdownload.entity.User;
import com.yangjiahai.ebookdownload.handler.CustomHandler;
import com.yangjiahai.ebookdownload.service.UserService;
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

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/2/20 13:53
 */
@RefreshScope
@Slf4j
@Api("用户服务")
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private RedisTemplate redisTemplate;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequiredToken
    @ApiOperation("根据用户id查询用户信息")
    @GetMapping("/userinfo")
    public Result getUserInfo(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        Long id = user.getId();
        User info = userService.getUserInfoById(id);
        return Result.ok().data(info);
    }

    @ApiOperation("根据用户id查询用户信息")
    @GetMapping("/userinfobyid")
    public Result getUserInfo(@RequestParam("id") Long id) {
        User user = userService.getUserById(id);
        return Result.ok().data(user);
    }

    @RequiredToken
    @ApiOperation("修改用户读友圈背景图")
    @PutMapping("/userinfobyid")
    public Result updateUserInfo(HttpServletRequest request,
                                 @RequestParam("imgUrl") String imgUrl) {
        User user = (User) request.getAttribute("user");
        Long id = user.getId();
        int i = userService.updateUserBackGround(id, imgUrl);
        return Result.ok().data(i);
    }

    @RequiredToken
    @ApiOperation("对图书评论点赞")
    @SentinelResource(value = "likedComments",blockHandlerClass = CustomHandler.class,blockHandler = "handleBlockException2")
    @GetMapping("/liked/{commentid}")
    public Result liked(HttpServletRequest request, @PathVariable String commentid) {
        User user = (User) request.getAttribute("user");
        Long id = user.getId();
        logger.info(user.toString());
        logger.info(commentid);
        //查看用户是否对该评论点赞
        Boolean member = redisTemplate.opsForSet().isMember("user_liked" + id, commentid);
        String info = null;
        if (member) {
            logger.info("已对该评论点赞");
            //将用户点赞的评论id从redis中移除
            redisTemplate.opsForSet().remove("user_liked" + id, commentid);
            //将当前评论的点赞数量-1
            redisTemplate.opsForValue().decrement("book_comment_count" + commentid);
            info = "取消点赞";
        } else {
            logger.info("未对该评论点赞");
            //将用户点赞的评论id加入到redis中
            redisTemplate.opsForSet().add("user_liked" + id, commentid);
            //将当前评论的点赞数量+1
            redisTemplate.opsForValue().increment("book_comment_count" + commentid);
            info = "点赞成功";
        }
        return Result.ok().data(info);
    }

    @ApiOperation("查看用户是否对该评论点赞")
    @RequiredToken
    @GetMapping("/isliked/{commentid}")
    public Result isLiked(HttpServletRequest request, @PathVariable String commentid) {
        User user = (User) request.getAttribute("user");
        Long id = user.getId();
        //查看用户是否对该评论点赞
        Boolean member = redisTemplate.opsForSet().isMember("user_liked" + id, commentid);
        return Result.ok().data(member);
    }

    @ApiOperation("获取图书评论点赞总数")
    @GetMapping("/liked/count/{commentid}")
    public Result likedCount(@PathVariable String commentid) {
        //根据评论id查询评论点赞总数
        Integer num = (Integer) redisTemplate.opsForValue().get("book_comment_count" + commentid);
        return Result.ok().data(num);
    }

    @ApiOperation("判断用户是否第一次进入小程序:1代表成功，其它代表失败")
    @GetMapping("/firstinto")
    @RequiredToken
    public Result firstInto(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        Long id = user.getId();
        boolean firstInto = userService.isFirstInto(id);
        return Result.ok().data(firstInto);
    }

    @ApiOperation("签到:1代表成功，其它代表失败")
    @PutMapping("/signIn")
    @RequiredToken
    public Result signIn(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        Long id = user.getId();
        int i = userService.addIntegralById(id);
        return Result.ok().message("签到成功,积分加10").data(i);
    }

    @RequiredToken
    @ApiOperation("对面试题点赞")
    @SentinelResource(value = "likedInterview",blockHandlerClass = CustomHandler.class,blockHandler = "handleBlockException3")
    @GetMapping("/liked/interview/{interviewid}")
    public Result interviewLiked(HttpServletRequest request, @PathVariable Long interviewid) {
        User user = (User) request.getAttribute("user");
        Long id = user.getId();
        logger.info(user.toString());
        logger.info(interviewid.toString());
        //查看用户是否对该面试题点赞
        Boolean member = redisTemplate.opsForSet().isMember("user_liked_interview" + id, interviewid);
        String info = null;
        if (member) {
            logger.info("已对该面试题点赞");
            //将用户点赞的评论id从redis中移除
            redisTemplate.opsForSet().remove("user_liked_interview" + id, interviewid);
            //将当前评论的点赞数量-1
            redisTemplate.opsForValue().decrement("interview_liked_count" + interviewid);
            info = "取消点赞";
        } else {
            logger.info("未对该面试题点赞");
            //将用户点赞的评论id加入到redis中
            redisTemplate.opsForSet().add("user_liked_interview" + id, interviewid);
            //将当前评论的点赞数量+1
            redisTemplate.opsForValue().increment("interview_liked_count" + interviewid);
            info = "点赞成功";
        }
        Integer num = (Integer) redisTemplate.opsForValue().get("interview_liked_count" + interviewid);
        //修改点赞数量
        System.out.println("num = " + num);
        userService.updateInterview(interviewid, num);
        return Result.ok().data(num).message(info);
    }

    @ApiOperation("查看用户是否对该面试题点赞")
    @RequiredToken
    @GetMapping("/isliked/interview/{interviewid}")
    public Result isLikedInterview(HttpServletRequest request, @PathVariable Long interviewid) {
        User user = (User) request.getAttribute("user");
        Long id = user.getId();
        logger.info("userid", id);
        logger.info("interviewid", interviewid);
        //查看用户是否对该面试题点赞
        Boolean member = redisTemplate.opsForSet().isMember("user_liked_interview" + id, interviewid);
        return Result.ok().data(member);
    }

    @ApiOperation("根据面试题评论id查询点赞数量")
    @GetMapping("/liked/interviewcomments/count/{interviewCommentsId}")
    public Result likedCountInterview(@PathVariable String interviewCommentsId) {
        //根据面试题评论id查询点赞数量
        Integer num = (Integer) redisTemplate.opsForValue()
                .get("interview_comments_liked_count" + interviewCommentsId);
        return Result.ok().data(num);
    }

    @ApiOperation("查询用户是否对该面试题评论点赞")
    @RequiredToken
    @GetMapping("/isliked/interviewcomments/{interviewCommentsId}")
    public Result isLikedInterviewComments(HttpServletRequest request, @PathVariable String interviewCommentsId) {
        User user = (User) request.getAttribute("user");
        Long id = user.getId();
        logger.info("userid" + id);
        logger.info("interviewCommentsId：" + interviewCommentsId);

        //查看用户是否对该面试题评论点赞
        Boolean member = redisTemplate.opsForSet().isMember("user_liked_interview_comments" + id, interviewCommentsId);
        return Result.ok().data(member);
    }

    @ApiOperation("对面试题评论进行点赞")
    @RequiredToken
    @SentinelResource(value = "likedInterviewComments",blockHandlerClass = CustomHandler.class,blockHandler = "handleBlockException4")
    @PostMapping("/liked/interviewcomments/{interviewCommentsId}")
    public Result interviewCommentsLiked(HttpServletRequest request, @PathVariable String interviewCommentsId) {
        User user = (User) request.getAttribute("user");
        Long id = user.getId();
        logger.info(user.toString());
        logger.info(interviewCommentsId.toString());
        //查看用户是否对该面试题评论点赞
        Boolean member = redisTemplate.opsForSet().isMember("user_liked_interview_comments" + id, interviewCommentsId);
        String info = null;
        if (member) {
            logger.info("已对该面试题评论点赞");
            //将用户点赞的评论id从redis中移除
            redisTemplate.opsForSet().remove("user_liked_interview_comments" + id, interviewCommentsId);
            //将当前评论的点赞数量-1
            redisTemplate.opsForValue().decrement("interview_comments_liked_count" + interviewCommentsId);
            info = "取消点赞";
        } else {
            logger.info("未对该面试题评论点赞");
            //将用户点赞的评论id加入到redis中
            redisTemplate.opsForSet().add("user_liked_interview_comments" + id, interviewCommentsId);
            //将当前评论的点赞数量+1
            redisTemplate.opsForValue().increment("interview_comments_liked_count" + interviewCommentsId);
        }
        Integer num = (Integer) redisTemplate.opsForValue().get("interview_comments_liked_count" + interviewCommentsId);
        return Result.ok().data(num).message(info);
    }
}