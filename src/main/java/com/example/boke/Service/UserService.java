package com.example.boke.Service;

import com.example.boke.Dto.UserStatisticsDto;
import com.example.boke.pojo.User;

public interface UserService {

    User doLogin(User user);

    void doregister(User user);

    void updateUer(User user);

    UserStatisticsDto getUserStatistics(Long userId);
}
