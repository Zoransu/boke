package com.example.boke.Mapper;


import com.example.boke.pojo.UserFriends;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserFriendMapper {

    void add(UserFriends userFriends);


}
