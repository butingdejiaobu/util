package org.util.security.Encrypt;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * 基于DES加密解密
 * 
 * @author limm
 * 
 */
public class DESCoder {
	
	/**加密算法*/
	public static final String KEY_ALGORTHM="DES";
	
	/**
	 * 文件file进行加密并保存目标文件destFile中
	 * 
	 * @param file
	 *            要加密的文件 如c:/test/srcFile.txt
	 * @param destFile
	 *            加密后存放的文件名 如c:/加密后文件.txt
	 * @param key 加密生成key
	 */
	public void encrypt(String file, String destFile,Key key) {
		try {
			Cipher cipher = Cipher.getInstance(KEY_ALGORTHM);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			InputStream is = new FileInputStream(file);
			OutputStream out = new FileOutputStream(destFile);
			CipherInputStream cis = new CipherInputStream(is, cipher);
			byte[] buffer = new byte[1024];
			int r;
			while ((r = cis.read(buffer)) > 0) {
				out.write(buffer, 0, r);
			}
			cis.close();
			is.close();
			out.close();
		}catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (InvalidKeyException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 文件采用DES算法解密文件
	 * 
	 * @param file
	 *            已加密的文件 如c:/加密后文件.txt * 
	 * @param dest 解密后存放的文件名 如c:/
	 *            test/解密后文件.txt
	 * @param key 加密生成key
	 *            
	 */
	public void decrypt(String file, String dest,Key key){
		Cipher cipher;
		try {
			cipher = Cipher.getInstance(KEY_ALGORTHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
			InputStream is;
			is = new FileInputStream(file);
			OutputStream out = new FileOutputStream(dest);
			CipherOutputStream cos = new CipherOutputStream(out, cipher);
			byte[] buffer = new byte[1024];
			int r;
			while ((r = is.read(buffer)) >= 0) {
				System.out.println();
				cos.write(buffer, 0, r);
			}
			cos.close();
			out.close();
			is.close();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (InvalidKeyException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 文件file进行加密并保存目标文件destFile中
	 * 
	 * @param file
	 *            要加密的文件 如c:/test/srcFile.txt
	 * @param destFile
	 *            加密后存放的文件名 如c:/加密后文件.txt
	 * @param key key(Base64编码）
	 */
	public void encryptByString(String file, String destFile,String key) {
		try {
			byte[] keyBytes = Coder.decryptBASE64(key);
			Key keyObj=toKey(keyBytes);
			encrypt(file, destFile, keyObj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 文件采用DES算法解密文件
	 * 
	 * @param file
	 *            已加密的文件 如c:/加密后文件.txt * 
	 * @param destFile 解密后存放的文件名 如c:/
	 *            test/解密后文件.txt
	 * @param key key(Base64编码）
	 *            
	 */
	public void decryptByString(String file, String destFile,String key){
		try {
			byte[] keyBytes = Coder.decryptBASE64(key);
			Key keyObj=toKey(keyBytes);
			decrypt(file, destFile, keyObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 private static Key toKey(byte[] key) throws Exception {
	        DESKeySpec dks = new DESKeySpec(key);
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORTHM);
	        SecretKey secretKey = keyFactory.generateSecret(dks);
	        return secretKey;
	    }
	    
}