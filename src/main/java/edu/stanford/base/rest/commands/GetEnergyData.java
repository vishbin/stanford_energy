package edu.stanford.base.rest.commands;

import com.google.gson.Gson;
import edu.stanford.base.config.BeanFactory;
import edu.stanford.widget.dao.KWHCalculateDAO;
import edu.stanford.widget.model.KwhCalculationBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;



/**
 *
 */

@Service("getEnergyData")
public class GetEnergyData extends HttpServlet {
	/** The Constant log. */
	private static final Log log = LogFactory.getLog(GetEnergyData.class);
	@Autowired @Qualifier("kWHCalculateDAO")
	private KWHCalculateDAO kWHCalculateDAO;
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		  String action = null;
		  String state = null;
		  String refType = null;
		  String manufactureYear = null;
		  String tvol = null;
		  String model = null;
		  String respFormat=null;
		  String xmlData = null;
		  String jsonData = null;
		  String respSource=null;String respFeature1Val=null;String respFeature2Val=null;String respEnergyStarVal=null;
		  if (req.getParameter("action") != null) action = req.getParameter("action");
		  if (req.getParameter("state") != null) state = req.getParameter("state");
		  if (req.getParameter("refType") != null) refType = req.getParameter("refType");	//private, public, shared
		  if (req.getParameter("manufactureYear") != null) manufactureYear = req.getParameter("manufactureYear");
		  if (req.getParameter("tvol") != null) tvol = req.getParameter("tvol");
		  if (req.getParameter("model") != null) model = req.getParameter("model");
		  if (req.getParameter("jsonData") != null) jsonData = req.getParameter("jsonData");
		  if (req.getParameter("respFormat") != null) respFormat = req.getParameter("respFormat");
		  if (req.getParameter("source") != null) respSource = req.getParameter("source");
		  
		  
		  if (req.getParameter("feature1Val") != null) respFeature1Val = req.getParameter("feature1Val");
		  if (req.getParameter("feature2Val") != null) respFeature2Val = req.getParameter("feature2Val");
		  if (req.getParameter("energyStarVal") != null) respEnergyStarVal = req.getParameter("energyStarVal");
		  
		 
		  
		  
		  log.info("action = " + action); log.info("state = " + state); log.info("refType = " + refType);
		  log.info("manufactureYear = " + manufactureYear);  log.info("tvol = " + StringUtils.replace(tvol," ","+"));
		  log.info("model = " + model);  log.info("jsonData = " + jsonData); log.info("respFormat = " + respFormat);
		  
		  
		  
