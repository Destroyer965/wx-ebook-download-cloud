package com.yangjiahai.ebookdownload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangjiahai.ebookdownload.entity.CollectionRecords;
import com.yangjiahai.ebookdownload.feignCilent.BookClient;
import com.yangjiahai.ebookdownload.mapper.CollectionRecordsMapper;
import com.yangjiahai.ebookdownload.service.CollectionRecordsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/2/25 18:23
 */
@Service
public class CollectionRecordsServiceImpl extends ServiceImpl<CollectionRecordsMapper, CollectionRecords> implements CollectionRecordsService {
    @Resource
    private CollectionRecordsMapper recordsMapper;
    @Resource
    private BookClient bookClient;

    /**
     * 根据用户id查询用户收藏列表
     *
     * @param pageNo
     * @param pageSize
     * @param userid
     * @return
     */
    @Override
    public Map<String, Object> selectCollectionsById(Integer pageNo, Integer pageSize, Long userid) {
        Page<CollectionRecords> page = new Page<>(pageNo, pageSize);
        QueryWrapper<CollectionRecords> qw = new QueryWrapper<>();
        qw.eq("userid", userid);
        IPage<CollectionRecords> collection = recordsMapper.selectCollection(page, qw);
        List<CollectionRecords> records = collection.getRecords();
        long total = collection.getTotal();
        Map<String, Object> map = new HashMap<>(32);
        map.put("data", records);
        map.put("total", total);
        return map;
    }

    /**
     * 根据用户id和图书id查询该用户是否收藏该图书
     *
     * @param userid
     * @param bookid
     * @return
     */
    @Override
    public CollectionRecords selectCollection(Long userid, Long bookid) {
        QueryWrapper<CollectionRecords> qw = new QueryWrapper<>();
        qw.eq("userid", userid);
        qw.eq("bookid", bookid);
        CollectionRecords records = recordsMapper.selectOne(qw);
        return records;
    }

    /**
     * 根据用户id和面试题id查询该用户是否收藏该面试题
     * @param userId
     * @param interviewid
     * @return
     */
    @Override
    public CollectionRecords selectInterviewCollection(Long userId, Long interviewid) {
        QueryWrapper<CollectionRecords> qw = new QueryWrapper<>();
        qw.eq("userid", userId);
        qw.eq("interviewid", interviewid);
        CollectionRecords records = recordsMapper.selectOne(qw);
        return records;
    }

    /**
     * 添加用户收藏列表
     *
     * @param userid
     * @param bookid
     * @return
     */
    @Override
    public int addCollectionById(Long userid, Long bookid) {
        CollectionRecords records = new CollectionRecords();
        //调用图书微服务，增加图书收藏数量
        bookClient.addCollection(bookid);
        records.setUserid(userid)
                .setBookid(bookid)
                .setGmtCreat(LocalDateTime.now())
                .setGmtModified(LocalDateTime.now());
        int i = recordsMapper.insert(records);
        return i;
    }

    /**
     * 删除用户收藏列表
     *
     * @param userid
     * @param bookid
     * @return
     */
    @Override
    public int delCollectionById(Long userid, Long bookid) {
        //调用图书微服务，减少图书收藏数量
        bookClient.delCollection(bookid);
        QueryWrapper<CollectionRecords> wrapper = new QueryWrapper<>();
        wrapper.eq("userid", userid);
        wrapper.eq("bookid", bookid);
        int i = recordsMapper.delete(wrapper);
        return i;
    }


    /**
     * 添加用户收藏面试题列表
     *
     * @param userid
     * @param interviewid
     * @return
     */
    @Override
    public int addInterviewCollectionById(Long userid, Long interviewid) {
        CollectionRecords records = new CollectionRecords();
        records.setUserid(userid)
                .setInterviewid(interviewid)
                .setGmtCreat(LocalDateTime.now())
                .setGmtModified(LocalDateTime.now());
        int i = recordsMapper.insert(records);
        return i;
    }

    /**
     * 删除用户收藏面试题列表
     *
     * @param userid
     * @param interviewid
     * @return
     */
    @Override
    public int delInterviewCollectionById(Long userid, Long interviewid) {
        QueryWrapper<CollectionRecords> wrapper = new QueryWrapper<>();
        wrapper.eq("userid", userid);
        wrapper.eq("interviewid", interviewid);
        int i = recordsMapper.delete(wrapper);
        return i;
    }


}
