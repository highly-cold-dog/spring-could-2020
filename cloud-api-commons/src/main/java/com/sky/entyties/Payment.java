package com.sky.entyties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author dlf
 * @date 2022/5/29 17:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment implements Serializable {

    //主键标识
    private Long id;
    //流水号
    private String serial;
}
