package com.spring.nebula.api.tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spring.nebula.util.HttpUtil;

public class InterfaceTest extends Thread{

	 private static final Logger logger = LoggerFactory.getLogger(InterfaceTest.class);
	 /**
	  * 测试接口性能
	  * @param args
	  */
	 public static void main(String[] args) {
		 
		
		/* int num=100;
		 long time = 60*4;
		 getInterfaceFunctionTest(num,time);*/
		
		
		 
	 }
	    @Override
		public void run() {
	    	logger.info("--------------------开始进行(获取平台站点仓库)接口接口调用---------------------");
	    	 int num=1;
			 long time = 10;
			 getInterfaceFunctionTest2(num,time);
	    	
	    	
	    	
			
	    	
		 
	    }
	 
	/**
	 * 
	 * @param loopNum  调用次数
	 * @param sleepTime 每次调用睡眠时间
	 */
	public static void getInterfaceFunctionTest(Integer loopNum,long sleepTime) {
		 try {
			 //初始时间
			 long initTime = System.currentTimeMillis();
			 //记录异常发生次数
			 int num =0;
			 //记录异常发生
			 long time1 =0;
			 long time2 =0;
			 long time3 =0;
			 long time4 =0;
			 long time5 =0;
			 //记录所有成功调用时间
			 StringBuilder stringBuilder = new StringBuilder();
			 //记录所有调用花费时间（包含异常调时间）
			 StringBuilder stringBuilder2 = new StringBuilder();
			 //详细记录调用时间
			 StringBuilder stringBuilder3 = new StringBuilder();
			 //记录所有发生异常时花费的时间
			 StringBuilder stringBuilder4 = new StringBuilder();
			 
			 for (int i = 0; i < loopNum; i++) {
		    		try {
		    			logger.info("(获取平台站点仓库接口)-------开始进行接口调用，此为第"+i+"次调用");
		    			time1 = System.currentTimeMillis();
		    			
		    			//----------------------------------此为需要测试的代码片段----------------------------------
			        	String json="{\"warehouse\":\"54923\",\"sku\":\"B01ARQBU3W\"}";
			   		    String sendHttpPost = HttpUtil.sendHttpPost("http://112.64.178.163:9999/api/getplatformsitewh", json);
			   		    logger.info("调用(获取平台站点仓库接口)接口返回信息:"+sendHttpPost);
		    			//--------------------------------------------------------------------
			        	
			        	time2 = System.currentTimeMillis();
			        	//接口调用成功一次总时间
			    		stringBuilder.append(","+(time2-time1)/1000);
		    			
		    		} catch (Exception e) {
		    			num++;
		    			time3 = System.currentTimeMillis();
		    			//接口调用异常时时间
			    		stringBuilder4.append(","+(time3-time1)/1000);
		    			logger.error("(获取平台站点仓库接口)出现异常信息，异常信息如下："+e.getMessage());
					}
		    		time4 = System.currentTimeMillis();
		    		//接口调用一次总时间
		    		stringBuilder2.append(","+(time4-time1)/1000);
		    		stringBuilder3.append(",当前第"+i+"次调用，耗时："+(time4-time1)/1000);
		    		try {
		    			Thread.sleep(1000*sleepTime);
					} catch (Exception e) {
						
						logger.error("(获取平台站点仓库接口)出现异常信息，异常信息如下："+e.getMessage());
					}
		    		time5 = System.currentTimeMillis();
		            logger.info("(获取平台站点仓库接口)此为第"+(i+1)+"次调用，"+",从开始调用完整结束占用时间（不含等待时间）："+(time4-time1)/1000+"s，接口调用时间（正常情况下）："+(time2-time1)/1000+"s,从开始调用到出现异常占用时间："+(time3-time1)/1000+"s,调用一次总耗时："+(time5-time1)/1000+"s，此时出现异常次数："+num);
		    		
				}
			 //结束时间
			 long endTime = System.currentTimeMillis();
			
			 String avgTime=stringBuilder.toString();
			 String[] split = avgTime.split(",");
			 Double avgtime1=0d;
			 int numSu=0;
			 for (String time : split) {
				  try {
					  if (time.contains("-")) {
						continue;
					  }
					  numSu++;
					  double doubleTime = Double.parseDouble(time);
					 
					  avgtime1=avgtime1+doubleTime;
					} catch (Exception e) {
						numSu--;
						logger.info("(获取平台站点仓库接口)a此时出现异常，得到的时间："+time);
					}
				 
			 }
			 
			 String avgTime2= stringBuilder2.toString();
			 String[] split2 = avgTime2.split(",");
			 Double avgtime2=0d;
			 int numSu2=0;
			 for (String time : split2) {
				 try {
					 if (time.contains("-")) {
							continue;
						  }
					 numSu2++;
					 double doubleTime = Double.parseDouble(time);
					  avgtime2=avgtime2+doubleTime;
					  
					} catch (Exception e) {
						numSu2--;
						logger.info("(获取平台站点仓库接口)b此时出现异常，得到的时间："+time);
					}
				 
			 }
			 
			 String avgTime3 = stringBuilder4.toString();
			 String[] split3 = avgTime3.split(",");
			 Double avgtime3=0d;
			 int numSu3=0;
			 for (String time : split3) {
				 try {
					 if (time.contains("-")||time.contains("0")) {
							continue;
						  }
					 numSu3++;
					 double doubleTime = Double.parseDouble(time);
					  avgtime3=avgtime3+doubleTime;
					  
					} catch (Exception e) {
						numSu3--;
						logger.info("(获取平台站点仓库接口)c此时出现异常，得到的时间："+time);
					}
				 
			 }
			 logger.info("(获取平台站点仓库接口)调用100次接口总耗时："+(endTime-initTime)/1000+"s，总共出现异常次数："+num);
			 logger.info("(获取平台站点仓库接口)单次调用详细信息(所有成功时间)："+stringBuilder.toString());
			 logger.info("(获取平台站点仓库接口)单次调用详细信息(所有异常情况时间)："+stringBuilder4.toString());
			 logger.info("(获取平台站点仓库接口)单次调用详细信息(所有情况)："+stringBuilder2.toString());
			 logger.info("(获取平台站点仓库接口)全部成功总耗时（不含等待时间）："+avgtime1+",平均耗时（全部成功）："+avgtime1/numSu);
			 logger.info("(获取平台站点仓库接口)全部异常总耗时（不含等待时间）："+avgtime3+",平均耗时（所有异常情况）："+avgtime3/numSu3);
			 logger.info("(获取平台站点仓库接口)全部接口调用耗时（不含等待时间）："+avgtime2+",平均耗时（包含异常情况）："+avgtime2/numSu2);
			
			 
	    		
	      }catch (Exception e) {
	    	  logger.error("(获取平台站点仓库接口)整体出现异常信息，异常信息如下："+e.getMessage());
		   }
	}
	
	
	/**
	 * 单次显示所有调用时间信息统计
	 * @param loopNum  调用次数
	 * @param sleepTime 每次调用睡眠时间
	 */
	public static void getInterfaceFunctionTest2(Integer loopNum,long sleepTime) {
		 try {
			 //初始时间
			 long initTime = System.currentTimeMillis();
			 //记录异常发生次数
			 int num =0;
			 //记录异常发生
			 long time1 =0;
			 long time2 =0;
			 long time3 =0;
			 long time4 =0;
			 long time5 =0;
			 //记录所有成功调用时间
			 StringBuilder stringBuilder = new StringBuilder();
			 //记录所有调用花费时间（包含异常调时间）
			 StringBuilder stringBuilder2 = new StringBuilder();
			 //详细记录调用时间
			 StringBuilder stringBuilder3 = new StringBuilder();
			 //记录所有发生异常时花费的时间
			 StringBuilder stringBuilder4 = new StringBuilder();
			 
			 for (int i = 0; i < loopNum; i++) {
		    		try {
		    			String json="{\"warehouse\":\"54923\",\"sku\":\"B01ARQBU3W\"}";
		    			logger.info("(获取平台站点仓库接口)-------开始进行接口调用，此为第"+i+"次调用"+"此时接口请求参数："+json);
		    			time1 = System.currentTimeMillis();
		    			//----------------------------------此为需要测试的代码片段----------------------------------
			        	
			   		    //String sendHttpPost = HttpUtil.sendHttpPost("http://112.64.178.163:9999/api/getplatformsitewh", json);
		    			//本地内网进行调用 
		    			String sendHttpPost = HttpUtil.sendHttpPost("http://192.168.1.46:9999/api/getplatformsitewh", json);
			   		    logger.info("调用(获取平台站点仓库接口)接口返回信息:"+sendHttpPost);
		    			//--------------------------------------------------------------------
			        	
			        	time2 = System.currentTimeMillis();
			        	//接口调用成功一次总时间
			    		stringBuilder.append(","+(time2-time1)/1000);
		    			
		    		} catch (Exception e) {
		    			num++;
		    			time3 = System.currentTimeMillis();
		    			//接口调用异常时时间
			    		stringBuilder4.append(","+(time3-time1)/1000);
		    			logger.error("(获取平台站点仓库接口)出现异常信息，异常信息如下："+e.getMessage());
					}
		    		time4 = System.currentTimeMillis();
		    		//接口调用一次总时间
		    		stringBuilder2.append(","+(time4-time1)/1000);
		    		stringBuilder3.append(",当前第"+i+"次调用，耗时："+(time4-time1)/1000);
		    		try {
		    			//设置等待时间
		    			Thread.sleep(1000*sleepTime);
					} catch (Exception e) {
						
						logger.error("(获取平台站点仓库接口)出现异常信息，异常信息如下："+e.getMessage());
					}
		    		time5 = System.currentTimeMillis();
		            logger.info("(获取平台站点仓库接口)此为第  "+(i+1)+"  次调用"+",从开始调用完整结束占用时间（不含等待时间）："+(time4-time1)/1000+"s，接口调用时间（正常情况下）："+(time2-time1)/1000+"s,从开始调用到出现异常占用时间："+(time3-time1)/1000+"s,调用一次总耗时："+(time5-time1)/1000+"s，此时出现异常次数："+num);
		    		
		            
		            
		             String avgTime=stringBuilder.toString();
					 String[] split = avgTime.split(",");
					 Double avgtime1=0d;
					 int numSu=0;
					 for (String time : split) {
						  try {
							  if (time.contains("-")) {
								continue;
							  }
							  numSu++;
							  double doubleTime = Double.parseDouble(time);
							 
							  avgtime1=avgtime1+doubleTime;
							} catch (Exception e) {
								numSu--;
								logger.info("(获取平台站点仓库接口)a此时出现异常，得到的时间："+time);
							}
						 
					 }
					 
					 String avgTime2= stringBuilder2.toString();
					 String[] split2 = avgTime2.split(",");
					 Double avgtime2=0d;
					 int numSu2=0;
					 for (String time : split2) {
						 try {
							 if (time.contains("-")) {
									continue;
								  }
							 numSu2++;
							 double doubleTime = Double.parseDouble(time);
							  avgtime2=avgtime2+doubleTime;
							  
							} catch (Exception e) {
								numSu2--;
								logger.info("(获取平台站点仓库接口)b此时出现异常，得到的时间："+time);
							}
						 
					 }
					 
					 String avgTime3 = stringBuilder4.toString();
					 String[] split3 = avgTime3.split(",");
					 Double avgtime3=0d;
					 int numSu3=0;
					 for (String time : split3) {
						 try {
							 if (time.contains("-")||time.contains("0")) {
									continue;
								  }
							 numSu3++;
							 double doubleTime = Double.parseDouble(time);
							  avgtime3=avgtime3+doubleTime;
							  
							} catch (Exception e) {
								numSu3--;
								logger.info("(获取平台站点仓库接口)c此时出现异常，得到的时间："+time);
							}
						 
					 }
					 logger.info("(获取平台站点仓库接口)此为第"+(i+1)+"次调用，"+"总共出现异常次数："+num);
					 logger.info("(获取平台站点仓库接口)单次调用详细信息(所有成功时间)："+stringBuilder.toString());
					 logger.info("(获取平台站点仓库接口)单次调用详细信息(所有异常情况时间)："+stringBuilder4.toString());
					 logger.info("(获取平台站点仓库接口)单次调用详细信息(所有情况)："+stringBuilder2.toString());
					 logger.info("(获取平台站点仓库接口)全部成功总耗时（不含等待时间）："+avgtime1+",平均耗时（全部成功）："+avgtime1/numSu);
					 logger.info("(获取平台站点仓库接口)全部异常总耗时（不含等待时间）："+avgtime3+",平均耗时（所有异常情况）："+avgtime3/numSu3);
					 logger.info("(获取平台站点仓库接口)全部接口调用耗时（不含等待时间）："+avgtime2+",平均耗时（包含异常情况）："+avgtime2/numSu2);
					
		            
				}
			 //结束时间
			 long endTime = System.currentTimeMillis();
			 logger.info("调用完成(获取平台站点仓库接口)调用100次接口总耗时："+(endTime-initTime)/1000+"s，总共出现异常次数："+num);
	    		
	      }catch (Exception e) {
	    	  logger.error("(获取平台站点仓库接口)整体出现异常信息，异常信息如下："+e.getMessage());
		   }
	} 
	 
	 
	 
	 
	 
	 
	 
	 
	 
}
