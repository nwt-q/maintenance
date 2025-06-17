package top.ptcc9.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 
 * @TableName record
 */
@TableName(value ="record")
public class Record implements Serializable {

    /**
     * 
     */
    @TableId
    private String id;

    /**
     * 
     */
    private String equipmentId;

    /**
     * 
     */
    private String equipmentName;

    /**
     * 
     */
    private String userId;

    /**
     * 
     */
    private String categoryId;

    /**
     * 
     */
    private String categoryName;

    /**
     * 
     */
    private String maintenanceId;

    /**
     * 
     */
    private String maintenanceName;

    /**
     * 
     */
    private String cause;

    /**
     * 
     */
    private Long createTime;

    /**
     * 
     */
    private String pic;

    /**
     * 任务开始时间（维修人员接单时间）
     */
    private Long startTime;

    /**
     * 任务完成时间
     */
    private Long finishTime;

    /**
     * 超时阈值（分钟）
     */
    private Integer timeoutMinutes;

    /**
     * 任务状态
     */
    private String status;

    /**
     * 紧急程度，紧急=管理员发起，普通=用户发起
     */
    private String emergencyLevel;

    /**
     * 用户评价内容，仅普通工单有
     */
    private String evaluateContent;

    /**
     * 用户评价星级，1~5星，仅普通工单有
     */
    private Integer evaluateStar;

    /**
     * 评价时间，仅普通工单有
     */
    private Long evaluateTime;

    /**
     * 发起人用户ID，普通工单绑定用户，紧急工单不绑定
     */
    private String initiatorId;

    private Integer isTimeout;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Integer getIsTimeout() {
        return isTimeout;
    }

    public void setIsTimeout(Integer isTimeout) {
        this.isTimeout = isTimeout;
    }

    /**
     * 
     */
    public String getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     */
    public String getEquipmentId() {
        return equipmentId;
    }

    /**
     * 
     */
    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    /**
     * 
     */
    public String getEquipmentName() {
        return equipmentName;
    }

    /**
     * 
     */
    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    /**
     * 
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     * 
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * 
     */
    public String getMaintenanceId() {
        return maintenanceId;
    }

    /**
     * 
     */
    public void setMaintenanceId(String maintenanceId) {
        this.maintenanceId = maintenanceId;
    }

    /**
     * 
     */
    public String getMaintenanceName() {
        return maintenanceName;
    }

    /**
     * 
     */
    public void setMaintenanceName(String maintenanceName) {
        this.maintenanceName = maintenanceName;
    }

    /**
     * 
     */
    public String getCause() {
        return cause;
    }

    /**
     * 
     */
    public void setCause(String cause) {
        this.cause = cause;
    }

    /**
     *
     */
    public LocalDateTime getCreateTime() {
        if (createTime == null) return null;
        return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(createTime),
                ZoneId.systemDefault()
        );
    }

    /**
     *
     */
    public Long getCreateTime(Integer y) {
        return createTime;
    }

    /**
     * 
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     */
    public String getPic() {
        return pic;
    }

    /**
     * 
     */
    public void setPic(String pic) {
        this.pic = pic;
    }

    /**
     * 任务开始时间（维修人员接单时间）
     */
    public LocalDateTime getStartTime(Integer ct) {
        if (startTime == null) return null;
        return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(startTime),
                ZoneId.systemDefault()
        );
    }

    public Long getStartTime() {
        return startTime;
    }

    /**
     * 任务开始时间（维修人员接单时间）
     */
    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    /**
     * 任务完成时间
     */
    public LocalDateTime getFinishTime(Integer ct) {
        if (finishTime == null) return null;
        return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(finishTime),
                ZoneId.systemDefault()
        );
    }

    /**
     * 任务完成时间
     */
    public Long getFinishTime() {
        return finishTime;
    }


    /**
     * 任务完成时间
     */
    public void setFinishTime(Long finishTime) {
        this.finishTime = finishTime;
    }

    /**
     * 超时阈值（分钟）
     */
    public Integer getTimeoutMinutes() {
        return timeoutMinutes;
    }

    /**
     * 超时阈值（分钟）
     */
    public void setTimeoutMinutes(Integer timeoutMinutes) {
        this.timeoutMinutes = timeoutMinutes;
    }

    /**
     * 任务状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 任务状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 紧急程度，紧急=管理员发起，普通=用户发起
     */
    public String getEmergencyLevel() {
        return emergencyLevel;
    }

    /**
     * 紧急程度，紧急=管理员发起，普通=用户发起
     */
    public void setEmergencyLevel(String emergencyLevel) {
        this.emergencyLevel = emergencyLevel;
    }

    /**
     * 用户评价内容，仅普通工单有
     */
    public String getEvaluateContent() {
        return evaluateContent;
    }

    /**
     * 用户评价内容，仅普通工单有
     */
    public void setEvaluateContent(String evaluateContent) {
        this.evaluateContent = evaluateContent;
    }

    /**
     * 用户评价星级，1~5星，仅普通工单有
     */
    public Integer getEvaluateStar() {
        return evaluateStar;
    }

    /**
     * 用户评价星级，1~5星，仅普通工单有
     */
    public void setEvaluateStar(Integer evaluateStar) {
        this.evaluateStar = evaluateStar;
    }

    /**
     * 评价时间，仅普通工单有
     */
    public Long getEvaluateTime() {
        return evaluateTime;
    }

    /**
     * 评价时间，仅普通工单有
     */
    public void setEvaluateTime(Long evaluateTime) {
        this.evaluateTime = evaluateTime;
    }

    /**
     * 发起人用户ID，普通工单绑定用户，紧急工单不绑定
     */
    public String getInitiatorId() {
        return initiatorId;
    }

    /**
     * 发起人用户ID，普通工单绑定用户，紧急工单不绑定
     */
    public void setInitiatorId(String initiatorId) {
        this.initiatorId = initiatorId;
    }
}