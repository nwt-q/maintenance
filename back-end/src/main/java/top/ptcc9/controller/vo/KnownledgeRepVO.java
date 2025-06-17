package top.ptcc9.controller.vo;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;


public class KnownledgeRepVO {

    private String name;

    private Integer  page;

    private Integer  pageSize;
//
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime startTime;
//
//
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime endTime;

    private Long startTime;
    private Long endTime;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
