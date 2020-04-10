package com.lrj.community.controller;

import com.lrj.community.dto.PaginationDTO;
import com.lrj.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    /**
     * 首页
     * @param request 请求，将登陆后的用户存入cookie中
     * @param model 将封装后的数据传递到前端页面
     * @param page 当前页码，默认第一页
     * @param size 每一页有多少条数据，默认5条
     * @return 返回首页
     */
    @GetMapping("/")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size) {
        //通过 当前页-每页的显示数量   进行分页查询
        PaginationDTO pagination = questionService.list(page, size);
        //将数据通过 model 传递到 前端页面
        model.addAttribute("pagination", pagination);
        System.out.println(pagination);
        return "index";
    }
}
