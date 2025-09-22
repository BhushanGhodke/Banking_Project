package com.user.service.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.service.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
