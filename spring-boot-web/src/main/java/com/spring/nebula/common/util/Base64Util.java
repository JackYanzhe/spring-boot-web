package com.spring.nebula.common.util;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.springframework.util.Base64Utils;

/**
 * 
* Base64Util.java
*  
* @author qiang.zhou 
* on 2018年5月7日  新建
 */
public class Base64Util {

	/**
	 * base64 byte[] 转本地图片
	 * @param imgStr
	 * @param path
	 * @return
	 */
	 public static boolean generateImage(String imgStr, String path) {
	        if (imgStr == null) {
				return false;
			}
	        try {
	            // 解密
	            byte[] b = Base64Utils.decodeFromString(imgStr);
	            // 处理数据
	            for (int i = 0; i < b.length; ++i) {
	                if (b[i] < 0) {
	                    b[i] += 256;
	                }
	            }
	            OutputStream out = new FileOutputStream(path);
	            out.write(b);
	            out.flush();
	            out.close();
	            return true;
	        } catch (Exception e) {
	            return false;
	        }
	    }
}
