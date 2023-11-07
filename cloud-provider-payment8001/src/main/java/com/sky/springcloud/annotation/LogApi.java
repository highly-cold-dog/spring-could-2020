package com.sky.springcloud.annotation;

import java.lang.annotation.*;

/**
 * 自定义日志记录注解
 *
 * @author dlf
 * @date 2022/10/15 13:17
 */
@Target(value = ElementType.METHOD)
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
public @interface LogApi {
}
