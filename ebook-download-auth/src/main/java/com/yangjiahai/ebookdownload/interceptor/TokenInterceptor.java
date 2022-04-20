package com.yangjiahai.ebookdownload.interceptor;

import com.yangjiahai.ebookdownload.annotation.RequiredToken;


import com.yangjiahai.ebookdownload.entity.User;
import com.yangjiahai.ebookdownload.enums.ResultCodeEnum;
import com.yangjiahai.ebookdownload.exception.EbookDownloadException;
import com.yangjiahai.ebookdownload.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/2/20 11:58
 */
@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(HandlerInterceptor.class);
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("进入拦截器");
        HandlerMethod myHandlerMethod = (HandlerMethod) handler;
        // 判断请求方法上是否有@RequiredToken注解
        if (myHandlerMethod.getMethod().isAnnotationPresent(RequiredToken.class)) {
            //获取请求头中的token
            String token = request.getHeader("Authorization");
            logger.info(token);
            if(StringUtils.isEmpty(token)){
                throw new EbookDownloadException(ResultCodeEnum.UN_LOGIN.getCode(),ResultCodeEnum.UN_LOGIN.getMessage());
            }
            //解析token
            Claims claims = JWTUtils.getTokenBody(token);
            logger.info(claims.toString());
            if(JWTUtils.isExpiration(token)){
                throw new RuntimeException("token已过期，请重新登录");
            }
            String username = (String) claims.get("username");
            if(!JWTUtils.validateToken(token,username)){
                throw new RuntimeException("token无效");
            }
            //从解析中的token获取用户id
            Object id = claims.get("id");
            //根据token到redis中获取用户信息
            String hashKey = "userinfo" + id;
            User user = (User) redisTemplate.opsForHash().get(hashKey, "user");
            if (ObjectUtils.isEmpty(user)) {
                throw new EbookDownloadException(ResultCodeEnum.USER_INEXISTENCE.getCode(),ResultCodeEnum.USER_INEXISTENCE.getMessage());
            }
            System.out.println(user);
            //将当前token的用户信息放入请求上下文
            request.setAttribute("user", user);
        }
        return true;
    }
}
