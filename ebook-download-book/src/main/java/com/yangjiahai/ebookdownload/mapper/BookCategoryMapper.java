package com.yangjiahai.ebookdownload.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yangjiahai.ebookdownload.entity.BookCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yangjiahai
 */

public interface BookCategoryMapper extends BaseMapper<BookCategory> {
    /**
     * 获取图书分类列表
     * @param iPage
     * @param queryWrapper
     * @return
     */
    List<BookCategory> selectGetRecommendList(IPage<BookCategory> iPage, @Param(Constants.WRAPPER) Wrapper<BookCategory> queryWrapper);
}
