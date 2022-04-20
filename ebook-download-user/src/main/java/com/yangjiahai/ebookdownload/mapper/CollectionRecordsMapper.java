package com.yangjiahai.ebookdownload.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yangjiahai.ebookdownload.entity.CollectionRecords;
import org.apache.ibatis.annotations.Param;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/2/20 13:56
 */
public interface CollectionRecordsMapper extends BaseMapper<CollectionRecords> {
    /**
     * 根据用户id查询用户收藏记录
     *
     * @param page
     * @param queryWrapper
     * @return
     */
    IPage<CollectionRecords> selectCollection(IPage page, @Param(Constants.WRAPPER) Wrapper<CollectionRecords> queryWrapper);
}
