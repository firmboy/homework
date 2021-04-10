package com.playboy.pojo;

/**
 * @ClassName MapperStatement
 * @Description
 * @Author playboy
 * @Date 2021/4/10 10:45 上午
 * @Version 1.0
 **/
public class MapperStatement {

    private String id;

    private String resultType;

    private String parameterType;

    private String sql;

    private SqlType sqlType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public SqlType getSqlType() {
        return sqlType;
    }

    public void setSqlType(SqlType sqlType) {
        this.sqlType = sqlType;
    }
}
