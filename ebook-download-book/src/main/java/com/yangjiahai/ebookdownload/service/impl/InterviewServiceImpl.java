package com.yangjiahai.ebookdownload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangjiahai.ebookdownload.entity.Interview;
import com.yangjiahai.ebookdownload.entity.InterviewCategory;
import com.yangjiahai.ebookdownload.mapper.InterviewMapper;
import com.yangjiahai.ebookdownload.service.InterviewService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/2/23 20:05
 */
@Service
public class InterviewServiceImpl extends ServiceImpl<InterviewMapper, Interview> implements InterviewService {
    @Resource
    private InterviewMapper interviewMapper;

    @Resource
    private RedisTemplate redisTemplate;
    /**
     * 分页查询所有面试题
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Map<String, Object> getAllInterview(Integer pageNo, Integer pageSize) {
        Page<Interview> page = interviewMapper.selectPage(new Page<Interview>(pageNo, pageSize),
                new QueryWrapper<Interview>().orderByDesc("gmt_creat"));
        List<Interview> records = page.getRecords();
        long total = page.getTotal();
        HashMap<String, Object> map = new HashMap<>(16);
        map.put("total", total);
        map.put("data", records);
        return map;
    }

    /**
     * 查询面试题分类
     *
     * @return
     */
    @Override
    public List<String> getInterviewCategory() {
        List<String> list = interviewMapper.queryInterviewCategory();
        return list;
    }

    /**
     * 按条件分页查询面试题
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Map<String, Object> getInterviewByCategory(Long categoryId,Integer pageNo, Integer pageSize) {
        Page<Interview> page = interviewMapper.selectPage(new Page<Interview>(pageNo, pageSize),
                new QueryWrapper<Interview>().eq("category_id",categoryId).orderByDesc("gmt_creat"));
        List<Interview> records = page.getRecords();
        records.forEach(record ->{
            Integer num = (Integer) redisTemplate.opsForValue().get("interview_liked_count" + record.getId());
            record.setLikeCount(num);
        });
        long total = page.getTotal();
        HashMap<String, Object> map = new HashMap<>(16);
        map.put("total", total);
        map.put("data", records);
        return map;
    }
    /**
     * 根据id查询面试题
     * @param id
     */
    @Override
    public Interview selectInterviewById(Long id) {
        Interview interview = interviewMapper.selectOne(new QueryWrapper<Interview>().eq("id", id));
        return interview;
    }
}
