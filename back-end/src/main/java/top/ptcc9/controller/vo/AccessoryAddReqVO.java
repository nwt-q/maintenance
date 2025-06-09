package top.ptcc9.controller.vo;

public class AccessoryAddReqVO {
    private String accessory_name;
    private String accessory_code;
    private String stock;
    private String used_num;

    public String getAccessory_name() {
        return accessory_name;
    }

    public void setAccessory_name(String accessory_name) {
        this.accessory_name = accessory_name;
    }

    public String getAccessory_code() {
        return accessory_code;
    }

    public void setAccessory_code(String accessory_code) {
        this.accessory_code = accessory_code;
    }

    public String getUsed_num() {
        return used_num;
    }

    public void setUsed_num(String used_num) {
        this.used_num = used_num;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }
}
