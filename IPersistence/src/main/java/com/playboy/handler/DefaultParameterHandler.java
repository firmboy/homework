package com.playboy.handler;

import com.playboy.pojo.MapperStatement;
import com.playboy.session.BoundSql;
import com.playboy.util.GenericTokenParser;
import com.playboy.util.ParameterMapping;
import com.playboy.util.ParameterMappingTokenHandler;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName DefaultParameterHandler
 * @Description
 * @Author playboy
 * @Date 2021/4/10 4:50 下午
 * @Version 1.0
 **/
public class DefaultParameterHandler implements ParameterHandler{

    private Connection connection;

    static private List<String> basicTypes = new ArrayList<>();

    static {
        basicTypes.add("int");
        basicTypes.add("short");
        basicTypes.add("long");
        basicTypes.add("float");
        basicTypes.add("double");
        basicTypes.add("char");
    }

    public DefaultParameterHandler(Connection connection) {
        this.connection = connection;
    }



    @Override
    public PreparedStatement getPreparedStatement(MapperStatement mapperStatement, Object[] params) throws Exception {
        String parameterType = mapperStatement.getParameterType();

        BoundSql boundSql = getBoundSql(mapperStatement.getSql());
        //参数设置
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSql());
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if(basicTypes.contains(parameterType)){
            //针对传入的参数不是对象的情况，并且只能包含一个
            preparedStatement.setObject(1,params[0]);
        }else{
            if(params!=null&&params.length!=0){
                if(parameterType!=null){
                    Class<?> aClass = Class.forName(parameterType);
                    Object param = params[0];
                    for (int i = 0; i < parameterMappings.size(); i++) {
                        Field field = aClass.getDeclaredField(parameterMappings.get(i).getContext());
                        field.setAccessible(true);
                        Object o = field.get(param);
                        preparedStatement.setObject(i+1,o);
                    }
                }
            }
        }
        return preparedStatement;
    }

    private BoundSql getBoundSql(String sql) {
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        String parse = genericTokenParser.parse(sql);
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();
        BoundSql boundSql = new BoundSql(parse, parameterMappings);
        return boundSql;
    }
}
