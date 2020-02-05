package com.hyf.mybatis.util;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MPGeneratorUtil {

    private static Properties prop = new Properties();
    private static String projectPath = System.getProperty("user.dir");

    private static GlobalConfig globalConfig = new GlobalConfig();
    private static DataSourceConfig dataSourceConfig = new DataSourceConfig();
    private static PackageConfig packageConfig = new PackageConfig();
    private static StrategyConfig strategyConfig = new StrategyConfig();
    private static InjectionConfig injectionConfig = null;

    static{
        String path = projectPath + "/src/main/resources/jdbc.properties";
        try {
            prop.load(new FileReader(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        initGlobalConfig();
        initDataSourceConfig();
        initPackageConfig();
        initStrategyConfig();
        initInjectionConfig();
    }

    /**
     * 调用生成文件
     */
    public static void generate() {

        // 整合配置
        AutoGenerator generator = new AutoGenerator();
        // 设置模板引擎
        generator.setTemplateEngine(new FreemarkerTemplateEngine());

        generator.setGlobalConfig(globalConfig);
        generator.setDataSource(dataSourceConfig);
        generator.setPackageInfo(packageConfig);
        generator.setStrategy(strategyConfig);
        generator.setCfg(injectionConfig);

        generator.execute();

    }

    /**
     * 添加生成文件的表
     */
    public static void addTable(String tableName){
        strategyConfig.setInclude(tableName); // 生成的表名，多个可用[,]分割
    }

    /**
     * 全局配置
     */
    private static void initGlobalConfig() {
        globalConfig.setActiveRecord(true); // 开启AR模式
        globalConfig.setAuthor("baB_hyf"); // 设置类作者
        globalConfig.setOutputDir(projectPath + File.separator + prop.getProperty("dir.java")); // 设置文件输出的位置
        globalConfig.setFileOverride(true); // 相同文件名会覆盖
        globalConfig.setIdType(IdType.AUTO); //主键策略
        globalConfig.setServiceName("%sService"); // 不让生成的文件开头有 I
        globalConfig.setBaseResultMap(true); // 生成简单resultMap映射
        globalConfig.setBaseColumnList(true); // 生成所有列名的sql片段
        globalConfig.setOpen(false);
    }

    /**
     * 数据源配置
     */
    private static void initDataSourceConfig() {
        dataSourceConfig.setDbType(DbType.MYSQL);
        dataSourceConfig.setUrl(prop.getProperty("jdbc.mysql.url"));
        dataSourceConfig.setDriverName(prop.getProperty("jdbc.mysql.driver"));
        dataSourceConfig.setUsername(prop.getProperty("jdbc.mysql.username"));
        dataSourceConfig.setPassword(prop.getProperty("jdbc.mysql.password"));
    }

    /**
     * 包名策略配置
     */
    private static void initPackageConfig() {
        packageConfig.setParent(prop.getProperty("dir.base.package"));
        packageConfig.setEntity("pojo"); // entity
        // packageConfig.setMapper("mapper");
        // packageConfig.setXml("mapper.xml");
        packageConfig.setController("controller"); // web
        // packageConfig.setService("service");
        // packageConfig.setServiceImpl("service.impl");
        // packageConfig.setModuleName("testmpmbg");
    }

    /**
     * 策略配置
     */
    private static void initStrategyConfig() {
        strategyConfig.setCapitalMode(true); // 全局大写命名
        strategyConfig.setDbColumnUnderline(false); // 指定表名 字段名是否使用下划线
        strategyConfig.setTablePrefix("mp_"); // 可映射的表名前缀
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        // 公共父类
        // strategyConfig.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        // strategyConfig.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        strategyConfig.setEntityLombokModel(false);
        strategyConfig.setRestControllerStyle(true);
        // 写于父类中的公共字段
        strategyConfig.setSuperEntityColumns("id");
        strategyConfig.setControllerMappingHyphenStyle(true);
        // strategyConfig.setInclude("class"); // 生成的表名
    }

    /**
     * 输出配置
     */
    private static void initInjectionConfig() {

        // 自定义配置
        injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> fileOutConfigList = new ArrayList<>();
        // 自定义配置会被优先输出
        fileOutConfigList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + File.separator + prop.getProperty("dir.resources") + "/mapper/" + tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        injectionConfig.setFileOutConfigList(fileOutConfigList);
    }
}
