package com.j.sso.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在不需要验证的方法上加此注解
 * 	check 为true时校验
 * 	check 为false时不校验
 * @author yizhishaonian
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface SSOCheckToken {
	
	boolean check() default true;
	
}
