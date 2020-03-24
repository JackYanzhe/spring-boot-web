package com.spring.nebula.redis.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.spring.nebula.api.controller.ApiController;
import com.spring.nebula.api.entity.ErrorHandleLogVo;
import com.spring.nebula.api.service.HandErrorLogService;
import com.spring.nebula.redis.entity.User;
import com.spring.nebula.util.JsonResult;
import com.spring.nebula.util.RedisUtil;
import com.spring.nebula.util.ResultCode;

/**
 * 
 * @author zheyan.yan
 *
 */
@RestController
@RequestMapping("/redis")
public class RedisTController {
	private static final Logger log = LoggerFactory.getLogger(ApiController.class);
	
	@Autowired
    private  StringRedisTemplate stringRedisTemplate;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private HandErrorLogService handErrorLogService;
	
    /**
	 * 接口测试get
	 * @param sku
	 * @return
	 */
    @GetMapping("/testRedisConfg")
    @ResponseBody
    public JsonResult testRedisConfg(String key,String value,String hash){
    	JsonResult jsonResult = new JsonResult();
        RedisUtil redisUtil = new RedisUtil(redisTemplate);
        //redisUtil.setRedisTemplate(redisTemplate);
        redisUtil.set("y003", "y003333");
        
        jsonResult.setData(redisUtil.get("y003"));
    	jsonResult.setCode(ResultCode.SUCCESS);
        return jsonResult;
    }
    
    /**
   	 * 接口测试get
   	 * @param sku
   	 * @return
   	 */
       @GetMapping("/testRedisHash")
       @ResponseBody
       public JsonResult testRedisHash(String key,String type){
       	JsonResult jsonResult = new JsonResult();
       	Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        List<ErrorHandleLogVo> list = new ArrayList<>();
        try {
        	RedisUtil redisUtil = new RedisUtil(redisTemplate);
            //查询错误日志信息并将日志存入到redis中
            List<ErrorHandleLogVo> logInfos = handErrorLogService.getLogInfos();
           
            if (null!=type && type.equals("hash")) {
         	   
         	   for (int i = 0; i < logInfos.size(); i++) {
             	   map.put("log-"+i,  logInfos.get(i));
     		   }
                redisUtil.hmset(key, map);
                Map<Object, Object> hmget = redisUtil.hmget(key);
                for (Entry<Object, Object> entry : hmget.entrySet()) {
             	   Object key2 = entry.getKey();
             	   Object value = entry.getValue();
             	   System.out.println("---hash--得到的key："+key2+",value:"+gson.toJson(value));
                 }
                jsonResult.setData(hmget);
            	   
 		   }else if (null!=type && type.equals("list")) {
 			   System.out.println("--------------------------");
 			   redisUtil.lSet(key, logInfos);
 			   List<Object> lGet = redisUtil.lGet(key, 0, 0);
 			   for (Object object : lGet) {
 				   System.out.println("--list--得到的key："+key+",value:"+gson.toJson(object));
 			    }
 			   jsonResult.setData(lGet);
 			   
 		   }else if (null!=type && type.equals("set")) {
 			   redisUtil.sSet(key, logInfos);
 			   Set<Object> sGet = redisUtil.sGet(key);
 			   for (Object object : sGet) {
 				   System.out.println("--set--得到的key："+key+",value:"+gson.toJson(object));
 			   }
 			   jsonResult.setData(sGet);
 		   }else {
 			   //string类型
 			   redisUtil.set(key, gson.toJson(logInfos));
 			   String value = (String) redisUtil.get(key);
 			   System.out.println("--String--得到的key："+key+",value:"+gson.toJson(value));
 			   jsonResult.setData(value);
 			   
 		   }
            jsonResult.setCode(ResultCode.SUCCESS);
		} catch (Exception e) {
			log.error("存入redis时出现异常信息："+e.getMessage());
			jsonResult.setCode(ResultCode.EXCEPTION);
			jsonResult.setMsg(e.getMessage());
		}
        
          
        
          
           return jsonResult;
       }
	
	
       
       
       /**
   	 * 接口测试get
   	 * @param sku
   	 * @return
   	 */
       @GetMapping("/redisTest")
       @ResponseBody
       public JsonResult getAllLogs(String key,String value,String hash){
       	JsonResult jsonResult = new JsonResult();
          
       	if (StringUtils.isEmpty(value)) {
       		List<String> list = operateList(key);
       		jsonResult.setData(list);
       		jsonResult.setMsg("成功");
           	jsonResult.setCode(ResultCode.SUCCESS);
           	return jsonResult;
   		}
       	if (!StringUtils.isEmpty(hash)) {
       		operateHash();
       		jsonResult.setMsg("成功hash");
           	jsonResult.setCode(ResultCode.SUCCESS);
           	return jsonResult;
   			
   		}
       	String vl = operateString( key,value);
       	jsonResult.setMsg(vl);
       	jsonResult.setCode(ResultCode.SUCCESS);
           return jsonResult;
       }
       
       
       
       
       
       
       
       
       
	/**
     * 操作字符串
     */
    private  String operateString(String key,String value) {
       // stringRedisTemplate.opsForValue().set(key, value);
        String values = stringRedisTemplate.opsForValue().get(key);
        log.info("stringRedisTemplate输出值：{}", values);
        return values;
    }

    /**
     * Redis List操作，Redis列表是简单的字符串列表，按照插入顺序排序。可以添加一个元素到列表的头部（左边）或者尾部（右边）
     */
    private List<String> operateList(String key) {
        String ke = "website";
        ListOperations<String, String> listOperations = stringRedisTemplate.opsForList();
        //从左压入栈
        listOperations.leftPush(key, "Github");
        listOperations.leftPush(key, "CSDN");
        //从右压入栈
        listOperations.rightPush(key, "SegmentFault");
        log.info("list size:{}", listOperations.size(ke));
        List<String> list = listOperations.range(key, 0, 2);
        list.forEach(log::info);
        return list;
    }
    
    /**
     * 操作hash，存放User对象
     */
    private void operateHash() {
        String key = "user";
        HashOperations<String, String, User> hashOperations = redisTemplate.opsForHash();
        hashOperations.put(key, "user1", new User("yanzhe", 100));
        hashOperations.put(key, "user2", new User("haiyan", 200));
        hashOperations.put(key, "user3", new User("liujie", 300));
        log.info("hash size:{}", hashOperations.size(key));
        log.info("--------拿到Map的key集合--------");
        Set<String> keys = hashOperations.keys(key);
        keys.forEach(log::info);
        log.info("--------拿到Map的value集合--------");
        List<User> users = hashOperations.values(key);
        users.forEach(user -> log.info(user.toString()));
        log.info("--------拿到user1的value--------");
        User user = hashOperations.get(key, "user1");
        log.info(user.toString());
    }

}
