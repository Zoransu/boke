package com.example.boke.Dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.beans.PropertyChangeSupport;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

@Slf4j
@Data
@AllArgsConstructor
@ApiModel(description = "文章详细数据传输对象")
public class ArticleDetailsDto {

    @ApiModelProperty(value = "文章ID", example = "1")
    private Long articleId;

    @ApiModelProperty(value = "文章标题", example = "我的第一篇文章")
    private String articleTitle;

    @ApiModelProperty(value = "文章内容", example = "这是文章的内容")
    private String articleContent;

    @ApiModelProperty(value = "文章发布日期", example = "2024-01-01T12:00:00Z")
    private Date articleDate;

    @ApiModelProperty(value = "格式化后的文章发布日期", example = "EEE MMM dd HH:mm:ss z yyyy")
    private String articleDateNew;

    @ApiModelProperty(value = "文章点赞数量", example = "100")
    private Long articleLikeCount;

    @ApiModelProperty(value = "文章作者")
    private User author;

    @ApiModelProperty(value = "文章评论列表")
    private List<Comment> comments;

    @ApiModelProperty(value = "文章标签列表")
    private List<Label> labels;

    private PropertyChangeSupport support;

    public ArticleDetailsDto() {
        this.support = new PropertyChangeSupport(this); // 初始化 PropertyChangeSupport
    }

    public ArticleDetailsDto(Long articleId, String articleTitle, String articleContent, Date articleDate, Long articleLikeCount, User author, List<Comment> comments, List<Label> labels) {
        this();
        this.articleId = articleId;
        this.articleTitle = articleTitle;
        this.articleContent = articleContent;
        this.articleDate = articleDate;
        this.articleLikeCount = articleLikeCount;
        this.author = author;
        this.comments = comments;
        this.labels = labels;
        setArticleDate(articleDate); // 设置初始值时格式化日期
    }

    public void setArticleDate(Date articleDate) {
        Date oldDate = this.articleDate;
        this.articleDate = articleDate;
        support.firePropertyChange("articleDate", oldDate, articleDate);
        System.out.println("oldDate:  "+oldDate);
        // 格式化日期
        if (articleDate != null) {
            try {
                ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(articleDate.toInstant(), ZoneId.systemDefault());
                this.articleDateNew = zonedDateTime.format(DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy"));
            } catch (DateTimeParseException e) {
                log.error("Error formatting date: " + e.getMessage());
                this.articleDateNew = null;
            }
        } else {
            this.articleDateNew = null;
        }
    }

    @Data
    @ApiModel(description = "文章作者")
    public static class User {
        @ApiModelProperty(value = "用户ID", example = "1")
        public Long userId;

        @ApiModelProperty(value = "用户名", example = "johndoe")
        public String userName;

        @ApiModelProperty(value = "用户头像URL", example = "http://example.com/user/profile/photo.jpg")
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
    @ApiModel(description = "文章评论")
    public static class Comment {
        @ApiModelProperty(value = "评论ID", example = "1")
        private Long commentId;

        @ApiModelProperty(value = "评论内容", example = "这是一个评论")
        private String commentContent;

        @ApiModelProperty(value = "评论日期", example = "2024-01-01T12:00:00Z")
        private Date commentDate;

        @ApiModelProperty(value = "评论用户")
        private User commenter;
    }

    @Data
    @ApiModel(description = "文章标签")
    public static class Label {
        @ApiModelProperty(value = "标签ID", example = "1")
        private Long labelId;

        @ApiModelProperty(value = "标签名称", example = "Java")
        private String labelName;
    }
}
