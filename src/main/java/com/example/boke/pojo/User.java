package com.example.boke.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long userId;
    private String userName;
    private String userPassword;
    private String userEmail;
    private String userProfilePhoto;
    private Date registrationDate;
    private String userTelephoneNumber;
    private String userNickname;
}
