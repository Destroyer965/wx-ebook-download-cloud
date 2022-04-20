package com.yangjiahai.ebookdownload.feignCilent;

import com.yangjiahai.ebookdownload.constants.Result;
import com.yangjiahai.ebookdownload.config.FeignMultipartSupportConfig;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/3/8 22:18
 */
@FeignClient(name = "uploadclient",configuration = FeignMultipartSupportConfig.class)
public interface UploadClient {
    /**
     * 文件上传
     *
     * @param files
     * @param mould
     * @return
     */
    @PostMapping(value = "/upload/img", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
     Result upload(
            @RequestPart("files")
                    MultipartFile[] files,
            @ApiParam(required = true, value = "模块")
            @RequestParam("mould")
                    String mould);
}
