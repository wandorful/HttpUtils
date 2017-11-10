package com.atguigu.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class PlainHttpUtil {
	/**
	 * 发送post请求--用于接口接收的参数为键值对
	 * 
	 * @param url
	 *            请求地址
	 * @param nameValuePairs
	 *            键值对
	 * @return
	 */
	public static String httpGet(String url, Map<String, String> params) throws Exception {
		String sb = "";
		if (params != null) {
			for (Entry<String, String> param : params.entrySet()) {
				sb += param.getKey() + "=" + URLEncoder.encode(param.getValue(), "UTF-8") + "&";
			}
			sb = "?" + sb.substring(0, sb.length() - 1);
		}
		URL localURL = new URL(url + sb);

		URLConnection connection = localURL.openConnection();
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

		httpURLConnection.setRequestProperty("Accept-Charset", "UTF-8");
		httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;
		// 响应失败
		if (httpURLConnection.getResponseCode() >= 300) {
			throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
		}

		try {
			inputStream = httpURLConnection.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream);
			reader = new BufferedReader(inputStreamReader);

			while ((tempLine = reader.readLine()) != null) {
				resultBuffer.append(tempLine);
			}

		} finally {

			if (reader != null) {
				reader.close();
			}

			if (inputStreamReader != null) {
				inputStreamReader.close();
			}

			if (inputStream != null) {
				inputStream.close();
			}

		}

		return resultBuffer.toString();
	}

	/**
	 * Do POST request
	 * 
	 * @param url
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public static String httpPost(String url, Map<String, String> params) throws Exception {

		String sb = "";
		if (params != null) {
			for (Entry<String, String> param : params.entrySet()) {
				sb += param.getKey() + "=" + URLEncoder.encode(param.getValue(), "UTF-8") + "&";
			}
			sb = sb.substring(0, sb.length() - 1);
		}
		URL localURL = new URL(url);

		URLConnection connection = localURL.openConnection();
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

		httpURLConnection.setDoOutput(true);
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setRequestProperty("Accept-Charset", "UTF-8");
		httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		httpURLConnection.setRequestProperty("Content-Length", String.valueOf(sb.length()));

		OutputStream outputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;

		try {
			outputStream = httpURLConnection.getOutputStream();
			outputStreamWriter = new OutputStreamWriter(outputStream);

			outputStreamWriter.write(sb);
			outputStreamWriter.flush();
			// 响应失败
			if (httpURLConnection.getResponseCode() >= 300) {
				throw new Exception(
						"HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
			}
			// 接收响应流
			inputStream = httpURLConnection.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream);
			reader = new BufferedReader(inputStreamReader);

			while ((tempLine = reader.readLine()) != null) {
				resultBuffer.append(tempLine);
			}

		} finally {

			if (outputStreamWriter != null) {
				outputStreamWriter.close();
			}

			if (outputStream != null) {
				outputStream.close();
			}

			if (reader != null) {
				reader.close();
			}

			if (inputStreamReader != null) {
				inputStreamReader.close();
			}

			if (inputStream != null) {
				inputStream.close();
			}

		}

		return resultBuffer.toString();
	}

	public static void main(String[] args) throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("yh_mch", "张三");
		params.put("yh_mm", "123456");
		String httpPost = httpPost("http://localhost:8080/mall_0525_sale_teacher/testHttpUtil.do", params);
		System.out.println(httpPost);
//		String httpGet = httpGet("http://localhost:8080/mall_0525_sale_teacher/testHttpUtil.do", params);
//		System.out.println(httpGet);
	}
}
