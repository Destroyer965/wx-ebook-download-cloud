package com.yangjiahai.ebookdownload.exception;

import com.yangjiahai.ebookdownload.constants.Result;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/2/17 16:11
 * 全局异常处理
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = EbookDownloadException.class)
    @ResponseBody
    public Result ebookDownloadExceptionHandler(EbookDownloadException e) {
        e.printStackTrace();
        logger.info("reason:", e);
        return Result.error(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result<String> exceptionHandler(Exception e) {
        e.printStackTrace();
        return Result.error(500,e.getMessage());
    }
}
