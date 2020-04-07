package com.lrj.community.mapper;

import com.lrj.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {

    /**
     * 创建一个问题
     * @param question 问题对象
     */
    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    /**
     * 查询所有问题
     * @param offset 从哪条数据开始
     * @param size 每一页展示多少条数据
     * @return
     */
    @Select("select * from question limit #{offset} , #{size}")
    List<Question> list(@Param("offset") Integer offset, @Param("size") Integer size);

    /**
     * 一共有多少条数据
     * @return
     */
    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question where creator = #{userId} limit #{offset} , #{size}")
    List<Question> listByUserId(@Param("userId") Integer userId ,@Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from question where creator = #{userId}")
    Integer countByUserId(@Param("userId") Integer userId);

    @Select("select * from question where id = #{id}")
    Question getById(@Param("id") Integer id);
}
