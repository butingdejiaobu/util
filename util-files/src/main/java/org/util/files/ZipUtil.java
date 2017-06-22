package org.util.files;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
  
/** 
 * 
 * 程序实现了ZIP压缩。
 *  
 */  
  
public class ZipUtil {
	
//	private static Log logger = LogFactory.getLog(ZipUtil.class);
  
    /**
     * 将文件压缩到zip包
     * @param zipFilePath 目标文件路径
     * @param filePath 源文件路径
     * @throws Exception
     */
	public static void zip(String zipFilePath,String[] filePath) {  
		try {
//			if(logger.isDebugEnabled()){
//				logger.info("开始压缩...");
//			}
		    ZipOutputStream out;
			out = new ZipOutputStream(new FileOutputStream(  
			 		zipFilePath));
			BufferedOutputStream bo = new BufferedOutputStream(out); 
		       
	        //将指定文件压缩到zip包中
	        for(int i = 0; i < filePath.length; ++i){
	        	File f = new File(filePath[i]);
	            zip(out, f, f.getName(), bo); 
	        }
	        // 输出流关闭  
	        bo.close(); 
	        out.close();
	        
//	    	if(logger.isDebugEnabled()){
//				logger.info("压缩完成");
//			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }  
  
    /**
     * 读取单个文件到zip包
     * @param out
     * @param f 
     * @param base
     * @param bo
     * @throws Exception
     */
    private static void zip(ZipOutputStream out, File f, String base,  
            BufferedOutputStream bo) throws Exception { // 方法重载
    	
        out.putNextEntry(new ZipEntry(base)); // 创建zip压缩进入点base  
//    	if(logger.isDebugEnabled()){
//			logger.info("读取"+base);
//		}
        FileInputStream in = new FileInputStream(f);  
        BufferedInputStream bi = new BufferedInputStream(in);  
        int b;  
        while ((b = bi.read()) != -1) {  
            bo.write(b); // 将字节流写入当前zip目录  
        } 
        bo.flush();//将读取的当前文件内容输出
        bi.close();  
        in.close(); // 输入流关闭  
    }  
    
}  