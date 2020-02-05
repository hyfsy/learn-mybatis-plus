package com.hyf.mybatis.test;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hyf.mybatis.mapper.UserMapper;
import com.hyf.mybatis.pojo.User;
import com.hyf.mybatis.util.MPGeneratorUtil;
import com.hyf.mybatis.util.MybatisPlusUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestHello {

    static {
        // 注册需要使用的mapper
        MybatisPlusUtil.addMapper(UserMapper.class);
    }

    @Test
    public void testHelloWorld() {
        try (SqlSession sqlSession = MybatisPlusUtil.getSqlSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            List<User> users = mapper.selectList(null);
            System.out.println(users);
        }
    }

    @Test
    public void testPhysicsPage() {
        try (SqlSession sqlSession = MybatisPlusUtil.getSqlSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            // 物理分页，先查询出所有数据，再进行分页
            List<User> users = mapper.selectPage(new Page<>(2, 1), null);
            System.out.println(users);
        }
    }

    @Test
    public void testEntityWrapper() {
        // 查询 名称有"张"字并且年龄大于13 或者 名称有"王"字并且年龄小于15
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.like("name", "张").ge("age", 13);
        // or -> ( or )
        // orNew -> () or ()
        wrapper.orNew();
        wrapper.like("name", "王").le("age", 15);

        try (SqlSession sqlSession = MybatisPlusUtil.getSqlSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            List<User> users = mapper.selectList(wrapper);
            System.out.println(users);
        }
    }

    @Test
    public void testCondition() {
        Wrapper condition = Condition.create()
                .like("name", "张").ge("age", 13)
                .orNew()
                .like("name", "王").le("age", 15);
        try (SqlSession sqlSession = MybatisPlusUtil.getSqlSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            List<User> users = mapper.selectList(condition);
            System.out.println(users);
        }
    }

    @Test
    public void testAR() {
        User user = new User();
        // 直接使用对象调用方法即可
        List<User> users = user.selectAll();
        System.out.println(users);
        // user.deleteById(); // 此处没有该id也会返回true
    }

    /**
     * AR模式selectPage会返回一个Page对象
     */
    @Test
    public void testARPage() {
        User user = new User();
        Page<User> userPage = user.selectPage(new Page<>(2, 1), null);
        System.out.println(userPage);
        int limit = userPage.getLimit();
        int offset = userPage.getOffset();
        long total = userPage.getTotal();
        int size = userPage.getSize();
        System.out.println(limit);
        System.out.println(offset);
        System.out.println(total);
        System.out.println(size);
    }

}
