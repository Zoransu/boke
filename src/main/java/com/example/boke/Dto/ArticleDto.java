package com.example.boke.Dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "文章数据传输对象")
public class ArticleDto {
    @ApiModelProperty(value = "用户ID", example = "1")
    private Long userId;

    @ApiModelProperty(value = "文章标题", example = "我的第一篇文章")
    private String title;

    @ApiModelProperty(value = "文章内容", example = "这是文章的内容")
    private String content;

    @ApiModelProperty(value = "标签ID列表", example = "[music、]")
    private List<String> labelNames;
}
