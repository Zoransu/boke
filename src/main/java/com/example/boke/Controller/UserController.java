package com.example.boke.Controller;


import com.example.boke.Dto.UserStatisticsDto;
import com.example.boke.Service.UserService;
import com.example.boke.pojo.User;
import com.example.boke.utils.JwtUtils;
import com.example.boke.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result userLogin(@RequestBody User user) {
        User user1=userService.doLogin(user);
        if(user1==null){
            log.info("用户名不存在，请重新登录");
            return Result.error("用户名不存在，请重新登录");
        }
        else if(user.getUserPassword().equals(user1.getUserPassword())){
            Map<String, Object> clan =new HashMap<>();
            clan.put("userId",user1.getUserId());
            String jwt = JwtUtils.generateToken(clan);
            log.info("欢迎登录：{}",user1.getUserName());
            return Result.success(jwt);
        }else {
            log.info("密码错误,请重新登录");
            return Result.error("密码错误");
        }
    }


    @PostMapping("/register")
    public Result register(@RequestBody User user){
        User user1 = userService.doLogin(user);
        if(user1==null){
            userService.doregister(user);
            log.info("新增用户：{}",user);
            return Result.success();
        }else {
            log.info("用户名{}已存在，请重新输入",user.getUserName());
            return Result.error("用户名已存在，请重新输入");
        }
    }

    @PostMapping("/update")
    public Result updateUser(@RequestBody User user, HttpServletRequest request) {
        try {
            // 从请求属性中获取用户ID
            Long userId = (Long) request.getAttribute("userId");
            if (userId == null) {
                return Result.error("用户未登录或会话已过期");
            }
            // 设置用户ID并更新用户信息
            user.setUserId(userId);
            userService.updateUer(user);
            log.info("更新用户信息成功: {}", user);
            return Result.success("更新成功：" + userId);
        } catch (Exception e) {
            log.error("更新用户信息失败: {}", e.getMessage());
            return Result.error("更新失败：" + e.getMessage());
        }
    }

    @GetMapping("/getUserStatistics")
    public Result getUserStatistics(HttpServletRequest request){
        try {
            // 从请求属性中获取用户ID
            Long userId = (Long) request.getAttribute("userId");
            if (userId == null) {
                return Result.error("用户未登录或会话已过期");
            }
            UserStatisticsDto userStatistics=userService.getUserStatistics(userId);
            return Result.success(userStatistics);
        }catch (Exception e) {
            log.error("信息获取失败: {}", e.getMessage());
            return Result.error("信息获取失败：" + e.getMessage());
        }
    }

    @PostMapping("/upload-avatar")
    public Result uploadAvatar(HttpServletRequest request,@RequestParam("file") MultipartFile file){
        try {
            // 从请求属性中获取用户ID
            Long userId = (Long) request.getAttribute("userId");
            if (userId == null) {
                return Result.error("用户未登录或会话已过期");
            }
            System.out.println(file.isEmpty());
            String fileDownloadUri = userService.storeFile(file, userId.toString());
            System.out.println(fileDownloadUri);
            userService.updateUserProfilePhoto(userId,fileDownloadUri);
            log.info("{}上传头像成功，uri为：{}",userId,fileDownloadUri);
            return Result.success("上传头像成功");
        } catch (IOException e) {
            log.error("上传头像失败: {}", e.getMessage());
            return Result.error("上传头像失败：" + e.getMessage());
        }
    }
}
