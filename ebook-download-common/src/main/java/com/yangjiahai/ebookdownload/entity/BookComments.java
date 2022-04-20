package com.yangjiahai.ebookdownload.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
@TableName("wx_book_comments")
@ApiModel(value = "BookComments对象", description = "")
public class BookComments implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    @ApiModelProperty("图书评论id")
    private String id;

    @ApiModelProperty("图书id")
    private Long bookid;

    @ApiModelProperty("用户id")
    private Long userid;

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

    @ApiModelProperty("子评论")
    @TableField(exist = false)
    private List<BookComments> children;

}
