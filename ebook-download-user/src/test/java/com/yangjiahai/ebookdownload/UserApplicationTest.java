package com.yangjiahai.ebookdownload;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yangjiahai.ebookdownload.entity.CollectionRecords;
import com.yangjiahai.ebookdownload.entity.DownloadHistory;
import com.yangjiahai.ebookdownload.mapper.CollectionRecordsMapper;
import com.yangjiahai.ebookdownload.mapper.DownloadHistoryMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/2/27 17:05
 */
@SpringBootTest
public class UserApplicationTest {
    @Resource
    private CollectionRecordsMapper mapper;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private DownloadHistoryMapper downloadHistoryMapper;
    @Test
    public void pageTest() {
        Page<CollectionRecords> page = new Page<>(1,3);
        QueryWrapper<CollectionRecords> wrapper = new QueryWrapper<>();
        wrapper.eq("userid",1);
        IPage<CollectionRecords> iPage = mapper.selectCollection(page, wrapper);
        System.out.println("iPage.getTotal() = " + iPage.getTotal());
        List<CollectionRecords> records = iPage.getRecords();
        records.forEach(collectionRecords -> System.out.println(collectionRecords));
    }
    @Test
    public void redisTest(){
         redisTemplate.opsForSet().add("a",1,2,34,5);
         redisTemplate.opsForSet().remove("a",2);
    }
    @Test
    public void filterTest(){
        DownloadHistory history1 = new DownloadHistory();
        history1.setId(1L)
                .setUserid(1L)
                .setBookid(1L)
                .setGmtCreat(LocalDateTime.now());
        DownloadHistory history2 = new DownloadHistory();
        history2.setId(2L)
                .setUserid(1L)
                .setBookid(1L)
                .setGmtCreat(LocalDateTime.now());
        DownloadHistory history3 = new DownloadHistory();
        history3.setId(3L)
                .setUserid(1L)
                .setBookid(2L)
                .setGmtCreat(LocalDateTime.now());
        List <DownloadHistory> list = new ArrayList();
        list.add(history1);
        list.add(history2);
        list.add(history3);
        List<DownloadHistory> newlist = list.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(DownloadHistory::getBookid))), ArrayList::new));

        System.out.println(list);
        System.out.println("collect = " + newlist);
    }
    @Test
    public void testGroup(){

    }
}
