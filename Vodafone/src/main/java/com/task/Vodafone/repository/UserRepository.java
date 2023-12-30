package com.task.Vodafone.repository;

import com.task.Vodafone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
//User and Long is based on the Class and ID type
    User findByEmail(String email);

}