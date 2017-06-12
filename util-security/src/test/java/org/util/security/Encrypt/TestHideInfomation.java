package org.util.security.Encrypt;

import org.junit.Ignore;
import org.junit.Test;

public class TestHideInfomation {
	
	@Ignore
	@Test
	public void TestSymmetryDESMethod() {
		String simpleText = "/Users/limm/Desktop/DICT/9-DICT-PERSON.TXT"; // 文本保存路径
		String simpleKey = "/Users/limm/Desktop/key/DESKey.dat"; // 文本保存路径
		byte[] byte_1=Files.readFileToByte(simpleText);
		byte[] byte_2=Files.readFileToByte(simpleKey);
		
		byte[] byte_3 = new byte[byte_1.length + byte_2.length];
		System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
		System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
		String filePath= "/Users/limm/Desktop/result.txt";
		
		Files.writeFile(filePath, byte_3);
		System.out.println("success");
	}
	
	@Ignore
	@Test
	public void TestAll() {
		String simpleText = "/Users/limm/Desktop/DICT/9-DICT-PERSON.TXT"; // 文本保存路径
		String simpleKey = "/Users/limm/Desktop/key/DESKey.dat"; // 文本保存路径
		byte[] byte_1=Files.readFileToByte(simpleText);
		byte[] byte_2=Files.readFileToByte(simpleKey);
		
		byte[] byte_3 = new byte[byte_1.length + byte_2.length];
		System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
		System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
		String filePath= "/Users/limm/Desktop/result.txt";
		
		Files.writeFile(filePath, byte_3);
		System.out.println("success");
	}

}
