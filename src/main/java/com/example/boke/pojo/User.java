package com.example.boke.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "用户实体")
public class User {

    @ApiModelProperty(value = "用户ID", example = "1")
    private Long userId;

    @ApiModelProperty(value = "用户名", example = "johndoe")
    private String userName;

    @ApiModelProperty(value = "用户密码", example = "password123")
    private String userPassword;

    @ApiModelProperty(value = "用户邮箱", example = "johndoe@example.com")
    private String userEmail;

    @ApiModelProperty(value = "用户头像图片的URL", example = "http://example.com/profile.jpg")
    private String userProfilePhoto;

    @ApiModelProperty(value = "用户注册时间", example = "2024-06-09T12:00:00")
    private Date registrationDate;

    @ApiModelProperty(value = "用户电话号码", example = "12345678901")
    private String userTelephoneNumber;

    @ApiModelProperty(value = "用户昵称", example = "John")
    private String userNickname;
}
