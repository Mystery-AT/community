package com.lrj.community.model;

//用户
public class User {
    private Integer id;//id，自增
    private String accountId;//接入github接口 返回github的账号id
    private String name;//返回github的账号名字
    private String token;//通过UUID生成，后续存入cookie中
    private Long gmtCreate;//创建时间
    private Long gmtModified;//修改时的时间---第一次生成是和创建时间一致
    private String avatarUrl;//github账号的图片地址

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", accountId='" + accountId + '\'' +
                ", name='" + name + '\'' +
                ", token='" + token + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}
