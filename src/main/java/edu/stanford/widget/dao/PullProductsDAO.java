package edu.stanford.widget.dao;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;




/**

 * 
 * TODO : Move the if else logic to a seperate business logic file
 * TODO : Externalize the queries and the Strings.
 *
 * @version 1.1
 */
public class PullProductsDAO extends BaseDAO {

	/** The Constant log. */
	private static final Log log = LogFactory.getLog(PullProductsDAO.class);
	
	Connection connDB2 = null;
	
		
	public void pullAllProducts() throws Exception {
		StringBuffer pullSql = new StringBuffer();


		
		String insertQuery="INSERT  INTO    products    (        productId,        name,        model_number,        image_path,        price,        attribute_name,        attribute_value    )    VALUES    (        ?,        ?,        ?,        ?,        ?,        ?,        ?    )";
		
		
	/*	productId VARCHAR(100) NOT NULL,
        name VARCHAR(400) NOT NULL,
        model_number VARCHAR(100) NOT NULL,
        image_path VARCHAR(100) NOT NULL,
        price DECIMAL NOT NULL,
        attribute_name VARCHAR(100) NOT NULL,
        attribute_value VARCHAR(300) NOT NULL
    */
		ResultSet rs = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		log.info("inside lookupKwhCalculationParams");
		Statement stmt = null;
		
		
		
		try {
			initDB();	
			initDB2();
				// and execute a simple query 
				stmt = connDB2.createStatement();
				rs = stmt.executeQuery(pullSql.toString());
				
				ps = conn.prepareStatement(insertQuery);
				ps1 = conn.prepareStatement("delete from products");
				ps2 = conn.prepareStatement("UPDATE products set name= replace(name,'Refrigerator','')");
				ps3 = conn.prepareStatement("UPDATE products set attribute_name ='BRAND' where attribute_name='Brand'");
				
				
				//insert
				ps1.executeUpdate();
				 
				int i=0,y=0;
				while (rs.next()) {
					
					ps.setString(1, rs.getString("partnumber"));
					ps.setString(2, rs.getString("name"));
					ps.setString(3, rs.getString("mfpartnumber"));
					ps.setString(4, rs.getString("imagepath"));
					ps.setString(5, rs.getString("currentprice"));
					ps.setString(6, rs.getString("DESCRIPTION"));
					ps.setString(7, rs.getString("STRINGVALUE"));
					
					try {
						ps.executeUpdate();	
						y++;
					} catch (MySQLIntegrityConstraintViolationException e) {
						log.error("ERROR WHILE INSERT DATA"+"",e);
						e.printStackTrace();
					}
					i++;		
				}
				log.info(i+"ROWS INSERTED");
				
				System.out.println("There were " + i + " records.");
				System.out.println(y+" ROWS INSERTED");
				//Clean up
				ps2.executeUpdate();
				ps3.executeUpdate();
		} catch (Exception ex) {
			log.error("Caught SQL Exception: " + ex);
			throw ex;
		}
		// now close the statement and connection if they exist
		if (stmt != null) {
			try {
				stmt.close();
				ps.close();
				rs.close();
				ps2.close();
				ps1.close();
			} catch (SQLException ex) {
				log.error("Could not close: " + ex);
				throw ex;
			}
		}
		closeDB();
		closeDB2();
		
		
		
		
		
	}


	/**
	 * Inits the db.
	 */
	public void initDB2() {
		String DBUrl,DBUser,DBPassword ="";
		



		DBUrl = "";
		DBUser = "";
		DBPassword = "";
		 
		try {
			// create a new instance of the mysql driver
			Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
		} catch (Exception Ex) {
			Ex.printStackTrace();
			log.error("Unable to Load Driver: " + Ex.toString());
		}
		// get a new connection object
		try {
			connDB2 = DriverManager.getConnection(DBUrl, DBUser, DBPassword);

			log.info(connDB2.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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