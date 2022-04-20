package com.yangjiahai.ebookdownload.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangjiahai.ebookdownload.entity.Interview;

import java.util.List;

/**
 * @author yangjiahai
 */
public interface InterviewMapper extends BaseMapper<Interview> {
    /**
     * 查询面试题分类
     * @return
     */
    List<String> queryInterviewCategory();
}
