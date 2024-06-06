package com.example.boke.Mapper;

import com.example.boke.Dto.UserStatisticsDto;
import com.example.boke.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    User doLogin(User user);

    void doregister(User user);

    void updateUer(User user);

    UserStatisticsDto getUserStatistics(Long userId);

    @Update("UPDATE user SET user_profile_photo = #{profilePhotoUrl} WHERE user_id = #{userId}")
    void updateUserProfilePhoto(Long userId, String fileDownloadUri);
}
