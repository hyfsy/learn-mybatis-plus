package com.hyf.mybatis.mapper;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

/**
 * 自定义元数据处理器
 */
public class MyMetaObjectHandler extends MetaObjectHandler {

    /**
     * 插入操作 自动填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        // 为实体对象属性名称
        Object value = getFieldValByName("userName", metaObject);
        if(value == null){
            System.out.println("=====插入执行字段自动填充=====");
            setFieldValByName("userName", "baB_hyf", metaObject);
        }
    }

    /**
     * 更新操作 自动填充
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        // ......
    }
}
