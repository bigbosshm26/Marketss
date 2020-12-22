package com.rateplus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rateplus.model.DBSession;

public interface DBSessionRepository extends JpaRepository<DBSession, String>{

}
