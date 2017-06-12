package org.util.security.Encrypt;

import org.junit.Ignore;
import org.junit.Test;

public class TestLageFileWithRSA {
	
	@Ignore
	@Test
	public void TestMethod1() {
		String pubKeyAddress = "/Users/limm/Desktop/key/pubKeyBasedRsa.dat"; // 公钥保存路径
		String prvKeyAddress = "/Users/limm/Desktop/key/prvKeyBasedRsa.dat"; // 私钥保存路径
		String publicKey = Files.getRSAKeyFromFile(pubKeyAddress);
		String privateKey = Files.getRSAKeyFromFile(prvKeyAddress);

		// 私钥公钥解密加密
		String filePath = "/Users/limm/Desktop/DICT/9-DICT-PERSON.TXT";
		String filePath_encrypt1 = "/Users/limm/Desktop/9-DICT-PERSON_encrypt.TXT";
		String filePath_decrypt1 = "/Users/limm/Desktop/9-DICT-PERSON_decrypt.TXT";

		byte[] fileByte = Files.readFileToByte(filePath);
		byte[] encryptFileByte = RSACoder.encryptByPublicKey(fileByte, publicKey);
		Files.writeFile(filePath_encrypt1, encryptFileByte);
		System.out.println("9-DICT-PERSON.TXT " + "加密完成");
		// 解密
		byte[] fileByte1 = Files.readFileToByte(filePath_encrypt1);
		byte[] encryptFileByte2 = RSACoder.decryptByPrivateKey(fileByte1, privateKey);
		Files.writeFile(filePath_decrypt1, encryptFileByte2);
		System.out.println("9-DICT-PERSON.TXT " + "解密完成");
		
		//结果：对于大的文件RSA不对称加密耗时太长
	}
	
	@Ignore
	@Test
	public void TestMethod2() {
		String pubKeyAddress = "/Users/limm/Desktop/key/pubKeyBasedRsa.dat"; // 公钥保存路径
		String prvKeyAddress = "/Users/limm/Desktop/key/prvKeyBasedRsa.dat"; // 私钥保存路径
		String publicKey = Files.getRSAKeyFromFile(pubKeyAddress);
		String privateKey = Files.getRSAKeyFromFile(prvKeyAddress);

		// 私钥公钥解密加密
		String filePath = "/Users/limm/Desktop/DICT/7-DICT-LOCATION.TXT";
		String filePath_encrypt1 = "/Users/limm/Desktop/7-DICT-LOCATION_encrypt.TXT";
		String filePath_decrypt1 = "/Users/limm/Desktop/7-DICT-LOCATION_decrypt.TXT";
		

		byte[] fileByte = Files.readFileToByte(filePath);
		byte[] encryptFileByte = RSACoder.encryptByPublicKey(fileByte, publicKey);
		Files.writeFile(filePath_encrypt1, encryptFileByte);
		System.out.println("7-DICT-LOCATION.TXT " + "加密完成");
		
		// 解密
		byte[] fileByte1 = Files.readFileToByte(filePath_encrypt1);
		byte[] encryptFileByte2 = RSACoder.decryptByPrivateKey(fileByte1, privateKey);
		Files.writeFile(filePath_decrypt1, encryptFileByte2);
		System.out.println("7-DICT-LOCATION.TXT " + "解密完成");
		
		//结果：对于大的文件RSA不对称加密耗时太长
	}
}
