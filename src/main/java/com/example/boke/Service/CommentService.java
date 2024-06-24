package com.example.boke.Service;

import com.example.boke.pojo.Comment;

public interface CommentService {
    void addComment(Comment comment);

    void deleteComment(Long commentId, Long userId);
}
