package com.playboy.session;

import com.playboy.pojo.MapperStatement;

import java.sql.SQLException;
import java.util.List;

/**
 * @ClassName Executor
 * @Description
 * @Author playboy
 * @Date 2021/4/10 12:22 下午
 * @Version 1.0
 **/
public interface Executor {

    public <T> List<T> query(MapperStatement mapperStatement,Object... params) throws Exception;

    int update(MapperStatement mapperStatement, Object... params) throws Exception;

    void commit() throws SQLException;

    void rollback() throws SQLException;
}
