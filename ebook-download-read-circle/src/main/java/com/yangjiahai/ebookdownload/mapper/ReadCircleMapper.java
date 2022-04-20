package com.yangjiahai.ebookdownload.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangjiahai.ebookdownload.entity.CircleFriends;
import com.yangjiahai.ebookdownload.entity.User;

/**
 * @author yangjiahai
 */
public interface ReadCircleMapper extends BaseMapper<CircleFriends> {
    /**
     * 根据读友圈id查询用户点赞列表
     * @param id
     * @return
     */
    User selectListById(Integer id);
}
