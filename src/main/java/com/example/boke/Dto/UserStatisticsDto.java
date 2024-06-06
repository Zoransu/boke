package com.example.boke.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStatisticsDto {
    private Long userId;
    private String userName;
    private int articlesCount;
    private int totalComments;
    private int totalLikes;
}
