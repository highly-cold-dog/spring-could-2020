package com.sky.apply.common;

import com.sky.apply.user.entity.UserDo;
import lombok.Data;

/**
 * 流程之后数据传递的上下文
 *
 * @author dlf
 * @date 2023/4/20 17:50
 */
@Data
public class Context {

    String operator = "flw";
    String entityId = "1885573359";
    String name = "dlf";

    private UserDo userDo;
}
