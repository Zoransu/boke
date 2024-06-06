package com.example.boke.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Long commentId;            // 评论ID
    private Long articleId;            // 评论所属的文章ID
    private Long userId;               // 发表评论的用户ID
    private Date commentDate;          // 评论发表时间
    private String commentContent;     // 评论内容
    private Long parentCommentId;      // 父评论ID（用于回复功能）
}
