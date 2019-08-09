package com.houseWork.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils {
	
	// 用来将字节转换成 16 进制表示的字符
	private static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',  'e', 'f'};
	
	public static String getMd5ByInputStream(InputStream ins) {
		try {

			byte[] buffer = new byte[8192];
			MessageDigest md5 = MessageDigest.getInstance("MD5");

			int len;
			while((len = ins.read(buffer)) != -1){
				md5.update(buffer, 0, len);
			}

			ins.close();

			//BigInteger bi = new BigInteger(1, md5.digest());
			//value = bi.toString(16);
			
			byte tmp[] = md5.digest();		// MD5 的计算结果是一个 128 位的长整数，
			return dealMD5Res(tmp);			// 换后的结果转换为字符串
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(null != ins) {
				try {
					ins.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static String getMd5ByFile(File file) {
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);

			MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
			MessageDigest md5 = MessageDigest.getInstance("md5");
			md5.update(byteBuffer);
			
			//BigInteger bi = new BigInteger(1, md5.digest());
			//value = bi.toString(16);
			
			byte tmp[] = md5.digest();		// MD5 的计算结果是一个 128 位的长整数，
			return dealMD5Res(tmp);			// 换后的结果转换为字符串
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 处理128长整数 --> 32位16进制字符
	 * @param tmp
	 * @return
	 */
	private static String dealMD5Res(byte tmp[]) {
		// MD5 的计算结果是一个 128 位的长整数，用字节表示就是 16 个字节
		char str[] = new char[16 * 2];					// 每个字节用 16 进制表示的话，使用两个字符，所以表示成 16 进制需要 32 个字符
		int k = 0;										// 表示转换结果中对应的字符位置
		for (int i = 0; i < 16; i++) {					// 从第一个字节开始，对 MD5 的每一个字节转换成 16 进制字符的转换
			byte byte0 = tmp[i];						// 取第 i 个字节
			str[k++] = hexDigits[byte0 >>> 4 & 0xf];	// 取字节中高 4 位的数字转换 >>> 为逻辑右移，将符号位一起右移
			str[k++] = hexDigits[byte0 & 0xf];			// 取字节中低 4 位的数字转换
		}
		
		String res = new String(str);					// 换后的结果转换为字符串
		
		while(res.length() < 32) {
			res = "0" + res;
		}
		return res;
	}
	
    /**
     * MD5加密
     * 
     * @param data
     * @return
     * @throws Exception
     */
    public static String getMD5(String data) throws Exception {
        byte[] bytes = data.getBytes("UTF-8");
        return getMD5(bytes);
    }
    
    /**
     * MD5加密
     * 
     * @param src
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String getMD5(byte[] src) throws NoSuchAlgorithmException {
        StringBuffer sb = new StringBuffer();

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(src);
        for (byte b : md.digest())
            sb.append(Integer.toString(b >>> 4 & 0xF, 16)).append(Integer.toString(b & 0xF, 16));

        return sb.toString();
    }

}
