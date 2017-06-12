package org.util.security.Encrypt;

import java.io.IOException;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

public class TestAll {
	
	@Ignore
	@Test
	public void verification() {
		String licenseKey="/Users/limm/Desktop/DICT/license.key";
		try {
			List<String> list=Files.readFileToBuffer(licenseKey);
			String des=list.get(0);
			String rsa=list.get(1);
			String sign=list.get(2);
			String license="/Users/limm/Desktop/DICT/license.txt";
			byte[] licensebyte=Files.readFileToByte(license);
			
			boolean sucesses=SignCoder.verify(licensebyte, rsa, sign);
			System.out.println(sucesses);
			if(sucesses){
				String encryptfilePath="/Users/limm/Desktop/DICT/9-DICT-PERSON_encrypt.TXT";
				DESCoder descoder=new DESCoder();
				String decryptfilePath="/Users/limm/Desktop/DICT/9-DICT-PERSON_decrypt.TXT";
				descoder.decryptByString(encryptfilePath, decryptfilePath, des);
			}else{
				System.out.println("注册文件被破坏");
			}
			System.out.println("end");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	@Ignore
	@Test
	public void testSaveStringToFile() {
		//获取对称秘钥的字符串
		String keyFile = "/Users/limm/Desktop/key/DESKey.dat"; // 秘钥保存路径
		String DESKeyString = (Files.getDESKeyStringFromFile(keyFile)).replaceAll("\n", "");
		
		//获取公钥字符串
		 String pubKeyAddress = "/Users/limm/Desktop/key/pubKeyBasedRsa.dat"; // 公钥保存路径
	     String publicKey=Files.getRSAKeyFromFile(pubKeyAddress).replaceAll("\n", "");;
		
	     //获取私钥对文件内容签名
	     String prvKeyAddress = "/Users/limm/Desktop/key/prvKeyBasedRsa.dat"; // 私钥保存路径
		 String privateKey=Files.getRSAKeyFromFile(prvKeyAddress);
		 String license="/Users/limm/Desktop/DICT/license.txt";
		 byte[] data=Files.readFileToByte(license);
		 String sign=SignCoder.sign(data, privateKey).replaceAll("\n", "");;
		 
		 //将内容写入到文件
		 String licenseKey="/Users/limm/Desktop/DICT/license.key";
		 Files.writeContentToFile(DESKeyString, licenseKey);
		 Files.writeContentToFile(publicKey, licenseKey);
		 Files.writeContentToFile(sign, licenseKey);
	}

	/**
	 * DES加密文件
	 */
	@Ignore
	@Test
	public void encryptFileByDesStringKey() {
		String keyFile = "/Users/limm/Desktop/key/DESKey.dat"; // 公钥保存路径
		String key = Files.getDESKeyStringFromFile(keyFile);
		DESCoder descoder = new DESCoder();
		descoder.encryptByString("/Users/limm/Desktop/DICT/9-DICT-PERSON.TXT",
				"/Users/limm/Desktop/DICT/9-DICT-PERSON_encrypt.TXT", key); // 加密
		System.out.print("9-DICT-PERSON.TXT " + "加密完成");
//		descoder.decryptByString("/Users/limm/Desktop/DICT/9-DICT-PERSON_encrypt.TXT",
//				"/Users/limm/Desktop/DICT/9-DICT-PERSON_decrypt.TXT", key); // 解密
//		System.out.print("9-DICT-PERSON.TXT " + "解密完成");
	}

	/**
	 * 生成DES秘钥到文件
	 */
	@Ignore
	@Test
	public void getDesKeyToFile() {
		DESBuilder desBuilder = new DESBuilder("123456a?");
		String filePath = "/Users/limm/Desktop/key/DESKey.dat";
		desBuilder.getKeyToFile(filePath);
	}

	/**
	 * 生成RSA非对称秘钥
	 */
	@Ignore
	@Test
	public void getRSAKeyToFile() {
		String pubKeyAddress = "/Users/limm/Desktop/key/pubKeyBasedRsa.dat"; // 公钥保存路径
		String prvKeyAddress = "/Users/limm/Desktop/key/prvKeyBasedRsa.dat"; // 私钥保存路径
		RSABuilder gen = new RSABuilder();
		gen.getPrivateKey(prvKeyAddress);// 保存私钥
		gen.getPublicKey(pubKeyAddress);// 保存公钥
	}

}
