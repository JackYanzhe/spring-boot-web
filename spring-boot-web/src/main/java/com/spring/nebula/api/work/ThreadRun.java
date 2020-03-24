package com.spring.nebula.api.work;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.spring.nebula.api.tool.InterfaceTest;
import com.spring.nebula.api.tool.InterfaceTest2;
import com.spring.nebula.api.tool.InterfaceTest3;

/**
 * 
 * @author zheyan.yan
 *
 */
public class ThreadRun {
	public static volatile int a = 0;

	public static void execute() {
		// 设置核心池大小
		int corePoolSize = 20;

		// 设置线程池最大能接受多少线程
		int maximumPoolSize = 20;
		// 当前线程数大于corePoolSize、小于maximumPoolSize时，超出corePoolSize的线程数的生命周期
		long keepActiveTime = 200L;

		// 设置时间单位，秒
		TimeUnit timeUnit = TimeUnit.SECONDS;

		// 设置线程池缓存队列的排队策略为FIFO，并且指定缓存队列大小为5
		BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(100);

		// 创建ThreadPoolExecutor线程池对象，并初始化该对象的各种参数
		ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepActiveTime, timeUnit,workQueue);
		List<String> list = getListNum();
		
		// 往线程池中循环提交线程
		for (int i = 0; i < list.size(); i++) {
			a++;
			//多个线程执行任务不同
			if (list.get(i).equals("1")) {
				
				 InterfaceTest t = new InterfaceTest();
				// 开启线程1,测试接口1
				executor.execute(t);
			}else if(list.get(i).equals("2")){
				
				InterfaceTest2 t2 = new InterfaceTest2();
				// 开启线程2，测试接口2
			    executor.execute(t2);
			}else if(list.get(i).equals("3")){
				
				InterfaceTest3 t3 = new InterfaceTest3();
				// 开启线程2，测试接口2
			    executor.execute(t3);
			}
			
		}
		System.out.println("----------------------------------------------------------现场开启完成--------------------------------------------");
		// 待线程池以及缓存队列中所有的线程任务完成后关闭线程池。
		executor.shutdown();
	}

	private static List<String> getListNum() {
		List<String> list = new ArrayList<>();
		list.add("1");
		list.add("2");
		list.add("3");
		return list;
	}

}
