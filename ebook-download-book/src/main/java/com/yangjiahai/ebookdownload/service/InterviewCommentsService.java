package com.yangjiahai.ebookdownload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangjiahai.ebookdownload.VO.CommentsVO;
import com.yangjiahai.ebookdownload.VO.InterviewCommentsVO;
import com.yangjiahai.ebookdownload.entity.BookComments;
import com.yangjiahai.ebookdownload.entity.Interview;
import com.yangjiahai.ebookdownload.entity.InterviewComments;

import java.util.Map;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/2/28 13:40
 */
public interface InterviewCommentsService extends IService<InterviewComments> {

    /**
     * 根据面试题id分页查询面试题评论
     * @param interviewid
     * @param pageNo
     * @param pageSize
     * @return
     */
    Map<String, Object> selectInterviewComments(Long interviewid, Integer pageNo, Integer pageSize);

    /**
     * 根据面试题id查询面试题评论
     * @param commentid
     * @return
     */
    Map<String, Object> selectInterviewCommentsById(Long commentid);

    /**
     * 添加评论
     * @param id
     * @param comments
     * @return
     */
    InterviewCommentsVO insertInterviewComments(Long id,InterviewComments comments);
}
