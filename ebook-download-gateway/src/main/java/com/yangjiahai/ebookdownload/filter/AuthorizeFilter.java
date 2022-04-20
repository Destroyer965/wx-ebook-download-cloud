package com.yangjiahai.ebookdownload.filter;

import com.alibaba.fastjson.JSON;
import com.yangjiahai.ebookdownload.constants.Result;
import com.yangjiahai.ebookdownload.enums.ResultCodeEnum;
import com.yangjiahai.ebookdownload.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/2/19 12:17
 * 认证过滤器
 */
@Slf4j
@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {
    private static final String AUTHORIZE_TOKEN = "Authorization";
    private static Boolean flag = false;

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获取请求
        ServerHttpRequest request = exchange.getRequest();
        //获取响应
        ServerHttpResponse response = exchange.getResponse();
        String path = request.getURI().getPath();
        AntPathMatcher matcher = new AntPathMatcher();

        //放行的请求

        List<String> paths = new ArrayList<>();
        paths.add("/auth/login");
        paths.add("/system/**");
        paths.add("/book/**");
        paths.add("/interview/**");
        paths.add("/readcircle/selectpage");
        paths.add("/readcircle/liked/users/**");
        paths.add("/readcircle/comment/**");
        paths.add("/readcircleimg/select");
        paths.forEach(route -> {
            if (matcher.match(route, path)) {
                flag = true;
            }
        });
        if (flag) {
            return chain.filter(exchange);
        }
        //获取请求头
        HttpHeaders headers = request.getHeaders();
        //请求头中获取令牌
        String token = headers.getFirst(AUTHORIZE_TOKEN);
        // 判断请求头中是否有令牌
        if (StringUtils.isEmpty(token)) {
            log.info("令牌为空");
            return authError(response, ResultCodeEnum.UN_LOGIN.getCode(), "未登录");
        }
        //如果请求头中有令牌则解析令牌
        try {
            Claims claims = JWTUtils.getTokenBody(token);
            JWTUtils.validateToken(token, claims.getSubject());
        } catch (ExpiredJwtException e) {

            log.info(e.getMessage());
            return authError(response, ResultCodeEnum.TOKEN_EXPIRED.getCode(), "您的认证权限已过期，请重新进行认证");
        } catch (Exception e) {
            log.info(e.getMessage());
            return authError(response, ResultCodeEnum.USER_INEXISTENCE.getCode(), "认证失败");
        }
        // 放行
        return chain.filter(exchange);
    }

    /**
     * 定义当前过滤器的优先级，值越小优先级越高
     */
    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * 认证错误输出
     *
     * @param response 响应对象
     * @param message  错误信息
     * @return 错误结果
     */
    private Mono<Void> authError(ServerHttpResponse response, Integer code, String message) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        Result result = new Result();
        result.setCode(code)
                .setMessage(message);
        String returnStr = "";
        try {
            returnStr = JSON.toJSONString(result);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        DataBuffer buffer = response.bufferFactory().wrap(returnStr.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Flux.just(buffer));
    }
}