package com.yangjiahai.ebookdownload.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.yangjiahai.ebookdownload.annotation.RequiredToken;
import com.yangjiahai.ebookdownload.constants.Result;
import com.yangjiahai.ebookdownload.entity.CollectionRecords;

import com.yangjiahai.ebookdownload.entity.User;
import com.yangjiahai.ebookdownload.handler.CustomHandler;
import com.yangjiahai.ebookdownload.service.CollectionRecordsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/2/20 13:53
 */
@RefreshScope
@Slf4j
@Api("收藏服务")
@RestController
@RequestMapping("/collection")
public class CollectionRecordsController {
    @Resource
    private CollectionRecordsService recordsService;
    private Logger logger = LoggerFactory.getLogger(CollectionRecordsController.class);

    @RequiredToken
    @ApiOperation("根据用户id查询用户收藏列表")
    @GetMapping("/select")
    public Result getCollectionById(HttpServletRequest request,
                                    @RequestParam("pageNo") Integer pageNo,
                                    @RequestParam("pageSize") Integer pageSize) {
        User user = (User) request.getAttribute("user");
        logger.info(user.toString());
        Long userId = user.getId();
        Map<String, Object> map = recordsService.selectCollectionsById(pageNo, pageSize, userId);
        return Result.ok().data(map);
    }

    @RequiredToken
    @ApiOperation("根据用户id和图书id查询该用户是否收藏该图书")
    @GetMapping("/{bookid}")
    public Result getCollection(HttpServletRequest request, @PathVariable Long bookid) {
        User user = (User) request.getAttribute("user");
        logger.info(user.toString());
        Long userId = user.getId();
        CollectionRecords records = recordsService.selectCollection(userId, bookid);
        logger.info("records",records);
        Boolean isInclude = ObjectUtils.isEmpty(records);

        return Result.ok().data(!isInclude);
    }
    @RequiredToken
    @ApiOperation("根据用户id和面试题id查询该用户是否收藏该面试题")
    @GetMapping("/interview/{interviewid}")
    public Result getInterviewCollection(HttpServletRequest request, @PathVariable Long interviewid) {
        User user = (User) request.getAttribute("user");
        logger.info(user.toString());
        Long userId = user.getId();
        CollectionRecords records = recordsService.selectInterviewCollection(userId, interviewid);
        logger.info("records",records);
        Boolean isInclude = ObjectUtils.isEmpty(records);

        return Result.ok().data(!isInclude);
    }

    @RequiredToken
    @ApiOperation("根据用户id图书id添加收藏记录")
    @SentinelResource(value = "addCollection", blockHandlerClass = CustomHandler.class, blockHandler = "handleBlockException1")
    @PutMapping("/add/{bookid}")
    public Result addCollection(HttpServletRequest request, @PathVariable Long bookid) {
        User user = (User) request.getAttribute("user");
        logger.info(user.toString());
        Long userId = user.getId();
        int i = recordsService.addCollectionById(userId, bookid);
        if (i <= 0) {
            throw new RuntimeException("添加失败");
        }
        return Result.ok().message("添加成功");
    }

    @RequiredToken
    @ApiOperation("根据用户id图书id删除用户收藏记录")
    @DeleteMapping("/del/{bookid}")
    public Result delCollection(HttpServletRequest request, @PathVariable Long bookid) {
        User user = (User) request.getAttribute("user");
        logger.info(user.toString());
        Long userId = user.getId();
        int i = recordsService.delCollectionById(userId, bookid);
        if (i <= 0) {
            throw new RuntimeException("删除失败");
        }

        return Result.ok().message("删除成功");
    }

    @RequiredToken
    @ApiOperation("根据用户id和面试题id添加收藏记录")
    @SentinelResource(value = "addInterviewCollection", blockHandlerClass = CustomHandler.class, blockHandler = "handleBlockException3")
    @PutMapping("/interview/add/{interviewid}")
    public Result addInterviewCollection(HttpServletRequest request, @PathVariable Long interviewid) {
        User user = (User) request.getAttribute("user");
        logger.info(user.toString());
        Long userId = user.getId();
        int i = recordsService.addInterviewCollectionById(userId, interviewid);
        if (i <= 0) {
            throw new RuntimeException("添加失败");
        }
        return Result.ok().message("添加成功");
    }

    @RequiredToken
    @ApiOperation("根据用户id和面试题id删除收藏记录")
    @DeleteMapping("/interview/del/{interviewid}")
    public Result delInterviewCollection(HttpServletRequest request, @PathVariable Long interviewid) {
        User user = (User) request.getAttribute("user");
        logger.info(user.toString());
        Long userId = user.getId();
        int i = recordsService.delInterviewCollectionById(userId, interviewid);
        if (i <= 0) {
            throw new RuntimeException("删除失败");
        }

        return Result.ok().message("删除成功");
    }

}
