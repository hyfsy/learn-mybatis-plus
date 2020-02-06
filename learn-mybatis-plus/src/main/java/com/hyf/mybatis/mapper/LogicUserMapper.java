package com.hyf.mybatis.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hyf.mybatis.pojo.LogicUser;

/**
 * 继承 BaseMapper 接口即可
 */
public interface LogicUserMapper extends BaseMapper<LogicUser> {
    int deleteAll();
}
