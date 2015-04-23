package edu.stanford.base.rest.commands;

import edu.stanford.base.config.BeanFactory;
import edu.stanford.widget.model.Product;
import edu.stanford.widget.model.ProductBean;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServlet;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



/**

 * @version 1.1
 * 
 */

@Service("getProductData")
@Path("/allproducts/")
public class GetProductData extends HttpServlet {

	ProductDataFormatter productDataFormatter;

/**
	 * 
	 */
	private static final long serialVersionUID = 5548674226983771199L;


/**
 * Gets the all products.
 *
 * @return the all products
 * @throws JSONException the jSON exception
 */
public JSONObject getAllProducts(@QueryParam("type") String type) throws JSONException{

	productDataFormatter = (ProductDataFormatter)BeanFactory.getInstance().getBean("productDataFormatter");	
	
  JSONObject myJSONObject = new JSONObject();
    
  if(type=="initial"){
		
	return null;	
  }
    

    List<Product> listProducts =productDataFormatter.getAllProductsFromDB();
    
    List<ProductBean> productBeans= productDataFormatter.getProductBeans(listProducts);
    

    JSONObject obj = new JSONObject();
    obj.put("page","1").put("total",productBeans.size()/10).put("records", productBeans.size());
    if(productBeans != null)
    {
        JSONArray empsArray = new JSONArray();
        JSONObject o;
        for(Iterator iterator = productBeans.iterator(); iterator.hasNext(); empsArray.put(o))
        {
     
     
        	ProductBean emp = (ProductBean)iterator.next();
        	o = new JSONObject();
            o.put("id", emp.getProductid()+"");
            
            o.put("Decription", emp.getDecription()+"");
            o.put("ImageUrl", emp.getImageUrl()+"");
            o.put("ModelNumber", emp.getModelNumber()+"");
            o.put("Price", emp.getPrice()+"");
            o.put("AnnualKwh", emp.getAnnualKwh()+"");
            o.put("LifeCycleEnergyCost", emp.getLifeCycleEnergyCost()+"");
            o.put("AnnualCostSavings", emp.getAnnualCostSavings()+"");
            
        	ArrayList<Map.Entry<String, String>> entries = new ArrayList<Map.Entry<String, String>>(emp.getAttribute().entrySet());
			 
			
			 for (Map.Entry<String, String> ent : entries) {
				 
				 o.put(ent.getKey(),ent.getValue());
				 
				 
			 }
			
        	
        }

        obj.put("entry", empsArray);
    }
   
    

    
    return obj ;
}




/**
 * Gets the all products for  grid.
 *
 * @return the all products for grid
 * @throws JSONException the jSON exception
 */
@GET
@Produces("application/json")
public JSONObject getAllProductsForJqGrid(@QueryParam("type") String type) throws JSONException{
    JSONObject myJSONObject = new JSONObject();
    
   /* if(StringUtils.equalsIgnoreCase(type,"initial")){
  		
  	return myJSONObject;	
    }*/
    productDataFormatter = (ProductDataFormatter)BeanFactory.getInstance().getBean("productDataFormatter");
    List<Product> listProducts =productDataFormatter.getAllProductsFromDB();
    
    List<ProductBean> listOrders= productDataFormatter.getProductBeans(listProducts);
    Collections.sort(listOrders, new CustomComparator());
    	

    JSONObject obj = new JSONObject();
    obj.put("page","1").put("total",listOrders.size()/10).put("records", listOrders.size());
    if(listOrders != null)
    {
        JSONArray empsArray = new JSONArray();
        JSONObject o;
        for(Iterator iterator = listOrders.iterator(); iterator.hasNext();)
        {
     
     
        	ProductBean emp = (ProductBean)iterator.next();
        	o = new JSONObject();
            o.put("id", emp.getProductid()+"");
        
            String kWh= ((Map<String, String>)emp.getAttribute()).get("Kilowatt Hrs. per Year");
            
            ArrayList myList= new ArrayList<String>();
            myList.add(emp.getProductid());
            myList.add(emp.getImageDislayBlock().replace("kWh", "<br>"+kWh+" kWh"));
            myList.add(emp.getPrice());
            
            //myList.add(kWh+" kWh");
            myList.add(kWh);
			emp.setAnnualKwh(kWh);
			
			productDataFormatter.calculateDefaultCosts(emp);
            myList.add(emp.getLifeCycleEnergyCost());
            myList.add(emp.getAnnualCostSavings());          
            

			String color= ((Map<String, String>)emp.getAttribute()).get("Color Family");
			myList.add(color);
			
			
			String iceMaker= ((Map<String, String>)emp.getAttribute()).get("Ice Maker");
			myList.add(iceMaker);
			
			
			String energyStar= ((Map<String, String>)emp.getAttribute()).get("ENERGY STAR Compliant");
			if(StringUtils.isBlank(energyStar)){
				energyStar="No";
			}
			
			myList.add(energyStar);
            
			

			String size= ((Map<String, String>)emp.getAttribute()).get("Overall Capacity (Cu Ft)");
			myList.add(size);
            
            o.put("cell",myList);	
            //}
            if(StringUtils.isNotBlank(emp.getAnnualKwh())){
            	empsArray.put(o);	
            }
        	
        }

        obj.put("rows", empsArray);
    }
   
    

    
    return obj ;
}



public class CustomComparator implements Comparator<ProductBean> {
    @Override
    public int compare(ProductBean o1, ProductBean o2) {
        return Integer.valueOf(o1.getPrice()).compareTo(Integer.valueOf(o2.getPrice()));
    }
}
	
}
