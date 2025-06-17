package top.ptcc9.controller.vo;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class WorkOrderReqVO {

    private String id;

    private String mobile;

    private String deviceName;

    private String description;

    private String createTime;

    // 紧急程度
    private String emergencyLevel;

    // 时限制
    private String timeoutMinutes;

    // 原因
    private String categoryName;

    private String photo;

    private String status;

    private String startTime;

    private String endTime;

    private Integer  page;

    private Integer  pageSize;

    private String address;

    public String getEmergencyLevel() {
        return emergencyLevel;
    }

    public void setEmergencyLevel(String emergencyLevel) {
        this.emergencyLevel = emergencyLevel;
    }

    public String getTimeoutMinutes() {
        return timeoutMinutes;
    }

    public void setTimeoutMinutes(String timeoutMinutes) {
        this.timeoutMinutes = timeoutMinutes;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getStartTime(Long t) {
        if (startTime == null) return null;
        return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(Long.parseLong(startTime)),
                ZoneId.systemDefault()
        );
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public LocalDateTime getEndTime(Long y) {
        if (endTime == null) return null;
        return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(Long.parseLong(endTime)),
                ZoneId.systemDefault()
        );
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime.toString();
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime.toString();
    }
}
