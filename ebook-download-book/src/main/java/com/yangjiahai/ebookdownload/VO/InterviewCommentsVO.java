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
 * @date 2022/2/28 12:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString
public class InterviewCommentsVO {
   private String id;
   private Long interviewid;
   private String parentid;
   private Integer level;
   private String replayUserName;
   private String content;
   private Reviewer reviewer;
   private LocalDateTime gmtCreat;
   private LocalDateTime gmtModified;
   private List<InterviewCommentsVO> subComments;
}
