package com.yangjiahai.ebookdownload.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.yangjiahai.ebookdownload.constants.Result;
import com.yangjiahai.ebookdownload.enums.ResultCodeEnum;

/**
 * @author yangjiahai
 * @description: TODO
 * @date 2022/4/2012:50
 */
public class CustomHandler {
    public static Result handleBlockException(BlockException e) {
        return Result.error(ResultCodeEnum.Frequent_Operation.getCode(),ResultCodeEnum.Frequent_Operation.getMessage());
    }
}
