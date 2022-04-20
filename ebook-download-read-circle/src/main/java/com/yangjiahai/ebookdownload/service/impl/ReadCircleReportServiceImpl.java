package com.yangjiahai.ebookdownload.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangjiahai.ebookdownload.VO.ReportVO;
import com.yangjiahai.ebookdownload.entity.CircleReport;
import com.yangjiahai.ebookdownload.mapper.ReadCircleReportMapper;
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
public class ReadCircleReportServiceImpl extends ServiceImpl<ReadCircleReportMapper, CircleReport> implements ReadCircleReportService {
    @Resource
    private ReadCircleReportMapper readCircleReportMapper;
    @Resource
    private ReadCircleReportImgService readCircleReportImgService;

    /**
     * 添加举报记录
     *
     * @param id
     * @param reportVO
     * @return
     */
    @Override
    public int addReport(Long id, ReportVO reportVO, String imgList) {
        CircleReport circleReport = new CircleReport();
        circleReport.setReportReson(reportVO.getReason())
                .setReportDescription(reportVO.getDiscription())
                .setCircleid(reportVO.getCircleid())
                .setEmail(reportVO.getEmail())
                .setGmtCreat(LocalDateTime.now())
                .setGmtModified(LocalDateTime.now());
        int insert = readCircleReportMapper.insert(circleReport);
        //将图片信息存入数据库
        readCircleReportImgService.addReportImg(circleReport.getId(), imgList);
        return insert;
    }
}
