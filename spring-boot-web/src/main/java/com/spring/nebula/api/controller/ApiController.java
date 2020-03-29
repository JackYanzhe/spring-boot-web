package com.spring.nebula.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.spring.nebula.api.entity.ErrorHandleLogVo;
import com.spring.nebula.api.entity.MailMqVo;
import com.spring.nebula.api.entity.WmsPrintSkuLabel;
import com.spring.nebula.api.service.HandErrorLogService;
import com.spring.nebula.mq.send.MessageClient;
import com.spring.nebula.mq.send.Send;
import com.spring.nebula.util.JsonResult;
import com.spring.nebula.util.ResultCode;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author zheyan.yan
 *
 */
@Api(tags = "API接口", value = "第三方API接口")
@RestController
@RequestMapping("/api")
public class ApiController {
	private static final Logger logger = LoggerFactory.getLogger(ApiController.class);
	public static final String EXCHANGE_A = "my-mq-exchange_A";
	public static final String QUEUE_A = "QUEUE_A";
	public static final String ROUTINGKEY_A = "spring-boot-routingKey_A";
	@Autowired
    private Send sender;
	@Autowired
	HandErrorLogService handErrorLogService;
	
	/**
	 * 
	 * @param mailMqVo
	 * @return
	 */
	@ApiOperation(value = "发送emailMq消息接口", notes = "")
    @PostMapping("/sendMqMsgToMail")
    @ResponseBody
    public JsonResult sendMqMsgToMail(@RequestBody MailMqVo mailMqVo){
		
		//kgjfk
    	JsonResult jsonResult = new JsonResult();
    	Gson gson = new Gson();
    	try {
			logger.info("调用消息发送接口传来参数："+gson.toJson(mailMqVo));
			if (!StringUtils.isEmpty(mailMqVo.getRoutingKeyMq()) && !StringUtils.isEmpty(mailMqVo.getExchangeMq())) {
				MessageClient message = new MessageClient(gson.toJson(mailMqVo), mailMqVo.getRoutingKeyMq(), mailMqVo.getExchangeMq());
				sender.sendMessage(message);
				jsonResult.setCode(ResultCode.SUCCESS);
				jsonResult.setMsg("消息发送成功");
			}else {
				logger.info("传来参数错误，相应Mqkey值不能为空!!");
				jsonResult.setCode(ResultCode.PARAMS_ERROR);
				jsonResult.setMsg("传来参数错误，相应Mqkey值不能为空!!");
			}
		} catch (Exception e) {
			jsonResult.setCode(ResultCode.EXCEPTION);
			jsonResult.setMsg("出现异常："+e.getMessage());
		}
    	
    	jsonResult.setCode(ResultCode.SUCCESS);
        return jsonResult;
    }
	
    
    /**
	 * 
	 * @param mailMqVo
	 * @return
	 */
	@ApiOperation(value = "发送emailMq消息接口ForGet", notes = "")
    @GetMapping("/sendMqMsgToMailForGet")
    @ResponseBody
    public JsonResult sendMqMsgToMailForGet(MailMqVo mailMqVo){
    	JsonResult jsonResult = new JsonResult();
    	Gson gson = new Gson();
    	try {
			logger.info("调用消息发送接口传来参数："+gson.toJson(mailMqVo));
			if (!StringUtils.isEmpty(mailMqVo.getRoutingKeyMq()) && !StringUtils.isEmpty(mailMqVo.getExchangeMq())) {
				MessageClient message = new MessageClient(gson.toJson(mailMqVo), mailMqVo.getRoutingKeyMq(), mailMqVo.getExchangeMq());
				sender.sendMessage(message);
				jsonResult.setCode(ResultCode.SUCCESS);
				jsonResult.setMsg("消息发送成功");
			}else {
				logger.info("传来参数错误，相应Mqkey值不能为空!!");
				jsonResult.setCode(ResultCode.PARAMS_ERROR);
				jsonResult.setMsg("传来参数错误，相应Mqkey值不能为空!!");
			}
		} catch (Exception e) {
			jsonResult.setCode(ResultCode.EXCEPTION);
			jsonResult.setMsg("出现异常："+e.getMessage());
		}
    	
    	jsonResult.setCode(ResultCode.SUCCESS);
        return jsonResult;
    }
	
