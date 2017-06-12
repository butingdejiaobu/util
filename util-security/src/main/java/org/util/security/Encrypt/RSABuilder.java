package org.util.security.Encrypt;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * 生成基于RSA算法的密钥对
 * 
 * @author limm
 * 
 */
public class RSABuilder {

	

	private KeyPairGenerator keyPairGen;
	private int keysize = 1024;
	private KeyPair keyPair;
	private RSAPrivateKey privateKey;
	private RSAPublicKey publicKey;
	
	/**
	 * 构造函数
	 */
	public RSABuilder() {
		generateKeyPair();
	}
	
	/**
	 * 生成密钥对
	 */
	public void generateKeyPair() {
		try {
			keyPairGen = KeyPairGenerator.getInstance("RSA", new BouncyCastleProvider());
			keyPairGen.initialize(keysize);
			keyPair = keyPairGen.generateKeyPair(); // 生成密钥对，包括一个公钥和一个私钥
			privateKey = (RSAPrivateKey) keyPair.getPrivate(); // 获得私钥
			publicKey = (RSAPublicKey) keyPair.getPublic(); // 获得公钥
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取公钥
	 * 
	 * @param PublicKeyAddress
	 *            保存公钥地址和名字(建议秘钥使用“.bat”后缀）
	 */
	public void getPublicKey(String publicKeyAddress) {
		FileOutputStream fileOutput = null;
		ObjectOutputStream objectOutput = null;
		try {
			fileOutput = new FileOutputStream(publicKeyAddress);
			objectOutput = new ObjectOutputStream(fileOutput);
			objectOutput.writeObject(this.publicKey);
			System.out.println("success: " + publicKeyAddress);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fileOutput.close();
				objectOutput.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取私钥
	 * 
	 * @param privateKeyAddress
	 *            保存私钥地址和名字(建议秘钥使用“.bat”后缀）
	 */
	public void getPrivateKey(String privateKeyAddress) {
		FileOutputStream fileOutput = null;
		ObjectOutputStream objectOutput = null;
		try {
			fileOutput = new FileOutputStream(privateKeyAddress);
			objectOutput = new ObjectOutputStream(fileOutput);
			objectOutput.writeObject(this.privateKey);
			System.out.println("success: " + privateKeyAddress);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fileOutput.close();
				objectOutput.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取公钥私钥map数据
	 * 
	 * @param
	 * @return 返回map,key分别是publicKey和privateKey，value(BASE64编码)；
	 * @throws Exception
	 */
	public Map<String, String> getKeyStringFromMap() {
		Map<String, String> keyMap = new HashMap<String, String>(2);
		String publicKey;
		try {
			publicKey = getPublicKeyBASE64(getKeyMap());
			String privateKey = getPrivateKeyBASE64(getKeyMap());
			keyMap.put("publicKey", publicKey);
			keyMap.put("privateKey", privateKey);
			return keyMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return keyMap;
	}

	/**
	 * 获取公钥私钥map数据
	 * 
	 * @param
	 * @return 返回map,key分别是publicKey和privateKey;
	 */
	public Map<String, Object> getKeyMap() {
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put("publicKey", publicKey);
		keyMap.put("privateKey", privateKey);
		return keyMap;
	}

	/**
	 * 取得公钥，并转化为String类型
	 * 
	 * @param keyMap
	 * @return 公钥(BASE64编码)
	 * @throws Exception
	 */
	public static String getPublicKeyBASE64(Map<String, Object> keyMap){
		Key key = (Key) keyMap.get("publicKey");
		return Coder.encryptBASE64(key.getEncoded());
	}

	/**
	 * 取得私钥，并转化为String类型
	 * 
	 * @param keyMap
	 * @return 私钥(BASE64编码)
	 * @throws Exception
	 */
	public static String getPrivateKeyBASE64(Map<String, Object> keyMap){
		Key key = (Key) keyMap.get("privateKey");
		return Coder.encryptBASE64(key.getEncoded());
	}
	
}