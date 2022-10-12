package com.bridgelabz.UserManagement.repository;

import com.bridgelabz.UserManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT * FROM user WHERE email_id = :email", nativeQuery = true)
    User findByEmail(String email);

    @Query(value = "SELECT * FROM user WHERE age between 18 and 24", nativeQuery = true)
    List<User> findByAge();
}
