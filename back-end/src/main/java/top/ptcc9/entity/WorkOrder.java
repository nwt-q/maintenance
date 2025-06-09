package top.ptcc9.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 维修工单表
 * @TableName work_order
 */
@TableName(value ="work_order")
public class WorkOrder implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private String deviceName;

    /**
     * 
     */
    private String description;

    /**
     * 处理中→已完成
     */
    private String status;

    /**
     * 
     */
    private LocalDateTime createTime;

    /**
     * 完成时间
     */
    private String finishTime;

    /**
     * 故障图片
     */
    private String photoUrl;

    /**
     * 维修人员id
     */
    private String maintenanceId;

    /**
     * 材料使用
     */
    private String materialUsed;

    /**
     * 维修时间 yyyy-MM-dd
     */
    private String workOrderDate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * 
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /**
     * 
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 处理中→已完成
     */
    public String getStatus() {
        return status;
    }

    /**
     * 处理中→已完成
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * 
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * 完成时间
     */
    public String getFinishTime() {
        return finishTime;
    }

    /**
     * 完成时间
     */
    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    /**
     * 故障图片
     */
    public String getPhotoUrl() {
        return photoUrl;
    }

    /**
     * 故障图片
     */
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    /**
     * 维修人员id
     */
    public String getMaintenanceId() {
        return maintenanceId;
    }

    /**
     * 维修人员id
     */
    public void setMaintenanceId(String maintenanceId) {
        this.maintenanceId = maintenanceId;
    }

    /**
     * 材料使用
     */
    public String getMaterialUsed() {
        return materialUsed;
    }

    /**
     * 材料使用
     */
    public void setMaterialUsed(String materialUsed) {
        this.materialUsed = materialUsed;
    }

    /**
     * 维修时间 yyyy-MM-dd
     */
    public String getWorkOrderDate() {
        return workOrderDate;
    }

    /**
     * 维修时间 yyyy-MM-dd
     */
    public void setWorkOrderDate(String workOrderDate) {
        this.workOrderDate = workOrderDate;
    }
}