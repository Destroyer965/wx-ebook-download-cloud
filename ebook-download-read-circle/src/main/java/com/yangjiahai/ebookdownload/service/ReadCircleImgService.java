package com.yangjiahai.ebookdownload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangjiahai.ebookdownload.entity.CircleFriendsImg;

/**
 * @author yangjiahai
 */
public interface ReadCircleImgService extends IService<CircleFriendsImg> {
    /**
     * 用户发表读友圈上传图片
     *
     * @param cid
     * @param url
     * @return
     */
    int addReadCircleImgByCid(Long cid, String url);

    /**
     * 根据读友圈id查询图片列表
     *
     * @param cid
     * @return
     */
    CircleFriendsImg selectCircleImg(Long cid);

    /**
     * 根据读友圈id删除图片列表
     *
     * @param cid
     * @return
     */
    int delReadCircleImgByCid(Long cid);
}
