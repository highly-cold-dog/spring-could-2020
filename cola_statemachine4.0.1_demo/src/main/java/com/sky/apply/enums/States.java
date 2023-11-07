package com.sky.apply.enums;

/**
 * @author dlf
 * @date 2023/4/20 17:48
 */
public enum States {



    /**
     * 申请
     */
    APPLY(1, "申请"),

    /**
     * 初审
     */
    FIRST_TRIAL(2, "初审"),


    /**
     * 终审
     */
    FINAL_JUDGEMENT(3, "终审"),

    /**
     * 移出
     */
    REMOVE(4, "移出");

    States(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    private final Integer value;
    private final String name;

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
