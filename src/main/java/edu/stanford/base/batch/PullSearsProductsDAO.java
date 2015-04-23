package edu.stanford.base.batch;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import edu.stanford.widget.dao.BaseDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


/**

 * @version 1.1
 */
public class PullSearsProductsDAO extends BaseDAO {

	/** The Constant log. */
	private static final Log log = LogFactory.getLog(PullSearsProductsDAO.class);
	
	
	public void pullAllProducts(List<SearsProduct> prodList) throws Exception {
		
		
		
		
		String insertQuery="INSERT INTO    test1.sears_products_1   (        imageid,        numreview,        cutprice,        storeorigin,        beantype,        directdelivery,        mfgpartnumber,        sellercount,        catentryid,        rating,        promoind,        ksnvalue,        partnumber,        automotivedivision,        skupartnumber,        image,        spuind,        displayprice,        pbtype,        saleindicator,        clearanceindicator,        resind,        stockindicator,        name,        defaultfullfillment,        brandname    )    VALUES    (        ?,        ?,        ?,        ?,        ?,        ?,        ?,        ?,        ?,        ?,        ?,        ?,        ?,        ?,        ?,        ?,        ?,        ?,        ?,        ?,        ?,        ?,        ?,        ?,        ?,        ?    )";
	/*	productId VARCHAR(100) NOT NULL,
        name VARCHAR(400) NOT NULL,
        model_number VARCHAR(100) NOT NULL,
        image_path VARCHAR(100) NOT NULL,
        price DECIMAL NOT NULL,
        attribute_name VARCHAR(100) NOT NULL,
        attribute_value VARCHAR(300) NOT NULL
    */
		String updateKwhQuery="update test1.sears_products_1 sp   set kwh= ( select kwh from test1.energystar e where e.model  REGEXP   sp.mfgpartnumber limit 1)";
		String updateKwhQuerySears="update test1.sears_products_1 sp   set kwh= ( select  p.attribute_value from test.products p where p.productId =sp.partnumber and p.attribute_name='Kilowatt Hrs. per Year'  limit 1)  ";
		PreparedStatement ps = null;
		
		PreparedStatement ps2 = null;
		PreparedStatement ps1 = null;
		log.info("inside lookupKwhCalculationParams");
		
		
		
		
		try {
			initDB();	
				ps = conn.prepareStatement(insertQuery);
				int i=0,y=0;
				System.out.println(prodList.size() +"  returned from Sears API");
				for (SearsProduct product : prodList) {
					 ps.setString(1, product.getImageid());
					 ps.setString(2, product.getNumreview());
					 ps.setString(3, product.getCutprice());
					 ps.setString(4, product.getStoreorigin());
					 ps.setString(5, product.getBeantype());
					 ps.setString(6, product.getDirectdelivery());
					 ps.setString(7, product.getMfgpartnumber());
					 ps.setString(8, product.getSellercount());
					 ps.setString(9, product.getCatentryid());
					 ps.setString(10, product.getRating());
					 ps.setString(11, product.getPromoind());
					 ps.setString(12, product.getKsnvalue());
					 ps.setString(13, product.getPartnumber());
					 ps.setString(14, product.getAutomotivedivision());
					 ps.setString(15, product.getSkupartnumber());
					 ps.setString(16, product.getImage());
					 ps.setString(17, product.getSpuind());
					 ps.setString(18, product.getDisplayprice());
					 ps.setString(19, product.getPbtype());
					 ps.setString(20, product.getSaleindicator());
					 ps.setString(21, product.getClearanceindicator());
					 ps.setString(22, product.getResind());
					 ps.setString(23, product.getStockindicator());
					 ps.setString(24, product.getName());
					 ps.setString(25, product.getDefaultfullfillment());
					 ps.setString(26, product.getBrandname());
					
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
				
				ps2 = conn.prepareStatement(updateKwhQuery);
				ps2.executeUpdate();

				ps1 = conn.prepareStatement(updateKwhQuerySears);
				ps1.executeUpdate();
				
				System.out.println("There were " + i + " records.");
				System.out.println(y+" ROWS INSERTED SUCESSFULLY ");
				
				
		} catch (Exception ex) {
			log.error("Caught SQL Exception: " + ex);
			throw ex;
		}
		// now close the statement and connection if they exist
		
			try {
				
				ps.close();
				ps2.close();
				
				
				
			} catch (SQLException ex) {
				log.error("Could not close: " + ex);
				throw ex;
			}
		
		closeDB();
		
		
		
		
		
		
	}


	
	

	

}