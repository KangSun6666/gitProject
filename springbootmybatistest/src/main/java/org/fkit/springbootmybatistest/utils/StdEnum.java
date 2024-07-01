package org.fkit.springbootmybatistest.utils;

public enum StdEnum {
    籼稻("籼稻",0),
    北方水稻("北方水稻",1),
    南方水稻("南方水稻",2),
    北方粳稻("北方粳稻",3),
    南方粳稻("南方粳稻",4),
    ;
    // 成员变量
    private String name;
    private int index;
    StdEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }
    // 普通方法
    public static String getName(int index) {
        for (StdEnum c : StdEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }
    // get set 方法
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
}
