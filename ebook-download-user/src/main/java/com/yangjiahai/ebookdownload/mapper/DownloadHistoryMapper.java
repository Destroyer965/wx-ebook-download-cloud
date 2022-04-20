package com.yangjiahai.ebookdownload.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yangjiahai.ebookdownload.entity.DownloadHistory;
import org.apache.ibatis.annotations.Param;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/3/5 18:44
 */
public interface DownloadHistoryMapper extends BaseMapper<DownloadHistory> {
    /**
     * 根据用户id查询用户下载记录
     *
     * @param page
     * @param queryWrapper
     * @return
     */
    IPage<DownloadHistory> selectDownloadCollection(IPage page, @Param(Constants.WRAPPER) Wrapper<DownloadHistory> queryWrapper);
}
