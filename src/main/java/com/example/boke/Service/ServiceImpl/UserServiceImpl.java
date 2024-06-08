package com.example.boke.Service.ServiceImpl;

import com.example.boke.Dto.UserStatisticsDto;
import com.example.boke.Mapper.UserMapper;
import com.example.boke.Service.UserService;
import com.example.boke.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User doLogin(User user) {
        return userMapper.doLogin(user);
    }

    @Override
    public User doregister(User user) {
        // 设置用户注册时间
        user.setRegistrationDate(new Date());
        // 插入用户数据
        userMapper.doregister(user);
        return user;
    }

    @Override
    public void updateUer(User user) {
        userMapper.updateUer(user);
    }

    @Override
    public UserStatisticsDto getUserStatistics(Long userId) {
        return userMapper.getUserStatistics(userId);
    }

    //头像上传
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public String storeFile(MultipartFile file, String username) throws IOException {
        Path targetLocation = Paths.get(uploadDir + username + "_avatar.jpg");
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        return targetLocation.toString();
    }

    @Override
    public void updateUserProfilePhoto(Long userId, String fileDownloadUri) {
        userMapper.updateUserProfilePhoto(userId,fileDownloadUri);
    }

    @Override
    public User findByUserName(String userName) {
        return userMapper.findByUserName(userName);
    }
}
