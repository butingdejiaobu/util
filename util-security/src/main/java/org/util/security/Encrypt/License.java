package org.util.security.Encrypt;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class License {

	/**
	 * 生成license.txt和licensekey
	 * 
	 * @param map
	 *            key＋“＝”＋value将存储进license.txt文件
	 * @param licenseTXTFile
	 *            生成licesnse.txt的路径
	 * @param licenseKeyFile
	 *            生成license.key的路径
	 * @param DESKeyFilePath
	 *            DES对称秘钥的路径
	 * @param RSAPubKeyAddress
	 *            RSA非对称秘钥公钥的路径
	 * @param RSAPrvKeyAddress
	 *            RSA非对称秘钥私钥的路径
	 */
	public static void generateLicense(Map<String, String> map, String licenseTXTFile, String licenseKeyFile,
		String DESKeyFilePath, String RSAPubKeyAddress, String RSAPrvKeyAddress) {
		
		Set<String> mapKeySets=map.keySet();
		for(String key:mapKeySets){
			String value=map.get(key);
			String str=key+"="+value;
			Files.writeContentToFile(str, licenseTXTFile);
		}
		
		// 获取对称秘钥的字符串
		String DESKeyString = (Files.getDESKeyStringFromFile(DESKeyFilePath)).replaceAll("\n", "");
		// 获取公钥字符串
		String publicKey = Files.getRSAKeyFromFile(RSAPubKeyAddress).replaceAll("\n", "");
		// 获取私钥对文件内容签名
		String privateKey = Files.getRSAKeyFromFile(RSAPrvKeyAddress);
		byte[] data = Files.readFileToByte(licenseTXTFile);
		String sign = SignCoder.sign(data, privateKey).replaceAll("\n", "");
		// 将内容写入到文件
		Files.writeContentToFile(DESKeyString, licenseKeyFile);
		Files.writeContentToFile(publicKey, licenseKeyFile);
		Files.writeContentToFile(sign, licenseKeyFile);
	}
	
	/**
	 * 通过license解密文件
	 * @param licenseKeyFilePath license.key文件路径
	 * @param licenseTxtFilePath license.txt文件路径
	 * @param encryptfilePath DES加密后文件路径
	 * @param decryptfilePath 解密文件路径
	 */
	public static void decryptByLicense(String licenseKeyFilePath,String licenseTxtFilePath,String encryptfilePath, String decryptfilePath) {
		
		try {
			List<String> list = Files.readFileToBuffer(licenseKeyFilePath);
			String des = list.get(0);
			String rsa = list.get(1);
			String sign = list.get(2);
			
			byte[] licensebyte = Files.readFileToByte(licenseTxtFilePath);

			boolean sucesses = SignCoder.verify(licensebyte, rsa, sign);
			System.out.println(sucesses);
			if (sucesses) {
				DESCoder descoder = new DESCoder();
				descoder.decryptByString(encryptfilePath, decryptfilePath, des);
			} else {
				System.out.println("license文件被破坏");
			}
			System.out.println("end");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
