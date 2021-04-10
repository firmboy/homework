package com.playboy.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.playboy.io.Resuouces;
import com.playboy.pojo.Configuration;
import com.playboy.pojo.MapperStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @ClassName XmlConfigBuilder
 * @Description
 * @Author playboy
 * @Date 2021/4/10 10:31 上午
 * @Version 1.0
 **/
public class XmlConfigBuilder {

    private InputStream in;

    private Configuration configuration = new Configuration();

    private Element rootElement;

    public XmlConfigBuilder(InputStream in) {
        this.in = in;
    }

    public Configuration parse() throws DocumentException {
        Document read = new SAXReader().read(in);
        //读取到根标签<configuration>
        rootElement = read.getRootElement();

        //解析数据库配置
        parseDataSource("//dataSource");

        //解析mapper文件
        parseMappers("//mappers");
        return configuration;
    }

    private void parseDataSource(String s) {
        List<Element> list = rootElement.selectNodes(s);
        Element element = list.get(0);
        List<Element> properties = element.selectNodes("//property");
        Properties properties1 = new Properties();
        for (Element property : properties) {
            properties1.put(property.attributeValue("name"),property.attributeValue("value"));
        }

        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(properties1.getProperty("driverClassName"));
        druidDataSource.setUrl(properties1.getProperty("jdbcUrl"));
        druidDataSource.setUsername(properties1.getProperty("username"));
        druidDataSource.setPassword(properties1.getProperty("password"));
        configuration.setDataSource(druidDataSource);
    }

    private void parseMappers(String s) throws DocumentException {
        List<Element> list = rootElement.selectNodes(s);
        Element element = list.get(0);

        List<Element> mappers = element.selectNodes("//mapper");
        List<Element> pack = element.selectNodes("//package");

        //通过mapper配置
        for (Element mapper : mappers) {
            String resource = mapper.attributeValue("resource");
            InputStream resourceAsStream = Resuouces.getResourceAsStream(resource);
            XmlMapperBuilder xmlMapperBuilder = new XmlMapperBuilder(resourceAsStream);
            Map<String, MapperStatement> parse = xmlMapperBuilder.parse();
            configuration.getMapperStatements().putAll(parse);
        }

        //通过package配置
    }

}
