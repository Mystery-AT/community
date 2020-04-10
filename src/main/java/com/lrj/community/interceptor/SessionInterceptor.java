package com.lrj.community.interceptor;

import com.lrj.community.mapper.UserMapper;
import com.lrj.community.model.User;
import com.lrj.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    UserExample example = new UserExample();
                    example.createCriteria().andTokenEqualTo(token);

                    List<User> users = userMapper.selectByExample(example);
//                    User user = userMapper.findByToken(token);
                    if (users.size() != 0){
                        request.getSession().setAttribute("user",users.get(0));
                        System.out.println("users.get(0):"+users.get(0));
                        return true;
                    }
                }
            }
            return true;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
