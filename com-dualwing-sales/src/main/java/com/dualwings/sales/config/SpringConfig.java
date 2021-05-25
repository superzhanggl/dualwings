package com.dualwings.sales.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


import cn.hutool.cache.impl.FIFOCache;

@Component
public class SpringConfig {
	
	
	@PostConstruct
	private void getMap() {
		
		
	}

}
