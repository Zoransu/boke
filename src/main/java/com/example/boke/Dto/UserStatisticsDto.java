package com.example.boke.Dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "用户统计数据传输对象")
public class UserStatisticsDto {

    @ApiModelProperty(value = "用户ID", example = "1")
    private Long userId;

    @ApiModelProperty(value = "用户名", example = "johndoe")
    private String userName;

    @ApiModelProperty(value = "发表文章数", example = "10")
    private int articlesCount;

    @ApiModelProperty(value = "总评论数", example = "50")
    private int totalComments;

    @ApiModelProperty(value = "总点赞数", example = "100")
    private int totalLikes;

    @ApiModelProperty(value = "头像", example = "http://localhost:8080/uploads/0.jpg")
    private String userProfilePhoto;
}
