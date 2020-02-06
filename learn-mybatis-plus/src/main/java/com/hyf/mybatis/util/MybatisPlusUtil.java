package com.hyf.mybatis.util;

import com.baomidou.mybatisplus.MybatisConfiguration;
import com.baomidou.mybatisplus.MybatisMapperRegistry;
import com.baomidou.mybatisplus.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.LogicSqlInjector;
import com.baomidou.mybatisplus.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.plugins.SqlExplainInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import com.hyf.mybatis.mapper.MyAutoSqlInject;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.type.JdbcType;

import javax.sql.DataSource;
import java.io.Reader;

public class MybatisPlusUtil {

    private static final String MYBATIS_CONFIG = "conf/mybatis-config.xml";
    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            // 只是用来获取数据源
            Reader reader = Resources.getResourceAsReader(MYBATIS_CONFIG);
            DataSource dataSource = new SqlSessionFactoryBuilder().build(reader).getConfiguration().getEnvironment().getDataSource();

            // 使用通用mapper默认Configuration
            MybatisConfiguration mybatisConfiguration = getMybatisConfiguration();

            MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
            sqlSessionFactoryBean.setDataSource(dataSource);
            sqlSessionFactoryBean.setConfiguration(mybatisConfiguration);
            sqlSessionFactoryBean.setGlobalConfig(getGlobalConfiguration());
            sqlSessionFactoryBean.setPlugins(getInterceptors());

            // 获取 SqlSessionFactory 对象
            sqlSessionFactory = sqlSessionFactoryBean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 自动提交事务
     *
     * @return SqlSession对象
     */
    public static SqlSession getSqlSession() {
        return getSqlSession(true);
    }

    public static SqlSession getSqlSession(boolean isAutoCommit) {
        return sqlSessionFactory.openSession(isAutoCommit);
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    /**
     * 注册 mapper使用
     */
    public static void addMapper(Class<? extends BaseMapper<?>> mapperClass) {
        MybatisConfiguration configuration = (MybatisConfiguration) sqlSessionFactory.getConfiguration();
        configuration.addMapper(mapperClass);
    }


    // =====================================通用mapper所需配置===============================================


    private static MybatisConfiguration getMybatisConfiguration() {
        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        // mybatisConfiguration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        mybatisConfiguration.setMapUnderscoreToCamelCase(true);
        mybatisConfiguration.setJdbcTypeForNull(JdbcType.NULL);
        return mybatisConfiguration;
    }

    private static GlobalConfiguration getGlobalConfiguration() {
        GlobalConfiguration conf = new GlobalConfiguration(new LogicSqlInjector());
        conf.setLogicDeleteValue("-1");
        conf.setLogicNotDeleteValue("1");
        conf.setIdType(2);
        // 注册自定义sql注入器
        // conf.setSqlInjector(new MyAutoSqlInject());
        // 逻辑删除
        conf.setSqlInjector(new LogicSqlInjector());
        // conf.setMetaObjectHandler(new H2MetaObjectHandler());
        return conf;
    }

    private static Interceptor[] getInterceptors(){
        // CUD全表操作会报错
        SqlExplainInterceptor sqlExplainInterceptor = new SqlExplainInterceptor();
        sqlExplainInterceptor.setStopProceed(true);

        // 输出执行sql的一些信息
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        performanceInterceptor.setFormat(true);
        // performanceInterceptor.setMaxTime(5);

        return new Interceptor[]{
                // 分页插件
                new PaginationInterceptor(),
                // 执行分析插件
                sqlExplainInterceptor,
                // 性能分析插件
                performanceInterceptor,
                // 乐观锁插件
                new OptimisticLockerInterceptor()
        };
    }
}
