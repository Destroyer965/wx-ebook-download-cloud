package com.yangjiahai.ebookdownload.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

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
@TableName("wx_book")
@ApiModel(value = "Book对象", description = "")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("图书id")
    private Long id;

    @ApiModelProperty("图书分类id")
    private Long categoryId;

    @ApiModelProperty("图片url")
    private String imgUrl;

    @ApiModelProperty("图书名称")
    private String bookName;

    @ApiModelProperty("图书作者")
    private String bookAuthor;

    @ApiModelProperty("图书出版社")
    private String bookPublish;

    @ApiModelProperty("图书ISBN编号")
    private String bookIsbn;

    @ApiModelProperty("图书简介")
    private String bookIntrduction;

    @ApiModelProperty("下载次数")
    private Long downloadCount;

    @ApiModelProperty("收藏次数")
    private Long collectionCount;

    @ApiModelProperty("推荐指数(1为一星推荐依次递增，最大为5)")
    private Integer recommendationIndex;

    @ApiModelProperty("下载链接")
    private String downloadUrl;

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
