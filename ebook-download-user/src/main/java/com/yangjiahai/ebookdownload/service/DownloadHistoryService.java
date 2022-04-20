package com.yangjiahai.ebookdownload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangjiahai.ebookdownload.entity.DownloadHistory;

import java.util.Map;

/**
 * @author yangjiahai
 */
public interface DownloadHistoryService extends IService<DownloadHistory> {

    /**
     * 根据用户ID查询用户下载历史
     *
     * @param id
     * @param pageNo
     * @param pageSize
     * @return
     */
    Map<String, Object> selectDownloadHistory(Long id, Long pageNo, Long pageSize);

    /**
     * 根据用户ID添加用户下载历史
     *
     * @param id
     * @param bookid
     * @return
     */
    int addDownloadHistory(Long id, Long bookid);

    /**
     * 根据用户ID删除用户下载历史
     *
     * @param bookid
     * @param userid
     * @return
     */
    int delDownloadHistory(Long userid, Long bookid);
}
