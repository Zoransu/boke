package com.example.boke.Service.ServiceImpl;

import com.example.boke.Mapper.UserFriendMapper;
import com.example.boke.Service.UserFriendService;
import com.example.boke.pojo.UserFriends;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserFriendServiceImpl implements UserFriendService {

    @Autowired
    UserFriendMapper userFriendMapper;

    @Override
    public void add(Long userId, Long friendId) {
        UserFriends userFriends =new UserFriends(0L,userId,friendId,null,null);
        userFriendMapper.add(userFriends);
    }
}




