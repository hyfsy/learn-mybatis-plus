package com.hyf.mybatis.pojo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

@TableName("mp_user")
public class User extends Model<User> {

    /**
     * 插入操作会默认获取主键值
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer userId; // 默认自动转换 userId -> user_id
    @TableField("name")
    private String userName;
    @TableField("age")
    private Integer userAge;
    @Version // 设置此数据库对应列为 乐观锁判断的版本列
    private Integer version;

    public User() {
    }

    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

    public User(Integer userId, String userName, Integer userAge) {
        this.userId = userId;
        this.userName = userName;
        this.userAge = userAge;
    }

    public User(Integer userId, String userName, Integer userAge, Integer version) {
        this.userId = userId;
        this.userName = userName;
        this.userAge = userAge;
        this.version = version;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userAge=" + userAge +
                '}';
    }
}

