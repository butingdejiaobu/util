package org.util.security.Encrypt;

import java.security.Key;
import org.junit.*;

public class TestDESCoder {
	
	@Ignore
	@Test
	public void TestSymmetryDESMethod(){
		DESBuilder obj = new DESBuilder("123456a?"); 
		Key key=obj.getKey();
		
		DESCoder descoder =new DESCoder();
		descoder.encrypt("/Users/limm/Desktop/DICT/9-DICT-PERSON.TXT", "/Users/limm/Desktop/DICT/9-DICT-PERSON_encrypt.TXT",key); //加密 
		System.out.print("9-DICT-PERSON.TXT " + "加密完成");
		descoder.decrypt("/Users/limm/Desktop/DICT/9-DICT-PERSON_encrypt.TXT", "/Users/limm/Desktop/DICT/9-DICT-PERSON_decrypt.TXT",key); //解密
		System.out.print("9-DICT-PERSON.TXT " + "解密完成");
	}
	
	@Ignore
	@Test
	public void TestSaveKeyToFileMethod(){
		String DESKey = "/Users/limm/Desktop/key/DESKey.dat"; // 公钥保存路径
		DESBuilder obj = new DESBuilder("123456dsafffffffffffffgeeewwwwnjhjqiphgwuewhaghajhgaejhfqpwigheiqhua?"); 
		obj.getKeyToFile(DESKey);
	}
	
	@Ignore
	@Test
	public void TestFromFileMethod(){
		String DESKey = "/Users/limm/Desktop/key/DESKey.dat"; // 公钥保存路径
		Key key=Files.getDESKeyFromFile(DESKey);
		DESCoder descoder =new DESCoder();
		descoder.encrypt("/Users/limm/Desktop/DICT/9-DICT-PERSON.TXT", "/Users/limm/Desktop/DICT/9-DICT-PERSON_encrypt.TXT",key); //加密 
		System.out.print("9-DICT-PERSON.TXT " + "加密完成");
		descoder.decrypt("/Users/limm/Desktop/DICT/9-DICT-PERSON_encrypt.TXT", "/Users/limm/Desktop/DICT/9-DICT-PERSON_decrypt.TXT",key); //解密
		System.out.print("9-DICT-PERSON.TXT " + "解密完成");
	}
	
	@Ignore
	@Test
	public void TestSymmetryDESString(){
		DESBuilder obj = new DESBuilder("123456a?"); 
		String key=obj.getKeyToString();
		System.out.println(key);
		DESCoder descoder =new DESCoder();
		descoder.encryptByString("/Users/limm/Desktop/DICT/9-DICT-PERSON.TXT", "/Users/limm/Desktop/DICT/9-DICT-PERSON_encrypt.TXT",key); //加密 
		System.out.print("9-DICT-PERSON.TXT " + "加密完成");
		descoder.decryptByString("/Users/limm/Desktop/DICT/9-DICT-PERSON_encrypt.TXT", "/Users/limm/Desktop/DICT/9-DICT-PERSON_decrypt.TXT",key); //解密
		System.out.print("9-DICT-PERSON.TXT " + "解密完成");
		System.out.println("完成");
	}
	
	@Ignore
	@Test
	public void TestgetDESKeyFromFileToString(){
		String DESKey = "/Users/limm/Desktop/key/DESKey.dat";
		String key =Files.getDESKeyStringFromFile(DESKey);
		
		System.out.println(key);
		DESCoder descoder =new DESCoder();
		descoder.encryptByString("/Users/limm/Desktop/DICT/9-DICT-PERSON.TXT", "/Users/limm/Desktop/DICT/9-DICT-PERSON_encrypt.TXT",key); //加密 
		System.out.print("9-DICT-PERSON.TXT " + "加密完成");
		descoder.decryptByString("/Users/limm/Desktop/DICT/9-DICT-PERSON_encrypt.TXT", "/Users/limm/Desktop/DICT/9-DICT-PERSON_decrypt.TXT",key); //解密
		System.out.print("9-DICT-PERSON.TXT " + "解密完成");
		System.out.println("完成");
	}
	
}