	 /**
		 * 接口测试get
		 * @param sku
		 * @return
		 */
	    @GetMapping("/getTest")
	    @ResponseBody
	    public JsonResult getAllLogs(String judgeInfo){
	    	JsonResult jsonResult = new JsonResult();
	    	List<ErrorHandleLogVo> logInfos = handErrorLogService.getLogInfos();
	    	Gson gson = new Gson();
	    	for (ErrorHandleLogVo errorHandleLogVo : logInfos) {
				logger.info(gson.toJson(errorHandleLogVo));
			}
	    	jsonResult.setData(logInfos);
	    	
	    	logger.info("getTest传来的参数信息："+judgeInfo);
	    	jsonResult.setCode(ResultCode.SUCCESS);
	        return jsonResult;
	    }
	/**
	 * 接口测试post
	 * @param sku
	 * @return
	 */
	@ApiOperation(value = "接口测试post接口", notes = "")
    @PostMapping("/judgeApiInfacePost")
    @ResponseBody
    public JsonResult judgeApiInface(@RequestBody String judgeInfo){
    	JsonResult jsonResult = new JsonResult();
    	logger.info("传来的参数信息："+judgeInfo);
    	try {
    		//等待30s判断接口调用是否超时
    		for (int i = 0; i < 10; i++) {
    			//等待60s
    			Thread.sleep(50);
    			logger.info("已等待"+i+"s");
			}
		} catch (Exception e) {
			logger.error("出现异常信息");
		}
    	logger.info("调用完成");
    	jsonResult.setCode(ResultCode.SUCCESS);
    	jsonResult.setMsg("调用成功");
        return jsonResult;
    }
    
