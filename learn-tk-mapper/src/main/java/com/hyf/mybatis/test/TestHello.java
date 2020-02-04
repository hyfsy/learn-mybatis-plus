package com.hyf.mybatis.test;

import com.hyf.mybatis.mapper.TestBatchInsertMapper;
import com.hyf.mybatis.mapper.UserMapper;
import com.hyf.mybatis.pojo.User;
import com.hyf.mybatis.util.MapperUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

public class TestHello {

    private ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
    private UserMapper userMapper = ioc.getBean(UserMapper.class);
    private TestBatchInsertMapper batchInsertMapper = ioc.getBean(TestBatchInsertMapper.class);

    /**
     * 结合spring方式执行
     */
    @Test
    void testHello() {
        List<User> users = userMapper.selectAll();
        System.out.println(users);
    }

    /**
     * java方式执行
     */
    @Test
    public void testJavaInvoke() {
        try (SqlSession sqlSession = MapperUtil.getSqlSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            List<User> users = mapper.selectAll();
            System.out.println(users);
        }
    }

    /**
     * Example查询，比较对象的字符串为实体类属性名称，而不是数据库列名
     */
    @Test
    public void testExample() {
        // 查询 名称有"张"字并且年龄大于13 或者 名称有"王"字并且年龄小于15
        Example example = new Example(User.class);

        // 第一个条件对象必须通过createCriteria方法创建
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("userName", "%张%").andGreaterThan("userAge", 13);

        // 获取第二个条件对象
        Example.Criteria criteria2 = example.or();
        criteria2.andLike("userName", "%王%").andLessThan("userAge", 15);

        List<User> users = userMapper.selectByExample(example);
        System.out.println(users);
    }

    @Test
    public void testBatchInsert () {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User(null, "测试批量插入01", null));
        users.add(new User(null, "测试批量插入02", null));
        Integer success = batchInsertMapper.batchInsert(users);
        System.out.println(success);
    }
}
