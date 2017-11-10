package com.atguigu.utils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpComponentsUtil {
	/**
	 * 发送post请求--用于接口接收的参数为键值对
	 * 
	 * @param url
	 *            请求地址
	 * @param nameValuePairs
	 *            键值对
	 * @return
	 */
	public static String httpPost(String url, Map<String, String> params) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		String strResult = "";
		CloseableHttpResponse response = null;

		List<NameValuePair> nameValuePairs = new ArrayList<>();
		if (params != null) {
			for (Entry<String, String> param : params.entrySet()) {
				nameValuePairs.add(new BasicNameValuePair(param.getKey(), param.getValue()));
			}
		}
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			response = httpClient.execute(httpPost);

			if (response.getStatusLine().getStatusCode() == 200) {
				/* 读返回数据 */
				strResult = EntityUtils.toString(response.getEntity());
				// System.out.println("conResult:"+conResult);
			} else {
				strResult += "发送失败:" + response.getStatusLine().getStatusCode();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return strResult;
	}

	public static String httpGet(String url, Map<String, String> params) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String sb = "";
		String result = "";
		CloseableHttpResponse response = null;
		try {
			if (params != null) {
				for (Entry<String, String> param : params.entrySet()) {
					sb += param.getKey() + "=" + URLEncoder.encode(param.getValue(), "UTF-8") + "&";
				}
				sb = "?" + sb.substring(0, sb.length() - 1);
			}
			HttpGet httpGet = new HttpGet(url + sb);

			response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				/* 读返回数据 */
				result = EntityUtils.toString(response.getEntity());
			} else {
				result += "发送失败:" + response.getStatusLine().getStatusCode();
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static void main(String[] args) {
		Map<String, String> params = new HashMap<>();
		params.put("yh_mch", "张三");
		params.put("yh_mm", "123456");
//		String httpPost = httpPost("http://localhost:8080/mall_0525_sale_teacher/testHttpUtil.do", params);
//		System.out.println(httpPost);
		String httpGet = httpGet("http://localhost:8080/mall_0525_sale_teacher/testHttpUtil.do", params);
		System.out.println(httpGet);
	}
}
