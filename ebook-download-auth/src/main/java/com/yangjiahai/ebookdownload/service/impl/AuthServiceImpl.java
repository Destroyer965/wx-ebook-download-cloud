package com.yangjiahai.ebookdownload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yangjiahai.ebookdownload.entity.User;
import com.yangjiahai.ebookdownload.mapper.AuthMapper;
import com.yangjiahai.ebookdownload.service.AuthService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yangjiahai
 * @since 2022-02-18
 */
@Service
public class AuthServiceImpl extends ServiceImpl<AuthMapper, User> implements AuthService {
    @Resource
    private AuthMapper authMapper;

    /**
     * 根据微信用户openid查询用户信息
     *
     * @param openId
     * @return
     */
    @Override
    public User selectUserByOpenId(String openId) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("open_id",openId);
        return authMapper.selectOne(qw);
    }

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    @Override
    public int addUser(User user) {
        return authMapper.insert(user);
    }


}
