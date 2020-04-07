package com.lrj.community.mapper;

import com.lrj.community.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    /**
     * 创建一个用户
     * @param user 用户对象
     */
    @Insert("insert into user(account_id,name,token,gmt_create,gmt_modified,avatar_url) values(#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    /**
     * 根据 token 查找用户
     * @param token 根据UUID生成的 token
     * @return
     */
    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    /**
     * 通过 id 查找用户
     * @param creator 创建者的id
     * @return 创建者
     */
    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Integer creator);

    @Select("select * from user where account_id = #{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    @Update("update user set name = #{name} , token = #{token} , gmt_modified = #{gmtModified} , avatar_url = #{avatarUrl} where id = #{id}")
    void update(User user);
}
