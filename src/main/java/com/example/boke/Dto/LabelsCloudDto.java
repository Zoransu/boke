package com.example.boke.Dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "标签词云数据传输对象")
public class LabelsCloudDto {
    @ApiModelProperty(value = "标签ID", example = "1")
    private Long labelId;

    @ApiModelProperty(value = "标签名称", example = "Java")
    private String labelName;

    @ApiModelProperty(value = "文章数量", example = "10")
    private int articleCount;
}
