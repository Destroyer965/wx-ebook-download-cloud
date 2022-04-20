package com.yangjiahai.ebookdownload.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangjiahai.ebookdownload.entity.User;
import org.apache.ibatis.annotations.Param;


/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/2/20 13:56
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * 修改面试题点赞数量
     * @param interviewid
     * @param num
     */
    void updateInterview(@Param("interviewid") Long interviewid, @Param("num") Integer num);

}
