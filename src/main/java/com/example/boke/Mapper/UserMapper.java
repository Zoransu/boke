package com.example.boke.Mapper;

import com.example.boke.Dto.UserStatisticsDto;
import com.example.boke.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    User doLogin(User user);



    void updateUer(User user);

    UserStatisticsDto getUserStatistics(Long userId);

    @Update("UPDATE user SET user_profile_photo = #{profilePhotoUrl} WHERE user_id = #{userId}")
    void updateUserProfilePhoto(Long userId, String fileDownloadUri);

    @Select("SELECT * FROM user WHERE user_name = #{userName}")
    User findByUserName(String userName);

    @Insert("INSERT INTO user (user_name, user_password, user_email, user_profile_photo, registration_date, user_telephone_number, user_nickname) " +
            "VALUES (#{userName}, #{userPassword}, #{userEmail}, #{userProfilePhoto}, #{registrationDate}, #{userTelephoneNumber}, #{userNickname})")
    void doregister(User user);
}
