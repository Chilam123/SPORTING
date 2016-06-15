package com.ycl.yuesport.utils;

/**
 * 多图片网址分解类
 */
public class SplitNetImagePath {

	/**
	 * 多图片网址分解类
	 * @param netImagePath 多个图片网址以&&拼接的字符串
	 * @return 单独的图片网址数组
     */
	public static String[] splitNetImagePath(String netImagePath){
		String[] strings=netImagePath.split("&&");
		return strings;
	}
}
