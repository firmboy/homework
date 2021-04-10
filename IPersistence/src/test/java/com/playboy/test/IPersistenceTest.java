package com.playboy.test;

import com.playboy.io.Resuouces;
import com.playboy.mapper.UserMapper;
import com.playboy.pojo.User;
import com.playboy.session.SqlSession;
import com.playboy.session.SqlSessionFacotry;
import com.playboy.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

/**
 * @ClassName IPersistenceTest
 * @Description
 * @Author playboy
 * @Date 2021/4/10 10:23 上午
 * @Version 1.0
 **/
public class IPersistenceTest {

    /**
     * 为了测试方便,数据库使用了H2数据库,
     * 直接执行测试selectOne、selectList、insert、update、delete、deleteById即可，
     * 控制台会打印出，方法执行前后数据库中的数据
     */

    SqlSession sqlSession;

    @Before
    public void testBefore() throws Exception{
        InputStream resourceAsStream = Resuouces.getResourceAsStream("sqlMapConfig.xml");

        SqlSessionFacotry build = new SqlSessionFactoryBuilder().build(resourceAsStream);

        sqlSession = build.openSession();

        h2();//h2数据库，如果替换，注释掉即可，然后更换sqlMapConfig.xml中的dataSource中的url即可

        System.out.println("执行前结果======");
        testSelectList();
        System.out.println("选中方法执行结果======");
    }
    
    @Test
    public void testSelectList() throws Exception {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.selectList();
        for (User user1 : users) {
            System.out.println(user1);
        }
    }
    
    @Test
    public void testSelectOne() throws Exception {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setId(1);
        user.setUsername("lucy");
        User user1 = mapper.selectOne(user);
        System.out.println(user1);
    }


    @Test
    public void testInsert(){
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setId(3);
        user.setUsername("zhaosi");
        user.setPassword("123");
        user.setBirthday("2020-12-22");
        int insert = mapper.insert(user);
        System.out.println(insert);
    }

    @Test
    public void testUpdate(){
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setId(1);
        user.setUsername("laoqi");
        user.setPassword("123");
        user.setBirthday("2020-12-22");
        int insert = mapper.update(user);
        System.out.println(insert);
    }

    @Test
    public void testDelete(){
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setId(1);
        user.setUsername("laoqi");
        user.setPassword("123");
        user.setBirthday("2020-12-22");
        int insert = mapper.delete(user);
        System.out.println(insert);
    }
    
    @Test
    public void testDeletById(){
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int i = mapper.deleteById(2);
        System.out.println(i);
    }

    @After
    public void testAfter() throws Exception {
        System.out.println("执行后结果======");
        testSelectList();
        sqlSession.commit();
    }

    final static String CREATE_TABLE = "create table user\n" +
            "(" +
            "    id       int auto_increment\n" +
            "        primary key,\n" +
            "    username varchar(50) null,\n" +
            "    password varchar(50) null,\n" +
            "    birthday varchar(50) null\n" +
            ")";

    final static String INSERT1 = "INSERT INTO user (id, username, password, birthday) VALUES (1, 'lucy', '123', '2019-12-12')";
    final static String INSERT2 =  "INSERT INTO user (id, username, password, birthday) VALUES (2, 'tom', '123', '2019-12-12')";

    private void h2()throws Exception{
        Connection connection = sqlSession.getConfiguration().getDataSource().getConnection();
        Statement statement = connection.createStatement();
        statement.execute(CREATE_TABLE);
        statement.executeUpdate(INSERT1);
        statement.executeUpdate(INSERT2);
        statement.close();
        connection.close();
    }
}
