package com.yangjiahai.ebookdownload.service;

import java.io.InputStream;
import java.util.List;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/3/7 20:20
 */
public interface UploadService {
    /**
     * 文件上传
     * @param in
     * @param moudle
     * @param originName
     * @return
     */
    List<String> upload(List<InputStream> in, String moudle, List<String> originName);

}
