package com.playboy.handler;

import com.playboy.pojo.MapperStatement;

import java.sql.PreparedStatement;

/**
 * @ClassName ParameterHandler
 * @Description
 * @Author playboy
 * @Date 2021/4/10 4:49 下午
 * @Version 1.0
 **/
public interface ParameterHandler {

    PreparedStatement getPreparedStatement(MapperStatement mapperStatement, Object[] params)throws Exception;
}
