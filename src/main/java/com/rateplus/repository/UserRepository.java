package com.rateplus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rateplus.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

	User findByEmail(String email);
}
