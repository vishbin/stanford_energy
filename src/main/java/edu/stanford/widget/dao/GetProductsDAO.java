package edu.stanford.widget.dao;

import edu.stanford.widget.model.Product;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @version 1.1
 */

@Repository("getProductsDAO")
public class GetProductsDAO extends BaseDAOHibernateImpl  {
	
	/** The Constant log. */
	private static final Log log = LogFactory.getLog(GetProductsDAO.class);
	
	/**
	 * Gets the all products.
	 *
	 * @return the all products
	 * @throws Exception the exception
	 */
	public List<Product> getProducts(List<String> pids) throws Exception {
		Statement stmt = null;
		StringBuffer stmtString = new StringBuffer();
		int i=0;
		for (String string : pids) {
			i++;
			stmtString.append("'");
			stmtString.append(string);
			stmtString.append("'");
			if (i<pids.size()){
				stmtString.append(",");
			}
		}
		
		List<Product> productList = new ArrayList<Product>();
		
		try {
			//initDB();	
	 
			stmt = getConn().createStatement();
			
			log.info(stmtString);
			if(stmtString.length()<1){
				return productList;
			}
			//ResultSet rs = stmt.executeQuery("SELECT *  FROM test.products where productId in ( "+stmtString.toString() +" )order by productId asc ");
			ResultSet rs = stmt.executeQuery("SELECT *  FROM products where productId in ( "+stmtString.toString() +" )order by productId asc ");
			
			while (rs.next()) {
				// iterate through the result set and get first record
				Product productBean= new Product();
				productBean.setProductid(rs.getString("productId"));
				productBean.setDecription(rs.getString("name"));
				productBean.setModelNumber(rs.getString("model_number"));
				productBean.setPrice(rs.getString("price"));
				productBean.setAttribute_name(rs.getString("attribute_name"));
				productBean.setAttribute_value(rs.getString("attribute_value"));
				productBean.setImagePath(rs.getString("image_path"));
				productList.add(productBean);
				//log.info("Fetched::"+productBean.getProductid());
				
				
				
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
		return productList;
		
	}
		
	
	/**
	 * Gets the all products.
	 *
	 * @return the all products
	 * @throws Exception the exception
	 */
	public List<Product> getAllProducts() throws Exception {
		Statement stmt = null;
		String stmtString = "";
		
		List<Product> productList = new ArrayList<Product>();
		
		try {
			//initDB();	
	 
			stmt = getConn().createStatement();
			//
			   
		      
		       
			stmtString = "SELECT *  FROM products  where price >0  and productid not in ('04609775000P',     '04608785000P', '04609787000P', '04608775000P', '04608755000P', '04608787000P', '04608745000P', '04609785000P', '04609775000P', '04608735000P', '04608786000P') order by productId asc ";
			
			//Query query = getSession().createSQLQuery(stmtString).addEntity(Product.class);
			//Query query = getSession().createSQLQuery(stmtString);
			//List myProduct=query.list();
			
			log.info(stmtString);
			
			//log.info(myProduct);
			
			
			
			ResultSet rs = stmt.executeQuery(stmtString);
			
			while (rs.next()) {
				// iterate through the result set and get first record
				Product productBean= new Product();
				productBean.setProductid(rs.getString("productId"));
				productBean.setDecription(StringUtils.remove(StringUtils.remove(rs.getString("name"),")"),"("));
				productBean.setModelNumber(rs.getString("model_number"));
				productBean.setPrice(rs.getString("price"));
				productBean.setAttribute_name(rs.getString("attribute_name"));
				productBean.setAttribute_value(rs.getString("attribute_value"));
				productBean.setImagePath(rs.getString("image_path"));
				productList.add(productBean);
				
				
				
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
		return productList;
		
	}
	
	

}