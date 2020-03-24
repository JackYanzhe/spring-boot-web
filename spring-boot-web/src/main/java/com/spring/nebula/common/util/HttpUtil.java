package com.spring.nebula.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * Http工具类
 * 
 * <p>
 * Http工具类，为系统提供通用Http访问操作方法：
 * 
 * <p>
 * 1、发送GET请求；
 * <p>
 * 2、发送POST请求。
 * 
 */
public class HttpUtil {

	/**
	 * <p>
	 * 发送GET请求
	 * 
	 * @param url
	 *            GET请求地址
	 * 
	 * @return 与当前请求对应的响应内容字节数组
	 * 
	 */
	public static byte[] doGet(String url) {

		return HttpUtil.doGet(url, null, null, 0);
	}

	/**
	 * <p>
	 * 发送GET请求
	 * 
	 * @param url
	 *            GET请求地址
	 * @param headerMap
	 *            GET请求头参数容器
	 * 
	 * @return 与当前请求对应的响应内容字节数组
	 * 
	 */
	public static byte[] doGet(String url, Map<String, Object> headerMap) {
		return HttpUtil.doGet(url, headerMap, null, 0);
	}

	/**
	 * <p>
	 * 发送GET请求
	 * 
	 * @param url
	 *            GET请求地址
	 * @param proxyUrl
	 *            代理服务器地址
	 * @param proxyPort
	 *            代理服务器端口号
	 * 
	 * @return 与当前请求对应的响应内容字节数组
	 * 
	 * @modify 窦海宁, 2012-03-19
	 */
	public static byte[] doGet(String url, String proxyUrl, int proxyPort) {

		return HttpUtil.doGet(url, null, proxyUrl, proxyPort);
	}

	/**
	 * <p>
	 * 发送GET请求
	 * 
	 * @param url
	 *            GET请求地址
	 * @param headerMap
	 *            GET请求头参数容器
	 * @param proxyUrl
	 *            代理服务器地址
	 * @param proxyPort
	 *            代理服务器端口号
	 * 
	 * @return 与当前请求对应的响应内容字节数组
	 * 
	 * @modify 窦海宁, 2012-03-19
	 */
	public static byte[] doGet(String url, Map<String, Object> headerMap, String proxyUrl, int proxyPort) {

		byte[] content = null;
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(url);

		if (headerMap != null) {

			// 头部请求信息
			if (headerMap != null) {

				Iterator<Entry<String, Object>> iterator = headerMap.entrySet().iterator();
				while (iterator.hasNext()) {

					Entry<String, Object> entry = (Entry<String, Object>) iterator.next();
					getMethod.addRequestHeader(entry.getKey().toString(), entry.getValue().toString());
				}
			}
		}

		if (StringUtils.isNotBlank(proxyUrl)) {

			httpClient.getHostConfiguration().setProxy(proxyUrl, proxyPort);
		}

		// 设置成了默认的恢复策略，在发生异常时候将自动重试3次，在这里你也可以设置成自定义的恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 10000);
		// postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER , new
		// DefaultHttpMethodRetryHandler());
		InputStream inputStream = null;
		try {

			if (httpClient.executeMethod(getMethod) == HttpStatus.SC_OK) {

				// 读取内容
				inputStream = getMethod.getResponseBodyAsStream();
				content = IOUtils.toByteArray(inputStream);
			} else {

				System.err.println("Method failed: " + getMethod.getStatusLine());
			}
		} catch (IOException ex) {

			ex.printStackTrace();
		} finally {

			IOUtils.closeQuietly(inputStream);
			getMethod.releaseConnection();
		}
		return content;
	}

	/**
	 * <p>
	 * 发送POST请求
	 * 
	 * @param url
	 *            POST请求地址
	 * @param parameterMap
	 *            POST请求参数容器
	 * 
	 * @return 与当前请求对应的响应内容字节数组
	 * 
	 */
	public static byte[] doPost(String url, Map<String, Object> parameterMap) {

		return HttpUtil.doPost(url, null, parameterMap, null, null, 0);
	}

	/**
	 * <p>
	 * 发送POST请求
	 * 
	 * @param url
	 *            POST请求地址
	 * @param parameterMap
	 *            POST请求参数容器
	 * @param paramCharset
	 *            参数字符集名称
	 * 
	 * @return 与当前请求对应的响应内容字节数组
	 * 
	 * @modify 窦海宁, 2012-05-21
	 */
	public static byte[] doPost(String url, Map<String, Object> parameterMap, String paramCharset) {

		return HttpUtil.doPost(url, null, parameterMap, paramCharset, null, 0);
	}

