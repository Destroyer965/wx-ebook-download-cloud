package com.yangjiahai.ebookdownload.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangjiahai.ebookdownload.entity.CircleFriendsImg;
import com.yangjiahai.ebookdownload.entity.CircleReportImg;
import com.yangjiahai.ebookdownload.mapper.ReadCircleImgMapper;
import com.yangjiahai.ebookdownload.mapper.ReadCircleReportImgMapper;
import com.yangjiahai.ebookdownload.service.ReadCircleReportImgService;
import com.yangjiahai.ebookdownload.service.ReadCircleReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/3/13 14:22
 */
@Slf4j
@Service
public class ReadCircleReportImgServiceImpl extends ServiceImpl<ReadCircleReportImgMapper, CircleReportImg> implements ReadCircleReportImgService {
    @Resource
    private ReadCircleReportImgMapper readCircleReportImgMapper;

    /**
     * 用户发表读友圈上传图片
     *
     * @param cid
     * @param url
     * @return
     */
    @Override
    public int addReportImg(Long cid, String url) {
        CircleReportImg reportImg = new CircleReportImg();
        reportImg.setCircleReportId(cid)
                .setImgList(url)
                .setGmtCreat(LocalDateTime.now())
                .setGmtModified(LocalDateTime.now());

        int insert = readCircleReportImgMapper.insert(reportImg);
        return insert;
    }

}
