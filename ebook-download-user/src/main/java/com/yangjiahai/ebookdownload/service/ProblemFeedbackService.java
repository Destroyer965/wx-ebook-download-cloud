package com.yangjiahai.ebookdownload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangjiahai.ebookdownload.entity.ProblemFeedback;


/**
 * @author yangjiahai
 */
public interface ProblemFeedbackService extends IService<ProblemFeedback> {
    /**
     * 添加问题反馈
     * @param id
     * @param problemFeedback
     * @return
     */
    int addProblem(Long id, ProblemFeedback problemFeedback);
}
