package com.yangjiahai.ebookdownload.controller;

import com.yangjiahai.ebookdownload.constants.Result;
import com.yangjiahai.ebookdownload.service.ReadCircleReportImgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/3/13 14:19
 */
@RefreshScope
@Slf4j
@Api("举报服务")
@RestController
@RequestMapping("/reportimg")
public class ReadCircleReportImgController {
    @Resource
    private ReadCircleReportImgService readCircleReportImgService;

    @ApiOperation(value = "举报图片")
    @PostMapping("/add")
    public Result addImage(@RequestParam("cid")
                                   Long cid,
                           @RequestParam("url") String url) {
        int i = readCircleReportImgService.addReportImg(cid, url);
        return Result.ok().data(i);
    }
}
