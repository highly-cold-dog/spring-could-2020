package com.sky.apply.user.dao;

import com.sky.apply.user.entity.UserDo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author dlf
 * @date 2023/4/20 18:07
 */
@Mapper
public interface UserDao {

    int add(UserDo userDo);
}
