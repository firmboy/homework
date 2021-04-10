package com.playboy.handler;

import com.playboy.pojo.MapperStatement;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName DefaultResultHandler
 * @Description
 * @Author playboy
 * @Date 2021/4/10 4:50 下午
 * @Version 1.0
 **/
public class DefaultResultHandler implements ResultHandler{
    @Override
    public <T> List<T> handleResultSet(MapperStatement mapperStatement, ResultSet resultSet) throws Exception {
        String resultType = mapperStatement.getResultType();
        List<T> list = new ArrayList<>();
        if(resultType!=null){
            Class<?> resultClass = Class.forName(resultType);
            //处理结果集
            while (resultSet.next()){
                Object result = resultClass.newInstance();
                list.add((T)result);
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                for (int i = 0; i < columnCount; i++) {
                    String columnName = metaData.getColumnName(i+1);
                    columnName = columnName.toLowerCase();
                    PropertyDescriptor propDesc=new PropertyDescriptor(columnName,resultClass);
                    Method writeMethod = propDesc.getWriteMethod();
                    writeMethod.invoke(result,resultSet.getObject(columnName));
                }
            }
        }
        return list;
    }
}
