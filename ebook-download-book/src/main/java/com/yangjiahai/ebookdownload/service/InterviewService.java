package com.yangjiahai.ebookdownload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangjiahai.ebookdownload.entity.Interview;

import java.util.List;
import java.util.Map;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/2/23 20:04
 */
public interface InterviewService extends IService<Interview> {

    /**
     * 分页查询所有面试题
     * @param pageNo
     * @param pageSize
     * @return
     */
   Map<String,Object> getAllInterview(Integer pageNo, Integer pageSize);


    /**
     * 查询面试题分类
     * @return
     */
    List<String> getInterviewCategory();

    /**
     * 分页分类查询所有面试题
     * @param category
     * @param pageNo
     * @param pageSize
     * @return
     */
    Map<String, Object> getInterviewByCategory(Long category,Integer pageNo, Integer pageSize);

    /**
     * 根据id查询面试题
     * @param id
     */
    Interview selectInterviewById(Long id);
}
