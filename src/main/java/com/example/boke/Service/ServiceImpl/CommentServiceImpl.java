package com.example.boke.Service.ServiceImpl;

import com.example.boke.Mapper.CommentMapper;
import com.example.boke.Service.CommentService;
import com.example.boke.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Override
    public void addComment(Comment comment) {
        commentMapper.addComment(comment);
    }
}
