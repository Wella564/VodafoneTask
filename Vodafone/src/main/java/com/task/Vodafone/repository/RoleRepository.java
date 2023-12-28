package com.task.Vodafone.repository;

import com.task.Vodafone.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    //User and Long is based on the Class and ID type
    Role findByName(String name);
}
