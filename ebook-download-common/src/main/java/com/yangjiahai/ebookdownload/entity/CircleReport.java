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
@TableName("wx_circle_report")
@ApiModel(value = "CircleReport对象", description = "")
public class CircleReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @ApiModelProperty("举报列表id")
    private Long id;

    @ApiModelProperty("读友圈id")
    private Long circleid;

    @ApiModelProperty("举报理由")
    private String reportReson;

    @ApiModelProperty("举报描述")
    private String reportDescription;

    @ApiModelProperty("邮箱地址")
    private String email;

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
