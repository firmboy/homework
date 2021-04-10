package com.playboy.session;

import com.playboy.handler.DefaultParameterHandler;
import com.playboy.handler.DefaultResultHandler;
import com.playboy.handler.ParameterHandler;
import com.playboy.handler.ResultHandler;
import com.playboy.pojo.MapperStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @ClassName DefaultExecutor
 * @Description
 * @Author playboy
 * @Date 2021/4/10 12:23 下午
 * @Version 1.0
 **/
public class DefaultExecutor implements Executor{

    private Connection connection;

    private ParameterHandler parameterHandler;

    private ResultHandler resultHandler;

    public DefaultExecutor(Connection connection) {
        this.connection = connection;
        this.parameterHandler = new DefaultParameterHandler(connection);
        this.resultHandler = new DefaultResultHandler();
    }

    @Override
    public <T> List<T> query(MapperStatement mapperStatement, Object... params) throws Exception {
        PreparedStatement preparedStatement = parameterHandler.getPreparedStatement(mapperStatement, params);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<T> list = resultHandler.handleResultSet(mapperStatement, resultSet);
        return list;
    }



    @Override
    public int update(MapperStatement mapperStatement, Object... params) throws Exception {
        PreparedStatement preparedStatement = parameterHandler.getPreparedStatement(mapperStatement, params);
        int i = preparedStatement.executeUpdate();
        return i;
    }

    @Override
    public void commit() throws SQLException {
        connection.commit();
    }

    @Override
    public void rollback() throws SQLException {
        connection.rollback();
    }

}
