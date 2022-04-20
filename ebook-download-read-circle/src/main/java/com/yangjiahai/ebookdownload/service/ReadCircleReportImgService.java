package com.yangjiahai.ebookdownload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangjiahai.ebookdownload.entity.CircleReportImg;

/**
 * @author yangjiahai
 */
public interface ReadCircleReportImgService extends IService<CircleReportImg> {
    /**
     * 用户举报上传图片
     *
     * @param cid
     * @param url
     * @return
     */
    int addReportImg(Long cid, String url);
}
