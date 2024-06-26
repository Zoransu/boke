package com.example.boke.Service;

import com.example.boke.Dto.UserFriendsDto;
import com.example.boke.pojo.UserFriends;

import java.util.List;

public interface UserFriendService {
    void add(Long userId, Long friendId);

    List<UserFriendsDto> getMyFriends(Long userId);

    UserFriends isMyFriend(Long userId, Long friendId);

    void updateFriendNoteName(Long userId, Long friendId, String userFriendNoteName);
}
