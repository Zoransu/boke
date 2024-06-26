package com.example.boke.Controller;


import com.example.boke.Dto.UserFriendsDto;
import com.example.boke.Dto.isMyFriendDto;
import com.example.boke.Service.UserFriendService;
import com.example.boke.pojo.UserFriends;
import com.example.boke.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/UserFriend")
@Api(tags = "好友管理")
public class UserFriendController {

    @Autowired
    UserFriendService userFriendService;


    @ApiOperation(value = "添加好友" ,notes = "添加好友")
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

    @ApiOperation(value = "查看好友" ,notes = "查看我的好友")
    @GetMapping("/getMyFriends")
    public Result getMyFriends(HttpServletRequest request){
        try {
            Long userId = (Long) request.getAttribute("userId");
            if (userId == null) {
                return Result.error("用户未登录或会话已过期");
            }
            List<UserFriendsDto> list=userFriendService.getMyFriends(userId);
            log.info("查看好友：{}",list);
            return Result.success(list);
        }catch (Exception e){
            log.error("查看好友失败: {}", e.getMessage());
            return Result.error("查看失败：" + e.getMessage());
        }
    }


    @ApiOperation(value = "判断是否为好友" ,notes = "判断是否为好友")
    @PostMapping("/isMyFriend")
    public Result isMyFriend(@RequestParam @ApiParam(value = "好友id")Long friendId,
                             HttpServletRequest request){
        try {
            Long userId = (Long) request.getAttribute("userId");
            if (userId == null) {
                return Result.error("用户未登录或会话已过期");
            }
            UserFriends userFriends= userFriendService.isMyFriend(userId,friendId);
            if(userFriends==null){
                log.info("用户 {} 与 用户 {} 不是好友",userId,friendId);
                return Result.success(new isMyFriendDto(null,false));
            }else {
                log.info("用户 {} 与 用户 {} 是好友",userId,friendId);
                return Result.success(new isMyFriendDto(userFriends.getUserNote(),true));
            }
        }catch (Exception e){
            log.error("查看好友关系失败: {}", e.getMessage());
            return Result.error("查看好友关系失败：" + e.getMessage());
        }
    }


    @PostMapping("/updateFriendNoteName")
    public Result updateFriendNoteName(@RequestParam @ApiParam(value = "好友id")Long friendId,
                                       @RequestParam @ApiParam(value = "好友昵称")String userFriendNoteName,
                                       HttpServletRequest request
                                       ){
        try {
            Long userId = (Long) request.getAttribute("userId");
            if (userId == null) {
                return Result.error("用户未登录或会话已过期");
            }
            userFriendService.updateFriendNoteName(userId,friendId,userFriendNoteName);
            log.info("好友 {} 修改备注 {}",friendId,userFriendNoteName);
            return Result.success("修改备注成功");
        }catch (Exception e){
            log.error("修改备注失败: {}", e.getMessage());
            return Result.error("修改备注失败：" + e.getMessage());
        }
    }
}
