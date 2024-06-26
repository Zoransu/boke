package com.example.boke.Mapper;


import com.example.boke.Dto.UserFriendsDto;
import com.example.boke.pojo.UserFriends;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserFriendMapper {

    void add(UserFriends userFriends);

    List<UserFriendsDto> getMyFriends(Long userId);

    UserFriends isMyFriend(Long userId, Long friendId);
}