	/**
	 * <p>
	 * 发送POST请求
	 * 
	 * @param url
	 *            POST请求地址
	 * @param headerMap
	 *            POST请求头参数容器
	 * @param parameterMap
	 *            POST请求参数容器
	 * @param paramCharset
	 *            参数字符集名称
	 * 
	 * @return 与当前请求对应的响应内容字节数组
	 * 
	 * @modify 窦海宁, 2012-05-21
	 */
	public static byte[] doPost(String url, Map<String, Object> headerMap, Map<String, Object> parameterMap,
			String paramCharset) {

		return HttpUtil.doPost(url, headerMap, parameterMap, paramCharset, null, 0);
	}

	/**
	 * <p>
	 * 发送POST请求
	 * 
	 * @param url
	 *            POST请求地址
	 * @param parameterMap
	 *            POST请求参数容器
	 * @param paramCharset
	 *            参数字符集名称
	 * @param proxyUrl
	 *            代理服务器地址
	 * @param proxyPort
	 *            代理服务器端口号
	 * 
	 * @return 与当前请求对应的响应内容字节数组
	 * 
	 */
	public static byte[] doPost(String url, Map<String, Object> parameterMap, String paramCharset, String proxyUrl,
			int proxyPort) {

		return HttpUtil.doPost(url, null, parameterMap, paramCharset, proxyUrl, proxyPort);
	}

	/**
	 * <p>
	 * 发送POST请求
	 * 
	 * @param url
	 *            POST请求地址
	 * @param headerMap
	 *            POST请求头参数容器
	 * @param parameterMap
	 *            POST请求参数容器
	 * @param paramCharset
	 *            参数字符集名称
	 * @param proxyUrl
	 *            代理服务器地址
	 * @param proxyPort
	 *            代理服务器端口号
	 * 
	 * @return 与当前请求对应的响应内容字节数组
	 * 
	 * @modify 窦海宁, 2012-05-21
	 */
	public static byte[] doPost(String url, Map<String, Object> headerMap, Map<String, Object> parameterMap,
			String paramCharset, String proxyUrl, int proxyPort) {

		byte[] content = null;
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);

		if (StringUtils.isNotBlank(paramCharset)) {

			postMethod.getParams().setContentCharset(paramCharset);
			postMethod.getParams().setHttpElementCharset(paramCharset);
		}

		if (headerMap != null) {

			// 头部请求信息
			if (headerMap != null) {

				Iterator<Entry<String, Object>> iterator = headerMap.entrySet().iterator();
				while (iterator.hasNext()) {

					Entry<String, Object> entry = (Entry<String, Object>) iterator.next();
					postMethod.addRequestHeader(entry.getKey().toString(), entry.getValue().toString());
				}
			}
		}

		Iterator<String> iterator = parameterMap.keySet().iterator();
		while (iterator.hasNext()) {

			String key = (String) iterator.next();
			postMethod.addParameter(key, (String) parameterMap.get(key));
		}

		if (StringUtils.isNotBlank(proxyUrl)) {

			httpClient.getHostConfiguration().setProxy(proxyUrl, proxyPort);
		}

		// 设置成了默认的恢复策略，在发生异常时候将自动重试3次，在这里你也可以设置成自定义的恢复策略
		postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 10000);
		// postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER , new
		// DefaultHttpMethodRetryHandler());
		InputStream inputStream = null;
		try {

			if (httpClient.executeMethod(postMethod) == HttpStatus.SC_OK) {

				// 读取内容
				inputStream = postMethod.getResponseBodyAsStream();
				content = IOUtils.toByteArray(inputStream);
			} else {

				System.err.println("Method failed: " + postMethod.getStatusLine());
			}
		} catch (IOException ex) {

			ex.printStackTrace();
		} finally {

			IOUtils.closeQuietly(inputStream);
			postMethod.releaseConnection();
		}
		return content;
	}
	
	  /**
     * 发送传参的Post请求
     *
     * @param url     发送请求的URL
     * @param param   请求参数，请求参数是 json
     */
    public static String sendHttpPost(String url,String param){
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
        	
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("Content-Type","application/json; charset=utf-8");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.write(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.toString(), e);
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }


}