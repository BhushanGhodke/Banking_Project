package com.user.service.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.service.entity.User;


public interface UserRepository extends JpaRepository<User, Integer>{

	public Optional<User> findByUsername(String username);
}
