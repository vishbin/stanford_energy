package edu.stanford.base.rest.commands;

import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import edu.stanford.base.config.BeanFactory;
import edu.stanford.base.external.GoogleFusionOAuth2;
import edu.stanford.base.monitoring.SendMailService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.UUID;




/**
 */
@Service("storeUserDataRestService")
@Path("/storeuserdata/")
public class StoreUserDataRestService {
	
	@Autowired
	public	Properties properties;
	
	@Context HttpServletRequest request;
	/** The logger. */
	private Logger logger = Logger.getLogger(StoreUserDataRestService.class.getName());
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss.SSS");	
	/**
	 *
	 * @param cookieId the cookie id
	 * @param currentApp the current app
	 * @param futureApp the future app
	 * @param searchResults the search results
	 * @return the jSON object
	 * @throws JSONException the jSON exception
	 * 	+"&placement="+getURLParameters('placement')
	+"&creative="+getURLParameters('creative')
	+"&keyword="+getURLParameters('keyword')
	+"&network="+getURLParameters('network');
	 */
	@POST
	public JSONObject storeUserdata(  @QueryParam("cookieId") String cookieId,
			@QueryParam("currentApp") String currentApp,
			@QueryParam("futureApp") String futureApp,
			@QueryParam("searchResults") String searchResults,
			@QueryParam("selectedPID") String selectedPID,
			@QueryParam("origURL") String origURL,
			@QueryParam("type") String type,
			@QueryParam("pids") String pids,
			@QueryParam("placement") String placement,
			@QueryParam("creative") String creative,
			@QueryParam("keyword") String keyword,
			@QueryParam("network") String network
			
			)  throws JSONException {
	
		logQueryParams(cookieId, currentApp, futureApp, searchResults,
				selectedPID, origURL, type, placement, creative, keyword,
				network);
		
		String recipe=StringUtils.substring(origURL, origURL.indexOf("gad00")+5,origURL.indexOf("gad00")+6);
		logger.info("recipe:   "+recipe);
		
		JSONObject o= new JSONObject();
		String ipAddress;
		String query = null;
		String details = null;
		
		if(!StringUtils.isNumeric(recipe)){
			recipe="1";
		}
		
		String timeStamp = sdf.format(Calendar.getInstance().getTime());
		String rankedPids="";

		if(StringUtils.equalsIgnoreCase(type,"Entry_SaveAndCompare_Page") || StringUtils.equalsIgnoreCase(type,"Before_Going_To_SaveAndCompare_Page")
				||  StringUtils.equalsIgnoreCase(type,"Closing_SaveAndCompare_Page")){
			
			rankedPids=selectedPID;
			selectedPID="";
			logger.info("rankedPids ::"+rankedPids);
		}
		
		if(StringUtils.isBlank(cookieId) || StringUtils.equalsIgnoreCase(cookieId,"null")){
			
			ipAddress=request.getHeader("X-Forwarded-For");
			if(StringUtils.isBlank(ipAddress)){
				ipAddress =request.getRemoteAddr();	
			}
			cookieId=	UUID.randomUUID().toString()+"_"+StringUtils.remove(ipAddress,", ");
			logger.debug("Generated New Cookie:"+cookieId);
		}
	
		try {
			
			 details = getRequestDetails();
		} catch (Exception e1) {
			
			logger.error(e1);
			e1.printStackTrace();
			ipAddress="NOT_KNOWN";
		}
		
		tryPersistTrackingData(cookieId, currentApp, futureApp, searchResults,
				selectedPID, origURL, type, pids, placement, creative, keyword,
				network, recipe, timeStamp, rankedPids, o, query, details);
		
		return o;

	}

	/**
	 * @param cookieId
	 * @param currentApp
	 * @param futureApp
	 * @param searchResults
	 * @param selectedPID
	 * @param origURL
	 * @param type
	 * @param placement
	 * @param creative
	 * @param keyword
	 * @param network
	 */
	private void logQueryParams(String cookieId, String currentApp,
			String futureApp, String searchResults, String selectedPID,
			String origURL, String type, String placement, String creative,
			String keyword, String network) {
		logger.info("cookieId::"+cookieId);
		logger.info("currentApp::"+currentApp);
		logger.info("futureApp ::"+futureApp);
		logger.info("searchResults ::"+searchResults);
		logger.info("selectedPID ::"+selectedPID);
		logger.info("origURL ::"+origURL);
		logger.info("type:"+type);
		logger.info("placement:  "+placement);
		logger.info("keyword:  "+keyword);
		logger.info("creative:   "+creative);
		logger.info("network:   "+network);
	}

