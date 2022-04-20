package com.yangjiahai.ebookdownload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangjiahai.ebookdownload.VO.BookVO;
import com.yangjiahai.ebookdownload.entity.Book;
import com.yangjiahai.ebookdownload.mapper.BookMapper;
import com.yangjiahai.ebookdownload.service.BookService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/2/23 20:05
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {
    @Resource
    private BookMapper bookMapper;
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public Map<String, Object> selectBookByCateGoryId(
            Integer pageNo, Integer pageSize,
            Long categoryId) {
        QueryWrapper<Book> qw = new QueryWrapper<>();
        qw.eq("category_id", categoryId);
        Page<Book> page = bookMapper.selectPage(new Page<>(pageNo, pageSize), qw);
        List<Book> bookList = page.getRecords();
        Map<String, Object> map = new HashMap<>(32);
        map.put("total", page.getTotal());
        map.put("data", bookList);
        return map;
    }

    /**
     * 根据图书id查询图书信息
     * @param id
     * @return
     */
    @Override
    public Book selectBookById(Long id) {
        Book book = bookMapper.selectById(id);
        return book;
    }

    /**
     * 用户下载图书，图书下载数量增加
     *
     * @param bookid
     * @return
     */
    @Override
    public int downloadBook(Long bookid) {
        Book book = bookMapper.selectById(bookid);
        Long count = book.getDownloadCount();
        book.setDownloadCount(++count);
        int update = bookMapper.update(book, new QueryWrapper<Book>().eq("id", bookid));

        return update;
    }


    /**
     * 用户收藏图书，添加图书收藏数量
     *
     * @param bookid
     * @return
     */
    @Override
    public int collectionBook(Long bookid) {
        Book book = bookMapper.selectById(bookid);
        Long count = book.getCollectionCount();
        book.setCollectionCount(++count);
        int update = bookMapper.update(book, new QueryWrapper<Book>().eq("id", bookid));
        return update;
    }

    /**
     * 用户取消收藏图书，减少图书收藏数量
     *
     * @param bookid
     * @return
     */
    @Override
    public int delCollectionBook(Long bookid) {
        Book book = bookMapper.selectById(bookid);
        Long count = book.getCollectionCount();
        book.setCollectionCount(--count);
        int update = bookMapper.update(book, new QueryWrapper<Book>().eq("id", bookid));
        return update;
    }

    /**
     * 模糊查询图书
     *
     * @param name
     * @return
     */
    @Override
    public List<Book> fuzzyQuery(String name) {
        QueryWrapper qw = new QueryWrapper();
        qw.like("book_name", name);
        List<Book> list = bookMapper.selectList(qw);
        return list;
    }

    /**
     * 添加搜索记录
     *
     * @param id
     * @param bookid
     * @return
     */
    @Override
    public Double addSearchList(Long id, Long bookid) {
        redisTemplate.opsForSet().add("search_history" + id, bookid);

        Double add = redisTemplate.opsForZSet().score("search_rank", bookid);
        return add;
    }

    /**
     * 删除搜索记录
     *
     * @param id
     * @return
     */
    @Override
    public boolean delSearchList(Long id) {
        boolean delete = redisTemplate.delete("search_history" + id);
        return delete;
    }

    /**
     * 根据用户id查询用户搜索历史
     *
     * @param id
     * @return
     */
    @Override
    public List<Book> querySearchList(Long id) {
        Set keys = redisTemplate.opsForSet().members("search_history" + id);
        List<Book> list = new ArrayList<>();
        keys.forEach(item -> {
            list.add(bookMapper.selectOne(new QueryWrapper<Book>().eq("id", item)));
        });
        return list;
    }

    /**
     * 条件筛选
     * name:搜索关键字，sort：排序条件、true为升序，false为降序
     *
     * @return
     */
    @Override
    public List<Book> selectBookByCondition(Long id, String name, boolean sort) {
        QueryWrapper<Book> wrapper = new QueryWrapper<>();
        wrapper.eq("category_id", id);
        if (sort) {
            wrapper.orderByAsc(name);
        } else {
            wrapper.orderByDesc(name);
        }
        List<Book> books = bookMapper.selectList(wrapper);
        return books;
    }

    /**
     * 查询图书列表
     *
     * @param list
     */
    @Override
    public List<BookVO> selectBookList(List<String> list) {
        List<Book> books = new ArrayList<>();
        list.forEach(item -> {
            Book book = bookMapper.selectOne(new QueryWrapper<Book>().eq("id", item));
            books.add(book);
        });

        List<BookVO> bookVOS = new ArrayList<>();
        books.forEach(book -> {
            Double score = redisTemplate.opsForZSet().score("search_rank", book.getId());
            BookVO bookVO = new BookVO();
            bookVO.setId(book.getId())
                    .setBookname(book.getBookName())
                    .setSearchCount(score)
                    .setDownloadCount(book.getDownloadCount());
            bookVOS.add(bookVO);
        });

        return bookVOS;
    }

    /**
     * 根据图书id查询图书名
     * @param id
     * @return
     */
    @Override
    public String selectBookNameById(Integer id) {
        String bookName = bookMapper.selectBookNameByID(id);
        return bookName;
    }


}
