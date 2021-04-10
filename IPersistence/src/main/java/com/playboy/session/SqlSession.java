package com.playboy.session;

import com.playboy.pojo.Configuration;

import java.sql.SQLException;
import java.util.List;

/**
 * @ClassName SqlSession
 * @Description
 * @Author playboy
 * @Date 2021/4/10 10:29 上午
 * @Version 1.0
 **/
public interface SqlSession {

    public <T> List<T> selectList(String statementId,Object... params) throws Exception;

    public <T> T selectOne(String statementId,Object... params) throws Exception;

    public <T> T getMapper(Class<T> clazz);

    public int update(String statementId, Object... params) throws Exception;

    public int insert(String statementId,Object... params) throws Exception;

    public int delete(String statementId,Object... params) throws Exception;

    public Configuration getConfiguration();

    public void commit() throws SQLException;

    public void rollback() throws SQLException;

    public void close() throws SQLException;



}
