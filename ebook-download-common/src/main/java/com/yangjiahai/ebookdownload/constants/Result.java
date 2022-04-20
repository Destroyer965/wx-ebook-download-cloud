package com.yangjiahai.ebookdownload.constants;//

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yangjiahai.ebookdownload.enums.ResultCodeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author yangjiahai
 * 统一返回结果
 */
@Data
@Accessors(chain = true)
public class Result<T> implements Serializable {
    private static final long serialVersionUID = -5399051578054163779L;
    private int code;
    private Boolean success;
    private String message;
    private T data;
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Timestamp currentTime = new Timestamp(System.currentTimeMillis());

    public static Result ok(){
        return getResult();
    }

    private static Result getResult() {
        Result result = new Result();
        result.setSuccess(ResultCodeEnum.SUCCESS.isSuccess());
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        result.setCurrentTime(new Timestamp(System.currentTimeMillis())) ;
        return result;
    }

    public static Result error(Integer code,String message){
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(code);
        result.setMessage(message);
        result.setCurrentTime(new Timestamp(System.currentTimeMillis()));
        return result;
    }


    public static Result setResult(ResultCodeEnum resultCodeEnum){
        return getResult();
    }
    public Result success(Boolean success) {
        this.success = success;
        return this;
    }
    public Result message(String message) {
        this.message = message;
        return this;
    }
    public Result code(Integer code) {
        this.code = code;
        return this;
    }
    public Result data(T data) {
        this.data = data;
        return this;
    }
}
