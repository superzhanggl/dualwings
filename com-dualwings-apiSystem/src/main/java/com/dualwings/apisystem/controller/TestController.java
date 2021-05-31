package com.dualwings.apisystem.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/a/test")
@Slf4j
public class TestController {

    @GetMapping("/get")
    public UserDTO getTest() {
        return new UserDTO("1", "张三");
    }

    @Data
    @AllArgsConstructor
    public static class UserDTO {
        private String userId;
        private String userName;
    }
}
