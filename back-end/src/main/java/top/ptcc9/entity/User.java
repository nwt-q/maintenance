package top.ptcc9.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import top.ptcc9.annotations.FieldsDes;

public class User {

    @TableId
    private String id;

    @FieldsDes(desc = "姓名")
    private String username;

    @FieldsDes(desc = "密码")
    private String password;

    @FieldsDes(desc = "身份")
    private String identity;

    @FieldsDes(desc = "手机号")
    private String mobile;

    @FieldsDes(desc = "邮箱")
    private String email;

    @FieldsDes(desc = "QQ号")
    private String qq;

    @FieldsDes(desc = "真实姓名")
    private String nickname;

    @FieldsDes(desc = "创建时间")
    private Long createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
