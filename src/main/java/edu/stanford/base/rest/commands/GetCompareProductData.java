package edu.stanford.base.rest.commands;

import edu.stanford.base.config.BeanFactory;
import edu.stanford.widget.model.Product;
import edu.stanford.widget.model.ProductBean;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServlet;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



/**

 * @version 1.1
 * 
 */

@Service("getCompareProductData")
@Path("/getcomparedata/")
public class GetCompareProductData extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired @Qualifier("productDataFormatter")
	ProductDataFormatter productDataFormatter;
	
	
/**
 * Gets the all products for  grid.
 *
 * @return the all products for grid
 * @throws JSONException the jSON exception
 */
@GET
@Produces("application/json")

public JSONObject getAllProductsForJqGrid( @QueryParam("pids") String pids) throws JSONException{
   
    productDataFormatter = (ProductDataFormatter)BeanFactory.getInstance().getBean("productDataFormatter");	
    
    List<Product> listProducts =productDataFormatter.getProductsFromDBForPids(productDataFormatter.getPropertyAsStringList(pids));
    JSONObject obj = new JSONObject();
    if(listProducts.size()>0){
    	
    
    
    List<ProductBean> listOrders= productDataFormatter.getProductBeans(listProducts);
    
    

    
    obj.put("page","1").put("total",listOrders.size()/10).put("records", listOrders.size());
    if(listOrders != null)
    {
        JSONArray empsArray = new JSONArray();
        JSONObject o;
        for(Iterator<ProductBean> iterator = listOrders.iterator(); iterator.hasNext();)
        {
     
     
        	ProductBean emp = (ProductBean)iterator.next();
        	o = new JSONObject();
            o.put("id", emp.getProductid()+"");
        
            
            ArrayList<String> myList= new ArrayList<String>();
            myList.add(emp.getProductid());
            myList.add(StringUtils.replace(emp.getImageDislayBlock(),"Clicked_See_At_Sears_From_Main_Page","Clicked_See_At_Sears_From_SaveAndCompare_Page"));
            myList.add(emp.getPrice());
            String kWh= ((Map<String, String>)emp.getAttribute()).get("Kilowatt Hrs. per Year");
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
   
    }

    
    return obj ;
}




	
}
