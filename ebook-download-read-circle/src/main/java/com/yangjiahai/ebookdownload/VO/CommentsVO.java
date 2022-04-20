package com.yangjiahai.ebookdownload.VO;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/3/13 11:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class CommentsVO {
    private Long id;
    private String username;
    private String contentText;
}
