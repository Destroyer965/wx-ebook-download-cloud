package com.yangjiahai.ebookdownload.feignClient;

import com.yangjiahai.ebookdownload.constants.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/2/28 16:14
 */
@FeignClient("userclient")
public interface UserClient {
    /**
     * 获取用户信息
     * @param id
     * @return
     */
    @RequestMapping("/user/userinfobyid")
    Result getUserInfo(@RequestParam("id") Long id);
}
