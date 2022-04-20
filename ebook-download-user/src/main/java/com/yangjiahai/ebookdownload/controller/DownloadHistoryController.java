package com.yangjiahai.ebookdownload.controller;

import com.yangjiahai.ebookdownload.annotation.RequiredToken;
import com.yangjiahai.ebookdownload.constants.Result;
import com.yangjiahai.ebookdownload.entity.User;
import com.yangjiahai.ebookdownload.service.DownloadHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/3/5 18:42
 */
@RefreshScope
@Slf4j
@Api("下载服务")
@RestController
@RequestMapping("/download")
public class DownloadHistoryController {
    @Resource
    private DownloadHistoryService downloadHistoryService;

    @RequiredToken
    @ApiOperation("根据用户id分页查询用户下载记录")
    @GetMapping("/history")
    public Result getDownloadHistory(
            HttpServletRequest request,
            @RequestParam("pageNo") Long pageNo,
            @RequestParam("pageSize") Long PageSize
    ) {
        User user = (User) request.getAttribute("user");
        Long id = user.getId();
        Map<String, Object> historyList = downloadHistoryService.selectDownloadHistory(id, pageNo, PageSize);
        return Result.ok().data(historyList);
    }


    @RequiredToken
    @ApiOperation("根据用户id添加用户下载记录")
    @PutMapping("/history/{bookid}")
    public Result addDownloadHistory(HttpServletRequest request, @PathVariable Long bookid) {
        User user = (User) request.getAttribute("user");
        Long id = user.getId();
        int i = downloadHistoryService.addDownloadHistory(id, bookid);
        return Result.ok().data(i);
    }

    @RequiredToken
    @ApiOperation("根据用户id以及收藏记录id删除用户下载记录")
    @DeleteMapping("/history/{bookid}")
    public Result delDownloadHistory(HttpServletRequest request, @PathVariable Long bookid) {
        User user = (User) request.getAttribute("user");
        Long userid = user.getId();
        int i = downloadHistoryService.delDownloadHistory(userid, bookid);
        return Result.ok().data(i);
    }

}
