package com.yangjiahai.ebookdownload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangjiahai.ebookdownload.entity.Banner;

import java.util.List;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/2/19 16:19
 */
public interface BannerService extends IService<Banner> {
    /**
     * 查询所有轮播图
     * @return
     */
    List<Banner> selectBanner();
}
