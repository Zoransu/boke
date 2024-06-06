package com.example.boke.Service.ServiceImpl;

import com.example.boke.Dto.UserStatisticsDto;
import com.example.boke.Mapper.UserMapper;
import com.example.boke.Service.UserService;
import com.example.boke.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User doLogin(User user) {
        return userMapper.doLogin(user);
    }

    @Override
    public void doregister(User user) {
        userMapper.doregister(user);
    }

    @Override
    public void updateUer(User user) {
        userMapper.updateUer(user);
    }

    @Override
    public UserStatisticsDto getUserStatistics(Long userId) {
        return userMapper.getUserStatistics(userId);
    }
}
