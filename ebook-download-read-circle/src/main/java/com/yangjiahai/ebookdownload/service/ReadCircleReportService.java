package com.yangjiahai.ebookdownload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangjiahai.ebookdownload.VO.ReportVO;
import com.yangjiahai.ebookdownload.entity.CircleReport;

/**
 * @author yangjiahai
 */
public interface ReadCircleReportService extends IService<CircleReport> {
    /**
     * 添加举报记录
     * @param id
     * @param reportVO
     * @return
     */
    int addReport(Long id, ReportVO reportVO,String imgList);
}
