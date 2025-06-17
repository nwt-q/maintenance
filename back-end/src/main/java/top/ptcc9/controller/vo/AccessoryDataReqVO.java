package top.ptcc9.controller.vo;

public class AccessoryDataReqVO {

    private String name;

    private Integer usage;

    private Integer stock;

    public AccessoryDataReqVO(String accessoryName, Integer usedNum, Integer stock) {
        this.name = accessoryName;
        this.usage = usedNum;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUsage() {
        return usage;
    }

    public void setUsage(Integer usage) {
        this.usage = usage;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
