package org.util.files;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Enumeration;
import java.util.zip.ZipException;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

public class FileUtil {

	/** * 功能:利用nio快速复制目录 */
	public static void copyDirectory(String srcDirectory, String destDirectory)
			throws java.io.FileNotFoundException, java.io.IOException {
		// 得到目录下的文件和目录数组
		File srcDir = new File(srcDirectory);
		File[] fileList = srcDir.listFiles();
		if (fileList == null)
			return;
		// 如果目标目录不存在，创建目标目录
		File descDir = new File(destDirectory);
		if (!descDir.exists()) {
			descDir.mkdirs();
		}
		// 循环处理数组
		for (int i = 0; i < fileList.length; i++) {
			if (fileList[i].isFile()) {
				// 数组中的对象为文件

				// 复制文件到目标目录
				copyFile(srcDirectory + File.separator + fileList[i].getName(),
						destDirectory + File.separator + fileList[i].getName());
			} else {
				// 数组中的对象为目录
				// 如果该子目录不存在就创建（其中也包含了对多级目录的处理）
				File subDir = new File(destDirectory + File.separator
						+ fileList[i].getName());
				if (!subDir.exists()) {
					subDir.mkdirs();
				}
				// 递归处理子目录
				copyDirectory(
						srcDirectory + File.separator + fileList[i].getName(),
						destDirectory + File.separator + fileList[i].getName());

			}
		}
	}

	/**
	 * 把指定路径的文件内容拷贝到另外路径文件
	 * 
	 * @param srcFilePath
	 * @param toFilePath
	 * @throws IOException
	 */
	public static void copyFile(String srcFilePath, String toFilePath)
			throws IOException {
		File srcFile = new File(srcFilePath);
		if (srcFile.exists()) {
			if (srcFile.isFile()) {
				FileInputStream input = new FileInputStream(srcFile);
				int index = toFilePath.lastIndexOf("/");
				String toDirPath = toFilePath.substring(0, index);
				File toDirFile = new File(toDirPath);
				if (!toDirFile.exists()) {
					toDirFile.mkdirs();
				}
				FileOutputStream output = new FileOutputStream(toFilePath);
				byte[] b = new byte[input.available()];
				input.read(b);
				output.write(b);
				output.flush();
				output.close();
				input.close();
			} else {
				File toFileDirFile = new File(toFilePath);
				System.out.println("toFilePath:" + toFilePath);
				if (!toFileDirFile.exists()) {
					toFileDirFile.mkdirs();
				}
				// throw new RuntimeException("只能拷贝文件，不能是文件夹！");
			}
		}
	}

	/**
	 * 
	 * @param filename
	 *            ,context 向文件名为filename的文件写入内容context！
	 * @return int
	 * 
	 */
	public static int writeFile(String filename, String context)
			throws IOException {
		Writer writer = null;
		try {
			File file = createNewFile(filename);
			FileOutputStream fileOutput = new FileOutputStream(file);
			OutputStreamWriter outputStream = new OutputStreamWriter(
					fileOutput, "UTF-8");
			writer = new BufferedWriter(outputStream);
			writer.write(context);
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (null != writer) {
					writer.flush();
					writer.close();
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		return 1;
	}

	/**
	 * 创建文件 如果存在 则不创建
	 * 
	 * @param pathName
	 * @return
	 */
	public static File createNewFile(String pathName) {

		File file = new File(pathName);
		if (file.exists()) {
			return file;
		}
		try {
			createNewDir(file.getParent());
			file.createNewFile();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return file;
	}

	/**
	 * 创建文件夹
	 * 
	 * @param path
	 * @return
	 */
	public static File createNewDir(String path) {
		File file = new File(path);
		if (file.exists()) {
			return file;
		}
		file.mkdirs();
		return file;
	}

	/**
	 * 解压文件
	 * 
	 * @param archive
	 * @param decompressDir
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws ZipException
	 */
	public static void unZipFile(String zipFile, String destFilepath)
			throws IOException, FileNotFoundException, ZipException {
		BufferedInputStream bi;

		ZipFile zf = new ZipFile(zipFile, "GBK");// 支持中文

		Enumeration e = zf.getEntries();
		while (e.hasMoreElements()) {
			ZipEntry ze2 = (ZipEntry) e.nextElement();
			String entryName = ze2.getName();
			String path = destFilepath + "/" + entryName;
			// 如果是目录
			if (ze2.isDirectory()) {
				// System.out.println("正在创建解压目录 - " + entryName);
				File decompressDirFile = new File(path);
				if (!decompressDirFile.exists()) {
					decompressDirFile.mkdirs();
				}
			}
			// 如果是文件
			else {
				// System.out.println("正在创建解压文件 - " + entryName);
				String fileDir = path.substring(0, path.lastIndexOf("/"));
				File fileDirFile = new File(fileDir);
				if (!fileDirFile.exists()) {
					fileDirFile.mkdirs();
				}
				BufferedOutputStream bos = new BufferedOutputStream(
						new FileOutputStream(destFilepath + "/" + entryName));

				bi = new BufferedInputStream(zf.getInputStream(ze2));
				byte[] readContent = new byte[1024];
				int readCount = bi.read(readContent);
				while (readCount != -1) {
					bos.write(readContent, 0, readCount);
					readCount = bi.read(readContent);
				}
				bos.close();
			}
		}
		zf.close();
	}

}
