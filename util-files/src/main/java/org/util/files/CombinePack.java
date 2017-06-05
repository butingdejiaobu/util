package org.util.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipException;

public class CombinePack {

	/**
	 * 合并包 1、合并包中的文件 2、合并升级说明内容
	 * 
	 * @param srcPackPath
	 * @param toPath
	 * @throws IOException
	 */
	public void combinePack(String srcPackPath, String toPath)
			throws IOException {
		File srcPackDir = new File(srcPackPath);
		File[] packs = srcPackDir.listFiles();
		List packList = new ArrayList();
		for (int i = 0; i < packs.length; i++) {
			String packName = packs[i].getName();
			packList.add(packName);
		}
		// 按照自然顺序排序
		Collections.sort(packList);
		// 合并包的位置
		File combinePath = new File(toPath);
		if (!combinePath.exists())
			combinePath.mkdirs();
		// 在合并包中，新建升级说明
		String newUpgradeDesc = toPath + File.separator + "new-升级说明.txt";
		File newUpgradeDescFile = new File(newUpgradeDesc);
		if (!newUpgradeDescFile.exists())
			newUpgradeDescFile.createNewFile();
		// 记录包中的升级说明
		StringBuffer sb = new StringBuffer();
		// 读取包中升级说明内容，升级说明在升级包根目录下，名称为“升级说明.txt”
		for (int i = 0; i < packList.size(); i++) {
			String packName = (String) packList.get(i);
			String packPath = srcPackPath + File.separator + packName;
			String upgradeDesc = packPath + File.separator + "升级说明.txt";
			// 读取升级文件内容
			String content = readTxtFile(upgradeDesc);
			if (!"".equals(content)) {
				sb.append(content + "\n\r");
			}
			// 拷贝升级包中的内容到目标目录
			FileUtil.copyDirectory(packPath, toPath);
		}
		// 将升级说明写入到合并后的升级包中
		try {
			FileUtil.writeFile(newUpgradeDesc, sb.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		// 在多个升级包覆盖后，会产生升级说明，删除
		String oldUpgradeDesc = toPath + File.separator + "升级说明.txt";
		File oldUpgradeDescFile = new File(oldUpgradeDesc);
		oldUpgradeDescFile.deleteOnExit();
		// 修改"new-升级说明.txt"为"升级说明.txt"
		newUpgradeDescFile.renameTo(oldUpgradeDescFile);
	}

	// 读取压缩包列表，并解压
	public void unZip(String zipSrcPath, String unZipPath)
			throws FileNotFoundException, ZipException, IOException {
		File zipSrcDir = new File(zipSrcPath);
		File[] packs = zipSrcDir.listFiles();
		for (int i = 0; i < packs.length; i++) {
			// 压缩包路径
			String zipPath = packs[i].getAbsolutePath();
			String packName = packs[i].getName();
			// 构造解压后的路径
			String unZipName = packName.substring(0, packName.lastIndexOf("."));
			String unZip = unZipPath + File.separator + unZipName;
			File unZipDir = new File(unZip);
			if (!unZipDir.exists())
				unZipDir.exists();
			// 解压
			FileUtil.unZipFile(zipPath, unZip);
		}
	}

	public void getCombinePack(String zipSrcPath, String unZipPath,
			String combinePackPath) {
		try {
			unZip(zipSrcPath, unZipPath);
			unZipPath=unZipPath.replaceAll("\\\\", "/");
			combinePackPath=combinePackPath.replaceAll("\\\\", "/");
			combinePack(unZipPath, combinePackPath);
			System.out.println("合并完成");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ZipException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String readTxtFile(String filePath) {
		StringBuffer content = new StringBuffer();
		try {
			String encoding = "UTF-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					// System.out.println(lineTxt);
					content.append(lineTxt);
				}
				read.close();
			} else {
				throw new RuntimeException("找不到指定的文件:" + filePath);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return content.toString();

	}

	public static void main(String argv[]) {
		CombinePack cp = new CombinePack();
		// 压缩包所在路径
		String zipSrcPath = "D:\\11\\33\\zip";
		// 解压到的路径
		String unZipPath = "D:\\11\\33\\unzip";
		// 合并包的路径
		String combinePackPath = "D:\\11\\33\\upgrade";
		cp.getCombinePack(zipSrcPath, unZipPath, combinePackPath);
	}

}
