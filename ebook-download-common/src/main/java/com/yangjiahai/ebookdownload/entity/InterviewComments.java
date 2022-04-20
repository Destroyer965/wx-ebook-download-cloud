package com.yangjiahai.ebookdownload.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author yangjiahai
 * @since 2022-02-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("wx_interview_comments")
@ApiModel(value = "InterviewComments对象", description = "")
public class InterviewComments implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("面试题评论id")
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty("面试题id")
    private Long interviewid;

    @ApiModelProperty("评论者id")
    private Long userid;

    @ApiModelProperty("评论等级")
    private Integer level;

    @ApiModelProperty("评论内容")
    private String content;

    @ApiModelProperty("父id")
    private String parentid;

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    @ApiModelProperty("创建时间")
    private LocalDateTime gmtCreat;

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    @ApiModelProperty("修改时间")
    private LocalDateTime gmtModified;
}
