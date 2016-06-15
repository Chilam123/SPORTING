package com.ycl.yuesport.http;

import com.ycl.yuesport.config.AppConfig;

public class NetUrlConstant {

	/**
	 * 服务器地址.
	 */
	public static final String SERVER;

	/**
	 * 正式时使用新浪sae托管; 测试使用本地, 节省SAE的消费.
	 */
	static {
		if (AppConfig.DEBUG) {
			SERVER = "http://192.168.1.116/HttpServer/";
		} else {
			SERVER = "http://1.nohttp.applinzi.com/";
		}
	}

	public static final String GOODURL = SERVER+"MeiTuanGoodsServlet";

}
