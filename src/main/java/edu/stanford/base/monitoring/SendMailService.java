package edu.stanford.base.monitoring;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.stanford.widget.dao.BaseDAODB2;

public class SendMailService {
  
	/** The Constant log. */
	private static final Log logger = LogFactory.getLog(SendMailService.class);
	
  
  public static void main(String[] args) throws Error,Exception {
	  	sendEmail("TEST SUBJECT",new Exception(),"QUERY");
}

/**
 * @param query 
 * @throws MessagingException
 * @throws AddressException
 * @throws NoSuchProviderException
 */
public static void sendEmail(String subject ,Exception e2, String query){
	try {
				
			//stanfordenergy@gmail.com","energy@123"
			//TODO externalize read from a prop file
			String host = "smtp.gmail.com";
			String from = "stanfordenergy@gmail.com";
			String pass = "energy@123";
			Properties props = System.getProperties();
			props.put("mail.smtp.starttls.enable", "true"); // added this line
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.user", from);
			props.put("mail.smtp.password", pass);
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.auth", "true");
		
			String[] to = {"vishal.patil@gmail.com"}; // added this line
		
			Session session = Session.getDefaultInstance(props, null);
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
		
			InternetAddress[] toAddress = new InternetAddress[to.length];
		
			// To get the array of addresses
			for( int i=0; i < to.length; i++ ) { // changed from a while loop
			    toAddress[i] = new InternetAddress(to[i]);
			}
			System.out.println(Message.RecipientType.TO);
		
			for( int i=0; i < toAddress.length; i++) { // changed from a while loop
			    message.addRecipient(Message.RecipientType.TO, toAddress[i]);
			}
			message.setSubject(subject +e2.getLocalizedMessage() +"    Error Occured in The Application . Please check the logs");
			message.setText(query+"\n \n \n \n"+stackTraceToString(e2));
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
	
	} catch (Exception e) {
	logger.error("ERROR SENDING EMAIL ",e);
	}
}
public static String stackTraceToString(Throwable e) {
    StringBuilder sb = new StringBuilder();
    for (StackTraceElement element : e.getStackTrace()) {
        sb.append(element.toString());
        sb.append("\n");
    }
    return sb.toString();
}

}
