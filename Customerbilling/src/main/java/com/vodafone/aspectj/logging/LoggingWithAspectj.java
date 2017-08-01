package com.vodafone.aspectj.logging;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingWithAspectj {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingWithAspectj.class);

	@Around("execution(* com.vodafone.service.*.*(..))")
	public Object logParamters(ProceedingJoinPoint joinPoint) throws Throwable{
		LOGGER.info("Before: "+joinPoint.getSignature().getName()+ " Paramters: "
				+Arrays.toString(joinPoint.getArgs()));
		Object result = joinPoint.proceed();
		LOGGER.info("After: " +joinPoint.getSignature().getName()+ " Paramters: "
				+Arrays.toString(joinPoint.getArgs()));
		return result;
	}
	
	
	@AfterThrowing(pointcut = "execution(* com.vodafone.service.*.*(..))",
			throwing="exception")
	public void logAfterThrowing(JoinPoint joinPoint,Exception exception){
		LOGGER.error("error in : " +joinPoint.getSignature().getName()+ " Paramters: "
				+Arrays.toString(joinPoint.getArgs()) +" exception type " + exception.getMessage());
	}
}
