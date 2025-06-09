package top.ptcc9.entity;


import top.ptcc9.annotations.FieldsDes;

public class Record {
    private String id;

    @FieldsDes(desc = "设备ID")
    private String equipmentId;

    @FieldsDes(desc = "设备名称")
    private String equipmentName;

    @FieldsDes(desc = "照片")
    private String pic;

    @FieldsDes(desc = "报备人ID")
    private String userId;

    @FieldsDes(desc = "类别ID")
    private String categoryId;

    @FieldsDes(desc = "维修类别")
    private String categoryName;

    @FieldsDes(desc = "维修人ID")
    private String maintenanceId;

    @FieldsDes(desc = "维修人")
    private String maintenanceName;

    @FieldsDes(desc = "维修记录")
    private String cause;

    private Long createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getMaintenanceId() {
        return maintenanceId;
    }

    public void setMaintenanceId(String maintenanceId) {
        this.maintenanceId = maintenanceId;
    }

    public String getMaintenanceName() {
        return maintenanceName;
    }

    public void setMaintenanceName(String maintenanceName) {
        this.maintenanceName = maintenanceName;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
