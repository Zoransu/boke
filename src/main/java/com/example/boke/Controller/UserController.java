package com.example.boke.Controller;

import com.example.boke.Dto.UserStatisticsDto;
import com.example.boke.Service.UserService;
import com.example.boke.pojo.User;
import com.example.boke.utils.JwtUtils;
import com.example.boke.utils.Result;
import io.swagger.annotations.*;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequestMapping("/user")
@Api(tags = "用户管理")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ApiOperation(value = "用户登录", notes = "用户登录接口")
    public Result userLogin(
            @RequestBody @ApiParam(value = "用户信息", required = true) User user) {
        User user1 = userService.doLogin(user);
        if (user1 == null) {
            log.info("用户名不存在，请重新登录");
            return Result.error("用户名不存在，请重新登录");
        } else if (user.getUserPassword().equals(user1.getUserPassword())) {
            Map<String, Object> clan = new HashMap<>();
            clan.put("userId", user1.getUserId());
            String jwt = JwtUtils.generateToken(clan);
            log.info("欢迎登录：{}", user1.getUserName());
            return Result.success(jwt);
        } else {
            log.info("密码错误,请重新登录");
            return Result.error("密码错误");
        }
    }

    @PostMapping("/register")
    @ApiOperation(value = "用户注册", notes = "用户注册接口")
    public Result register(@RequestBody @ApiParam(value = "用户信息", required = true) User user) {
        try {
            // 检查用户名是否存在
            if (userService.findByUserName(user.getUserName()) != null) {
                log.info("用户名 {} 已存在，请重新输入", user.getUserName());
                return Result.error("用户名已存在，请重新输入");
            }
            // 注册用户
            userService.doregister(user);
            log.info("新增用户：{}", user);
            return Result.success();
        } catch (Exception e) {
            log.error("注册用户失败: {}", e.getMessage());
            return Result.error("注册失败：" + e.getMessage());
        }
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新用户信息", notes = "更新用户信息接口")
    public Result updateUser(
            @RequestBody @ApiParam(value = "用户信息", required = true) User user,
            HttpServletRequest request) {
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
    @ApiOperation(value = "获取用户统计信息", notes = "获取用户统计信息接口")
    public Result getUserStatistics(HttpServletRequest request) {
        try {
            // 从请求属性中获取用户ID
            Long userId = (Long) request.getAttribute("userId");
            if (userId == null) {
                return Result.error("用户未登录或会话已过期");
            }
            log.info("获取用户统计信息，用户ID: {}", userId);
            UserStatisticsDto userStatistics = userService.getUserStatistics(userId);
            return Result.success(userStatistics);
        } catch (Exception e) {
            log.error("信息获取失败: {}", e.getMessage());
            return Result.error("信息获取失败：" + e.getMessage());
        }
    }

    private static final String UPLOAD_DIR = "uploads/";

    @PostMapping("/upload-avatar")
    @ApiOperation(value = "上传用户头像", notes = "上传用户头像接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "JWT令牌", required = true, paramType = "header",
                    dataTypeClass = String.class)
    })
    public Result uploadAvatar(HttpServletRequest request,
                               @RequestParam("file") @ApiParam(value = "头像文件", required = true) MultipartFile file) {
        try {
            // 从请求属性中获取用户ID
            Long userId = (Long) request.getAttribute("userId");
            if (userId == null) {
                return Result.error("用户未登录或会话已过期");
            }
            // 确保上传目录存在
            Files.createDirectories(Paths.get(UPLOAD_DIR));

            // 删除用户之前的头像
            try (Stream<Path> files = Files.list(Paths.get(UPLOAD_DIR))) {
                files.filter(f -> f.getFileName().toString().startsWith(userId + "_"))
                        .forEach(f -> {
                            try {
                                Files.delete(f);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
            }

            // 生成唯一的文件名，避免文件名冲突
            String originalFileName = file.getOriginalFilename();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String fileName = userId + "_" + System.currentTimeMillis() + fileExtension;

            // 保存文件到指定目录
            Path path = Paths.get(UPLOAD_DIR + fileName);
            try {
                @Cleanup
                InputStream inputStream = file.getInputStream();
                Files.copy(inputStream, path);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 生成文件URL
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/")
                    .path(fileName)
                    .toUriString();

            // 更新用户头像URL
            userService.updateUserProfilePhoto(userId, fileDownloadUri);
            log.info("{} 上传头像成功，uri为：{}", userId, fileDownloadUri);
            return Result.success("上传头像成功：" + fileDownloadUri);
        } catch (IOException e) {
            log.error("上传头像失败: {}", e.getMessage());
            return Result.error("上传头像失败：" + e.getMessage());
        }
    }
}
