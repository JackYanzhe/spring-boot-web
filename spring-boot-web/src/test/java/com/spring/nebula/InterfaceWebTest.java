package com.spring.nebula;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.oigbuy.jeesite.common.fastdfs.notify.Notify;
import com.oigbuy.jeesite.common.fastdfs.util.FastdfsHelper;
import com.spring.nebula.api.entity.MailMqVo;
import com.spring.nebula.java.itextpdf.util.BasePDFWrite;
import com.spring.nebula.mq.config.RabbitMQConfig;
import com.spring.nebula.mq.send.MessageClient;
import com.spring.nebula.mq.send.Send;

public class InterfaceWebTest {
	/*public static void main(String[] args) {
		Gson gson = new Gson();
		WmsPrintSkuLabel wmsPrintSkuLabel = new WmsPrintSkuLabel();
		
		//String json = "{\"warehousename\":\"中英万邑通海运仓库\",\"consignee\":\"49732\",\"orderno\":\"A1810270006\\u0026OT+TZD\\u002649732\",\"boxno\":\"A1810270006\\u0026OT+TZD\\u002649732\",\"singleboxflage\":\"N\",\"data\":{\"sku\":\"OT+TZ'D\",\"quantity\":130,\"englishname\":\"3000LM Super Bright LED\",\"skuname\":\"LED探照灯\"}}";
		wmsPrintSkuLabel.setWarehousename("中英万邑通海运仓库");
		Data data =wmsPrintSkuLabel.new Data();
		data.setSku("OT+T'ZD");
		data.setEnglishname("OT+TZD+ENGLIST");
		wmsPrintSkuLabel.setData(data);
		String json = gson.toJson(wmsPrintSkuLabel);
		System.out.println(json);
		//String json = new gson
		String sendHttpPost = HttpUtil.sendHttpPost("http://47.100.29.15:8080/api/judgeApiInfacePost", json);
		System.out.println("调用(获取产品尺寸接口)返回信息:"+sendHttpPost);
	}*/
	
	
	
	@Test
	public void testMq() throws Exception {
		 CachingConnectionFactory connectionFactory = new CachingConnectionFactory("47.100.29.15",5672);
	     connectionFactory.setUsername("admin");
	     connectionFactory.setPassword("123456");
	     connectionFactory.setVirtualHost("/");
	     connectionFactory.setPublisherConfirms(true);
	     RabbitTemplate template = new RabbitTemplate(connectionFactory);
	     Send send = new Send(template);
	     for (int i = 0; i < 10; i++) {
			 send.sendMsg("hello 老铁，你好啊"+i);
		 }
	     
	     MessageClient messageClient=new MessageClient("*********此为客户机启动时注册信息****",RabbitMQConfig.ROUTINGKEY_B,RabbitMQConfig.EXCHANGE_A );
		 send.sendMessages(messageClient);
	}
	
	@Test
	public void testMqJson() throws Exception {
		MailMqVo mailMqVo  = new MailMqVo();
		mailMqVo.setRoutingKeyMq(RabbitMQConfig.ROUTINGKEY_A);
		mailMqVo.setExchangeMq(RabbitMQConfig.EXCHANGE_A);
		mailMqVo.setMailFrom("532968876@qq.com");
		mailMqVo.setMailTo("532968876@qq.com");
		mailMqVo.setMailSubject("该邮件为收到消息后发送的邮件信息");
		mailMqVo.setMailText("成功发送邮件信息，hello，闫哲");
		mailMqVo.setTemplateName("emailTemple2.ftl");
		mailMqVo.setUsername("yanzhe");
		mailMqVo.setContent("每次调用都创建一个fresh流，当你考虑像JavaMail这样的API时,这个需求尤其重要。它在创建邮件附件的时候需要能够多次读取流。对于这样的一个用例，它要求getInputStream()每次返回一个新的流对象，输入流的底层资源不能为空，如果不能正确的获得输入流，将会抛出IOException!!!");
		List<String> fileUrlList= new ArrayList<>();
		fileUrlList.add("https://goss1.veer.com/creative/vcg/veer/612/veer-308763260.jpg");
		fileUrlList.add("https://goss.veer.com/creative/vcg/veer/612/veer-311871755.jpg");
		fileUrlList.add("http://192.168.2.67/group1/M00/05/7C/wKgCQ1sRN3yANftmAAAcesI7Ev460.xlsx");
		mailMqVo.setFileUrls(fileUrlList);
		
		Gson gson = new Gson();
		System.out.println(gson.toJson(mailMqVo));
		
	}
	
