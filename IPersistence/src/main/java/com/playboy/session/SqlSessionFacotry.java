package com.playboy.session;

import java.sql.SQLException;

/**
 * @ClassName SqlSessionFacotry
 * @Description
 * @Author playboy
 * @Date 2021/4/10 10:30 上午
 * @Version 1.0
 **/
public interface SqlSessionFacotry {

    public SqlSession openSession() throws SQLException;

}
