package com.yangjiahai.ebookdownload;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yangjiahai.ebookdownload.entity.BookCategory;
import com.yangjiahai.ebookdownload.mapper.BookCategoryMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/2/20 18:21
 */
@SpringBootTest
public class BookApplicationTest {
    @Resource
    private BookCategoryMapper bookCategoryMapper;
    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 分页查询测试
     */
    @Test
    public void pageTest() {
        Page<BookCategory> bookCategoryPage = bookCategoryMapper.selectPage(new Page<>(1, 8), new QueryWrapper<>());
        List<BookCategory> records = bookCategoryPage.getRecords();
        System.out.println(records);
    }

    /**
     * zset测试
     */
    @Test
    public void zSetTest() {
        redisTemplate.opsForZSet().add("rank", 1, 10);
        redisTemplate.opsForZSet().add("rank", 2, 20);
        redisTemplate.opsForZSet().add("rank", 3, 30);
        redisTemplate.opsForZSet().add("rank", 4, 40);
        redisTemplate.opsForZSet().add("rank", 7, 40);
        redisTemplate.opsForZSet().add("rank", 8, 30);
        redisTemplate.opsForZSet().add("rank", 9, 10);

        redisTemplate.opsForZSet().incrementScore("rank", 1, 10);
    }

    @Test
    public void zSetTest2() {
        Set search_rank1 = redisTemplate.opsForZSet().reverseRange("search_rank", 0, 1);
        System.out.println("keys = " + search_rank1);
//        Set rank = redisTemplate.opsForZSet().reverseRange("search_rank", 0, -1);
        Set rank = redisTemplate.opsForZSet().reverseRangeByScore("search_rank", 0, Long.MAX_VALUE, 0, -1);
        System.out.println("rank = " + rank);
        rank.forEach(item ->{
            System.out.println(redisTemplate.opsForZSet().score("search_rank", item));
        });


    }
}
