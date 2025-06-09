package top.ptcc9.entity;


import top.ptcc9.annotations.FieldsDes;

public class Equipment {

    private String id;

    @FieldsDes(desc = "设备名")
    private String name;

    @FieldsDes(desc = "设备状态信息")
    private String type;

    @FieldsDes(desc = "位置")
    private String location;

    @FieldsDes(desc = "状态")
    private String status;

    @FieldsDes(desc = "维修人员列表")
    private String userIds;

    @FieldsDes(desc = "电压")
    private Integer current;

    @FieldsDes(desc = "电流")
    private Integer voltage;

    private Long createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getVoltage() {
        return voltage;
    }

    public void setVoltage(Integer voltage) {
        this.voltage = voltage;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
