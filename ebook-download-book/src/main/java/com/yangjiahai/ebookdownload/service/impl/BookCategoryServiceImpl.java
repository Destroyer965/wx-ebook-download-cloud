package com.yangjiahai.ebookdownload.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangjiahai.ebookdownload.entity.BookCategory;
import com.yangjiahai.ebookdownload.mapper.BookCategoryMapper;
import com.yangjiahai.ebookdownload.service.BookCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/2/20 17:48
 */
@Service
public class BookCategoryServiceImpl extends ServiceImpl<BookCategoryMapper, BookCategory> implements BookCategoryService {
    @Resource
    private BookCategoryMapper bookCategoryMapper;


    /**
     * 获取图书分类列表
     *
     * @param pageNo   起始页
     * @param pageSize 每页查询数量
     * @return
     */
    @Override
    public Map<String, Object> selectBookCategoryList(Integer pageNo, Integer pageSize) {
        Page<BookCategory> page = new Page<>(pageNo, pageSize);
        Page<BookCategory> categoryPage = bookCategoryMapper.selectPage(page, null);
        List<BookCategory> records = categoryPage.getRecords()
                .stream().
                sorted(Comparator.comparing(BookCategory::getCategoryName))
                .collect(Collectors.toList());

        long pages = categoryPage.getPages();

        HashMap<String, Object> map = new HashMap<>(16);
        map.put("records", records);
        map.put("pages", pages);
        return map;
    }

    /**
     * 获取图书分类列表
     *
     * @param iPage
     * @param queryWrapper
     * @return
     */
    @Override
    public List<BookCategory> getRecommendList(IPage<BookCategory> iPage, Wrapper<BookCategory> queryWrapper) {
        List<BookCategory> list = bookCategoryMapper.selectGetRecommendList(iPage, queryWrapper);
        return list;
    }


}