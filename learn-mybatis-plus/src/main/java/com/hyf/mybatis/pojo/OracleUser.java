package com.hyf.mybatis.pojo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.KeySequence;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * 指定使用：
 * 序列名称为：seq_mp_test_user
 * 填充的字段类型：Integer
 */
@TableName("mp_test_user")
@KeySequence(value = "seq_mp_test_user", clazz = Integer.class)
public class OracleUser extends Model<OracleUser> {

    /**
     * 主键策略指定为用户输入形式
     */
    @TableId(type = IdType.INPUT)
    private Integer id;
    private String name;

    public OracleUser() {
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public OracleUser(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "OracleUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
