package com.lrj.community.controller;

import com.lrj.community.mapper.QuestionMapper;
import com.lrj.community.model.Question;
import com.lrj.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class PublishController {

    @Autowired(required = false)
    private QuestionMapper questionMapper;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(HttpSession session,
                            Model model,
                            @RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag) {
        //看一下用户有没有登陆
        User user = (User) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("error","用户未登录");
            //如果未登录则返回当前页，并显示用户登陆的警告
            return "publish";
        }
        //用于数据回显
        model.addAttribute("title",title);
        model.addAttribute("description" ,description);
        model.addAttribute("tag",tag);
        //问题的 对象
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        //将数据封装到对象中 存入数据库
        questionMapper.create(question);
        //返回到首页
        return "redirect:/";
    }
}
