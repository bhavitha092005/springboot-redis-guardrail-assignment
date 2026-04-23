package com.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.assignment.entity.Bot;

public interface BotRepository extends JpaRepository<Bot, Long> {

}