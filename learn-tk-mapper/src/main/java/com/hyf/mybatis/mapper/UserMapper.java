package com.hyf.mybatis.mapper;

import com.hyf.mybatis.pojo.User;
import tk.mybatis.mapper.common.Mapper;

/**
 * 继承Mapper接口即可使用一些通过CRUD方法
 */
public interface UserMapper extends Mapper<User> {

}
