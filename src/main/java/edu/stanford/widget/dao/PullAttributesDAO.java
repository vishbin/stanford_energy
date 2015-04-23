package edu.stanford.widget.dao;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;




/**

 * @version 1.1
 */
@Repository("pullAttributesDAO")
public class PullAttributesDAO extends BaseDAO {

	/** The Constant log. */
	private static final Log log = LogFactory.getLog(PullAttributesDAO.class);
	
	Connection connDB2 = null;
	
	public void pullAllProducts() throws Exception {
		

		ResultSet rs = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		log.info("inside lookupKwhCalculationParams");
		Statement stmt = null;
		String pid=null,previousPid=null;
		StringBuffer productRow=new StringBuffer();
		
		try {
			initDB();	
			//initDB2();
				// and execute a simple query 
				stmt = conn.createStatement();
				rs = stmt.executeQuery("SELECT * FROM PRODUCTS where productId in ('00897880000P','195000000000KWC4P')");
				

				int i=0,y=0;
				while (rs.next()) {
					pid=rs.getString("productId");
					
					if (StringUtils.isBlank(previousPid) || StringUtils.equalsIgnoreCase(pid, previousPid)){
						
						productRow.append(rs.getString("attribute_name")+" : ");
     					productRow.append(rs.getString("attribute_value")+" , ");

					
					}else{

						writeToFile(previousPid+" ," +productRow);
						productRow=new StringBuffer();
						productRow.append(rs.getString("attribute_name")+" : ");
     					productRow.append(rs.getString("attribute_value")+" , ");

					}
					previousPid=pid;

					i++;		
				}
				writeToFile(pid+" ," +productRow);
				log.info(i+"ROWS INSERTED");
				
				System.out.println("There were " + i + " records.");
				System.out.println(y+" ROWS INSERTED");
				
	//			ps2.executeUpdate();
		} catch (Exception ex) {
			log.error("Caught SQL Exception: " + ex);
			throw ex;
		}
		// now close the statement and connection if they exist
		if (stmt != null) {
			try {
				stmt.close();
				//ps.close();
				rs.close();
				//ps2.close();
				//ps1.close();
			} catch (SQLException ex) {
				log.error("Could not close: " + ex);
				throw ex;
			}
		}
		closeDB();
		//closeDB2();
		
		
		
		
		
	}


	private void writeToFile(String productRow) {
		System.out.println(productRow);
		
	}


	/**
	 * Inits the db.
	 */
	public void initDB2() {


		try {
			// create a new instance of the mysql driver
			Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
		} catch (Exception Ex) {
			log.error("Unable to Load Driver: " + Ex.toString());
		}
		// get a new connection object
		try {
			connDB2 = DriverManager.getConnection("", "", "");

			log.info(connDB2.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error(e);
		}

	}
	
	/**
 * Close db.
 *
 * @throws Exception the exception
 */
public void closeDB2() throws Exception {
		if (conn != null) {
			try {
				connDB2.close();
			} catch (SQLException sqlEx) {
				log.error("Could not close db2: " + sqlEx.toString());
			}
		}
	}
	

}