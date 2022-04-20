package com.yangjiahai.ebookdownload.controller;

import com.yangjiahai.ebookdownload.constants.Result;
import com.yangjiahai.ebookdownload.entity.Interview;
import com.yangjiahai.ebookdownload.service.InterviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/2/23 20:03
 */
@RefreshScope
@Slf4j
@RestController
@Api("面试题")
@RequestMapping("/interview")
public class InterviewController {
    @Resource
    private InterviewService interviewService;

    @ApiOperation("查询面试题分类")
    @GetMapping("/category")
    public Result interviewCategory() {
        List<String> category = interviewService.getInterviewCategory();
        return Result.ok().data(category);
    }

    @ApiOperation("分页按分类查询面试题")
    @GetMapping("/query")
    public Result interviewByCategory(@RequestParam("categoryId") Long categoryId,
                                      @RequestParam("pageNo") Integer pageNo,
                                      @RequestParam("pageSize") Integer pageSize) {

        if (categoryId != 0) {
            Map<String, Object> interview = interviewService.getInterviewByCategory(categoryId, pageNo, pageSize);
            return Result.ok().data(interview);
        } else {
            Map<String, Object> allInterview = interviewService.getAllInterview(pageNo, pageSize);
            return Result.ok().data(allInterview);
        }
    }
    @ApiOperation("根据id查询面试题信息")
    @GetMapping("/{id}")
    public Result  getInterviewById(@PathVariable Long id){
        Interview interview = interviewService.selectInterviewById(id);
        return Result.ok().data(interview);
    }
}
