package com.playboy.session;

import com.playboy.handler.DefaultParameterHandler;
import com.playboy.handler.DefaultResultHandler;
import com.playboy.pojo.Configuration;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @ClassName DefaultSqlSessionFactory
 * @Description
 * @Author playboy
 * @Date 2021/4/10 10:37 上午
 * @Version 1.0
 **/
public class DefaultSqlSessionFactory implements SqlSessionFacotry{

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() throws SQLException {
        Connection connection = configuration.getDataSource().getConnection();
        DefaultExecutor defaultExecutor = new DefaultExecutor(connection);
        connection.setAutoCommit(false);//设置不自动提交事务
        DefaultSqlSession defaultSqlSession = new DefaultSqlSession(defaultExecutor, configuration);
        return defaultSqlSession;
    }
}
