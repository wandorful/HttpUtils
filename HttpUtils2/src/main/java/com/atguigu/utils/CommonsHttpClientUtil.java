package com.atguigu.utils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

public class CommonsHttpClientUtil {
	/**
	 * 发送post请求--用于接口接收的参数为键值对
	 * 
	 * @param url
	 *            请求地址
	 * @param nameValuePairs
	 *            键值对
	 * @return
	 */
	public static String httpPost(String url, Map<String, String> params) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		String response = "";
		PostMethod method = new PostMethod(url);
		if (params != null) {
			for (Entry<String, String> param : params.entrySet()) {
				method.addParameter(param.getKey(), param.getValue());
			}
		}
		method.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		// client.getParams().setContentCharset("UTF-8");
		client.executeMethod(method);
		int statusCode = method.getStatusCode();
		if (statusCode == 200) {
			response = method.getResponseBodyAsString();
		}
		return response;
	}

	public static String httpGet(String url, Map<String, String> params) throws IOException {
		HttpClient client = new HttpClient();
		String sb = "";
		String response = "";
		if (params != null) {
			for (Entry<String, String> param : params.entrySet()) {
				sb += param.getKey() + "=" + URLEncoder.encode(param.getValue(), "UTF-8") + "&";
			}
			sb = "?" + sb.substring(0, sb.length() - 1);
		}
		GetMethod method = new GetMethod(url + sb);
		client.executeMethod(method);
		int statusCode = method.getStatusCode();
		if (statusCode == 200) {
			response = method.getResponseBodyAsString();
		}
		return response;

	}

	public static void main(String[] args) throws HttpException, IOException {
		Map<String, String> params = new HashMap<>();
		params.put("yh_mch", "张三");
		params.put("yh_mm", "123456");
		String httpPost = httpPost("http://localhost:8080/mall_0525_sale_teacher/testHttpUtil.do", params);
		System.out.println(httpPost);
//		String httpGet = httpGet("http://localhost:8080/mall_0525_sale_teacher/testHttpUtil.do", params);
//		System.out.println(httpGet);
	}
}
