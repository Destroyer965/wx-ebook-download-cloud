package com.yangjiahai.ebookdownload.controller;

import com.yangjiahai.ebookdownload.VO.BookVO;
import com.yangjiahai.ebookdownload.annotation.RequiredToken;
import com.yangjiahai.ebookdownload.constants.Result;
import com.yangjiahai.ebookdownload.entity.Book;
import com.yangjiahai.ebookdownload.entity.User;
import com.yangjiahai.ebookdownload.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/2/23 20:03
 */
@RefreshScope
@Slf4j
@RestController
@Api("图书信息")
@RequestMapping("/book")
public class BookController {
    @Resource
    private BookService bookService;
    private Logger logger = LoggerFactory.getLogger(BookController.class);
    @Resource
    private RedisTemplate redisTemplate;

    @ApiOperation("根据图书类别id查询所有图书")
    @GetMapping("/category")
    public Result category(
            @RequestParam("pageNo") Integer pageNo,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("categoryId") Long categoryId) {
        Map<String, Object> map = bookService.selectBookByCateGoryId(pageNo, pageSize, categoryId);
        return Result.ok().setData(map);
    }

    @GetMapping("{id}")
    @ApiOperation("根据图书id查询图书信息")
    public Result getBookById(@PathVariable Long id) {
        Book book = bookService.selectBookById(id);
        logger.info("bookinfo", book);
        return Result.ok().data(book);
    }

    @GetMapping("/name")
    @ApiOperation("根据图书id查询图书名")
    public Result getBookNameById(@RequestParam Integer id) {
        String bookName = bookService.selectBookNameById(id);
        logger.info("bookName", bookName);
        return Result.ok().data(bookName);
    }

    @GetMapping("/list")
    @ApiOperation("查询图书信息")
    public Result bookList(@RequestParam("listid") String listid) {
        List arrayList = Arrays.asList(listid.split(","));
        logger.info("arrayList", arrayList);
        List<BookVO> bookVOS = bookService.selectBookList(arrayList);
        return Result.ok().data(bookVOS);
    }

    @ApiOperation("下载")
    @GetMapping("/download/{bookid}")
    public Result download(@PathVariable Long bookid) {
        int i = bookService.downloadBook(bookid);
        return Result.ok().data(i);
    }


    @ApiOperation("添加图书收藏数量")
    @PutMapping("/collection/add/{bookid}")
    public Result addCollection(@PathVariable Long bookid) {
        int i = bookService.collectionBook(bookid);
        return Result.ok().data(i);
    }

    @ApiOperation("减少图书收藏数量")
    @DeleteMapping("/collection/del/{bookid}")
    public Result delCollection(@PathVariable Long bookid) {
        int i = bookService.delCollectionBook(bookid);
        return Result.ok().data(i);
    }

    @ApiOperation("模糊查询图书")
    @GetMapping("/search/{name}")
    public Result search(@PathVariable String name) {
        logger.info(name);
        List<Book> books = bookService.fuzzyQuery(name);
        return Result.ok().data(books);
    }

    @RequiredToken
    @ApiOperation("根据用户id搜索记录")
    @GetMapping("/query/search")
    public Result querySearch(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        Long id = user.getId();
        List<Book> books = bookService.querySearchList(id);
        return Result.ok().data(books);
    }

    @RequiredToken
    @ApiOperation("添加搜索记录")
    @PostMapping("/search/{bookid}")
    public Result addSearch(HttpServletRequest request, @PathVariable Long bookid) {
        logger.info("id", bookid);
        User user = (User) request.getAttribute("user");
        Long id = user.getId();
 
        Double num = bookService.addSearchList(id, bookid);
        return Result.ok().data(num);
    }

    @ApiOperation("添加图书搜索次数")
    @PutMapping("/search/count/{bookid}")
    public Result addSearchCount(@PathVariable Long bookid) {
        logger.info(bookid.toString());
        //每次搜索搜索数量加1
        Double score = redisTemplate.opsForZSet().incrementScore("search_rank", bookid, 1);
        logger.info(score.toString());
        return Result.ok().message("添加成功").data(score);
    }

    @RequiredToken
    @ApiOperation("删除搜索记录")
    @DeleteMapping("/search")
    public Result delSearch(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        Long id = user.getId();
        boolean del = bookService.delSearchList(id);
        return Result.ok().data(del);
    }

    @ApiOperation("查询搜索排行榜")
    @GetMapping("/searchrank")
    public Result searchRank() {
        List<Map<String, Object>> list = new ArrayList<>(32);
        Set byScore = redisTemplate.opsForZSet().reverseRangeByScore("search_rank", 0, Long.MAX_VALUE, 0, -1);
        logger.info("byScore = " + byScore);
        byScore.forEach(item -> {
            logger.info("item = " + item);
            String bookName = bookService.selectBookNameById((Integer) item);

            Map<String, Object> map = new HashMap<>(32);
            map.put("book_id", item);
            map.put("book_name", bookName);
            map.put("search_count", redisTemplate.opsForZSet().score("search_rank", item).longValue());
            list.add(map);
        });
        List<Map<String, Object>> collect = list.stream()
                .limit(10)
                .collect(Collectors.toList());
        return Result.ok().data(collect);
    }

    @ApiOperation("条件搜索:id图书分类id,name:搜索关键字，sort：排序条件、true为升序，false为降序")
    @GetMapping("/conditions/{id}/{name}/{sort}")
    public Result conditions(
            @PathVariable Long id,
            @PathVariable String name,
            @PathVariable boolean sort) {
        List<Book> books = bookService.selectBookByCondition(id, name, sort);
        return Result.ok().data(books);
    }

}
