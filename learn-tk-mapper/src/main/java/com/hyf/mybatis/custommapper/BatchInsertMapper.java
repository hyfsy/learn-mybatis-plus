package com.hyf.mybatis.custommapper;

import org.apache.ibatis.annotations.InsertProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;

import java.util.List;

/**
 * 扩展的Mapper接口
 */
@RegisterMapper
public interface BatchInsertMapper<T> {

    @InsertProvider(type = BatchBaseInsertProvider.class, method = "dynamicSQL")
    Integer batchInsert(List<T> recordList);
}
