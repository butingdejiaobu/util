package org.util.security.Encrypt;
 

import java.security.SecureRandom;  
  
import javax.crypto.Cipher;  
import javax.crypto.SecretKey;  
import javax.crypto.SecretKeyFactory;  
import javax.crypto.spec.DESKeySpec;  
  

public class DESCoderByte {  
  

    /** 
     * 加密 
     *  
     * @param content 
     *            待加密内容 
     * @param key 
     *            加密的密钥 
     * @return 
     */  
    public static byte[] encryptToByte(String content, String key) {  
        try {  
            SecureRandom random = new SecureRandom();  
            DESKeySpec desKey = new DESKeySpec(key.getBytes());  
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
            SecretKey securekey = keyFactory.generateSecret(desKey);  
            Cipher cipher = Cipher.getInstance("DES");  
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);  
            byte[] result = cipher.doFinal(content.getBytes());  
            return result;  
        } catch (Throwable e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
  
    /** 
     * 解密 
     *  
     * @param content 
     *            待解密内容 
     * @param key 
     *            解密的密钥 
     * @return 
     */  
    public static String decryptToByte(byte[] content, String key) {  
        try {  
            SecureRandom random = new SecureRandom();  
            DESKeySpec desKey = new DESKeySpec(key.getBytes());  
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
            SecretKey securekey = keyFactory.generateSecret(desKey);  
            Cipher cipher = Cipher.getInstance("DES");  
            cipher.init(Cipher.DECRYPT_MODE, securekey, random);  
            byte[] result = cipher.doFinal(content);  
            return new String(result);  
        } catch (Throwable e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
}  