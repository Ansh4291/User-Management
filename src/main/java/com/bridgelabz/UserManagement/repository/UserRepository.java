package com.bridgelabz.UserManagement.repository;

import com.bridgelabz.UserManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT * FROM user WHERE  email_id = :email", nativeQuery = true)
    User findByEmail(String email);
}
