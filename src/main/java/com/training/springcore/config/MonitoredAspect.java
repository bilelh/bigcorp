package com.training.springcore.config;

import com.training.springcore.model.Site;
import com.training.springcore.service.SiteService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MonitoredAspect {
    private final static Logger logger = LoggerFactory.getLogger(MonitoredAspect.class);

    @Pointcut("execution(* com.training.springcore..service.*.*(..))")
    public void inService(){}
    @Pointcut("execution(public * find*(..))")
    public void anyFinder(){}

    @Before("@annotation(BigCorpApplicationConfig.Monitored)")
    public void logServiceBeforeCall(JoinPoint jp) {

        logger.info("Appel finder " + jp.getSignature());
    }
}
