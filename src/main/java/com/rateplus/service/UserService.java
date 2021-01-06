package com.rateplus.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rateplus.exception.EmailNotFoundException;
import com.rateplus.repository.UserRepository;
import com.rateplus.user.CustomUserDetails;
import com.rateplus.user.User;

@Service
public class UserService implements UserDetailsService{

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email){
		// Kiểm tra xem user có tồn tại trong database không?
        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new EmailNotFoundException("Email '" + email + "' not found");
        }
        return new CustomUserDetails(user);
	}

	// JWTAuthenticationFilter sẽ sử dụng hàm này
    @Transactional
    public UserDetails loadUserById(String id) {
        User user = userRepo.findById(String.valueOf(id)).orElseThrow(
                () -> new EmailNotFoundException("Account not found with id : " + id)
        );

        return new CustomUserDetails(user);
    }
	
}
