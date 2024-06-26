package com.example.boke.Dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "判断是否为好友的数据类型")
public class isMyFriendDto {

    @ApiModelProperty(value = "好友昵称", example = "john")
    private String userFriendNoteName;


    @ApiModelProperty(value = "判断", example = "true")
    private Boolean isMyFriend;

}
