/**
 * 
 */
package edu.stanford.base.batch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.stanford.widget.dao.KWHCalculateDAO;
import edu.stanford.widget.dao.PullAttributesDAO;
import edu.stanford.widget.dao.PullProductsDAO;

/**
 *
 */
public class PullProductsBatch {

	/** The Constant log. */
	private static final Log log = LogFactory.getLog(KWHCalculateDAO.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		PullAttributesDAO PullAttributesDAO=new PullAttributesDAO();
		try {
			//PullAttributesDAO.pullAllProducts();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PullProductsDAO pullProductsDAO=new PullProductsDAO();
		try {
			pullProductsDAO.pullAllProducts();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		

	}

}
