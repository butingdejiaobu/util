package org.util.security.Encrypt;

import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

public class TestLicense {

	@Ignore
	@Test
	public void testGenerateLicense() {

		// licesnse.txt文件将要生成路径
		String licenseTXTFile = "/Users/limm/Desktop/key/license.txt";

		// licesnse.key文件将要生成路径
		String licenseKeyFile = "/Users/limm/Desktop/key/license.key";

		// 对称秘钥DESKey文件存储路径
		String DESKeyFilePath = "/Users/limm/Desktop/key/DESKey.dat";

		// RSA公钥文件存储路径
		String RSAPubKeyAddress = "/Users/limm/Desktop/key/pubKeyBasedRsa.dat";

		// RSA私钥文件存储路径
		String RSAPrvKeyAddress = "/Users/limm/Desktop/key/prvKeyBasedRsa.dat";

		Map<String, String> map = new HashMap<String, String>();
		map.put("user", "limm");
		map.put("version", "1.0.0");
		map.put("name", "file");
		map.put("validity", "2018-6-30");

		License.generateLicense(map, licenseTXTFile, licenseKeyFile, DESKeyFilePath, RSAPubKeyAddress,
				RSAPrvKeyAddress);
	}
	
	@Ignore
	@Test
	public void testDecryptByLicense() {
		// licesnse.txt文件路径
		String licenseTxtFilePath = "/Users/limm/Desktop/key/license.txt";

		// licesnse.key文件路径
		String licenseKeyFilePath = "/Users/limm/Desktop/key/license.key";
		
		String encryptfilePath="/Users/limm/Desktop/key/files/9-DICT-PERSON_encrypt.TXT";
		String decryptfilePath="/Users/limm/Desktop/key/files/9-DICT-PERSON_decrypt.TXT";
		License.decryptByLicense(licenseKeyFilePath, licenseTxtFilePath, encryptfilePath, decryptfilePath);
		
	}
}
