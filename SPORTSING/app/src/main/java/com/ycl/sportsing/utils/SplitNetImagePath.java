package com.ycl.sportsing.utils;

public class SplitNetImagePath {
 
	public static String[] splitNetImagePath(String netImagePath){
		String[] strings=netImagePath.split("&&");
		return strings;
	}
}
