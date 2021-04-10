package com.playboy.handler;

import com.playboy.pojo.MapperStatement;

import java.sql.ResultSet;
import java.util.List;

/**
 * @ClassName ResultHandler
 * @Description
 * @Author playboy
 * @Date 2021/4/10 4:49 下午
 * @Version 1.0
 **/
public interface ResultHandler {

   <T> List<T> handleResultSet(MapperStatement mapperStatement, ResultSet resultSet) throws ClassNotFoundException, Exception;
}
