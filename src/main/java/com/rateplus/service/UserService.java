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
		User user = userRepo.findByEmail(email).orElse(null);
		if(user != null) {
			return user;
		}
		throw new EmailNotFoundException(
                "Email '" + email + "' not found");
	}

	// JWTAuthenticationFilter sẽ sử dụng hàm này
    @Transactional
    public UserDetails loadUserById(long id) {
        User user = userRepo.findById(String.valueOf(id)).orElseThrow(
                () -> new EmailNotFoundException("Account not found with id : " + id)
        );

        return new CustomUserDetails(user);
    }
	
}
