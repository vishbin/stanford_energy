package edu.stanford.widget.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// TODO: Auto-generated Javadoc
/**

 * @version 1.1
 * 
 */
public class BaseDAO {

	/** The Constant log. */
	private static final Log log = LogFactory.getLog(BaseDAO.class);
	
	/** The conn. */
	protected Connection conn = null;

	/**
	 * Inits the db.
	 */
	public void initDB() {
		String DBUrl,DBUser,DBPassword ="";
		
		 boolean isLocal=false;
		 isLocal = checkServer(isLocal);

		// DBUrl = ServerConfig.instance().getCSVTempDir() ; // temp folder for this csv generated file 

		if(isLocal){

		}else{

		}

		 
		try {
			// create a new instance of the mysql driver
			Class.forName("org.gjt.mm.mysql.Driver").newInstance();
		} catch (Exception Ex) {
			log.error("Unable to Load Driver: " + Ex.toString());
		}
		// get a new connection object
		try {
			conn = DriverManager.getConnection("", "", "");

			log.info(conn.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error(e);
		}

	}

	/**
	 * @param isLocal
	 * @return
	 */
	private boolean checkServer(boolean isLocal) {
		try {
			InetAddress addr = InetAddress.getLocalHost();
			if(addr.getHostAddress().contains("192.168")
					|| addr.getHostAddress().contains("127.0.0")
					|| addr.getHostAddress().contains("148.162")
					|| addr.getHostAddress().contains("151.149")
					|| addr.getHostAddress().contains("172.28")){
				isLocal=true;
			}
			log.info(addr.getHostAddress());
			
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return isLocal;
	}

	/**
 * Close db.
 *
 * @throws Exception the exception
 */
public void closeDB() throws Exception {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException sqlEx) {
				log.error("Could not close: " + sqlEx.toString());
			}
		}
	}

}