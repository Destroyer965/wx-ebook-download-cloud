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
@TableName("wx_interview")
@ApiModel(value = "Interview对象", description = "")
public class Interview implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("面试题id")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("面试题分类id")
    private Long categoryId;

    @ApiModelProperty("面试题题目")
    private String topic;

    @ApiModelProperty("面试题答案")
    private String answer;

    @ApiModelProperty("收藏数量")
    private Integer collectionCount;

    @ApiModelProperty("点赞数量")
    private Integer likeCount;

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
