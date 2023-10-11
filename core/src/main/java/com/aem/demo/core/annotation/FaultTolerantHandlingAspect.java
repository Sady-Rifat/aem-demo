package com.aem.demo.core.annotation;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class FaultTolerantHandlingAspect {
    @AfterThrowing(pointcut="execution(* *(..)) && " +
            "(@within(com.aem.demo.core.annotation.FaultTolerant) || " +
            "@annotation(com.aem.demo.core.annotation.FaultTolerant))", throwing="exception")
    public void handleException(Exception exception) {
        log.error("Exception Caught: {}", exception.getMessage(), exception);
    }
}
