package com.yangjiahai.ebookdownload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangjiahai.ebookdownload.entity.Banner;
import com.yangjiahai.ebookdownload.mapper.BannerMapper;
import com.yangjiahai.ebookdownload.service.BannerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/2/19 16:47
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {
    @Resource
    private BannerMapper bannerMapper;
    @Override
    public List<Banner> selectBanner() {
        return bannerMapper.selectList(null);
    }
}
