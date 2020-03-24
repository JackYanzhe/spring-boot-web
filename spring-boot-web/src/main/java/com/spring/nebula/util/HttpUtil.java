/**
 *
 */
package com.spring.nebula.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

/**
 * Http公工具
 *
 * @author tony.hao
 * @data 2017年3月30日
 */
public class HttpUtil {

    private static final int ERROR_CODE = 400;

    /**
     * 发送传参的Post请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 {}
     */
    public static String sendHttpPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url       发送请求的 URL
     * @param json      请求参数
     * @param useProxy  是否使用代理
     * @param proxyUrl  代理地址
     * @param proxyPort 代理端口
     * @param token     访问令牌
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String json, String param, boolean useProxy, String proxyUrl, int proxyPort, String token) {
        PrintWriter pWriter = null;
        BufferedReader bReader = null;
        String result = "";

        try {
            String urlNameString = "".equals(param) ? url : url + "?" + param;
            URL realUrl = new URL(urlNameString);
            URLConnection conn = null;
            if (useProxy) {
                @SuppressWarnings("static-access")
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyUrl, proxyPort));
                // 打开和URL之间的连接
                conn = realUrl.openConnection(proxy);
            } else {
                conn = realUrl.openConnection();
            }
            // 设置通用的请求属性
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);

            // 获取URLConnection对象对应的输出流
            pWriter = new PrintWriter(conn.getOutputStream());
            // 写入json参数
            pWriter.write(json);
            // flush输出流的缓冲
            pWriter.flush();
            // 定义BufferedReader输入流来读取URL的响应
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            if (httpConn.getResponseCode() >= ERROR_CODE) {
                bReader = new BufferedReader(new InputStreamReader(httpConn.getErrorStream()));
            } else {
                bReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
            }
            String line;
            while ((line = bReader.readLine()) != null) {
                result += line;
            }

        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (pWriter != null) {
                    pWriter.close();
                }
                if (bReader != null) {
                    bReader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }


    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url       发送请求的URL
     * @param param     请求参数，请求参数应该是 name1=value1&name2=value2 的形式
     * @param useProxy  是否使用代理
     * @param proxyUrl  代理地址
     * @param proxyPort 代理端口
     * @param token     访问令牌
     * @return 所代表远程资源的响应结果
     */
    public static String senGet(String url, String param, boolean useProxy, String proxyUrl, int proxyPort, String token) {
        String result = "";
        BufferedReader bReader = null;
        try {
            String urlNameString = "".equals(param) ? url : url + "?" + param;
            URL realUrl = new URL(urlNameString);
            URLConnection conn = null;
            if (useProxy) {
                @SuppressWarnings("static-access")
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyUrl, proxyPort));
                // 打开和URL之间的连接
                conn = realUrl.openConnection(proxy);
            } else {
                conn = realUrl.openConnection();
            }
            // 设置通用的请求属性
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            // 建立实际的连接
            conn.connect();

            HttpURLConnection httpConn = (HttpURLConnection) conn;
            if (httpConn.getResponseCode() >= ERROR_CODE) {
                bReader = new BufferedReader(new InputStreamReader(httpConn.getErrorStream()));
            } else {
                bReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
            }

            String line;
            while ((line = bReader.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (bReader != null) {
                    bReader.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
}
