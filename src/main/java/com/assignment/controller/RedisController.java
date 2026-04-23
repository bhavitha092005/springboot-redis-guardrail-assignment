package com.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.assignment.service.RedisService;

@RestController
@RequestMapping("/api/redis")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @GetMapping("/virality/{postId}")
    public String getVirality(@PathVariable Long postId) {
        return redisService.getVirality(postId);
    }
}