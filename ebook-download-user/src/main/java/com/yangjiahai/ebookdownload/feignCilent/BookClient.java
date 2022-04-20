package com.yangjiahai.ebookdownload.feignCilent;

import com.yangjiahai.ebookdownload.constants.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/3/2 19:02
 */
@FeignClient("bookclient")
public interface BookClient {
    /**
     * 增加图书收藏数量
     * @param bookid
     * @return
     */
    @PutMapping("/book/collection/add/{bookid}")
    Result addCollection(@PathVariable("bookid") Long bookid);

    /**
     * 减少图书收藏数量
     * @param bookid
     * @return
     */
    @DeleteMapping("/book/collection/del/{bookid}")
    Result delCollection(@PathVariable("bookid") Long bookid);

}
