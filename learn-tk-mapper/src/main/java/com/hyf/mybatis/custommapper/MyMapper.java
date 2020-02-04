package com.hyf.mybatis.custommapper;

import tk.mybatis.mapper.common.RowBoundsMapper;
import tk.mybatis.mapper.common.base.BaseSelectMapper;

/**
 * 注册自定义Mapper接口
 * @param <T>
 */
@tk.mybatis.mapper.annotation.RegisterMapper
public interface MyMapper<T> extends BaseSelectMapper<T>, RowBoundsMapper<T> {
}
