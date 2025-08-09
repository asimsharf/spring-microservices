package com.sudagoarth.user_service.repo;

import com.sudagoarth.user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
