package com.yangjiahai.ebookdownload.controller;

import com.alibaba.fastjson.JSONObject;
import com.yangjiahai.ebookdownload.VO.CommentsVO;
import com.yangjiahai.ebookdownload.VO.InterviewCommentsVO;
import com.yangjiahai.ebookdownload.annotation.RequiredToken;
import com.yangjiahai.ebookdownload.constants.Result;
import com.yangjiahai.ebookdownload.entity.BookComments;
import com.yangjiahai.ebookdownload.entity.InterviewComments;
import com.yangjiahai.ebookdownload.entity.User;
import com.yangjiahai.ebookdownload.mapper.InterviewCommentsMapper;
import com.yangjiahai.ebookdownload.service.BookCommentsService;
import com.yangjiahai.ebookdownload.service.InterviewCommentsService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/2/28 13:40
 */
@RefreshScope
@Slf4j
@RestController
@RequestMapping("/interview")
public class InterviewCommentsController {
    @Resource
    private InterviewCommentsService interviewCommentsService;
    Logger logger = LoggerFactory.getLogger(InterviewCommentsController.class);

    @ApiOperation("根据图书id查询图书评论列表")
    @GetMapping("/comments/{interviewid}")
    public Result getInterviewComments(
            @PathVariable Long interviewid,
            @RequestParam("pageNo") Integer pageNo,
            @RequestParam("pageSize") Integer pageSize
    ) {
        Map<String, Object> comments = interviewCommentsService.selectInterviewComments(interviewid, pageNo, pageSize);
        return Result.ok().data(comments);
    }

    @ApiOperation("根据评论id查询评论信息及子评论")
    @GetMapping("/comments/subcomments/{commentid}")
    public Result getInterviewCommentsById(
            @PathVariable Long commentid
    ) {
        Map<String, Object> map = interviewCommentsService.selectInterviewCommentsById(commentid);
        return Result.ok().data(map);
    }

    @ApiOperation("添加评论")
    @PostMapping("/comments")
    @RequiredToken
    public Result addInterviewComments(
            InterviewComments comments,
            HttpServletRequest request
    ) {

        logger.info(comments.toString());
        User user = (User) request.getAttribute("user");
        Long id = user.getId();
        InterviewCommentsVO commentsVO = interviewCommentsService.insertInterviewComments(id, comments);
        return Result.ok().data(commentsVO);
    }



}
