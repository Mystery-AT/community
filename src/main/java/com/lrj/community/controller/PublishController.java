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

import javax.servlet.http.HttpServletRequest;
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

        User user = (User) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        model.addAttribute("title",title);
        model.addAttribute("description" ,description);
        model.addAttribute("tag",tag);
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.create(question);

        return "redirect:/";
    }
}
