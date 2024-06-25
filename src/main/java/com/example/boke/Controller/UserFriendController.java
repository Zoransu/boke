package com.example.boke.Controller;


import com.example.boke.Service.UserFriendService;
import com.example.boke.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/UserFriend")
@Api(tags = "好友管理")
public class UserFriendController {

    @Autowired
    UserFriendService userFriendService;


    @ApiOperation("添加好友")
    @PostMapping("/add")
    public Result add(@RequestParam("friendId") Long friendId, HttpServletRequest request){
        try {
            Long userId = (Long) request.getAttribute("userId");
            if (userId == null) {
                return Result.error("用户未登录或会话已过期");
            }
            userFriendService.add(userId,friendId);
            log.info("用户{} 添加好友：{}",userId,friendId);
            return Result.success("添加成功");
        }catch (Exception e){
            log.error("添加好友失败: {}", e.getMessage());
            return Result.error("添加失败：" + e.getMessage());
        }

    }
}
