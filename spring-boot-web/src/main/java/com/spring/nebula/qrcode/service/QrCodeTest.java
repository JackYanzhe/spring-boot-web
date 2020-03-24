package com.spring.nebula.qrcode.service;

public class QrCodeTest {

	public static void main(String[] args) throws Exception {
		// 存放在二维码中的内容
		String text = "我是闫哲";
		// 嵌入二维码的图片路径
		String imgPath = "D:/111/qt.png";
		// 生成的二维码的路径及名称
		String destPath = "D:/111/jam.jpg";
		//生成二维码
		QRCodeUtil.encode(text, imgPath, destPath, true);
		// 解析二维码
		String str = QRCodeUtil.decode(destPath);
		// 打印出解析出的内容
		System.out.println(str);
		//如何实现扫码登录
		/**
		 * 1.首先生成二维码信息
		 * 2.使用公司内部系统app进行二维码扫描
		 * 3.通过二维码扫描登陆信息，此时内部处理app对应的账户信息，根据内部账户信息查询内部员工信息数据，判断符合则直接登陆，不符合则显示登陆失败
		 * 问题：
		 *    1.如何实现内部判断处理："此时需要再app进行扫描时对应拿到二维码信息后再对应的方法中做相关处理"
		 * 二维码要求：
		 *    1.生成二维码需要有公司标记信息特点，只有属于自己公司的二维码才可以扫描后操作
		 *    
		 * 	  
		 * 生成二维码时存入redis中，设置过期时间（uuid即可）（json）
		 * 扫描二维码（json）时进行解析json信息，之后做相关处理
		 * 
		 * 1.首先新建app
		 * 2.可以扫描，之后调用接口处理
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 */
 
	}

}
