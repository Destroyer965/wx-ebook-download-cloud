package com.yangjiahai.ebookdownload.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yangjiahai.ebookdownload.entity.BookCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author yangjiahai
 */
public interface BookCategoryService extends IService<BookCategory> {


    /**
     * 获取图书分类列表
     *
     * @param pageNo   起始页
     * @param pageSize 每页查询数量
     * @return
     */
    Map<String, Object> selectBookCategoryList(Integer pageNo, Integer pageSize);

    /**
     * 获取图书分类列表
     *
     * @param iPage
     * @param queryWrapper
     * @return
     */
    List<BookCategory> getRecommendList(IPage<BookCategory> iPage, @Param(Constants.WRAPPER) Wrapper<BookCategory> queryWrapper);
}
