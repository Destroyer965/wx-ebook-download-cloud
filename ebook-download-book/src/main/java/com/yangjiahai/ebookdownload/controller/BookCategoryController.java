package com.yangjiahai.ebookdownload.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yangjiahai.ebookdownload.constants.Result;
import com.yangjiahai.ebookdownload.entity.BookCategory;
import com.yangjiahai.ebookdownload.service.BookCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/2/20 17:47
 */
@RefreshScope
@Slf4j
@Api("图书分类信息")
@RestController
@RequestMapping("/book")
public class BookCategoryController {
    @Resource
    private BookCategoryService bookService;
    Logger logger = LoggerFactory.getLogger(BookCategoryController.class);
    @ApiOperation("获取图书分类列表")
    @GetMapping("/categoryList")
    public Result getCategoryList(@ApiParam(required = true) @RequestParam("pageNo") Integer pageNo, @ApiParam(required = true) @RequestParam("pageSize") Integer pageSize) {
        Map<String, Object> list = bookService.selectBookCategoryList(pageNo, pageSize);
        return Result.ok().setData(list);
    }
    @ApiOperation("获取图书推荐列表")
        @GetMapping("/recommendedList")
    public Result getRecommendList(@RequestParam("pageSize") Integer pageSize,
                                   @RequestParam("pageNo") Integer pageNo) throws InterruptedException {
        Page<BookCategory> page = new Page<>(pageNo,pageSize*4);
        List<BookCategory> recommendList = bookService.getRecommendList(page,new QueryWrapper<>());
        long total = page.getTotal();
        System.out.println("total = " + total);
        HashMap<String, Object> map = new HashMap<>(32);
        map.put("data",recommendList);
        map.put("total",total);
        return Result.ok().setData(map);
    }
}
