package com.yangjiahai.ebookdownload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangjiahai.ebookdownload.entity.CollectionRecords;

import java.util.Map;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/2/20 13:56
 */
public interface CollectionRecordsService extends IService<CollectionRecords> {
    /**
     * 根据用户id查询用户收藏列表
     *
     * @param pageNo
     * @param pageSize
     * @param userid
     * @return
     */
    Map<String, Object> selectCollectionsById(Integer pageNo,Integer pageSize,Long userid);


    /**
     * 根据用户id和图书id查询该用户是否收藏该图书
     *
     * @param userId
     * @param bookId
     * @return
     */
    CollectionRecords selectCollection(Long userId, Long bookId);

    /**
     * 添加用户收藏列表
     *
     * @param userId
     * @param bookId
     * @return
     */
    int addCollectionById(Long userId, Long bookId);

    /**
     * 根据用户id和图书id删除
     *
     * @param userId
     * @param bookId
     * @return
     */
    int delCollectionById(Long userId, Long bookId);

    /**
     * 根据用户id和面试题id查询该用户是否收藏该面试题
     * @param userId
     * @param interviewid
     * @return
     */
    CollectionRecords selectInterviewCollection(Long userId, Long interviewid);

    /**
     * 添加用户收藏面试题列表
     *
     * @param userId
     * @param interviewid
     * @return
     */
    int addInterviewCollectionById(Long userId, Long interviewid);

    /**
     * 根据用户id和面试题id删除收藏记录
     *
     * @param userId
     * @param interviewid
     * @return
     */
    int delInterviewCollectionById(Long userId, Long interviewid);

}
