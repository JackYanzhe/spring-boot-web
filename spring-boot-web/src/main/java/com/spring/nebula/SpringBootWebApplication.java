package com.spring.nebula;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.spring.nebula.util.RedisUtil;

@SpringBootApplication
public class SpringBootWebApplication{
	 private static final Logger logger = LoggerFactory.getLogger(SpringBootWebApplication.class);
   /* @Override
	   protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
	       // 注意这里要指向原先用main方法执行的Application启动类
	       return builder.sources(SpringBootWebApplication.class);
	   }*/
    
	public static void main(String[] args) {
		try {
			SpringApplication.run(SpringBootWebApplication.class, args);
			logger.info("----------------------------开始执行接口测试程序----------------------------");
			//ThreadRun threadRun = new ThreadRun();
			//threadRun.execute();
			/*RedisUtil redisUtil = new RedisUtil();
			redisUtil.set("t1", "yanzhetest");
			
			System.out.println(redisUtil.get("t1"));*/
			
			logger.info("----------------------------接口测试程序执行完成----------------------------");
		} catch (Exception e) {
			logger.error("出现异常信息："+e.getMessage());
		}
		
	}
	
		
}
