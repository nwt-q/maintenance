package top.ptcc9.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * 配件表
 * @TableName accessory
 */
@TableName(value ="accessory")
public class Accessory implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 配件名称
     */
    private String accessoryName;

    /**
     * 配件编号
     */
    private String accessoryCode;

    /**
     * 使用数量
     */
    private Integer usedNum;

    /**
     * 库存
     */
    private Integer stock;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 配件名称
     */
    public String getAccessoryName() {
        return accessoryName;
    }

    /**
     * 配件名称
     */
    public void setAccessoryName(String accessoryName) {
        this.accessoryName = accessoryName;
    }

    /**
     * 配件编号
     */
    public String getAccessoryCode() {
        return accessoryCode;
    }

    /**
     * 配件编号
     */
    public void setAccessoryCode(String accessoryCode) {
        this.accessoryCode = accessoryCode;
    }

    /**
     * 使用数量
     */
    public Integer getUsedNum() {
        return usedNum;
    }

    /**
     * 使用数量
     */
    public void setUsedNum(Integer usedNum) {
        this.usedNum = usedNum;
    }

    /**
     * 库存
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * 库存
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }
}