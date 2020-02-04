package com.hyf.mybatis.test;

import com.hyf.mybatis.mapper.UserMapper;
import com.hyf.mybatis.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class TestHello {

    private ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
    private UserMapper userMapper = ioc.getBean(UserMapper.class);

    @Test
    void testHello(){
        List<User> users = userMapper.selectAll();
        System.out.println(users);
    }
    
}
