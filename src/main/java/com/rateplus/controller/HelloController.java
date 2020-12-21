package com.rateplus.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rateplus.entity.Service;
import com.rateplus.entity.User;
import com.rateplus.model.ServiceRegisterForm;
import com.rateplus.repository.ServiceRepository;
import com.rateplus.repository.UserRepository;


@RestController
public class HelloController {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ServiceRepository serviceRepo;

	@PostMapping("/create-user")
	public User createUser(@RequestBody User user) {
		
		long id = (long)((Math.random())*100000000000000L);	
		user.setId(String.valueOf(id));
		userRepo.save(user);
		return user;
	}
	
	@PostMapping("/regis-service")
	public Service registerService(@RequestBody ServiceRegisterForm regisForm) {
	
		Service service = new Service();
		service.setServiceId(regisForm.getServiceId());
		service.setUserId(regisForm.getUserId());
		service.setPurchasedTime(new Date());
		service.setType(regisForm.getType());
		service.setExpiredTime(regisForm.getExpiredTime());
		serviceRepo.save(service);
		return service;
	}
}
