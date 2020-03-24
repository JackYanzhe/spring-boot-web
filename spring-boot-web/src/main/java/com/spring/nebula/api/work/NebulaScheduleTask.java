package com.spring.nebula.api.work;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 * @author zheyan.yan
 *
 */
@Component
public class NebulaScheduleTask {

	@Scheduled(fixedDelay = 1000 * 60 * 6)
	public void delUploadMailFileTask() {
		System.out.println("开始定时任务，没6小时检查一次");
		
		
	}
}
