package com.yangjiahai.ebookdownload.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/3/4 21:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString
public class BookVO {
    private Long id;
    private String bookname;
    private Double searchCount;
    private Long downloadCount;
}
