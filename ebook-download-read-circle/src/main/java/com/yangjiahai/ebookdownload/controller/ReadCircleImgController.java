package com.yangjiahai.ebookdownload.controller;

import com.yangjiahai.ebookdownload.constants.Result;
import com.yangjiahai.ebookdownload.entity.CircleFriendsImg;
import com.yangjiahai.ebookdownload.service.ReadCircleImgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/3/9 18:59
 */
@RefreshScope
@Slf4j
@Api("读友圈图片服务")
@RestController
@RequestMapping("/readcircleimg")
public class ReadCircleImgController {
    @Resource
    private ReadCircleImgService readCircleImgService;

    @ApiOperation(value = "用户发表读友圈存入图片", tags = "cid：读友圈id，url：图片路径")
    @PostMapping("/add")
    public Result addImage(@RequestParam("cid")
                                   Long cid,
                           @RequestParam("url") String url) {
        int i = readCircleImgService.addReadCircleImgByCid(cid, url);
        return Result.ok().data(i);
    }

    @ApiOperation(value = "用户删除读友圈，删除图片", tags = "cid：读友圈id")
    @DeleteMapping("/del")
    public Result delImage(@RequestParam("cid")
                                   Long cid) {
        int i = readCircleImgService.delReadCircleImgByCid(cid);
        return Result.ok().data(i);
    }

    @ApiOperation("根据读友圈id查询图片列表")
    @GetMapping("/select")
    public Result selectImage(@RequestParam("cid") Long cid) {
        CircleFriendsImg friendsImg = readCircleImgService.selectCircleImg(cid);
        return Result.ok().data(friendsImg);
    }
}
