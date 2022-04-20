package com.yangjiahai.ebookdownload.controller;

import com.yangjiahai.ebookdownload.VO.CommentsVO;
import com.yangjiahai.ebookdownload.annotation.RequiredToken;
import com.yangjiahai.ebookdownload.constants.Result;
import com.yangjiahai.ebookdownload.entity.BookComments;
import com.yangjiahai.ebookdownload.entity.User;
import com.yangjiahai.ebookdownload.service.BookCommentsService;
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
@RequestMapping("/book")
public class BookCommentsController {
    @Resource
    private BookCommentsService bookCommentsService;
    Logger logger = LoggerFactory.getLogger(BookCommentsController.class);


    @ApiOperation("根据图书id查询图书评论列表")
    @GetMapping("/comments/{bookid}")
    public Result getBookComments(
            @PathVariable Long bookid,
            Long pageNo,
            Long pageSize
    ) {
        Map<String, Object> map = bookCommentsService.selectBookCommentsByBookId(bookid, pageNo, pageSize);
        logger.info(map.toString());
        return Result.ok().data(map);
    }

    @RequiredToken
    @ApiOperation("发表评论")
    @PostMapping("/comments/publish")
    public Result publish(
            HttpServletRequest request,
            String content,
            Long bookid
    ) {
        User user = (User) request.getAttribute("user");
        Long userid = user.getId();
        CommentsVO comment = bookCommentsService.addComment(userid, bookid, content);
        return Result.ok().data(comment);
    }

    @RequiredToken
    @ApiOperation("回复")
    @PostMapping("/comments/replay")
    public Result replay(
            HttpServletRequest request,
         @RequestParam("content")   String content,
         @RequestParam("bookid")   Long bookid,
         @RequestParam(value = "parentid")   String parentid
    ) {

        logger.info(bookid.toString());
        logger.info(parentid);
        User user = (User) request.getAttribute("user");
        Long userid = user.getId();
        CommentsVO commentsVO = bookCommentsService.replayComment(userid, bookid, content, parentid);
        return Result.ok().data(commentsVO);
    }

    @ApiOperation("根据评论id查询回复")
    @GetMapping("/comment/{id}")
    public Result getCommentById(@PathVariable String id) {
        logger.info(id);
        BookComments records = bookCommentsService.getCommentById(id);
        return Result.ok().data(records);
    }

    @ApiOperation("根据评论id获取评论点赞数量")
    @GetMapping("/comment/count/{id}")
    public Result getCommentCountById(@PathVariable String id) {
        logger.info("id", id);
        Integer num = bookCommentsService.selectCommentsLikeById(id);
        return Result.ok().data(num);
    }
}
