package com.dualwings.apisystem.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
//@FeignClient
@Api(tags="销售管理")
@RequestMapping("api")
public class SalesInfoController {
    
}
