package com.yangjiahai.ebookdownload.utils;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/2/19 09:57
 * jwt工具类
 */
@Component
public class JWTUtils {
    private static String sign = "ebookdownload";
    /**
     * 生成token
     * @param map
     * @return
     */
    public static String createToken(Map<String, Object> map) {
        JwtBuilder builder = Jwts.builder();
        builder.setClaims(map);
        builder.setExpiration(new Date(System.currentTimeMillis()  + 1000 * 60 * 60 * 24 * 15));
        return builder.signWith(SignatureAlgorithm.HS256,sign).compact();
    }
    /**
     * 获取token中的信息
     *
     * @param token
     * @return
     */
    public static Claims getTokenBody(String token) {
        return Jwts.parser()
                .setSigningKey(sign)
                .parseClaimsJws(token)
                .getBody();
    }
    /**
     * 从token中获取用户名
     *
     * @param token
     * @return
     */
    public static String getUserNameFromToken(String token) {

        return (String) getTokenBody(token).get("username");
    }


    /**
     * 是否已过期
     * @param token
     * @return
     */
    public static boolean isExpiration(String token) {
        return getTokenBody(token).getExpiration().before(new Date());
    }

    /**
     * 验证token是否有效
     *
     * @param token
     * @param
     * @return
     */
    public static boolean validateToken(String token, String username) {
        String name = getUserNameFromToken(token);
        return name.equals(username) && !isExpiration(token);
    }

}
