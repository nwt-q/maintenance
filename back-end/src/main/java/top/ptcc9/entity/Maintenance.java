package top.ptcc9.entity;

import top.ptcc9.annotations.FieldsDes;

public class Maintenance {

    private String id;

    @FieldsDes(desc = "姓名")
    private String username;

    @FieldsDes(desc = "联系方式")
    private String mobile;

    @FieldsDes(desc = "地址")
    private String address;

    @FieldsDes(desc = "邮箱")
    private String mailbox;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMailbox() {
        return mailbox;
    }

    public void setMailbox(String mailbox) {
        this.mailbox = mailbox;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
