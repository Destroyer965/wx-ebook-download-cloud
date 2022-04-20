package com.yangjiahai.ebookdownload.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.yangjiahai.ebookdownload.constants.Result;
import com.yangjiahai.ebookdownload.enums.ResultCodeEnum;
import javax.servlet.http.HttpServletRequest;

/**
 * @author yangjiahai
 * @description: TODO
 * @date 2022/4/2012:33
 * Sentinel兜底方法
 */
public class CustomHandler {
    public static Result handleBlockException1(HttpServletRequest request, Long bookid, BlockException exception) {
        return Result.error(ResultCodeEnum.Frequent_Operation.getCode(), ResultCodeEnum.Frequent_Operation.getMessage());
    }

    public static Result handleBlockException2(HttpServletRequest request, Long commentid, BlockException exception) {
        return Result.error(ResultCodeEnum.Frequent_Operation.getCode(), ResultCodeEnum.Frequent_Operation.getMessage());
    }

    public static Result handleBlockException3(HttpServletRequest request, Long interviewid, BlockException exception) {
        return Result.error(ResultCodeEnum.Frequent_Operation.getCode(), ResultCodeEnum.Frequent_Operation.getMessage());
    }

    public static Result handleBlockException4(HttpServletRequest request, String interviewCommentsId, BlockException exception) {
        return Result.error(ResultCodeEnum.Frequent_Operation.getCode(), ResultCodeEnum.Frequent_Operation.getMessage());
    }
}