	@Test
	public void testFile() throws Exception {
		File file = new File("http://img1.imgtn.bdimg.com/it/u=415002215,2999154422&fm=26&gp=0.jpg");
		try {
			  //new一个URL对象  
	        URL url = new URL("https://goss1.veer.com/creative/vcg/veer/612/veer-308763260.jpg");  
	        //打开链接  
	        HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
	        //设置请求方式为"GET"  
	        conn.setRequestMethod("GET");  
	        //超时响应时间为5秒  
	        conn.setConnectTimeout(5 * 1000);  
	        //通过输入流获取图片数据  
	        InputStream inStream = conn.getInputStream();  
	        //得到图片的二进制数据，以二进制封装得到数据，具有通用性  
	        byte[] data = readInputStream(inStream);  
	        //new一个文件对象用来保存图片，默认保存当前工程根目录  
	        File imageFile = new File("D://BeautyGirl.jpg");  
	        //创建输出流  
	        FileOutputStream outStream = new FileOutputStream(imageFile);  
	        //写入数据  
	        outStream.write(data);  
	        //关闭输出流  
	        outStream.close();  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static byte[] readInputStream(InputStream inStream) throws Exception{  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        //创建一个Buffer字符串  
        byte[] buffer = new byte[1024];  
        //每次读取的字符串长度，如果为-1，代表全部读取完毕  
        int len = 0;  
        //使用一个输入流从buffer里把数据读取出来  
        while( (len=inStream.read(buffer)) != -1 ){  
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度  
            outStream.write(buffer, 0, len);  
        }  
        //关闭输入流  
        inStream.close();
        //把outStream里的数据写入内存  
        return outStream.toByteArray();  
    }  
	
	
	@Test
	public void test5() throws Exception {
		String fileUrl="http://192.168.2.67/group1/M00/05/7C/wKgCQ1sRN3yANftmAAAcesI7Ev460.xlsx";
		String fileName="temp.jpg";
		String[] split = fileUrl.split("/");
		if (split.length>0) {
			fileName= split[split.length-1];
		}else {
			if (!StringUtils.isEmpty(fileUrl)&&fileUrl.length()>10) {
				System.out.println(fileUrl.length());
				fileName= fileUrl.substring(fileUrl.length()-10, fileUrl.length());
			}
			
		}
		System.out.println(fileName);
	}
	
	
	 /**
	  * 测试fastdfs上传/下载操作
	  * @throws Exception
	  */
	 @Test
	  public void test78() throws Exception {
		 System.out.println(1111);
	    Notify uploadFile = FastdfsHelper.uploadFile(new File("E:\\图片测试\\logo.jpg"));
	    System.out.println(uploadFile.getAbsoluteUrl()+","+uploadFile.getRelativeUrl());
	  }
	
	
	@Test
	public void test79() throws Exception {
		BigInteger globalPageSize = getGlobalPageSize("45446464654674fgdfgdf87844545");
		System.out.println(globalPageSize);
		
	}
	
	public BigInteger getGlobalPageSize(String key) {
		     BigInteger size1 =null;
		     BigInteger size2 = new BigInteger("15");
		    if (null!=key && !"".equals(key)) {
		    	 size1 = new BigInteger(key);
			}
			return size1 == null ? size2 : size1;
			//return size1.add(size2);
	}
	
	@Test
	public void testRedis() throws Exception {
		/* Jedis jedis = new Jedis("47.100.29.15", 6379);
	        jedis.auth("123456");
	        jedis.set("name2", "yanzhe2");
	        String value = jedis.get("name2");
	        System.out.println("得到的名称为："+value);
	        jedis.close();*/
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
