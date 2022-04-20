package com.yangjiahai.ebookdownload.controller;


import com.yangjiahai.ebookdownload.constants.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.yangjiahai.ebookdownload.service.UploadService;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/3/7 20:19
 */
@Api("文件上传")
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Resource
    private UploadService uploadService;

    @PostMapping(value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation("图片上传")
    public Result upload(
            @ApiParam(required = true, value = "文件")
            @RequestParam(value = "files",required = true)
                   MultipartFile[] files,
            @ApiParam(required = true, value = "模块")
            @RequestParam(value = "mould",required = false,defaultValue = "friends-circle")
                    String mould) throws IOException {
        List<String> originalFilenames = new ArrayList<>();
        List<InputStream> in = new ArrayList<>();
        for (MultipartFile multipartFile : files) {
            originalFilenames.add(multipartFile.getOriginalFilename());
            in.add(multipartFile.getInputStream());
        }
        List<String> uploadSrc = uploadService.upload(in, mould, originalFilenames);
        return Result.ok().data(uploadSrc);
    }

}
