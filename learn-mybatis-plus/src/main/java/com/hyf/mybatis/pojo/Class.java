package com.hyf.mybatis.pojo;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author baB_hyf
 * @since 2020-02-05
 */
@TableName("mp_class")
public class Class extends Model<Class> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "c_id", type = IdType.AUTO)
    private Integer cId;

    private String name;

    private Integer age;

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    protected Serializable pkVal() {
        return this.cId;
    }

    @Override
    public String toString() {
        return "Class{" +
        "cId=" + cId +
        ", name=" + name +
        ", age=" + age +
        "}";
    }
}
