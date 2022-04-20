package com.yangjiahai.ebookdownload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangjiahai.ebookdownload.entity.User;


/**
 * @author yangjiahai
 */
public interface UserService extends IService<User> {
    /**
     * 根据用户id查询用户信息
     *
     * @param id
     * @return
     */
    User getUserInfoById(Long id);

    /**
     * 根据用户id查询用户信息
     *
     * @param id
     * @return
     */
    User getUserById(Long id);

    /**
     * 添加用户积分
     *
     * @param id
     * @return
     */
    int addIntegralById(Long id);

    /**
     * 判断用户是否第一次进入小程序
     *
     * @param id
     * @return
     */
    boolean isFirstInto(Long id);

    /**
     * 根据用户id修改用户读友圈背景图
     * @param id
     * @param imgUrl
     * @return
     */
    int updateUserBackGround(Long id, String imgUrl);

    /**
     * 修改面试题点赞数量
     * @param interviewid
     * @param num
     */
    void updateInterview(Long interviewid,Integer num);


}
