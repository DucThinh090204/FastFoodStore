package com.example.fastfoodstore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.fastfoodstore.model.User;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {
    // Tìm user theo email
    User findByEmail(String email);
}
