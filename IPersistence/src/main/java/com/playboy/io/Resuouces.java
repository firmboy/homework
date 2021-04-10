package com.playboy.io;

import java.io.InputStream;

/**
 * @ClassName Resuouces
 * @Description
 * @Author playboy
 * @Date 2021/4/10 10:27 上午
 * @Version 1.0
 **/
public class Resuouces {

    public static InputStream getResourceAsStream(String path){
        return Resuouces.class.getClassLoader().getResourceAsStream(path);
    }

}
