package com.yangjiahai.ebookdownload.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/3/4 13:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class EbookDownloadException extends RuntimeException{
    private Integer code;
    private String message;
}
