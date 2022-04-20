package com.yangjiahai.ebookdownload.controller;

import com.yangjiahai.ebookdownload.annotation.RequiredToken;
import com.yangjiahai.ebookdownload.constants.Result;
import com.yangjiahai.ebookdownload.entity.ProblemFeedback;
import com.yangjiahai.ebookdownload.entity.User;
import com.yangjiahai.ebookdownload.service.ProblemFeedbackService;
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
 * @date 2022/3/13 16:04
 */
@RefreshScope
@Slf4j
@Api("问题反馈")
@RestController
@RequestMapping("/feedback")
public class ProblemFeedbackController {
    @Resource
    private ProblemFeedbackService problemFeedbackService;

    @RequiredToken
    @ApiOperation("添加举报记录")
    @PostMapping("/add")
    public Result addReport(HttpServletRequest request, ProblemFeedback problemFeedback) {

        User user = (User) request.getAttribute("user");
        Long id = user.getId();
        int i = problemFeedbackService.addProblem(id, problemFeedback);
        return Result.ok().data(i);
    }
}
