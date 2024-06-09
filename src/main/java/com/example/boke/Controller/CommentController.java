package com.example.boke.Controller;


import com.example.boke.Service.CommentService;
import com.example.boke.pojo.Comment;
import com.example.boke.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/Comment")
@Api(tags = "评论管理")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/add")
    @ApiOperation(value = "添加评论", notes = "添加评论到指定文章")
    public Result addComment(
            @ApiParam(value = "评论内容", required = true) @RequestBody Comment comment,
            HttpServletRequest request) {
        try {
            // 从请求属性中获取用户ID
            Long userId = (Long) request.getAttribute("userId");
            if (userId == null) {
                return Result.error("用户未登录或会话已过期");
            }
            comment.setUserId(userId);
            comment.setCommentDate(new Date());
            comment.setParentCommentId(null);
            commentService.addComment(comment);
            log.info("添加评论成功：{}",comment);
            return Result.success(comment);
        }catch (Exception e){
            log.error("添加评论失败: {}", e.getMessage());
            return Result.error("添加评论失败：" + e.getMessage());
        }
    }
}
