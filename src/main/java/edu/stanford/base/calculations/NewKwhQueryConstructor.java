package edu.stanford.base.calculations;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import edu.stanford.widget.dao.KWHCalculateDAO;
import edu.stanford.widget.model.KwhCalculationBean;

/**

 */
@Service("newKwhQueryConstructor")
public class NewKwhQueryConstructor {

	/** The Constant log. */
	private static final Log log = LogFactory.getLog(KWHCalculateDAO.class);
	
	/**
		 * @param contactBean
		 * @return
		 */
		public String constructQueryString(KwhCalculationBean contactBean) {
			StringBuffer paramString =new StringBuffer(500);
			String stmtString;
			StringBuffer selectString=new StringBuffer(500);;
			
			if (!StringUtils.isBlank(contactBean.getStyle())) {
				
				selectString.append("T");
				paramString.append(" type ='" +StringUtils.trim(contactBean.getStyle())+"'");
			}
			
			if (!StringUtils.isBlank(contactBean.getYear_id()) ) {
				if(paramString.length()>0){
					paramString.append(" and ");	
				}
				selectString.append("Y");
				
				paramString.append("  year_id ='" +StringUtils.trim(contactBean.getYear_id())+"'");
			}
			
			if (	!StringUtils.isBlank(contactBean.getSize_id())) {
				
				if(paramString.length()>0){
					paramString.append(" and ");	
				}
				selectString.append("S");
				paramString.append("  size_id ='" +StringUtils.trim(contactBean.getSize_id())+"'");
				
			}
			
			if (!StringUtils.isBlank(contactBean.getIceMaker())) {
				if(paramString.length()>0){
					paramString.append(" and ");	
				}
				selectString.append("I");
				paramString.append("  icemaker ='" +StringUtils.trim(contactBean.getIceMaker())+"'");
			}
			
			if (!StringUtils.isBlank(contactBean.getEnergyStar()) ) {
				if(paramString.length()>0){
					paramString.append(" and ");	
				}
				
				selectString.append("E");
				paramString.append(" EnergyStar ='" +StringUtils.trim(contactBean.getEnergyStar())+"'");
			}
			
			if(paramString.length()>0){
				stmtString = "SELECT distinct  "+selectString.toString()+"_m as output_kwh_m , "+selectString.toString()+"_c as output_kwh_sd  FROM kwhnew WHERE " + paramString.toString() ;
			}else{
				stmtString = "SELECT distinct  E_m as output_kwh_m,E_c as output_kwh_sd FROM kwhnew WHERE year_id ='12312'"  ;
			}
			
			log.info(stmtString);
			return stmtString;
		}
		
}