		  log.info("respSource = " + respSource);  StringBuffer responseData = null;
		 if(StringUtils.isBlank(respSource)){
			 
			 
				KwhCalculationBean kwhCalculationBean= new KwhCalculationBean();
				kwhCalculationBean.setYear_id(manufactureYear);
				kwhCalculationBean.setEnergyStar(respEnergyStarVal);
				kwhCalculationBean.setFeature1(respFeature1Val);
				kwhCalculationBean.setIceMaker(respFeature1Val);
				kwhCalculationBean.setFeature2(respFeature2Val);
				
				if (StringUtils.equalsIgnoreCase(tvol, "25.5 cu. ft.")){
					kwhCalculationBean.setSize_id("25.5 cu. ft. & More");	
				}else{
					kwhCalculationBean.setSize_id(tvol);
				}
				
				
				
				kwhCalculationBean.setStyle(refType);
				kwhCalculationBean.setStateRate(state);
		
			    responseData = getDataFromDataBase(kwhCalculationBean);	 
		 }else{
			  responseData = getDataFromEnergyStar(resp, state, refType,manufactureYear, tvol);
		 }
		  
		 
		  resp.setContentType("text/json");
		  resp.getWriter().println(responseData);
	}
	
	/**
	 * Gets the data from data base.
	 *
	 * @param kwhCalculationBean the kwh calculation bean
	 * @return the data from data base
	 */
	private StringBuffer getDataFromDataBase(KwhCalculationBean kwhCalculationBean) {
		StringBuffer responseData=new StringBuffer();
		String responseStr=null;
		
		
		
		
		kWHCalculateDAO = (KWHCalculateDAO)BeanFactory.getInstance().getBean("kWHCalculateDAO");	
		
		
		try {
			
			kWHCalculateDAO.lookupKwhCalculationParams(kwhCalculationBean);
			
			
			if( kwhCalculationBean.getOutputKwhMed()!=0){
				//Double stateRate= (Double.valueOf(kwhCalculationBean.getStateRate())* kwhCalculationBean.getOutputKwhMed())/100;
				
				
				//responseStr="<br> Median: "+Double.toString(kwhCalculationBean.getOutputKwhMed())+ "  <br>Standard Dev : "+Double.toString(kwhCalculationBean.getOutputKwhSd());
				//responseStr="Annual Kwh : "+Math.round(kwhCalculationBean.getOutputKwhMed()) +"  (M)  Annual Costs : $" +Math.round(stateRate);
				//responseStr="Electricity Consumption of Your Refrigerator:"+Math.round(kwhCalculationBean.getOutputKwhMed()) +" kWh/yr" +"<a style=\"display:none\">Annual Costs : $" +Math.round(stateRate)+" </a><br>Number of Refrigerators that Fit Your Description: " +Math.round(kwhCalculationBean.getOutputKwhSd())+" Average Electricity Price in Your State: $" +Double.valueOf(kwhCalculationBean.getStateRate())/100+"/kWh"	;
				//(double)Math.round(value * 100000) / 100000
				
				if(StringUtils.isBlank(kwhCalculationBean.getStateRate())){
					kwhCalculationBean.setStateRate("11.7");
				}			
				double avgStateElecPrice=(double)Math.round(Double.valueOf(kwhCalculationBean.getStateRate())/100 * 1000) / 1000 ;
				responseStr=Math.round(kwhCalculationBean.getOutputKwhMed()) +"$" +avgStateElecPrice;
				//responseStr="Electricity Consumption of Your Refrigerator:"+Math.round(kwhCalculationBean.getOutputKwhMed()) +" kWh/yr <br>  Average Electricity Price in Your State: $" +avgStateElecPrice+"/kWh"	;
				
			}else{
				responseStr=" Please Select Above Options ";
			}
			
				
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
       
		log.info("Response = " + responseStr);
		 
		  responseData.append("{\"version\":\"1.0\",\"encoding\":\"UTF-8\",");
		  responseData.append("\"status\":{\"responseCode\": ");
		  responseData.append("\"" + 0 + "\"");
		  responseData.append(", \"responseMsg\": ");
		  responseData.append("\"" + "Success" + "\"");
		  responseData.append("}, ");  
		  responseData.append("\"feed\":{\"entry\":");
		  Gson gson = new Gson();
		  String jsonResponse = gson.toJson(responseStr);
		  responseData.append(jsonResponse.toString());
		  responseData.append("}}");
		  log.info("responseData = " + responseData);
		return responseData;
	}
	
	/**
	 * Gets the data from energy star.
	 *
	 * @param resp the resp
	 * @param state the state
	 * @param refType the ref type
	 * @param manufactureYear the manufacture year
	 * @param tvol the tvol
	 * @return the data from energy star
	 */
	private StringBuffer getDataFromEnergyStar(HttpServletResponse resp,
			String state, String refType, String manufactureYear, String tvol) {
		
		//String strURL="http://www.energystar.gov/index.cfm?fuseaction=refrig.calculator&which=4&rate=0.111&rconfig=Top+Freezer&screen=4&manu=1990-1992&tvol=19.0-21.4+Cubic+Feet&submit.x=112&submit.y=3&model=";
		String baseURL="http://www.energystar.gov/index.cfm?";
		String fuseactionParamName="fuseaction=";
		String fuseactionParamValue="refrig.calculator&which=4&";
		String rateParamName="rate=";
		String rateParamValue=state;
		String rconfigParamName="&rconfig=";
		String rconfigParamValue=refType+"+Freezer&screen=4&";
		if(StringUtils.equalsIgnoreCase(refType,"Side-by-Side")){
			rconfigParamValue=refType+"&screen=4&";
		}
		  
		
		
		
		String manuParamName="manu=";
		
		String manuParamValue=StringUtils.replace(manufactureYear," ","+")+"&";
		String tvolParamName="tvol=";
		
		String tvolParamValue=StringUtils.replace(tvol," ","+")+"&";
	
		String submitxParamName="submit.x";
		String submitxParamValue="112";
		
		String submityParamName="submit.y";
		String submityParamValue="3&";
		
		String modelParamName="model=";
		String modelParamValue="";
		
		String urlStr=baseURL+fuseactionParamName+fuseactionParamValue+rateParamName+rateParamValue+rconfigParamName+rconfigParamValue+manuParamName
		+manuParamValue+tvolParamName+tvolParamValue+submitxParamName+submitxParamValue+submityParamName+submityParamValue+modelParamName+modelParamValue;
		
		
		 log.info("urlStr = " + urlStr);
		
		
		StringBuffer response=new StringBuffer();
		 StringBuffer responseData = new StringBuffer();
		
		try {
            URL url = new URL(urlStr);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                // ...
            	//log.info("line = " + line);
            	response.append(line);
            }
            reader.close();

        } catch (MalformedURLException e) {
            // ...
        } catch (IOException e) {
            // ...
        }

       String responseStr=StringUtils.substringBetween(response.toString(), "<table width=80 class=\"yourinfo\" cellpadding=0 border=0 cellspacing=0>", "</table>");
		
       
       responseStr=StringUtils.substringAfterLast(responseStr, "27>");
       
       responseStr=" State Energy Rates :: "+responseStr;
       
       responseStr=StringUtils.replace(responseStr,"</td>			</tr>			<tr>				<td height=28>","    Annual Cost  :: ");
       
       responseStr=StringUtils.replace(responseStr,"</td>			</tr>			<tr>				<td height=31>","    Annual kWh :: ");
       responseStr=StringUtils.replace(responseStr,"</td>			</tr>","");
       
       responseStr=StringUtils.replace(responseStr,"			"," ");
       
       
       
       
		log.info("Response = " + responseStr);
		  resp.setContentType("text/json");
		  responseData.append("{\"version\":\"1.0\",\"encoding\":\"UTF-8\",");
		  responseData.append("\"status\":{\"responseCode\": ");
		  responseData.append("\"" + 0 + "\"");
		  responseData.append(", \"responseMsg\": ");
		  responseData.append("\"" + "Success" + "\"");
		  responseData.append("}, ");  
		  responseData.append("\"feed\":{\"entry\":");
		  Gson gson = new Gson();
		  String jsonResponse = gson.toJson(responseStr);
		  responseData.append(jsonResponse.toString());
		  responseData.append("}}");
		  log.info("responseData = " + responseData);
		return responseData;
	}
}
