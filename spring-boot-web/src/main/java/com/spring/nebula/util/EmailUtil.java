package com.spring.nebula.util;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import com.spring.nebula.api.entity.MailMqVo;
import com.spring.nebula.common.constants.NebulaItsConstant;

import freemarker.template.Template;
import freemarker.template.TemplateException;
/**
 * 邮件发送工具类
 * @author zheyan.yan
 *
 */
public class EmailUtil {
	private static final Logger logger = LoggerFactory.getLogger(EmailUtil.class);
	 /**
     * 
     * @param mail
     * @param mailSender
     */
    public static void sendTemplateMail(MailMqVo mail,JavaMailSender mailSender,FreeMarkerConfigurer configurer) {
    	String templateName=mail.getTemplateName();
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		 try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			 //发送者
			helper.setFrom(mail.getMailFrom());
			//接收者
			helper.setTo(mail.getMailTo());
			//发送的标题
			helper.setSubject(mail.getMailSubject());
			//此时为传入文件地址----发送附件
			List<String> fileUrlList = mail.getFileUrls();
			if (null!=fileUrlList&&fileUrlList.size()>0) {
				//删除特定文件夹下的所有文件信息（暂时未加）
				
				Map<String, Object> filesByFileUrl = getFilesByFileUrl(fileUrlList);
				for(Map.Entry<String, Object> entry : filesByFileUrl.entrySet()){
				    String fileName = entry.getKey();
				    File file = (File)entry.getValue();
				    helper.addAttachment(fileName, file);
				}
			}
			//此时为传入文件流---发送附件
			if (!StringUtils.isEmpty(mail.getFileStream())) {
				try {
					InputStream initialStream = mail.getFileStream();
					byte[] buffer = new byte[initialStream.available()];
					initialStream.read(buffer);
					File targetFile = new File("src/main/resources/targetFile.tmp");
					@SuppressWarnings("resource")
					OutputStream outStream = new FileOutputStream(targetFile);
					outStream.write(buffer); 
					helper.addAttachment("附件-2.jpg", targetFile);
				} catch (Exception e) {
					logger.error(e.getMessage());
					throw new RuntimeException(e);
				}
			}
			
			//此时为传入多个文件的方式
			if (null!=mail.getFiles()&&mail.getFiles().size()>0) {
				List<File> files = mail.getFiles();
				for (File file : files) {
					helper.addAttachment("附件-2.jpg", file);
				}
			}
			
			Map<String, Object> model = new HashMap<String, Object>();
	        model.put("username", mail.getUsername());
	        model.put("content", mail.getContent());
	            try {
	                Template template = configurer.getConfiguration().getTemplate(templateName);
	                try {
	                    String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
	                    helper.setText(text, true);
	                    mailSender.send(mimeMessage);
	                } catch (TemplateException e) {
	                	logger.error("使用freemarker生成模板发送邮件时出现异常-1："+e.getMessage());
	                	throw new RuntimeException(e);
	                }
	            } catch (IOException e) {
	            	logger.error("使用freemarker生成模板发送邮件时出现异常-2："+e.getMessage());
	            	throw new RuntimeException(e);
	            }
	        } catch (MessagingException e) {
	            e.printStackTrace();
	            logger.error("出现异常："+e.getMessage());
	            throw new RuntimeException(e);
	        }
     }
    
    /**
     * 简单邮件发送
     * @param mail
     * @param mailSender
     */
    public static void sendSimpleEmail(MailMqVo mail,JavaMailSender mailSender) {
    	try {
    		 //建立邮件消息
			SimpleMailMessage mainMessage = new SimpleMailMessage();
			//发送者
			mainMessage.setFrom(mail.getMailFrom());
			//接收者
			mainMessage.setTo(mail.getMailTo());
			//发送的标题
			mainMessage.setSubject(mail.getMailSubject());
			//发送的内容
			mainMessage.setText(mail.getMailText());
			
			mailSender.send(mainMessage);
		} catch (Exception e) {
			logger.error("出现异常："+e.getMessage());
			throw new RuntimeException();
		}
    	       
     }
    
    /**
     * 
     * @param mail
     * @param mailSender
     */
    public static void sendAttachmentEmail(MailMqVo mail,JavaMailSender mailSender) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			//发送者
			helper.setFrom(mail.getMailFrom());
			//接收者
			helper.setTo(mail.getMailTo());
			//发送的标题
			helper.setSubject(mail.getMailSubject());
			//发送的内容
			helper.setText(mail.getMailText());
			List<File> files = mail.getFiles();
			for (File file : files) {
				helper.addAttachment("附件", file);
			}
			mailSender.send(mimeMessage);
		} catch (Exception e) {
			logger.error("出现异常："+e.getMessage());
			throw new RuntimeException();
		}
		
		
     }
    
    /**
     * 
     * @param mail
     * @param mailSender
     */
    public static void sendInlineMail(MailMqVo mail,JavaMailSender mailSender) {
    	MimeMessage mimeMessage = mailSender.createMimeMessage();
		 try {
			    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			  //发送者
				helper.setFrom(mail.getMailFrom());
				//接收者
				helper.setTo(mail.getMailTo());
				//发送的标题
				helper.setSubject(mail.getMailSubject());
				helper.setText("<html><body><h2>哈，小燕...</h2><img src=\"cid:meinv\" ><img src=\"cid:fashe\" ><img src=\"cid:feiwen\" >"
						+ "<img src=\"cid:gaoxiao\" ><img src=\"cid:gongzuo\" ></body></html>", true);
				FileSystemResource file = new FileSystemResource(new File("E:\\图片测试\\meinv.gif"));
				FileSystemResource file2 = new FileSystemResource(new File("E:\\图片测试\\fashe.gif"));
				FileSystemResource file3 = new FileSystemResource(new File("E:\\图片测试\\feiwen.gif"));
				FileSystemResource file4 = new FileSystemResource(new File("E:\\图片测试\\gaoxiao.gif"));
				FileSystemResource file5 = new FileSystemResource(new File("E:\\图片测试\\gongzuo.gif"));
				helper.addInline("meinv", file);
				helper.addInline("fashe", file2);
				helper.addInline("feiwen", file3);
				helper.addInline("gaoxiao", file4);
				helper.addInline("gongzuo", file5);
				FileSystemResource file6 = new FileSystemResource(new File("E:\\图片测试\\odoo k32018.10.15.xlsx"));
				helper.addAttachment("附件-odoo k32018.10.15.xlsx", file6);
				mailSender.send(mimeMessage);
		} catch (Exception e) {
			   logger.error(e.getMessage());
			   mailSender.send(mimeMessage);
		}
		
     }
        /**
         * 读取输入流
         * @param inStream
         * @return
         * @throws Exception
         */
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
	
	/**
		 * 设置得到文件名称
		 * @param fileUrl
		 * @return
		 */
	    public static String getFileName(String fileUrl) {
			String fileName="temp.jpg";
			String[] split = fileUrl.split("/");
			if (split.length>0) {
				fileName= split[split.length-1].trim();
			}else {
				if (!StringUtils.isEmpty(fileUrl)) {
					if (fileUrl.length()>10) {
						fileName= fileUrl.substring(fileUrl.length()-10, fileUrl.length());
					}else {
						fileName=fileUrl;
					}
					
				}
				
			}
	    	return fileName;
	    }
	    
	    /**
	     * 下载并设置文件信息集合
	     * @param urls
	     * @return
	     */
	    public static Map<String, Object> getFilesByFileUrl(List<String> urls){
	    	Map<String, Object> fileList= new HashMap<>();
	    	for (String fileUrl : urls) {
	    		
	    		long currentTime = System.currentTimeMillis();
	    		//key
				String fileName = currentTime+"-"+getFileName(fileUrl);
				//-----------------------------------------
				try {
					 //new一个URL对象  
					//String fileUrl="http://192.168.2.67/group1/M00/05/7C/wKgCQ1sRN3yANftmAAAcesI7Ev460.xlsx";
			        URL url = new URL(fileUrl);
			        //打开链接  
			        HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
			        //设置请求方式为"GET"  
			        conn.setRequestMethod("GET");  
			        //超时响应时间为5秒  
			        conn.setConnectTimeout(10 * 1000);
			        //通过输入流获取图片数据  
			        InputStream inStream = conn.getInputStream();  
			        //得到图片的二进制数据，以二进制封装得到数据，具有通用性  
			        byte[] data = readInputStream(inStream);
			        //new一个文件对象用来保存图片，默认保存当前工程根目录  
			        //File file = new File("/usr/local/nebula/spring-nebula-web/file/"+fileName);
			        File file = new File(NebulaItsConstant.email_file_basepath+fileName);
			        //创建输出流  
			        FileOutputStream outStream = new FileOutputStream(file);
			        //写入数据
			        outStream.write(data);  
			        //关闭输出流  
			        outStream.close();  
			        fileList.put(fileName, file);
				} catch (Exception e) {
					logger.error("组装文件时出现异常："+e.getMessage());
					throw new RuntimeException(e);
				}
			}
	    	return fileList;
	    }
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
    
    
    
}
