package com.rateplus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rateplus.entity.Service;

@Repository
public interface ServiceRepository extends JpaRepository<Service, String>{

}
