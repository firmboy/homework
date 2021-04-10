package com.playboy.config;

import com.playboy.pojo.MapperStatement;
import com.playboy.pojo.SqlType;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName XmlMapperBuilder
 * @Description
 * @Author playboy
 * @Date 2021/4/10 11:02 上午
 * @Version 1.0
 **/
public class XmlMapperBuilder {

    private InputStream in;

    private String namespace;

    private Map<String,MapperStatement> mapperStatements = new HashMap<>();

    public XmlMapperBuilder(InputStream in) {
        this.in = in;
    }

    public Map<String,MapperStatement> parse() throws DocumentException {
        Document read = new SAXReader().read(in);
        Element rootElement = read.getRootElement();
        namespace = rootElement.attributeValue("namespace");
        //解析select标签
        List<Element> selects = rootElement.selectNodes("//select");
        for (Element select : selects) {
            parseSql(select,SqlType.SELECT);
        }
        //解析update标签
        List<Element> updates = rootElement.selectNodes("//update");
        for (Element update : updates) {
            parseSql(update,SqlType.UPDATE);
        }
        //解析insert标签
        List<Element> inserts = rootElement.selectNodes("//insert");
        for (Element insert : inserts) {
            parseSql(insert,SqlType.INSERT);
        }
        //解析delete标签
        List<Element> deletes = rootElement.selectNodes("//delete");
        for (Element delete : deletes) {
            parseSql(delete,SqlType.DELETE);
        }
        return mapperStatements;
    }

    private void parseSql(Element element,SqlType sqlType) {
        MapperStatement mapperStatement = new MapperStatement();

        if(element.attributeValue("id")==null){
            throw new RuntimeException("sql片段必须包含id属性");
        }
        mapperStatement.setId(namespace+"."+element.attributeValue("id"));
        mapperStatement.setParameterType(element.attributeValue("paramterType"));
        mapperStatement.setResultType(element.attributeValue("resultType"));
        mapperStatement.setSql(element.getTextTrim());
        mapperStatement.setSqlType(sqlType);
        mapperStatements.put(mapperStatement.getId(),mapperStatement);
    }

}
