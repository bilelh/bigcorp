package com.training.springcore.repository;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.training.springcore.repository",
        "com.training.springcore.utils"})
public class DaoTestConfig {
}
