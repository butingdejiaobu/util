package org.limingming.email;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;

public class SendEmailJob implements Runnable{

	private static String account;//登录用户名
    private static String pass;        //登录密码
    private static String from;        //发件地址
    private static String host;        //服务器地址
    private static String port;        //端口
    private static String protocol; //协议
    
    private Map<String, String> emailInfo;
    
    public SendEmailJob(Map<String, String> emailInfo){
    	this.emailInfo = emailInfo;
    }
    
    static{
        Properties prop = new Properties();
        try {
            prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("email.properties"));
        } catch (IOException e) {
            System.out.println("加载属性文件失败");
        }
        account = prop.getProperty("mail.account");
        pass = prop.getProperty("mail.pass");
        from = prop.getProperty("mail.from");
        host = prop.getProperty("mail.host");
        port = prop.getProperty("mail.port");
        protocol = prop.getProperty("mail.protocol");
    }
    
    @Override
	public void run() {
		exec();
	}
	
	public void exec() {
		
		String recipient = emailInfo.get("recipient");
		String subject = emailInfo.get("subject");
		String emailContent = emailInfo.get("emailContent");
		
		Properties prop = new Properties();
        //协议
        prop.setProperty("mail.transport.protocol", protocol);
        //服务器
        prop.setProperty("mail.smtp.host", host);
        //端口
        prop.setProperty("mail.smtp.port", port);
        //使用smtp身份验证
        prop.setProperty("mail.smtp.auth", "true");
        //使用SSL，企业邮箱必需！
        //开启安全协议
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
        } catch (GeneralSecurityException e1) {
            System.out.println("发送邮件开启安全协议出错..." + e1);
        }
        prop.put("mail.smtps.ssl.enable", "true");//使用https
        prop.put("mail.smtps.ssl.socketFactory", sf);
////	        prop.put("mail.smtp.socketFactory.fallback", "true");
        prop.put("mail.smtps.starttls.enable","true");
        //
        Session session = Session.getDefaultInstance(prop, new MyAuthenricator(account, pass));
//	        Session session = Session.getDefaultInstance(prop, null);//failed to connect, no password specified?
//        session.setDebug(true);//默认不打印日志
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setFrom(new InternetAddress(from));//设置邮件发件人
            
            //多个收件人地址以逗号分隔，然后添加到收件人列表进行邮件发送
            String[] recipients = recipient.split(",");
            for (String re : recipients) {
            	mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(re));//设置收信人
			}
            mimeMessage.setSubject(subject);//设置邮件主题
            mimeMessage.setSentDate(new Date());
            mimeMessage.setText(emailContent);
            mimeMessage.saveChanges();
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
        	 System.out.println("邮件发送出错..." + e);
        }
	}
	
    public static void main(String[] args) {
    	
    	Map<String, String> param = new HashMap<>();
    	param.put("recipient", "limm@inspur.com");
    	param.put("subject", "测试邮件发送");
    	param.put("emailContent", "测试测试邮件发送，加密功能暂未实现");
    	
    	Thread sendEmailThread = new Thread(new SendEmailJob(param));
    	sendEmailThread.start();
	}
    
    //用户名密码验证，需要实现抽象类Authenticator的抽象方法PasswordAuthentication
    static class MyAuthenricator extends Authenticator{  
        String u = null;  
        String p = null;  
        public MyAuthenricator(String u,String p){  
            this.u=u;  
            this.p=p;  
        }  
        @Override  
        protected PasswordAuthentication getPasswordAuthentication() {  
            return new PasswordAuthentication(u,p);  
        }  
    }

    /**
     * 发送邮件
     * @param recipient 收件人
     * @param subject 邮件主题
     * @param emailContent 正文内容
     */
//    public static void send(String recipient, String subject, String emailContent){
//        Properties prop = new Properties();
//        //协议
//        prop.setProperty("mail.transport.protocol", protocol);
//        //服务器
//        prop.setProperty("mail.smtp.host", host);
//        //端口
//        prop.setProperty("mail.smtp.port", port);
//        //使用smtp身份验证
//        prop.setProperty("mail.smtp.auth", "true");
//        //使用SSL，企业邮箱必需！
//        //开启安全协议
//        MailSSLSocketFactory sf = null;
//        try {
//            sf = new MailSSLSocketFactory();
//            sf.setTrustAllHosts(true);
//        } catch (GeneralSecurityException e1) {
//            e1.printStackTrace();
//        }
//        prop.put("mail.smtps.ssl.enable", "true");//使用https
//        prop.put("mail.smtps.ssl.socketFactory", sf);
//////        prop.put("mail.smtp.socketFactory.fallback", "true");
//        prop.put("mail.smtps.starttls.enable","true");
//        //
//        Session session = Session.getDefaultInstance(prop, new MyAuthenricator(account, pass));
////        Session session = Session.getDefaultInstance(prop, null);//failed to connect, no password specified?
//        session.setDebug(true);//默认不打印日志
//        MimeMessage mimeMessage = new MimeMessage(session);
//        try {
//            mimeMessage.setFrom(new InternetAddress(from));//设置邮件发件人
//            
//            //多个收件人地址以逗号分隔，然后添加到收件人列表进行邮件发送
//            String[] recipients = recipient.split(",");
//            for (String re : recipients) {
//            	mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(re));//设置收信人
//			}
//            mimeMessage.setSubject(subject);//设置邮件主题
//            mimeMessage.setSentDate(new Date());
//            mimeMessage.setText(emailContent);
//            mimeMessage.saveChanges();
//            Transport.send(mimeMessage);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//            log.debug(e.getMessage());
//        }
//    }

}