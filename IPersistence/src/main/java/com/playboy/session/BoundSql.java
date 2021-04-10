package com.playboy.session;

import com.playboy.util.ParameterMapping;

import java.util.List;

/**
 * @ClassName BoundSql
 * @Description
 * @Author playboy
 * @Date 2021/4/10 1:49 下午
 * @Version 1.0
 **/
public class BoundSql {

    private String sql;

    private List<ParameterMapping> parameterMappings;

    public BoundSql(String sql, List<ParameterMapping> parameterMappings) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public void setParameterMappings(List<ParameterMapping> parameterMappings) {
        this.parameterMappings = parameterMappings;
    }
}
