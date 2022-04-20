package com.yangjiahai.ebookdownload.VO;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/3/13 14:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class ReportVO {
    private Long circleid;
    private String reason;
    private String discription;
    private String email;
}
