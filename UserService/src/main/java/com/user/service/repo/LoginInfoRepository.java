package com.user.service.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.user.service.entity.LoginInfo;

public interface LoginInfoRepository extends JpaRepository<LoginInfo, Integer> {

	@Query(value = "SELECT * FROM login_info_table " +
            "WHERE user_id = :userId " +
            "ORDER BY login_time DESC " +
            "LIMIT 1,1", nativeQuery = true)
LoginInfo getSecondLatestLoginByUserId(@Param("userId") Integer userId);
}
