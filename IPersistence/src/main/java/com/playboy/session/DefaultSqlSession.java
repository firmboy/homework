package com.playboy.session;

import com.playboy.pojo.Configuration;
import com.playboy.pojo.MapperProxy;
import com.playboy.pojo.MapperStatement;

import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.List;

/**
 * @ClassName DefaultSqlSession
 * @Description
 * @Author playboy
 * @Date 2021/4/10 10:39 上午
 * @Version 1.0
 **/
public class DefaultSqlSession implements SqlSession{

    private Executor executor;

    private Configuration configuration;

    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public void commit() throws SQLException {
        executor.commit();
    }

    @Override
    public void rollback() throws SQLException {
        executor.rollback();
    }

    @Override
    public void close() throws SQLException {
        executor.rollback();
    }

    public DefaultSqlSession(Executor executor, Configuration configuration) {
        this.executor = executor;
        this.configuration = configuration;
    }

    @Override
    public <T> List<T> selectList(String statementId, Object... params) throws Exception {
        MapperStatement mapperStatement = configuration.getMapperStatements().get(statementId);
        List<T> query = executor.query(mapperStatement, params);
        return query;
    }

    @Override
    public <T> T selectOne(String statementId, Object... params) throws Exception {
        List<T> list = selectList(statementId, params);
        if(list.size()==0){
            return null;
        }
        if(list.size()==1){
            return list.get(0);
        }else {
            throw new RuntimeException("查询结果不唯一");
        }
    }

    @Override
    public <T> T getMapper(Class<T> clazz) {
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new MapperProxy<T>(this,clazz));
        return (T)o;
    }

    @Override
    public int update(String statementId, Object... params) throws Exception {
        MapperStatement mapperStatement = configuration.getMapperStatements().get(statementId);
        int result = executor.update(mapperStatement, params);
        return result;
    }

    @Override
    public int insert(String statementId, Object... params) throws Exception {
        MapperStatement mapperStatement = configuration.getMapperStatements().get(statementId);
        int result = executor.update(mapperStatement, params);
        return result;
    }

    @Override
    public int delete(String statementId, Object... params) throws Exception {
        MapperStatement mapperStatement = configuration.getMapperStatements().get(statementId);
        int result = executor.update(mapperStatement, params);
        return result;
    }


}
