package com.example.boke.Mapper;

import com.example.boke.pojo.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {
    @Insert("INSERT INTO comments (article_id, user_id, comment_date, comment_content, parent_comment_id)" +
            "VALUES (#{articleId}, #{userId}, #{commentDate}, #{commentContent}, #{parentCommentId})")
    void addComment(Comment comment);
}
