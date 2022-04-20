package com.yangjiahai.ebookdownload.controller;

import com.yangjiahai.ebookdownload.VO.ReportVO;
import com.yangjiahai.ebookdownload.annotation.RequiredToken;
import com.yangjiahai.ebookdownload.constants.Result;
import com.yangjiahai.ebookdownload.entity.User;
import com.yangjiahai.ebookdownload.service.ReadCircleImgService;
import com.yangjiahai.ebookdownload.service.ReadCircleReportService;
import com.yangjiahai.ebookdownload.service.ReadCircleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/3/13 14:19
 */
@RefreshScope
@Slf4j
@Api("举报服务")
@RestController
@RequestMapping("/report")
public class ReadCircleReportController {
    @Resource
    private ReadCircleReportService readCircleReportService;

    @RequiredToken
    @ApiOperation("添加举报记录")
    @PostMapping("/add")
    public Result addReport(HttpServletRequest request, ReportVO reportVO,
                            @RequestParam("imgList") String imgList) {

        User user = (User) request.getAttribute("user");
        Long id = user.getId();
        int i = readCircleReportService.addReport(id, reportVO,imgList);
        return Result.ok().data(i);
    }
}
