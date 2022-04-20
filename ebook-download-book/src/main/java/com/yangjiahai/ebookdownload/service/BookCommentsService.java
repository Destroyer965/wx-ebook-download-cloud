package com.yangjiahai.ebookdownload.service;

import com.yangjiahai.ebookdownload.VO.CommentsVO;
import com.yangjiahai.ebookdownload.entity.BookComments;

import java.util.Map;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/2/28 13:40
 */
public interface BookCommentsService {
    /**
     * 根据图书id查询图书评论列表
     *
     * @param bookid
     * @param pageNo
     * @param pageSize
     * @return
     */
    Map<String, Object> selectBookCommentsByBookId(Long bookid, Long pageNo, Long pageSize);

    /**
     *  根据图书id添加图书评论
     * @param userid
     * @param bookid
     * @param content
     * @return
     */
    CommentsVO addComment(Long userid, Long bookid, String content);
    /**
     * 根据评论id获取评论信息
     * @param id
     * @return
     */
    BookComments getCommentById(String id);

    /**
     * 回复评论
     * @param userid
     * @param bookid
     * @param content
     * @param parentid
     * @return
     */
    CommentsVO replayComment(Long userid, Long bookid, String content, String parentid);

    /**
     * 根据评论id获取评论点赞数
     * @param id
     * @return
     */
    Integer selectCommentsLikeById(String id);
}
