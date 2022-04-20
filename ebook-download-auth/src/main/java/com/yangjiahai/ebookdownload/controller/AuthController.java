package com.yangjiahai.ebookdownload.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yangjiahai.ebookdownload.annotation.RequiredToken;
import com.yangjiahai.ebookdownload.constants.Result;

import com.yangjiahai.ebookdownload.entity.User;
import com.yangjiahai.ebookdownload.utils.JWTUtils;
import com.yangjiahai.ebookdownload.utils.WechatUtil;
import com.yangjiahai.ebookdownload.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yangjiahai
 * @since 2022-02-18
 */
@Slf4j
@Api("用户认证")
@RestController
@RefreshScope
@RequestMapping("/auth")
public class AuthController {
    @Resource
    private AuthService authService;
    @Resource
    private RedisTemplate redisTemplate;
    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public Result login(@RequestParam(value = "code") String code,
                        @RequestParam(value = "rawData") String rawData
    ) {
        JSONObject rawDataJson = JSON.parseObject(rawData);
        // .接收小程序发送的code
        // 开发者服务器 登录凭证校验接口 appi + appsecret + code
        JSONObject sessionKeyOpenId = WechatUtil.getSessionKeyOrOpenId(code);
        // 接收微信接口服务 获取返回的参数
        String openid = sessionKeyOpenId.getString("openid");
        String sessionKey = sessionKeyOpenId.getString("session_key");
//        根据返回的User实体类，判断用户是否是新用户，是的话，将用户信息存到数据库；不是的话，更新最新登录时间
        User user = authService.selectUserByOpenId(openid);

        if (ObjectUtils.isEmpty(user)) {
            String nickname = rawDataJson.getString("nickName");
            String avatarUrl = rawDataJson.getString("avatarUrl");
            user = new User();
            user.setOpenId(openid);
            user.setGmtCreat(LocalDateTime.now());
            user.setGmtModified(LocalDateTime.now());
            user.setAvatar(avatarUrl);
            user.setUsername(nickname);
            authService.addUser(user);
        } else {
            // 已存在，更新用户登录时间
            user.setGmtModified(LocalDateTime.now());
            authService.updateById(user);
        }
        // 存储在token payload中的信息
        Map<String, Object> payload = new HashMap<>(16);
        payload.put("id", user.getId());
        payload.put("username", user.getUsername());
        // 生成token
        String token = JWTUtils.createToken(payload);
        logger.info("token:" + token);
        String hashKey = "userinfo" + user.getId();
        if (!redisTemplate.hasKey(hashKey)) {
            log.info("redis中无用户信息");
            redisTemplate.opsForHash().put(hashKey, "token", token);
            redisTemplate.opsForHash().put(hashKey, "user", user);
        }
        logger.info("user", user);
        return Result.ok().setData(user).setMessage("登录成功").setData(token);
    }


    @RequiredToken
    @ApiOperation("退出登录")
    @DeleteMapping("/logout")
    public Result logout(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        Long id = user.getId();
        //删除redis中存储的用户信息
        redisTemplate.delete("userinfo" + id);
        return Result.ok().setMessage("退出成功");
    }

    @RequiredToken
    @ApiOperation("获取用户信息")
    @GetMapping("/getUserInfo")
    public Result getUserInfo(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        System.out.println(user);
        return Result.ok().setData(user);
    }

}
