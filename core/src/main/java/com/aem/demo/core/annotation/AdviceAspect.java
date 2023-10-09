package com.aem.demo.core.annotation;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class AdviceAspect {
    @AfterThrowing(
            pointcut = "execution(* com.aem.demo.core..*.*(..)) && " +
                    "!execution(* com.aem.demo.core.annotation..*.*(..)) && " +
                    "!execution(* com.aem.demo.core.filters..*.*(..))",
            throwing = "exception"
    )
    public void handleException(Exception exception) {
        // Handle or log the exception here
        log.error("Exception Caught: {}", exception.getMessage());
    }
    //pointcut = "execution(* com.aem.demo.core..*.*(..)) && !execution(* com.aem.demo.core.annotation.*.*(..))",
}
