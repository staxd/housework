package com.houseWork.utils;

import java.util.Random;

/**
 * @author luogm
 *
 */
public class RandomUtil {

	/**
	 * 获取随机n位数字，以字符串形式返回
	 * @return
	 */
	public static String getRandomNumString(int num) {
		Random random = new Random();
		String result="";
		for(int i=0; i<num; i++){
			result+=random.nextInt(10);
		}
		return result;
	}
	
	/**
	 * 获取随机n位数字+字母,以字符串形式返回
	 * @return
	 */
	public static String getRandomCharString(int num) {
		String a = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		char[] rands = new char[num]; 
		for (int i = 0; i < rands.length; i++) { 
			int rand = (int) (Math.random() * a.length()); 
			rands[i] = a.charAt(rand); 
		}
		return new String(rands);
	}
	
	/**
	 * 获取随机n位大写字母,以字符串形式返回
	 * @return
	 */
	public static String getRandomUpperCharString(int num) {
		String a = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		char[] rands = new char[num]; 
		for (int i = 0; i < rands.length; i++) { 
			int rand = (int) (Math.random() * a.length()); 
			rands[i] = a.charAt(rand); 
		}
		return new String(rands);
	}
}
