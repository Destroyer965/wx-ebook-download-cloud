package com.yangjiahai.ebookdownload.service;


/**
 * @author yangjiahai
 */
public interface DownloadService {
    /**
     * 下载文件
     * @param url
     * @return
     */
    Boolean download(String url);
}
