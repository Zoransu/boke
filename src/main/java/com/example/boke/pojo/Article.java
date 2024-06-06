package com.example.boke.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    private Long articleId;         // 文章ID
    private Long userId;            // 用户ID
    private String articleTitle;    // 文章标题
    private String articleContent;  // 文章内容
    private Long articleCommentCount; // 评论数量
    private Date articleDate;       // 发布时间
    private Long articleLikeCount;  // 点赞数量
}
