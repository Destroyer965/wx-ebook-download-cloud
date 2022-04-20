package com.yangjiahai.ebookdownload.VO;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/3/12 13:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class UserVO {
    private Integer userId;
    private String userName;
}
