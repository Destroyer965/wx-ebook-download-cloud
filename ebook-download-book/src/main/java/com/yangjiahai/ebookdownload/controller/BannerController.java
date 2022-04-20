package com.yangjiahai.ebookdownload.controller;

import com.yangjiahai.ebookdownload.constants.Result;
import com.yangjiahai.ebookdownload.entity.Banner;
import com.yangjiahai.ebookdownload.service.BannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/2/19 16:12
 */
 @RefreshScope
@RestController
@RequestMapping("/system")
@Api("系统信息")
public class BannerController {
    @Resource
    private BannerService bannerService;
    @ApiOperation("获取轮播图数据")
    @GetMapping("/banner")
    public Result getBanner(){
        System.out.println(1);
        List<Banner> banners = bannerService.selectBanner();
        return Result.ok().setData(banners);
    }
}
