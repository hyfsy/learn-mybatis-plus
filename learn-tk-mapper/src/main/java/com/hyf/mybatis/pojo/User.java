package com.hyf.mybatis.pojo;

import javax.persistence.*;

@Table(name = "mapper_user")
public class User {

    @Id // 否则 xxxByPrimaryKey 会 where 所有属性
    @GeneratedValue(strategy = GenerationType.IDENTITY) // mysql insert会获取自增主键
                                                        // oracle + generator="select seq from dual"
    @Column(name = "id")
    private Integer userId; // 自动： userId(实体属性) -> user_id(数据库列名)
    @Column(name = "name")
    private String userName;
    @Column(name = "age")
    private Integer userAge;

    public User() {
    }

    public User(Integer userId, String userName, Integer userAge) {
        this.userId = userId;
        this.userName = userName;
        this.userAge = userAge;
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

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userAge=" + userAge +
                '}';
    }
}

