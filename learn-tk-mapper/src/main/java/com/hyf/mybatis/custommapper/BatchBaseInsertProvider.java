package com.hyf.mybatis.custommapper;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;

import java.util.Set;

/**
 * 创建批量插入模板
 */
public class BatchBaseInsertProvider extends MapperTemplate {

    public BatchBaseInsertProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * 1. 自定义 Provider 要继承 MapperTemplate
     * 2. 方法名称要和接口方法名称相同
     * 3. 参数名称必须为 MappedStatement
     * 4. 必须返回 String
     *
     * mysql支持此批量插入方式需要在url后面添加 allowMultiQueries=true
     *
     */
    public String batchInsert(MappedStatement ms) {

        // 获取到实体类类型
        Class<?> entityClass = super.getEntityClass(ms);
        // 获取实体对应表名
        String tableName = super.tableName(entityClass);
        // SqlHelper获取所有属性的对应列名
        Set<EntityColumn> columns = EntityHelper.getColumns(entityClass);

        StringBuilder dynamicSql = new StringBuilder();
        dynamicSql.append("<foreach collection=\"list\" item=\"record\" separator=\";\">");
        dynamicSql.append("insert into ").append(tableName).append("(");

        StringBuilder columnCollect = new StringBuilder();
        StringBuilder holderCollect = new StringBuilder();
        for (EntityColumn column : columns) {
            // 数据库列名
            String columnName = column.getColumn();
            // #{record.id,javaType=Integer.class...}
            String columnHolder = column.getColumnHolder("record");

            columnCollect.append(columnName).append(",");
            holderCollect.append(columnHolder).append(",");
        }

        dynamicSql.append(columnCollect.deleteCharAt(columnCollect.length() - 1)).append(") values (");
        dynamicSql.append(holderCollect.deleteCharAt(holderCollect.length() - 1)).append(")");
        dynamicSql.append("</foreach>");

        return dynamicSql.toString();
    }
}
