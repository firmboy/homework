package com.playboy.session;

import com.playboy.config.XmlConfigBuilder;
import com.playboy.pojo.Configuration;
import org.dom4j.DocumentException;

import java.io.InputStream;

/**
 * @ClassName SqlSessionFactoryBuilder
 * @Description
 * @Author playboy
 * @Date 2021/4/10 10:30 上午
 * @Version 1.0
 **/
public class SqlSessionFactoryBuilder {

    public SqlSessionFacotry build(InputStream in) throws DocumentException {
        //解析核心配置文件
        XmlConfigBuilder xmlConfigBuilder = new XmlConfigBuilder(in);
        Configuration configuration = xmlConfigBuilder.parse();

        //创建SqlSessonFactory
        SqlSessionFacotry sqlSessionFacotry = new DefaultSqlSessionFactory(configuration);
        return sqlSessionFacotry;
    }


}
