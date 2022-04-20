package com.yangjiahai.ebookdownload;

import com.yangjiahai.ebookdownload.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/2/19 10:20
 */

public class CommonTest {
    private String token = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZXhwIjoxNjQ3Mzk1NDEyLCJ1c2VybmFtZSI6Ilx1RDgzQ1x1REYzQiJ9.LycTRH42-DxIGLT_TCDbCpnVsS3JI2hh8XoSAsic7Js";

    /**
     * 创建token测试
     */
    @Test
    public void testCreateToken() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1);
        map.put("username", "zhangsan");
        String token = JWTUtils.createToken(map);
        System.out.println(token);
    }

    /**
     * 验证token
     */
    @Test
    public void testValidateToken() {
        boolean validateToken = JWTUtils.validateToken(token, "🌻");
        System.out.println(validateToken);
    }

    /**
     * 测试token是否过期
     */
    @Test
    public void testTokenIsExpiration() {
        System.out.println(JWTUtils.isExpiration(token));
    }

    /**
     * 测试从token中获取用户名
     */
    @Test
    public void testGetUserNameFromToken() {
        System.out.println(JWTUtils.getUserNameFromToken(token));

    }

    @Test
    public void testGetTokenBody() {
        Claims tokenBody = JWTUtils.getTokenBody(token);
        System.out.println(tokenBody);
    }

}
