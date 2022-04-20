package com.yangjiahai.ebookdownload.VO;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/2/28 15:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class Reviewer {
    private Integer id;
    private String username;
    private String avatar;
}
