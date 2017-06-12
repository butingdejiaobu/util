package org.util.security.Encrypt;

import org.junit.Ignore;
import org.junit.Test;

public class TestSignCoder {
	
	@Ignore
	@Test
	public void testSign(){
		String pubKeyAddress = "/Users/limm/Desktop/key/pubKeyBasedRsa.dat"; // 公钥保存路径
	    String prvKeyAddress = "/Users/limm/Desktop/key/prvKeyBasedRsa.dat"; // 私钥保存路径
	    
	    String filePath="/Users/limm/Desktop/corpus2.txt";
	    byte[] data=Files.readFileToByte(filePath);
	    String privateKey=Files.getRSAKeyFromFile(prvKeyAddress);
	    
	    String sign=SignCoder.sign(data, privateKey);
	    
	    String filePath1="/Users/limm/Desktop/corpus3.txt";
	    
	    String publicKey=Files.getRSAKeyFromFile(pubKeyAddress);
	    byte[] data1=Files.readFileToByte(filePath1);
	    boolean mark=SignCoder.verify(data1, publicKey, sign);
	    System.out.println(mark);
	}
	
}
