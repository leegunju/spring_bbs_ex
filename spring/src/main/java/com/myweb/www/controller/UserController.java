package com.myweb.www.controller;


import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myweb.www.domain.UserVO;
import com.myweb.www.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/member/*")
@Controller
public class UserController {
	
	//private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Inject
	private UserService userService;
	
	@GetMapping("/signup")
	public String index(Model m) {
		log.info("home 접근 완료");
//		m.addAttribute("msg_home",1);
		return "/user/signup";
	}
	
	@PostMapping("/signup")
	public String signupPost(Model m, UserVO user) {
		log.info("회원가입 접근 완료");
		log.info(user.toString());
		int isOk = userService.signUp(user);
		if(isOk > 0) {
			m.addAttribute("msg_signup", 1);
		}else {
			m.addAttribute("msg_signup", 0);
			return "/user/signup";
		}
		return "home";  //결과 페이지
	}
	
	@GetMapping("/login")
	public String loginGet() {
		return "/user/login";
	}
	
	@PostMapping("/login")
	public String loginPost(Model m, String id, String pw, HttpServletRequest request) {
		//log.info(">>> user"+ user.toString());
		log.info(">>>id "+id+", "+"pw "+pw);
		//파라미터로 받은 id, pw를 DB에 넘겨 일치하는 객체를 받음.
		UserVO isUser = userService.isUser(id, pw); 
		//log.info(">>>isUser "+isUser.toString());
		
		//DB에서 얻은 객체가 null이 아니라면 세션 연결 저장
		if(isUser != null) {
			HttpSession ses = request.getSession();
			ses.setAttribute("ses", isUser);  //세션에 객체 담기
			ses.setMaxInactiveInterval(60*10); //로그인 유지시간 
			m.addAttribute("user", isUser);
		}else {
			m.addAttribute("msg_login",0);
			return "/user/login";
		}
		return "home";
	}
	
	@GetMapping("/logout")
	public String logout(Model m, HttpServletRequest request) {
		request.getSession().removeAttribute("ses");
		request.getSession().invalidate(); //세션끊기
		m.addAttribute("msg_logout", 1);
		return "home";
	}

}
