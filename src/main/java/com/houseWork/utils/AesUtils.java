package com.houseWork.utils;

import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @Type AesUtils
 * @Desc
 * @author luogm
 * @date 2014-2-11
 * @Version V1.0
 */
public class AesUtils {
    private static final Log logger = LogFactory.getLog(AesUtils.class);
    private static final String ENCODING = "utf-8";
    private static final String KEY_ALGORITHM = "AES";
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    
    private static Key getSecretKey(String secretKey) throws UnsupportedEncodingException {
        byte[] oigByte = secretKey.getBytes(ENCODING);
        oigByte = Base64.decodeBase64(oigByte);
        return new SecretKeySpec(oigByte, KEY_ALGORITHM);
    }

    public static AlgorithmParameters generateIV(String ivStr) throws Exception {
    	AlgorithmParameters params = AlgorithmParameters.getInstance(KEY_ALGORITHM);
    	byte[] iv = ivStr.getBytes(ENCODING);
    	iv = Base64.decodeBase64(iv);
    	params.init(new IvParameterSpec(iv));
    	return params;
    }
    
    public static String encrypt(String originData, String secretKey) {
        String ret = null;
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            byte[] dataBytes = originData.getBytes(ENCODING);
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(secretKey));
            byte[] enBytes = cipher.doFinal(dataBytes);
            enBytes = Base64.encodeBase64(enBytes);
            ret = new String(enBytes, ENCODING);
        } catch (Exception e) {
            logger.error(e);
        }
        return ret;
    }

    public static String decrypt(String encryptData, String secretKey) throws Exception {
        String ret = null;
        byte[] encryptByte = encryptData.getBytes(ENCODING);
        encryptByte = Base64.decodeBase64(encryptByte);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(secretKey));
        byte[] enBytes = cipher.doFinal(encryptByte);
        ret = new String(enBytes, ENCODING);
        return ret;
    }
    
    public static String decrypt(String encryptData, String secretKey, String iv) throws Exception {
    	Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); //调用BouncyCastleProvider,让java支持PKCS7Padding
    	String ret = null;
        byte[] encryptByte = encryptData.getBytes(ENCODING);
        encryptByte = Base64.decodeBase64(encryptByte);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(secretKey), generateIV(iv));
        byte[] enBytes = cipher.doFinal(encryptByte);
        ret = new String(enBytes, ENCODING);
        return ret;
    }
}
