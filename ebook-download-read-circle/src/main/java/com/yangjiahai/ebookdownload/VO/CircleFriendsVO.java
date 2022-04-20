package com.yangjiahai.ebookdownload.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 杨加海
 * @version V1.0
 * @date 2022/3/11 12:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString
public class CircleFriendsVO {
    private Long id;
    private String contentText;
    private double latitude;
    private double longitude;
    private String location;
    private List<String> imgList;
    private List<UserVO> userVOS;
    private Long parentid;
    private Reviewer reviewer;
    private LocalDateTime gmtCreat;
    private LocalDateTime gmtModified;
    private List<CommentsVO> subComments;
}
