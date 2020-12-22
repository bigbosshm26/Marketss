package com.rateplus.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rateplus.entity.User;
import com.rateplus.model.DBSession;
import com.rateplus.model.SignupForm;
import com.rateplus.repository.DBSessionRepository;
import com.rateplus.repository.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Controller
public class LoginController {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	public DBSessionRepository sessionRepo;
	
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
			String token = getJWTToken(email);
			DBSession dbsession = new DBSession();
			dbsession.setSessionId(session.getId());
			dbsession.setEmail(email);
			dbsession.setToken(token);
			sessionRepo.save(dbsession);
			return "index";
		};
		
		return "login";
	}
	
	private String getJWTToken(String username) {
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		String token = Jwts
				.builder()
				.setId("softtekJWT")
				.setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return "Bearer " + token;
	}
	
	
	
	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.removeAttribute("username");
		return "login";
	}
	
	@GetMapping("signup")
	public String showSignupForm(Model model) {
		model.addAttribute("signupForm", new SignupForm());
		return "signup";
	}
	
	@PostMapping("signup")
	public String signup(@ModelAttribute("signupForm") SignupForm signupForm, Errors errors) {
		
		if(!signupForm.getPassword().equals(signupForm.getConfirmPassword())){
			System.out.println("Mat khau confirm khong giong mat khau!");
			return "signup";
		}
		String email = signupForm.getEmail();
		if(userRepo.findByEmail(email)!=null) {
			System.out.println("Email da ton tai!");
			return "signup";
		}
		User user = new User();
		long id = (long)((Math.random())*100000000000000L);	
		user.setId(String.valueOf(id));
		user.setAge(signupForm.getAge());
		user.setEmail(signupForm.getEmail());
		user.setPassword(signupForm.getPassword());
		user.setFirstName(signupForm.getFirstName());
		user.setLastName(signupForm.getLastName());
		user.setLink(signupForm.getLink());
		user.setPhoneNumber(signupForm.getPhoneNumber());
		userRepo.save(user);
		System.out.println("Tao user thanh cong!");
		return "redirect:index.html";
	}
	
	@PostMapping("/generate-token")
	public String generateToken() {
		return "";
	}
	
}
