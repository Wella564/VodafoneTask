package com.task.Vodafone.repository;

import com.task.Vodafone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
   //User and Long is based on the Class and ID type of the entity "User"
   //we use this to add all the CRUD methods implemented inside
   //Save, FindbyID, findall-> It actually parses these method names based on your entity class
    User findByEmail(String email);

}