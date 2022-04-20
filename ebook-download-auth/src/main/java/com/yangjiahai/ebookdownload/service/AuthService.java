package com.yangjiahai.ebookdownload.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yangjiahai.ebookdownload.entity.User;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yangjiahai
 * @since 2022-02-18
 */
public interface AuthService extends IService<User> {


    /**
     * 根据用户微信openid查询用户信息
     *
     * @param openId
     * @return
     */
    User selectUserByOpenId(String openId);

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    int addUser(User user);
}
