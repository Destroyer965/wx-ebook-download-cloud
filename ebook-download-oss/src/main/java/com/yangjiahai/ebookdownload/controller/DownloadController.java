package com.yangjiahai.ebookdownload.controller;

import com.yangjiahai.ebookdownload.constants.Result;
import com.yangjiahai.ebookdownload.service.DownloadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/3/15 12:22
 */
@Api("文件下载")
@RestController
@RequestMapping("/download")
public class DownloadController {
    @Resource
    private DownloadService downloadService;

    @PostMapping(value = "/file")
    @ApiOperation("图片下载")
    public Result download(@RequestParam("url") String url) {
        System.out.println(url);
        Boolean isSuccess = downloadService.download(url);
        return Result.ok().data(isSuccess);
    }
}
