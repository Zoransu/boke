package com.example.boke.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDetailsDto {
    private Long articleId;
    private String articleTitle;
    private String articleContent;
    private Date articleDate;
    private User author;
    private List<Comment> comments;
    private List<Label> labels;

    @Data
    public static class User {
        public Long userId; // 这里添加了userId，通常用于识别和引用
        public String userName;
        public String userProfilePhoto;

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserProfilePhoto() {
            return userProfilePhoto;
        }

        public void setUserProfilePhoto(String userProfilePhoto) {
            this.userProfilePhoto = userProfilePhoto;
        }
    }

    @Data
    public static class Comment {
        private Long commentId; // 这里添加了commentId，通常用于识别和引用
        private String commentContent;
        private Date commentDate; // 添加评论时间
        private User commenter;
    }

    @Data
    public static class Label {
        private Long labelId; // 这里添加了labelId，通常用于识别和引用
        private String labelName;
    }
}
