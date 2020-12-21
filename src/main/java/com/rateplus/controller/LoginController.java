package com.rateplus.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.rateplus.entity.User;
import com.rateplus.repository.UserRepository;

@Controller
public class LoginController {

	@Autowired
	private UserRepository userRepo;
	
	@GetMapping("/index.html")
	public String hello() {
		return "index";
	}
	
	@GetMapping("/about.html")
	public String about() {
		return "about";
	}
	@GetMapping("/contact.html")
	public String contact() {
		return "contact";
	}
	
	@GetMapping
	public String show(HttpSession session) {
		if(session.getAttribute("username") != null) {
			String username = (String) session.getAttribute("username");
			System.out.println(username);
			return "login_info";
		}
		
		return "index";
	}
	
	@GetMapping("login")
	public String showLogin() {
		return "login";
	}
	
	@PostMapping("login")
	public String submit(@RequestParam("email") String email, @RequestParam("password") String password,
			HttpSession session) {
		User user = userRepo.findByEmail(email);
		if(user!= null && user.getPassword().equals(password)) {
			session.setAttribute("username", email);
			System.out.println("Dang nhap thanh cong!");
			return "index";
		};
		return "login";
	}
	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.removeAttribute("username");
		return "login";
	}
}
