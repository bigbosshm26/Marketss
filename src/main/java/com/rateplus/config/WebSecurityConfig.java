package com.rateplus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.rateplus.service.UserService;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserService userService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		// Password encoder, để Spring Security sử dụng mã hóa mật khẩu người dùng
	    return new BCryptPasswordEncoder(); 
	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // Get AuthenticationManager bean
        return super.authenticationManagerBean();
    }
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
 
        // Sét đặt dịch vụ để tìm kiếm User trong Database.
        // Và sét đặt PasswordEncoder.
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
			.antMatchers("/").permitAll() // Cho phép tất cả mọi người truy cập vào địa chỉ này
			.antMatchers("/login").permitAll(); // Cho phép tất cả mọi người truy cập vào địa chỉ này
//			.anyRequest().authenticated(); // Tất cả các request khác đều cần phải xác thực mới được truy cập
//            .and()
//			.logout() // Cho phép logout
//			.permitAll();
	}

}
