package com.lrj.community.mapper;

import com.lrj.community.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    @insert
    void insert(User user);
}
