/*package edu.stanford.base.calculations;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.stanford.widget.dao.KWHCalculateDAO;
import edu.stanford.widget.model.KwhCalculationBean;

*//**
 * This class creates the query from the input parameters from the user interface
 * and fetch data from the kwh table  
 * @version 1.1
 * 
 *//*
public class KwhQueryConstructor {

	*//** The Constant log. *//*
	private static final Log log = LogFactory.getLog(KWHCalculateDAO.class);
	
	*//**
		 * @param contactBean
		 * @return
		 *//*
		public String constructQueryString(KwhCalculationBean contactBean) {
			String paramString;
			String stmtString;
			String selectString;
			if (!StringUtils.isBlank(contactBean.getStyle()) &&
					StringUtils.isBlank(contactBean.getSize_id()) && 
					StringUtils.isBlank(contactBean.getYear_id()) &&
					StringUtils.isBlank(contactBean.getIceMaker()) && 
					 
					StringUtils.isBlank(contactBean.getEnergyStar()) ) {
				
				selectString=" style_m as output_kwh_m , style_sd as  output_kwh_sd";
				paramString=" style ='" +StringUtils.trim(contactBean.getStyle())+"'";
			}else
			if (!StringUtils.isBlank(contactBean.getStyle()) &&
					!StringUtils.isBlank(contactBean.getYear_id()) && 
					StringUtils.isBlank(contactBean.getSize_id()) &&
					StringUtils.isBlank(contactBean.getIceMaker()) && 
					 
					StringUtils.isBlank(contactBean.getEnergyStar()) ) {
				
				selectString="style_year_m  as output_kwh_m,style_year_sd as  output_kwh_sd";
				paramString=" year_id ='" +StringUtils.trim(contactBean.getYear_id())+"' and style='" +StringUtils.trim(contactBean.getStyle())+"'";
			}
			else
			if (!StringUtils.isBlank(contactBean.getYear_id()) &&
					!StringUtils.isBlank(contactBean.getSize_id()) && 
					!StringUtils.isBlank(contactBean.getStyle()) &&
					StringUtils.isBlank(contactBean.getIceMaker()) && 
					 
					StringUtils.isBlank(contactBean.getEnergyStar()) ) {
				
				selectString="style_year_size_m  as output_kwh_m , style_year_size_sd as  output_kwh_sd";
				paramString=" year_id ='" +StringUtils.trim(contactBean.getYear_id())
				+"' and size_id='" +StringUtils.trim(contactBean.getSize_id())+"'"
				+" and style='" +StringUtils.trim(contactBean.getStyle())+"'";
				
			}else
			if (!StringUtils.isBlank(contactBean.getYear_id()) &&
					!StringUtils.isBlank(contactBean.getSize_id()) && 
					!StringUtils.isBlank(contactBean.getStyle()) &&
					!StringUtils.isBlank(contactBean.getIceMaker()) && 
					 
					StringUtils.isBlank(contactBean.getEnergyStar()) ) {
				
				selectString="style_year_size_feat1_m  as output_kwh_m ,style_year_size_feat1_sd as  output_kwh_sd";
				paramString=" year_id ='" +StringUtils.trim(contactBean.getYear_id())
				+"' and size_id='" +StringUtils.trim(contactBean.getSize_id())+"'"
				+" and style='" +StringUtils.trim(contactBean.getStyle())+"'"
				+" and iceMaker='" +StringUtils.trim(contactBean.getIceMaker())+"'";
			}else
			if (!StringUtils.isBlank(contactBean.getYear_id()) &&
					!StringUtils.isBlank(contactBean.getSize_id()) && 
					!StringUtils.isBlank(contactBean.getStyle()) &&
					!StringUtils.isBlank(contactBean.getIceMaker()) && 
					!StringUtils.isBlank(contactBean.getEnergyStar()) ) {
				
				selectString=" style_year_size_feat1_ES_m  as output_kwh_m ,style_year_size_feat1_ES_sd as  output_kwh_sd ";
				paramString=" year_id ='" +StringUtils.trim(contactBean.getYear_id())
				+"' and size_id='" +StringUtils.trim(contactBean.getSize_id())+"'"
				+" and style='" +StringUtils.trim(contactBean.getStyle())+"'"
				+" and iceMaker='" +StringUtils.trim(contactBean.getIceMaker())+"'"
				+" and EnergyStar='" +StringUtils.trim(contactBean.getEnergyStar())+"'";
				
			}else{
				
				selectString=" style_m as output_kwh_m , style_sd as  output_kwh_sd";
				paramString=" style ='asdasdasda'";
				
			}
			
			
			stmtString = "SELECT distinct  "+selectString+" FROM test.kwh WHERE " + paramString ;
			log.info(stmtString);
			return stmtString;
		}
		
}

*/