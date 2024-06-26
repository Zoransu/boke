package com.example.boke.Dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@AllArgsConstructor
@ApiModel(description = "好友传输对象")
public class UserFriendsDto {

    @ApiModelProperty(value = "好友ID", example = "1")
    private Long userFriendId;

    @ApiModelProperty(value = "好友头像图片的URL", example = "http://example.com/profile.jpg")
    private String userFriendProfilePhoto;

    @ApiModelProperty(value = "好友用户名", example = "john")
    private String userFriendName;

    @ApiModelProperty(value = "好友昵称", example = "john")
    private String userFriendNoteName;
}
