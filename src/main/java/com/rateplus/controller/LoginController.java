package com.rateplus.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rateplus.entity.DBSession;
import com.rateplus.model.UserDTO;
import com.rateplus.repository.DBSessionRepository;
import com.rateplus.repository.UserRepository;
import com.rateplus.user.User;

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
		if(session.getAttribute("email") != null) {
			String username = (String) session.getAttribute("email");
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
		User user = userRepo.findByEmail(email).orElse(null);

		if(user!= null ) {
			String encodedPassword = new BCryptPasswordEncoder().encode(password);
			if(user.getPassword().equals(encodedPassword)) {
				session.setAttribute("email", email);
				System.out.println("Dang nhap thanh cong!");	
				String token = getJWTToken(session.getId());
				DBSession dbsession = new DBSession();
				dbsession.setSessionId(session.getId());
				dbsession.setEmail(email);
				dbsession.setToken(token);
				sessionRepo.save(dbsession);
				return "index";
			}	
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
		session.removeAttribute("email");
		return "login";
	}	
	@GetMapping("signup")
	public String showSignupForm(Model model) {
		model.addAttribute("userDTO", new UserDTO());
		return "signup";
	}	
	@PostMapping("signup")
	public String signup(@ModelAttribute("userDTO") UserDTO userDTO, Errors errors) {
		
		if(!userDTO.getPassword().equals(userDTO.getConfirmPassword())){
			System.out.println("Mat khau confirm khong giong mat khau!");
			return "signup";
		}
		String email = userDTO.getEmail();
		if(userRepo.findByEmail(email)!=null) {
			System.out.println("Email da ton tai!");
			return "signup";
		}
		User user = new User(userDTO);
		long id = (long)((Math.random())*100000000000000L);	
		user.setId(id);
		userRepo.save(user);
		System.out.println("Tao user thanh cong!");
		return "redirect:index.html";
	}
	@PostMapping("/generate-token")
	public String generateToken() {
		return "";
	}
	
}
