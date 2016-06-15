package com.ycl.sportsing.interfaces;

public interface Netcallback {
	/**
	 *
	 * @param res res为请求网络成功后的Json串
	 * @param flag 返回的请求网络
     */
	void preccess(Object res, boolean flag);//res为请求网络成功后的Json串，返回的请求网络
}
