package com.playboy.pojo;

import com.playboy.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @ClassName MapperProxy
 * @Description mapper的代理对象，真正执行逻辑的类
 * @Author playboy
 * @Date 2021/4/10 3:39 下午
 * @Version 1.0
 **/
public class MapperProxy<T> implements InvocationHandler {

    private SqlSession sqlSession;

    private Class<T> mapperInterface;


    public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        String className = mapperInterface.getName();//className
        String methodName = method.getName();//methodName
        String statementId = className+"."+methodName;
        MapperStatement mapperStatement = sqlSession.getConfiguration().getMapperStatements().get(statementId);
        SqlType sqlType = mapperStatement.getSqlType();
        switch (sqlType){
            case SELECT:
                Object select = select(mapperStatement,objects,method);
                return select;
            case UPDATE:
                Object update = sqlSession.update(statementId,objects);
                return update;
            case INSERT:
                Object insert = sqlSession.insert(statementId,objects);
                return insert;
            case DELETE:
                Object delete = sqlSession.delete(statementId,objects);
                return delete;
        }
        return null;
    }

    //需要解决的问题，怎样通过返回值判断需要调用selectList还是selectOne方法
    private Object select(MapperStatement mapperStatement, Object[] objects, Method method) throws Exception {
        Class<?> returnType = method.getReturnType();
        Object result = null;
        if(returnType == List.class){
            //调用selectList
            result = sqlSession.selectList(mapperStatement.getId(), objects);
        }else{
            //调用selectOne
            result = sqlSession.selectOne(mapperStatement.getId(), objects);
        }
        return result;
    }
}
