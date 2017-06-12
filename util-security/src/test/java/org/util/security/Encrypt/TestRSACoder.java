package org.util.security.Encrypt;

import java.io.IOException;
import java.security.Key;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

public class TestRSACoder{
	
	@Ignore
	@Test
	public void TestgenKeyMethod(){
	     String pubKeyAddress = "/Users/limm/Desktop/key/pubKeyBasedRsa.dat"; // 公钥保存路径
	     String prvKeyAddress = "/Users/limm/Desktop/key/prvKeyBasedRsa.dat"; // 私钥保存路径
	  	RSABuilder gen = new RSABuilder();
	  	gen.getPrivateKey(prvKeyAddress);//保存私钥
	  	gen.getPublicKey(pubKeyAddress);//保存公钥
	}
	
	@Ignore
	@Test
	public void TesEncodeByPrivateKey(){
		RSABuilder rsaCoder =new RSABuilder();
	    Map<String,String> keyMap=rsaCoder.getKeyStringFromMap();
	    String publicKey=keyMap.get("publicKey");
	    String privateKey=keyMap.get("privateKey");
	    
	    //私钥公钥解密加密
	    String filePath="/Users/limm/Desktop/corpus2.txt";
	    String filePath_encrypt="/Users/limm/Desktop/corpus1_encrypt.txt";
	    String filePath_decrypt="/Users/limm/Desktop/corpus1_decrypt.txt";
	    byte[] fileByte=Files.readFileToByte(filePath);
	    byte[] encryptFileByte=RSACoder.encryptByPrivateKey(fileByte, privateKey);
	    Files.writeFile(filePath_encrypt, encryptFileByte);
	   
	    //解密
	    byte[] fileByte1=Files.readFileToByte(filePath_encrypt);
	    byte[] encryptFileByte2=RSACoder.decryptByPublicKey(fileByte1, publicKey);
	    Files.writeFile(filePath_decrypt, encryptFileByte2);
	}
	
	@Ignore
	@Test
	public void TesEncodeByPublicKey(){
		RSABuilder rsaCoder =new RSABuilder();
	    Map<String,String> keyMap=rsaCoder.getKeyStringFromMap();
	    String publicKey=keyMap.get("publicKey");
	    String privateKey=keyMap.get("privateKey");
	    
	    //私钥公钥解密加密
	    String filePath="/Users/limm/Desktop/corpus2.txt";
	    String filePath_encrypt2="/Users/limm/Desktop/corpus1_encrypt2.txt";
	    String filePath_decrypt2="/Users/limm/Desktop/corpus1_decrypt2.txt";
	    byte[] fileByte=Files.readFileToByte(filePath);
	    byte[] encryptFileByte=RSACoder.encryptByPublicKey(fileByte,publicKey);
	    Files.writeFile(filePath_encrypt2, encryptFileByte);
	   
	    //解密
	    byte[] fileByte1=Files.readFileToByte(filePath_encrypt2);
	    byte[] encryptFileByte2=RSACoder.decryptByPrivateKey(fileByte1, privateKey);
	    Files.writeFile(filePath_decrypt2, encryptFileByte2);
	}
	
	@Ignore
	@Test
	public void TesGetKeyMethod(){
	     String pubKeyAddress = "/Users/limm/Desktop/key/pubKeyBasedRsa.dat"; // 公钥保存路径
	     String prvKeyAddress = "/Users/limm/Desktop/key/prvKeyBasedRsa.dat"; // 私钥保存路径
	     String publicKey=Files.getRSAKeyFromFile(pubKeyAddress);
		 String privateKey=Files.getRSAKeyFromFile(prvKeyAddress);
		
		 //私钥公钥解密加密
		String filePath="/Users/limm/Desktop/corpus2.txt";
		String filePath_encrypt1="/Users/limm/Desktop/corpus1_encrypt1.txt";
		String filePath_decrypt1="/Users/limm/Desktop/corpus1_decrypt1.txt";
   
		byte[] fileByte=Files.readFileToByte(filePath);
		byte[] encryptFileByte=RSACoder.encryptByPublicKey(fileByte,publicKey);
		Files.writeFile(filePath_encrypt1, encryptFileByte);
   
		//解密
		byte[] fileByte1=Files.readFileToByte(filePath_encrypt1);
		byte[] encryptFileByte2=RSACoder.decryptByPrivateKey(fileByte1, privateKey);
		Files.writeFile(filePath_decrypt1, encryptFileByte2);
	}
	
	@Ignore
	@Test
	public void TestLast(){
	     String pubKeyAddress = "/Users/limm/Desktop/key/pubKeyBasedRsa.dat"; // 公钥保存路径
	     String prvKeyAddress = "/Users/limm/Desktop/key/prvKeyBasedRsa.dat"; // 私钥保存路径
	     String publicKey=Files.getRSAKeyFromFile(pubKeyAddress);
		 String privateKey=Files.getRSAKeyFromFile(prvKeyAddress);
		
		
		String filePath_encrypt_public="/Users/limm/Desktop/corpus1_encrypt2.txt";
		String filePath_encrypt_pri="/Users/limm/Desktop/corpus1_encrypt.txt";
		String filePath_decrypt_public="/Users/limm/Desktop/corpus1_decrypt_public.txt";
		String filePath_decrypt_pri="/Users/limm/Desktop/corpus1_decrypt_pri.txt";
   
		//解密公钥加密文件
		byte[] fileByte1=Files.readFileToByte(filePath_encrypt_public);
		byte[] encryptFileByte2=RSACoder.decryptByPrivateKey(fileByte1, privateKey);
		Files.writeFile(filePath_decrypt_public, encryptFileByte2);
		
		//解密私钥加密文件
		byte[] fileByte2=Files.readFileToByte(filePath_encrypt_pri);
		byte[] encryptFileByte21=RSACoder.decryptByPublicKey(fileByte2, publicKey);
		Files.writeFile(filePath_decrypt_pri, encryptFileByte21);
		
		//结果：不能使用文件读取的私钥解密String加密的数据。
	     
	}
	
}
