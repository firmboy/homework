package com.playboy.mapper;

import com.playboy.pojo.User;

import java.util.List;

/**
 * @ClassName UserMapper
 * @Description
 * @Author playboy
 * @Date 2021/4/10 11:47 上午
 * @Version 1.0
 **/
public interface UserMapper {

    public List<User> selectList();

    public User selectOne(User user);

    public int insert(User user);

    public int update(User user);

    public int delete(User user);

    public int deleteById(Integer id);



}
