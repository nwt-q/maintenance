package top.ptcc9.controller.vo;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class WorkOrderReqVO {

    private Long id;

    private String deviceName;

    private String description;

    private String createTime;

    private String photo;

    private String status;

    private Long startTime;

    private Long endTime;

    private Integer  page;

    private Integer  pageSize;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public LocalDateTime getStartTime() {
        if (startTime == null) return null;
        return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(startTime),
                ZoneId.systemDefault()
        );
    }

    public LocalDateTime getEndTime() {
        if (endTime == null) return null;
        return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(endTime),
                ZoneId.systemDefault()
        );
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
}
