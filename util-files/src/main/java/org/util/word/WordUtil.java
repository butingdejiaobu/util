package org.util.word;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.FileURIResolver;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.w3c.dom.Document;

/**
 *转化word文件为html
 * 
 */
public class WordUtil {

	/**
	 * @param fileName,绝对路径的文件名
	 * @return
	 * @throws ParserConfigurationException 
	 * @throws TransformerException 
	 * @throws IOException 
	 */
	public static void wordToHtml(String fileName) throws IOException, TransformerException, ParserConfigurationException {
		File file = new File(fileName);
		String filePath = file.getParent();
		String fileNameActual = file.getName();
		if(fileNameActual.endsWith("doc")){
			String htmlName = fileNameActual.replace(".doc", ".html");
			convertWordDocTohTML(fileNameActual,htmlName,filePath);
		}else if(fileNameActual.endsWith("docx")){
			String htmlName = fileNameActual.replace(".docx", ".html");
			convertWordDocxTohTML(fileNameActual,htmlName,filePath);
		}
	}

	public static void convertWordDocTohTML(String fileName,String htmlName, String filePath) throws IOException, TransformerException, ParserConfigurationException {
        final String imagepath = filePath + File.separator + "word/media/";
//      String htmlName = "NSS服务使用手册.html";
        final String file = filePath+ File.separator +fileName;
        File htmlFile = new File(filePath + File.separator + htmlName);
        InputStream input = new FileInputStream(new File(file));
        HWPFDocument wordDocument = new HWPFDocument(input);
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
        //设置图片存放的位置
        wordToHtmlConverter.setPicturesManager(new PicturesManager() {
            public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches, float heightInches) {
                File imgPath = new File(imagepath);
                if(!imgPath.exists()){//图片目录不存在则创建
                    imgPath.mkdirs();
                }
                File file = new File(imagepath + suggestedName);
                try {
                    OutputStream os = new FileOutputStream(file);
                    os.write(content);
                    os.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return imagepath + suggestedName;
            }
        });
        
        //解析word文档
        wordToHtmlConverter.processDocument(wordDocument);
        Document htmlDocument = wordToHtmlConverter.getDocument();
        
        
        OutputStream outStream = new FileOutputStream(htmlFile);
        
        //也可以使用字符数组流获取解析的内容
//        ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
//        OutputStream outStream = new BufferedOutputStream(baos);

        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(outStream);

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer serializer = factory.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        
        serializer.transform(domSource, streamResult);

        //也可以使用字符数组流获取解析的内容
//        String content = baos.toString();
//        System.out.println(content);
//        baos.close();
        outStream.close();
    }
	private static void convertWordDocxTohTML(String fileName, String htmlName, String filePath) throws IOException{
        final String file = filePath+ File.separator +fileName;
        File f = new File(file);  
        if (!f.exists()) {  
            System.out.println("Sorry File does not Exists!");  
        } else {  
            if (f.getName().endsWith(".docx") || f.getName().endsWith(".DOCX")) {  
                // 1) 加载word文档生成 XWPFDocument对象  
                InputStream in = new FileInputStream(f);  
                XWPFDocument document = new XWPFDocument(in);  
                // 2) 解析 XHTML配置 (这里设置IURIResolver来设置图片存放的目录)  
                File imageFolderFile = new File(filePath);  
                XHTMLOptions options = XHTMLOptions.create().URIResolver(new FileURIResolver(imageFolderFile));  
                options.setExtractor(new FileImageExtractor(imageFolderFile));  
                options.setIgnoreStylesIfUnused(false);  
                options.setFragment(true);  
                // 3) 将 XWPFDocument转换成XHTML 
                //生成html文件上级文件夹
                File folder = new File(filePath + File.separator);
                if(!folder.exists()){ 
					folder.mkdirs(); 
				}
                OutputStream out = new FileOutputStream(new File(filePath + File.separator + htmlName));  
                XHTMLConverter.getInstance().convert(document, out, options);  
                //也可以使用字符数组流获取解析的内容
//                ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
//                XHTMLConverter.getInstance().convert(document, baos, options);  
//                String content = baos.toString();
//                System.out.println(content);
//                 baos.close();
            } else {  
              
            }  
        }  
    }
}
