package com.lrj.community.controller;

import com.lrj.community.dto.AccessTokenDTO;
import com.lrj.community.dto.GithubUser;
import com.lrj.community.mapper.UserMapper;
import com.lrj.community.model.User;
import com.lrj.community.provider.GithubProvider;
import com.lrj.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserService userService;

    /**
     * 下面3个的数据从 application.yml中进行注入
     */
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    /**
     * github的回调 方法
     *
     * @param code     github提供的code
     * @param state    github提供的状态码
     * @param response
     * @return
     */
    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code, @RequestParam("state") String state, HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        //通过get请求获得 access_token
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        //通过post请求传递 access_token 获得 github 的账号信息
        GithubUser githubUser = githubProvider.getGithubUser(accessToken);
        System.out.println("未重新赋值的user:" + githubUser);
        //为了不报 NullPointException 而设置
        if (githubUser == null) {
            githubUser = new GithubUser();
            githubUser.setName("一定要升本");
            githubUser.setId(001L);
            githubUser.setBio("好好学习");
        }
        System.out.println(githubUser.getName());
        System.out.println(githubUser);
        if (githubUser != null) {
            //封装一个User用户对象
            User user = new User();
            //用于 服务器 突然停止后 无需再去调用 github的 登陆接口
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            //github中的名字
            user.setName(githubUser.getName());
            //github中的账号id
            user.setAccountId(String.valueOf(githubUser.getId()));

            //github中账号的 头像 的地址
            user.setAvatarUrl(githubUser.getAvatar_url());
            //存入数据库中
//            userMapper.insert(user);

            userService.createOrUpdate(user);

            //将该用户的token 存入 cookie中
            response.addCookie(new Cookie("token", token));
            return "redirect:/";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
