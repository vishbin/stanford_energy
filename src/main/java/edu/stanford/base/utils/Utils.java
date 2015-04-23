package edu.stanford.base.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Utils {

	/** The Constant log. */
	private static final Log log = LogFactory.getLog(Utils.class);
	
	/**
	 * @param isLocal
	 * @return
	 */
	public static boolean checkServer() {
		boolean isLocal=false;
		try {
			InetAddress addr = InetAddress.getLocalHost();
			if(addr.getHostAddress().contains("192.168")
					|| addr.getHostAddress().contains("127.0.0")
					|| addr.getHostAddress().contains("151.149.12")
					|| addr.getHostAddress().contains("172.28.4")){
				isLocal=true;
			}
			log.info(addr.getHostAddress());
			
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return isLocal;
	}
}
