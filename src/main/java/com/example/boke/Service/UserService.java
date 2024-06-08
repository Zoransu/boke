package com.example.boke.Service;

import com.example.boke.Dto.UserStatisticsDto;
import com.example.boke.pojo.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {

    User doLogin(User user);

    void updateUer(User user);

    UserStatisticsDto getUserStatistics(Long userId);

    String storeFile(MultipartFile file, String toString) throws IOException;

    void updateUserProfilePhoto(Long userId, String fileDownloadUri);

    User doregister(User user);

    User findByUserName(String userName);
}
