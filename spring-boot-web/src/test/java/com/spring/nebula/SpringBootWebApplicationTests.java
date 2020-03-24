package com.spring.nebula;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;
import com.spring.nebula.api.dao.HandErrorLogDao;
import com.spring.nebula.api.entity.ErrorHandleLogVo;
import com.spring.nebula.api.entity.WmsPrintSkuLabel;
import com.spring.nebula.api.entity.WmsPrintSkuLabel.Data;
import com.spring.nebula.util.EmailUtil;
import com.spring.nebula.util.HttpUtil;
import com.spring.nebula.util.JsonResult;

/*@RunWith(SpringRunner.class)
@SpringBootTest*/
public class SpringBootWebApplicationTests {
    
	@Autowired
	HandErrorLogDao handErrorLogDao;
	
	@Autowired
	private EmailUtil emailUtil;
	
	/**
	 * 测试json解析异常问题
	 */
	@Test
	public void contextLoads() {
		   
		//sku信息，接口1和接口2
		//testData1(5);
		//memo，5和6（现存错误问题）
		//testData2(5);
	}
	
	
	public void testData1(int num) {
		try {
			 Gson gson = new Gson();
			 List<ErrorHandleLogVo> logInfos = handErrorLogDao.getLogInfos();
			 String sku = "";
			  for (ErrorHandleLogVo errorHandleLogVo : logInfos) {
				   if (errorHandleLogVo.getId()==num) {
					   sku = errorHandleLogVo.getResult();
				    }
			    }
				WmsPrintSkuLabel wmsPrintSkuLabel = new WmsPrintSkuLabel();
				wmsPrintSkuLabel.setWarehousename("中英万邑通海运仓库");
				Data data =wmsPrintSkuLabel.new Data();
				data.setSku(sku);
				data.setEnglishname("OT+TZDENGLIST");
				wmsPrintSkuLabel.setData(data);
				//String json = "{\"warehousename\":\"中英万邑通海运仓库\",\"consignee\":\"49732\",\"orderno\":\"A1810270006\",\"boxno\":\"A1810270006\",\"singleboxflage\":\"N\",\"data\":{\"sku\":\"OT+SLP+4.5\"+10P\",\"quantity\":130,\"englishname\":\"3000LM Super Bright LED\",\"skuname\":\"LED探照灯\"}}";
				//String json =" {\"origin\": \"A1810240095\", \"data\": [{\"sample_qty\": 0, \"refuse_qty\": 0, \"default_code\": \"OT+SLP+4.5\\\"+10P\", \"product_qty\": 117}], \"method\": \"PurchaseStockInOrder\", \"purchaser_no\": \"PO11251\"}";
				String json = gson.toJson(wmsPrintSkuLabel);
				
				//String json = sku;
				System.out.println("请求json:"+json);
				//String json = new gson
				String sendHttpPost = HttpUtil.sendHttpPost("http://192.168.0.28:8080/api/getSkuLabel", json);
				System.out.println("调用(获取产品尺寸接口)返回信息:"+sendHttpPost);
				JsonResult resultJson = gson.fromJson(sendHttpPost, JsonResult.class);
				System.out.println("返回sku信息："+resultJson.getMsg());
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		
	}
	
	public void testData2(int num) {
		try {
			 Gson gson = new Gson();
			 List<ErrorHandleLogVo> logInfos = handErrorLogDao.getLogInfos();
			 String sku = "";
			  for (ErrorHandleLogVo errorHandleLogVo : logInfos) {
				   if (errorHandleLogVo.getId()==num) {
					   sku = errorHandleLogVo.getMemo();
				    }
			    }
				WmsPrintSkuLabel wmsPrintSkuLabel = new WmsPrintSkuLabel();
				wmsPrintSkuLabel.setWarehousename("中英万邑通海运仓库");
				Data data =wmsPrintSkuLabel.new Data();
				data.setSku(sku);
				data.setEnglishname("OT+TZDENGLIST");
				wmsPrintSkuLabel.setData(data);
				String json = sku;
				System.out.println("请求json:"+json);
				String sendHttpPost = HttpUtil.sendHttpPost("http://192.168.0.28:8080/api/getSkuLabel3", json);
				System.out.println("调用(获取产品尺寸接口)返回信息:"+sendHttpPost);
				JsonResult resultJson = gson.fromJson(sendHttpPost, JsonResult.class);
				System.out.println("返回sku信息："+resultJson.getMsg());
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		
	}
	
	@Test
	public void testEmail() throws Exception {
		//emailUtil.sendEmail("532968876@qq.com", "532968876@qq.com", "IT测试", "这个是邮件测试内容");
		
	}

}
