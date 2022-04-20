package com.yangjiahai.ebookdownload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangjiahai.ebookdownload.entity.DownloadHistory;
import com.yangjiahai.ebookdownload.mapper.DownloadHistoryMapper;
import com.yangjiahai.ebookdownload.service.DownloadHistoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/3/5 18:46
 */
@Service
public class DownloadHistoryServiceImpl extends ServiceImpl<DownloadHistoryMapper, DownloadHistory> implements DownloadHistoryService {
    @Resource
    private DownloadHistoryMapper downloadHistoryMapper;

    /**
     * 根据用户id查询用户信息
     *
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> selectDownloadHistory(Long id, Long pageNo, Long pageSize) {
        QueryWrapper<DownloadHistory> qw = new QueryWrapper<>();
        qw.eq("userid", id);
        Page<DownloadHistory> page = new Page<>(pageNo, pageSize);
        IPage<DownloadHistory> historyPage = downloadHistoryMapper.selectDownloadCollection(page, qw);

        List<DownloadHistory> records = historyPage.getRecords();

        //根据bookid去重
//        ArrayList<DownloadHistory> collect = records.stream()
//                .collect(Collectors.collectingAndThen(Collectors
//                                .toCollection(() -> new TreeSet<>(Comparator
//                                        .comparing(DownloadHistory::getBookid))),
//                        ArrayList::new));
        long total = historyPage.getTotal();
        HashMap<String, Object> map = new HashMap<>(32);
        map.put("total", total);
        map.put("data", records);
        return map;
    }

    /**
     * 根据用户id添加用户信息
     *
     * @param id
     * @param bookid
     * @return
     */
    @Override
    public int addDownloadHistory(Long id, Long bookid) {
        QueryWrapper<DownloadHistory> qw = new QueryWrapper<>();
        qw.eq("userid", id);
        qw.eq("bookid", bookid);
        DownloadHistory history = new DownloadHistory();
        history.setBookid(bookid)
                .setUserid(id)
                .setGmtCreat(LocalDateTime.now())
                .setGmtModified(LocalDateTime.now());
        int i = downloadHistoryMapper.insert(history);
        return i;
    }

    /**
     * 根据用户id删除用户信息
     *
     * @param bookid
     * @param userid
     * @return
     */
    @Override
    public int delDownloadHistory(Long userid, Long bookid) {
        QueryWrapper<DownloadHistory> qw = new QueryWrapper<>();
        qw.eq("userid", userid);
        qw.eq("bookid", bookid);
        int i = downloadHistoryMapper.delete(qw);
        return i;
    }
}