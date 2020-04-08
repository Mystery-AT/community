package com.lrj.community.service;

import com.lrj.community.dto.PaginationDTO;
import com.lrj.community.dto.QuestionDTO;
import com.lrj.community.mapper.QuestionMapper;
import com.lrj.community.mapper.UserMapper;
import com.lrj.community.model.Question;
import com.lrj.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired(required = false)
    private QuestionMapper questionMapper;

    @Autowired(required = false)
    private UserMapper userMapper;

    /**
     * 分页查询
     *
     * @param page 第几页
     * @param size 每一页有多少条数据
     * @return 每一页的总数据显示以及按钮控制
     */
    public PaginationDTO list(Integer page, Integer size) {
        //分页后将数据封装到这里
        PaginationDTO paginationDTO = new PaginationDTO();
        //通过数据库查询一共有多少条数据
        Integer totalPage;
        //通过数据库查询一共有多少条数据
        Integer totalCount = questionMapper.count();

        if (totalCount % size == 0) {
            //获得总页数
            totalPage = totalCount / size;
            //如果 != 0 就 +1 页
        } else {
            //获得总页数
            totalPage = totalCount / size + 1;
        }

        //将数据传入进行分页控制
        paginationDTO.setPagination(totalPage, page);
        //如果当前页 的页码 <1 默认是第一页
        if (page < 1) {
            page = 1;
        }
        //如果当前页的页面 大于最后一页 默认是最后一页
        if (page > totalPage) {
            page = totalPage;
        }

        //分页逻辑 limit a , b 中的 a
        Integer offset = size * (page - 1);
        //从 第几条数据开始 ， 每页多少条数据  传入   返回分页后的数据 集合
        List<Question> questionList = questionMapper.list(offset, size);
        //用于首页展示的问题集合
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        //
        for (Question question : questionList) {
            //将每个问题的创建者 通过id 进行查询出来
            User user = userMapper.findById(question.getCreator());
            //问题的所有数据（内容，创建者）
            QuestionDTO questionDTO = new QuestionDTO();
            //数据封装
            BeanUtils.copyProperties(question, questionDTO);
            //将 创建者 封装到里面
            questionDTO.setUser(user);
            //用集合将这些全部 封装起来
            questionDTOList.add(questionDTO);
        }
        //再次封装  每一页的数据显示以及按钮控制
        paginationDTO.setQuestions(questionDTOList);
        //返回
        return paginationDTO;
    }

    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        System.out.println("userId:" + userId);
        //分页后将数据封装到这里
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        //通过数据库查询一共有多少条数据
        Integer totalCount = questionMapper.countByUserId(userId);

        if (totalCount % size == 0) {
            //获得总页数
            totalPage = totalCount / size;
            //如果 != 0 就 +1 页
        } else {
            //获得总页数
            totalPage = totalCount / size + 1;
        }
        //将数据传入进行分页控制
        paginationDTO.setPagination(totalPage, page);
        System.out.println("page1:"+page);
        //如果当前页 的页码 <1 默认是第一页
        if (page < 1) {
            page = 1;
        }
        System.out.println("page2:"+page);
        //如果当前页的页面 大于最后一页 默认是最后一页
        if (page > totalPage) {
            page = totalPage;
        }
        System.out.println("total:"+totalPage);
        System.out.println("page1+"+page);
        //分页逻辑 limit a , b 中的 a
        Integer offset = size * (page - 1);
        System.out.println("size:"+size);
        System.out.println("page:"+page);
        System.out.println("offset:"+offset);
        //从 第几条数据开始 ， 每页多少条数据  传入   返回分页后的数据 集合
        List<Question> questionList = questionMapper.listByUserId(userId, offset, size);
        //用于首页展示的问题集合
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        System.out.println("page:"+page);
        //
        for (Question question : questionList) {
            //将每个问题的创建者 通过id 进行查询出来
            User user = userMapper.findById(question.getCreator());
            //问题的所有数据（内容，创建者）
            QuestionDTO questionDTO = new QuestionDTO();
            //数据封装
            BeanUtils.copyProperties(question, questionDTO);
            //将 创建者 封装到里面
            questionDTO.setUser(user);
            //用集合将这些全部 封装起来
            questionDTOList.add(questionDTO);
        }
        //再次封装  每一页的数据显示以及按钮控制
        paginationDTO.setQuestions(questionDTOList);
        //返回
        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() != null) {
            question.setGmtModified(question.getGmtCreate());
            questionMapper.update(question);
        } else {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        }
    }
}
