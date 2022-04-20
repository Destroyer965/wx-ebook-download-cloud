package com.yangjiahai.ebookdownload.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangjiahai.ebookdownload.entity.ProblemFeedback;
import com.yangjiahai.ebookdownload.mapper.ProblemFeedbackMapper;
import com.yangjiahai.ebookdownload.service.ProblemFeedbackService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/3/13 16:07
 */
@Service
public class ProblemFeedbackServiceImpl extends ServiceImpl<ProblemFeedbackMapper, ProblemFeedback> implements ProblemFeedbackService {
    @Resource
    private ProblemFeedbackMapper problemFeedbackMapper;
    @Override
    public int addProblem(Long id, ProblemFeedback problemFeedback) {
        ProblemFeedback feedback = new ProblemFeedback();
        feedback.setUserid(id)
                .setFeedbackImg(problemFeedback.getFeedbackImg())
                .setProblemDec(problemFeedback.getProblemDec())
                .setContactInfo(problemFeedback.getContactInfo())
                .setGmtCreat(LocalDateTime.now())
                .setGmtModified(LocalDateTime.now());
        int insert = problemFeedbackMapper.insert(feedback);
        return insert;
    }
}
