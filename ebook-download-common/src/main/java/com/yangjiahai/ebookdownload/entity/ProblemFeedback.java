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
@TableName("wx_problem_feedback")
@ApiModel(value = "ProblemFeedback对象", description = "")
public class ProblemFeedback implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("问题反馈id")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户id")
    private Long userid;

    @ApiModelProperty("反馈内容")
    private String problemDec;

    @ApiModelProperty("反馈图片")
    private String feedbackImg;

    @ApiModelProperty("联系方式")
    private String contactInfo;

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
