package com.yangjiahai.ebookdownload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yangjiahai.ebookdownload.entity.User;
import com.yangjiahai.ebookdownload.mapper.UserMapper;
import com.yangjiahai.ebookdownload.service.UserService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/2/20 13:55
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;

    /**
     * 根据用户id获取用户信息
     * @param id
     * @return
     */
    @Override
    public User getUserInfoById(Long id) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("id",id);
        User user = userMapper.selectOne(qw);
        return user;
    }

    @Override
    public User getUserById(Long id) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("id",id);
        User user = userMapper.selectOne(qw);
        return user;
    }


    /**
     * 添加用户积分
     * @param id
     * @return
     */
    @Override
    public int addIntegralById(Long id) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("id",id);
        User user = userMapper.selectOne(qw);
        //获得用户上次修改时间
        LocalDateTime modifiedTime = user.getGmtModified();
        Date modifiedDate = Date.from(modifiedTime.atZone( ZoneId.systemDefault()).toInstant());
       //获得当前时间
        LocalDateTime nowTime = LocalDateTime.now();
        Date nowDate = Date.from(nowTime.atZone( ZoneId.systemDefault()).toInstant());
        //判断上次修改时间和当前时间是否是同一天，是同一天则不能再次签到
        if (DateUtils.isSameDay(modifiedDate,nowDate)) {
            return 0;
        }
        user.setGmtModified(LocalDateTime.now())
                        .setPoints(user.getPoints() + 10);
        int update = userMapper.update(user, qw);
        return update;
    }

    /**
     * 判断用户是否第一次登录
     * @param id
     * @return
     */
    @Override
    public boolean isFirstInto(Long id) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("id",id);
        User user = userMapper.selectOne(qw);
        //获得用户上次修改时间
        LocalDateTime modifiedTime = user.getGmtModified();
        Date modifiedDate = Date.from(modifiedTime.atZone( ZoneId.systemDefault()).toInstant());
        //获得当前时间
        LocalDateTime nowTime = LocalDateTime.now();
        Date nowDate = Date.from(nowTime.atZone( ZoneId.systemDefault()).toInstant());
        //当前时间和上次登录时间相同则表示不是第一次登录
        if (DateUtils.isSameDay(modifiedDate,nowDate)) {
            return false;
        }
        return true;
    }

    /**
     * 根据用户id修改用户读友圈背景图
     * @param id
     * @param imgUrl
     * @return
     */
    @Override
    public int updateUserBackGround(Long id, String imgUrl) {
        User user = new User();
        user.setBackgroundImg(imgUrl);
        int i = userMapper.update(user,new QueryWrapper<User>().eq("id",id));
        return i;
    }

    /**
     * 修改面试题点赞数量
     * @param interviewid
     * @param num
     */
    @Override
    public void updateInterview(Long interviewid,Integer num) {
        userMapper.updateInterview(interviewid,num);
    }

}
