package com.example.Assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Assignment.entites.User;

public interface UserRepository extends JpaRepository<User, Long> {
}