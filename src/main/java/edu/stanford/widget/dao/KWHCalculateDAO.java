package edu.stanford.widget.dao;

import edu.stanford.base.calculations.NewKwhQueryConstructor;
import edu.stanford.widget.model.KwhCalculationBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**

 * TODO : Move the if else logic to a seperate business logic file
 * TODO : Externalize the queries and the Strings.
 *
 * @version 1.1
 */
@Repository("kWHCalculateDAO")
public class KWHCalculateDAO extends BaseDAOHibernateImpl {

	/** The Constant log. */
	private static final Log log = LogFactory.getLog(KWHCalculateDAO.class);
	@Autowired @Qualifier("newKwhQueryConstructor")
	protected  NewKwhQueryConstructor newKwhQueryConstructor;
	// get contact info
	/**
	 * Lookup kwh calculation params.
	 *
	 * @param contactBean the contact bean
	 * @throws Exception the exception
	 */
	public void lookupKwhCalculationParams(KwhCalculationBean contactBean) throws Exception {
		
		
		log.info("inside lookupKwhCalculationParams");
		Statement stmt = null;
		String stmtString = "";
		long outkWhmed = 0 ;long outkWhsd = 0 ;

		try {
		//	initDB();	
			// and execute a simple query 
			stmt = getConn().createStatement();
			
			stmtString = newKwhQueryConstructor.constructQueryString(contactBean);
	
			ResultSet rs = stmt.executeQuery(stmtString);
			
			while (rs.next()) {
				// iterate through the result set and get first record
				
				outkWhmed=Math.round(rs.getDouble("output_kwh_m"));
				outkWhsd=Math.round(rs.getDouble("output_kwh_sd"));
				
			}
		} catch (SQLException ex) {
			log.error("Caught SQL Exception: " + ex);
			throw ex;
		}
		// now close the statement and connection if they exist
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException ex) {
				log.error("Could not close: " + ex);
				throw ex;
			}
		}
		//closeDB();
		
		contactBean.setOutputKwhMed(outkWhmed);
		log.info(" outkWhmed= "+outkWhmed);
		contactBean.setOutputKwhSd(outkWhsd);
		log.info(" outkWhsd= "+outkWhsd);
		
	}


	
	
	

}