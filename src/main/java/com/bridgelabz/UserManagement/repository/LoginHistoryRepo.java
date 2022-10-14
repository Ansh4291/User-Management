package com.bridgelabz.UserManagement.repository;

import com.bridgelabz.UserManagement.model.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginHistoryRepo extends JpaRepository<LoginHistory,Integer> {
    @Query(value = "SELECT * FROM login_history WHERE email_id =:email  ", nativeQuery = true)
    List<LoginHistory> findByEmail(String email);
}