    /**
	 * 接口测试get
	 * @param sku
	 * @return
	 */
    @GetMapping("/judgeApiInfaceGet")
    @ResponseBody
    public JsonResult getSkuSizeInfo(String judgeInfo){
    	JsonResult jsonResult = new JsonResult();
    	logger.info("get传来的参数信息："+judgeInfo);
    	jsonResult.setCode(ResultCode.SUCCESS);
    	jsonResult.setMsg("get请求调用成功");
        return jsonResult;
    }
	
	
    /**打印产品标签**/
    @PostMapping("/getSkuLabel")
    @ResponseBody
    public JsonResult getSkuLabel(@RequestBody WmsPrintSkuLabel wmsPrintSkuLabel){
    	System.out.println("------------------------开始进入接口进行操作------------------");
        JsonResult jsonResult = new JsonResult();
        Gson gson = new Gson();
        try {
        	logger.info("==>打印产品标签传入参数," + gson.toJson(wmsPrintSkuLabel)+","+wmsPrintSkuLabel.getWarehousename());
        	System.out.println("传来的sku信息："+wmsPrintSkuLabel.getData().getSku());
        	jsonResult.setMsg(wmsPrintSkuLabel.getData().getSku());
            String jsonResultStr = gson.toJson(jsonResult);
            //10-14修改打印日志
            if (jsonResultStr!=null&&jsonResultStr.length()>400) {
				jsonResultStr = jsonResultStr.substring(0, 400);
			}
            logger.info("==>调用打印产品标签接口返回信息(截取):"+jsonResultStr);
        }catch (Exception e){
        	logger.error("打印产品标签失败, " + e.getMessage());
            jsonResult = new JsonResult(ResultCode.EXCEPTION);
        }
        return jsonResult;
    }
    
    
    /**打印产品标签**/
    @PostMapping("/getSkuLabel2")
    @ResponseBody
    public JsonResult getSkuLabel2(@RequestBody String wmsPrintSkuLabel2){
    	System.out.println("开始进入接口进行操作:"+wmsPrintSkuLabel2);
        JsonResult jsonResult = new JsonResult();
        Gson gson = new Gson();
        try {
        	WmsPrintSkuLabel wmsPrintSkuLabel = gson.fromJson(wmsPrintSkuLabel2, WmsPrintSkuLabel.class);
        	
        	logger.info("==>打印产品标签传入参数," + gson.toJson(wmsPrintSkuLabel));
        	System.out.println("传来的sku信息："+wmsPrintSkuLabel.getData().getSku());
        	jsonResult.setMsg(wmsPrintSkuLabel.getData().getSku());
            String jsonResultStr = gson.toJson(jsonResult);
            //10-14修改打印日志
            if (jsonResultStr!=null&&jsonResultStr.length()>400) {
				jsonResultStr = jsonResultStr.substring(0, 400);
			}
            logger.info("==>调用打印产品标签接口返回信息(截取):"+jsonResultStr);
        }catch (Exception e){
        	logger.error("打印产品标签失败, " + e.getMessage());
            jsonResult = new JsonResult(ResultCode.EXCEPTION);
        }
        return jsonResult;
    }
	
    
    /**打印产品标签**/
    @PostMapping("/getSkuLabel3")
    @ResponseBody
    public JsonResult getSkuLabel3(@RequestBody WmsPrintSkuLabel wmsPrintSkuLabel){
    	System.out.println("------------------------开始进入接口进行操作------------------");
        JsonResult jsonResult = new JsonResult();
        Gson gson = new Gson();
        try {
        	logger.info("==>打印产品标签传入参数," + gson.toJson(wmsPrintSkuLabel)+","+wmsPrintSkuLabel.getWarehousename());
        	System.out.println("传来的仓库信息："+wmsPrintSkuLabel.getWarehousename());
        	jsonResult.setMsg(wmsPrintSkuLabel.getWarehousename());
            String jsonResultStr = gson.toJson(jsonResult);
            //10-14修改打印日志
            if (jsonResultStr!=null&&jsonResultStr.length()>400) {
				jsonResultStr = jsonResultStr.substring(0, 400);
			}
            logger.info("==>调用打印产品标签接口返回信息(截取):"+jsonResultStr);
        }catch (Exception e){
        	logger.error("打印产品标签失败, " + e.getMessage());
            jsonResult = new JsonResult(ResultCode.EXCEPTION);
        }
        return jsonResult;
    }
    
    /**打印仓库信息**/
    @PostMapping("/getSkuLabel4")
    @ResponseBody
    public JsonResult getSkuLabel4(@RequestBody String wmsPrintSkuLabel2){
    	System.out.println("开始进入接口进行操作:"+wmsPrintSkuLabel2);
        JsonResult jsonResult = new JsonResult();
        Gson gson = new Gson();
        try {
        	WmsPrintSkuLabel wmsPrintSkuLabel = gson.fromJson(wmsPrintSkuLabel2, WmsPrintSkuLabel.class);
        	
        	logger.info("==>打印产品标签传入参数," + gson.toJson(wmsPrintSkuLabel));
        	System.out.println("传来的仓库信息："+wmsPrintSkuLabel.getWarehousename());
        	jsonResult.setMsg(wmsPrintSkuLabel.getWarehousename());
            String jsonResultStr = gson.toJson(jsonResult);
            //10-14修改打印日志
            if (jsonResultStr!=null&&jsonResultStr.length()>400) {
				jsonResultStr = jsonResultStr.substring(0, 400);
			}
            logger.info("==>调用打印产品标签接口返回信息(截取):"+jsonResultStr);
        }catch (Exception e){
        	logger.error("打印产品标签失败, " + e.getMessage());
            jsonResult = new JsonResult(ResultCode.EXCEPTION);
        }
        return jsonResult;
    }
	
	
	
	
}
