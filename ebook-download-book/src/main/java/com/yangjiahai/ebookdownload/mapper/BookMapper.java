package com.yangjiahai.ebookdownload.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangjiahai.ebookdownload.entity.Book;

import java.util.List;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/2/23 20:04
 */
public interface BookMapper extends BaseMapper<Book> {
    /**
     * 根据用户id查询用户下载历史
     * @param id
     * @return
     */
    List<Book> selectDownloadHistory(Long id);

    /**
     * 根据图书id查询图书名
     * @param id
     * @return
     */
    String selectBookNameByID(Integer id);
}