	/**
	 * @param cookieId
	 * @param currentApp
	 * @param futureApp
	 * @param searchResults
	 * @param selectedPID
	 * @param origURL
	 * @param type
	 * @param pids
	 * @param placement
	 * @param creative
	 * @param keyword
	 * @param network
	 * @param recipe
	 * @param timeStamp
	 * @param rankedPids
	 * @param o
	 * @param query
	 * @param details
	 * @throws JSONException
	 */
	private void tryPersistTrackingData(String cookieId, String currentApp,
			String futureApp, String searchResults, String selectedPID,
			String origURL, String type, String pids, String placement,
			String creative, String keyword, String network, String recipe,
			String timeStamp, String rankedPids, JSONObject o, String query,
			String details) throws JSONException {
		int retryCount = 0;
        while (retryCount < 3){
		try {
			//callPostOrderService( cookieId+"_"+ipAddress, currentApp,futureApp,searchResults,selectedPID,origURL,type,pids,placement,creative,keywords,network);
		
			query = persistTrackingData(cookieId, currentApp, futureApp,
					searchResults, selectedPID, origURL, type, pids, placement,
					creative, keyword, network, recipe, timeStamp, rankedPids,
					details);	
		
			
			o.put("cookieId", cookieId);
			break;
		
		} catch (Exception e) {
			try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e1) {
				logger.error(e1);	
			}	
			logger.warn("retrying to  store in Google fusion  "+retryCount +" time "+details+query,e);
			
			if (retryCount == 2){
            	logger.error("retry to  store in Google fusion  "+retryCount +" times  Now ..sending error email "+details+query,e);
    			SendMailService.sendEmail( cookieId+"   "+timeStamp +"  Error Occured while inserting in google fusion tables...",e,query+details);
    			o.put("status", "failed");	
            }
			retryCount++;
		}
        }
	}

	/**
	 * @return
	 */
	private String getRequestDetails() {
		String details;
		details= " 		\n LocalAddr::" + request.getLocalAddr()
				 + "		\n RemoteAddr::"+ request.getRemoteAddr()
				 + "		\n RemoteAddrForwarded::"+ request.getHeader("X-Forwarded-For")
				 
				   
				 + "		\n RemoteHost::"+ request.getRemoteHost()
				 + "		\n RemotePort::"+ request.getRemotePort()
				 + "		\n RemoteUser::"+ request.getRemoteUser()
				 + "		\n RequestedSessionId::"+ request.getRequestedSessionId()
				 + "		\n RequestURI::"+ request.getRequestURI()
				 + "		\n ServerName::"+ request.getServerName()
				+ "		\n AuthType::" + request.getAuthType()
				+ "		\n ContextPath::" + request.getContextPath()
				+ "		\n ContentType::" + request.getContentType()
				+ "		\n LocalName::" + request.getLocalName()
				+ "		\n LocalPort::" + request.getLocalPort()
				+ "		\n LocalAddr::" + request.getLocalAddr()
				+ "		\n getMethod::" + request.getMethod()
				+ "		\nPathInfo::"+ request.getPathInfo() 
				// + "\nPathTranslated    ::   "	+ request.getPathTranslated() 
				+ "		\nProtocol::"+ request.getProtocol() 
				+ "		\n QueryString::"+ request.getQueryString() 
				+ "		\n RequestURL::"+ request.getRequestURL();
		logger.info(details);
		return details;
	}

	/**
	 * @param cookieId
	 * @param currentApp
	 * @param futureApp
	 * @param searchResults
	 * @param selectedPID
	 * @param origURL
	 * @param type
	 * @param pids
	 * @param placement
	 * @param creative
	 * @param keyword
	 * @param network
	 * @param recipe
	 * @param timeStamp
	 * @param rankedPids
	 * @param details
	 * @return
	 * @throws InterruptedException
	 * @throws AuthenticationException
	 * @throws IOException
	 * @throws ServiceException
	 */
	private String persistTrackingData(String cookieId, String currentApp,
			String futureApp, String searchResults, String selectedPID,
			String origURL, String type, String pids, String placement,
			String creative, String keyword, String network, String recipe,
			String timeStamp, String rankedPids, String details)
			throws InterruptedException, AuthenticationException, IOException,
			ServiceException {
		String query=null;
		
			
			logger.info("::::::TYPE FLOW :::::"+type);
		
			
			String tableId = getTableId();
		/*	query="INSERT INTO "+tableId+" (UserId,TimeStamp,CurrentAppSel,FutureAppSel,SearchResults,Remarks,click_buy_now_product,origURL,type,savedpids,source,GoogleAd_id,keywords,network,recipe,ranked_products) VALUES	" +
					"('"+cookieId+"','"+Calendar.getInstance().getTime()+"','"+currentApp+"','"+futureApp+"','"+searchResults+"','THIS IS A TEST AT "+timeStamp+StringUtils.remove(details,"\n")+"','"+selectedPID+"','"+origURL+"','"+type+"','"+pids+"','"+placement+"','"+creative+"','"+keyword+"' ,'"+network+"','"+recipe+"' ,'"+rankedPids+"')";
			*/
			query="INSERT INTO "+tableId+" (UserId,TimeStamp,CurrentAppSel,FutureAppSel,SearchResults,Remarks,click_buy_now_product,origURL,type,savedpids,source,GoogleAd_id,keywords,network,recipe,ranked_products) VALUES	" +
					"('"+cookieId+"','"+Calendar.getInstance().getTime()+"','"+currentApp+"','"+futureApp+"','"+searchResults+"','"+timeStamp+StringUtils.remove("","\n")+"','"+selectedPID+"','"+origURL+"','"+type+"','"+pids+"','"+placement+"','"+creative+"','"+keyword+"' ,'"+network+"','"+recipe+"' ,'"+rankedPids+"')";
			
			
			logger.info(query); 
			
		/*	FusionApiService fusionApiService =new FusionApiService("stanfordenergy@gmail.com","energy@123"); 
			fusionApiService.runUpdate(query);*/
			GoogleFusionOAuth2 googleFusionOAuth2 = GoogleFusionOAuth2.getInstance();
			System.out.println("query>>>>>>>>>>>>>>>>>>>>>>"+query);
			googleFusionOAuth2.runQuery(query);

		
		return query;
	}

	private String getTableId() {
		
		String tableId="1J6Xjb6YZjtbGLWx4U8lf2Ar2Ruj6QpYFXIZExXc";
	//	String tableId="3442137";//1152588 //3442137 =qa //3442136 =dev
		properties = (Properties)BeanFactory.getInstance().getBean("properties");
		 
		tableId=properties.getProperty("google_table_id");
		logger.debug("Google Fusion tableId from properties ::: "+tableId);
		return tableId;
	}
}