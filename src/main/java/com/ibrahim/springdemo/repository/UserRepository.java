package com.ibrahim.springdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ibrahim.springdemo.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}