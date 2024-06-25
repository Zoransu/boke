package com.example.boke.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFriends {
    private Long id;                  // 记录ID
    private Long userId;              // 用户ID
    private Long userFriendId;        // 好友的用户ID
    private String userNote;          // 用户给好友的备注
    private String userStatus;        // 好友关系的状态
}
