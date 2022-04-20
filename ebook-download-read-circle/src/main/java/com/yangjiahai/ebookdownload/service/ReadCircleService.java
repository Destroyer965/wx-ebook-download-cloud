package com.yangjiahai.ebookdownload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangjiahai.ebookdownload.VO.CommentsVO;
import com.yangjiahai.ebookdownload.VO.ReportVO;
import com.yangjiahai.ebookdownload.entity.CircleFriends;
import com.yangjiahai.ebookdownload.VO.UserVO;

import java.util.List;
import java.util.Map;

/**
 * @author yangjiahai
 */
public interface ReadCircleService extends IService<CircleFriends> {
    /**
     * 添加读友圈
     * @param id
     * @param circleFriends
     * @param imgList
     * @return
     */
    int addReadCircle( Long id,String imgList, CircleFriends circleFriends);

    /**
     * 分页查询读友圈列表
     * @param pageNo
     * @param pageSize
     * @return
     */
    Map<String, Object> selectAllReadCircle(Long pageNo, Long pageSize);

    /**
     * 根据读友圈id查询点赞列表
     * @param circleid
     * @return
     */
    List<UserVO> selectLikedList(Integer circleid);

    /**
     * 根据读友圈id删除
     * @param circleid
     * @return
     */
    int deleteCircleById(Long circleid);

    /**
     * 根据读友圈id查询
     * @param circleid
     * @return
     */
    CircleFriends selectCircleById(Long circleid);

    /**
     * 对读友圈进行评论
     * @param id
     * @param circleid
     * @param content
     * @return
     */
    int addCommentByCircleId(Long id, Long circleid, String content);

    /**
     * 根据读友圈id查询评论列表
     * @param circleid
     * @return
     */
    List<CommentsVO> selectCommentsByCircleId(Long circleid);


}
