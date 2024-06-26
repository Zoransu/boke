package com.example.boke.Service.ServiceImpl;

import com.example.boke.Dto.UserFriendsDto;
import com.example.boke.Mapper.UserFriendMapper;
import com.example.boke.Service.UserFriendService;
import com.example.boke.pojo.UserFriends;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFriendServiceImpl implements UserFriendService {

    @Autowired
    UserFriendMapper userFriendMapper;

    @Override
    public void add(Long userId, Long friendId) {
        UserFriends userFriends =new UserFriends(0L,userId,friendId,null,null);
        userFriendMapper.add(userFriends);
    }

    @Override
    public List<UserFriendsDto> getMyFriends(Long userId) {
        return userFriendMapper.getMyFriends(userId);
    }

    @Override
    public UserFriends isMyFriend(Long userId, Long friendId) {
        return userFriendMapper.isMyFriend(userId,friendId);
    }

    @Override
    public void updateFriendNoteName(Long userId, Long friendId, String userFriendNoteName) {
        userFriendMapper.updateFriendNoteName(userId,friendId,userFriendNoteName);
    }
}




