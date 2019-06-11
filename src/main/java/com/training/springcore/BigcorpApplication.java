package com.training.springcore;

import com.training.springcore.config.properties.BigcorpApplicationProperties;
import com.training.springcore.service.SiteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableConfigurationProperties
public class BigcorpApplication {
	private final static Logger logger = LoggerFactory.getLogger(BigcorpApplication.class);

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(BigcorpApplication.class, args);

		BigcorpApplicationProperties properties = context.getBean(BigcorpApplicationProperties.class);
		logger.info("================================================================== ======");
		logger.info("Application [" + properties.getName() + "] - version: " + properties.getVersion());
		logger.info("plus d'informations sur " +
				properties.getWebSiteUrl());
		logger.info("================================================================== ======");
		context.getBean(SiteService.class).findById("site1");
	}

}




