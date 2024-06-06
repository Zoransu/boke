package com.example.boke.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Label {
    private Long labelId;             // 标签ID
    private String labelName;         // 标签名称
    private String labelAlias;        // 标签别名
    private String labelDescription;  // 标签描述
}
