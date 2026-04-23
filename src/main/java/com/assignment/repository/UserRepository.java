package com.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.assignment.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}