package com.hyf.mybatis.test;

import com.hyf.mybatis.mapper.UserMapper;
import com.hyf.mybatis.pojo.User;
import com.hyf.mybatis.util.MybatisPlusUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestHello {

    static{
        // 注册需要使用的mapper
        MybatisPlusUtil.addMapper(UserMapper.class);
    }

    @Test
    public void testHelloWorld () {
        try(SqlSession sqlSession = MybatisPlusUtil.getSqlSession()){
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            List<User> users = mapper.selectList(null);
            System.out.println(users);
        }
    }

}
