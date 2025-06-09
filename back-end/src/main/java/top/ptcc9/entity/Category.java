package top.ptcc9.entity;


import top.ptcc9.annotations.FieldsDes;

public class Category {

    private String id;

    @FieldsDes(desc = "类别名")
    private String name;

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

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
