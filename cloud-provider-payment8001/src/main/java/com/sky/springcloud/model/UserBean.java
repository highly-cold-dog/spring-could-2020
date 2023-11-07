package com.sky.springcloud.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 测试AOP实体类
 * @author dlf
 * @date 2022/10/14 15:09
 */
@Data
@Accessors(chain = true)
public class UserBean {

    private String userName;
    private String passWord;

}
