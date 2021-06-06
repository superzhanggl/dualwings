package com.dualwings.order.controller;



import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
//@FeignClient
@Api(tags="订单管理")
@RequestMapping("")
public class OrderInfoController {
    // @Resource
    private RestTemplate restTemplate;
    private static Logger  logger=Logger.getLogger(OrderInfoController.class);
    
   
    
    
}
