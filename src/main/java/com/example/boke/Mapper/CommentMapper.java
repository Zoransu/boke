package com.example.boke.Mapper;

import com.example.boke.pojo.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {

    void addComment(Comment comment);

    void deleteComment(Long commentId, Long userId);
}
