package com.example.demo.maapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    @Insert("insert into account(username,password,number) values(#{username},#{password},#{number})")
    void createUser(String username, String password, String number);

    @Delete("delete from account where number = #{number}")
    void deleteUserByEmail(String number);

}
