package com.yangjiahai.ebookdownload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangjiahai.ebookdownload.entity.CircleFriendsImg;
import com.yangjiahai.ebookdownload.mapper.ReadCircleImgMapper;
import com.yangjiahai.ebookdownload.service.ReadCircleImgService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/3/9 19:23
 */
@Service
public class ReadCircleImgServiceImpl extends ServiceImpl<ReadCircleImgMapper, CircleFriendsImg> implements ReadCircleImgService {
    @Resource
    private ReadCircleImgMapper readCircleImgMapper;
    /**
     *  用户发表读友圈上传图片
     * @param cid
     * @param url
     * @return
     */
    @Override
    public int addReadCircleImgByCid(Long cid, String url) {
        CircleFriendsImg friendsImg = new CircleFriendsImg();
        friendsImg.setCircleFriendsId(cid)
                .setImgUrl(url)
                .setGmtCreat(LocalDateTime.now())
                .setGmtModified(LocalDateTime.now());

        int insert = readCircleImgMapper.insert(friendsImg);
        return insert;
    }

    /**
     *根据读友圈id查询图片列表
     * @param cid
     * @return
     */
    @Override
    public CircleFriendsImg selectCircleImg(Long cid) {
        QueryWrapper<CircleFriendsImg> wrapper = new QueryWrapper<>();
        wrapper.eq("circle_friends_id",cid);
        CircleFriendsImg circleFriendsImg = readCircleImgMapper.selectOne(wrapper);
        return circleFriendsImg;
    }

    @Override
    public int delReadCircleImgByCid(Long cid) {
        int i = readCircleImgMapper.delete(new QueryWrapper<CircleFriendsImg>().eq("circle_friends_id",cid));
        return i;
    }
}
