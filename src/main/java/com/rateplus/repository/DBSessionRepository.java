package com.rateplus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rateplus.entity.DBSession;

public interface DBSessionRepository extends JpaRepository<DBSession, String>{

}
