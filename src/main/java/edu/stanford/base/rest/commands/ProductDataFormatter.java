package edu.stanford.base.rest.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.stanford.base.constants.UIConstants;
import edu.stanford.widget.dao.GetProductsDAO;
import edu.stanford.widget.model.Product;
import edu.stanford.widget.model.ProductBean;
@Service("productDataFormatter")
public class ProductDataFormatter {
	
	@Autowired @Qualifier("getProductsDAO")
	GetProductsDAO getProductsDAO;
	/**
	 * @param emp
	 */
	public  void  calculateDefaultCosts(ProductBean emp) {
		if(StringUtils.isNotBlank(emp.getAnnualKwh())){
			
			double lifeCycleEnergyCost=Double.parseDouble(emp.getAnnualKwh())* 0.1079 * 11.626 ;
			emp.setLifeCycleEnergyCost(""+Math.round(lifeCycleEnergyCost));
				
		}else{
			emp.setLifeCycleEnergyCost("N.A");
		}
		//Savings: (kwh_old-kwh_new)*price*elec
		if(StringUtils.isNotBlank(emp.getAnnualKwh())){
			
			double annualCostSaving=(697 - Double.parseDouble(emp.getAnnualKwh()) )* 0.1079 ;
			emp.setAnnualCostSavings(""+Math.round(annualCostSaving));
				
		}else{
			emp.setAnnualCostSavings("N.A");
		}
	}
	
	
	
	/**
	 * Gets the product beans.
	 *
	 * @param listProducts the list products
	 * @return the product beans
	 */
	public  List<ProductBean> getProductBeans(List<Product> listProducts) {
	
		List<ProductBean> productBeans=new ArrayList<ProductBean>();
		Map <String,String> attributeMap= new HashMap<String, String>();
		Product previousProduct=null;
		for (Product product : listProducts) {
			
			
			if(previousProduct ==null ||  StringUtils.equalsIgnoreCase(previousProduct.getProductid(), product.getProductid())){
		
				attributeMap.put(product.getAttribute_name(),product.getAttribute_value());
				
			}else{
				
				attributeMap = constructProductBean(productBeans, attributeMap,	previousProduct, product);
				
			}
			previousProduct=product;
			
		}
		attributeMap = constructProductBean(productBeans, attributeMap,	previousProduct, null);
		return productBeans;
	}



	/**
	 * @param productBeans
	 * @param attributeMap
	 * @param previousProduct
	 * @param product
	 * @return
	 */
	private  Map<String, String> constructProductBean(List<ProductBean> productBeans,
			Map<String, String> attributeMap,Product previousProduct, Product product) {
		
		
		ProductBean myProductBean= new ProductBean();
		myProductBean.setProductid(previousProduct.getProductid());
		myProductBean.setDecription(previousProduct.getDecription());
		myProductBean.setModelNumber(previousProduct.getModelNumber());
		if(StringUtils.isEmpty(previousProduct.getPrice())){
			myProductBean.setPrice("CALL NOW for Pricing and Availability: 1-800-829-5854 ");
		}else{
			myProductBean.setPrice(previousProduct.getPrice());	
		}
		
		//myProductBean.setImageUrl("http://s.shld.net/is/image/Sears/"+previousProduct.getImagePath());
		myProductBean.setImageUrl(previousProduct.getImagePath());
		myProductBean.setAttribute(attributeMap);
		
		constructImageDisplayBlock(myProductBean);
		productBeans.add(myProductBean);
		
		attributeMap=new HashMap<String, String>();
		if(product !=null){
			attributeMap.put(product.getAttribute_name(),product.getAttribute_value());	
		}
		
		return attributeMap;
	}
	
	/**
	 * Construct image display block.
	 *
	 * @param myProductBean the my product bean
	 */
	public  void constructImageDisplayBlock(ProductBean myProductBean) {
		String siteUrl="http://www.sears.com/shc/s/search_10153_12605?keyword=";
		if(StringUtils.equalsIgnoreCase(myProductBean.getAttribute().get("PARENT_STORE"),"KMART")){
			siteUrl="http://www.kmart.com/shc/s/search_10151_10104?keyword=";
		}
		Pattern p = Pattern.compile("BRAND");
		
		String proddesc=StringUtils.replace(myProductBean.getDecription(),"-"," ");
		String imageDislayBlock=UIConstants.imageDisplayBlock1+myProductBean.getImageUrl()+UIConstants.imageDisplayBlock1a+proddesc+"  Price $"+myProductBean.getPrice()+"  kWh "+UIConstants.imageDisplayBlock11+
					 myProductBean.getImageUrl()+
					 UIConstants.imageDisplayBlock2+myProductBean.getAttribute().get("BRAND")+" "+ proddesc+
					// UIConstants.imageDisplayBlock3+myProductBean.getPrice()+
					 UIConstants.imageDisplayBlock4+siteUrl+myProductBean.getProductid()
					 +UIConstants.imageDisplayBlock5+myProductBean.getProductid()
					 +UIConstants.imageDisplayBlock6;
					 
		myProductBean.setImageDislayBlock(imageDislayBlock);			 
					 
					 
		
	}
	
	
	/**
	 * Gets the all products from db.
	 *
	 * @return the all products from db
	 */
	public  List<Product> getAllProductsFromDB() {
	
		
		//GetProductsDAO productDao = new GetProductsDAO();
		
		try {
			return getProductsDAO.getAllProducts();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
		
	}
	

	/**
	 * Gets the all products from db.
	 *
	 * @return the all products from db
	 */
	public  List<Product> getProductsFromDBForPids(List<String> pids) {
	
		
		//GetProductsDAO productDao = new GetProductsDAO();
		
		try {
			return getProductsDAO.getProducts(pids);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
		
	}
	
	
	
	
	public  List<String> getPropertyAsStringList(String tempCompleteStr) {
		List<String> propValue = new ArrayList<String>();
		
			try {
				
				StringTokenizer st = new StringTokenizer(tempCompleteStr, ","); 
			
				while (st.hasMoreTokens()) {
					propValue.add((String)st.nextToken());
					
				} 
								  
				
			} catch (Exception e) {
				//errorLog.error("Could not get property for : " + propName + " - "
					//	+ e);
				e.printStackTrace();
			}
		return propValue;
	}

	
	
	
	
}

