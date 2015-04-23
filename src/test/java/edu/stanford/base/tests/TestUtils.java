/**
 * 
 */
package edu.stanford.base.tests;

import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 *
 */
public class TestUtils {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//http://localhost:8080/StanEnergy/gad001_for_testing_orig.html?placement={placement}creative={creative}network={network}
		//http://www.powerdownstanford.org:8080/StanEnergy/gad001.html?keyword=energy%20savings%20refrigerators&placement=&creative=10366294024&network=g
		
		//http://www.powerdownstanford.org:8080/StanEnergy/gad001.html?keyword=energy savings refrigeratorsplacement=creative=10366294024network=g
		
		String origURL="http://www.powerdownstanford.org:8080/StanEnergy/gad001.html?keyword=energy savings refrigeratorsplacement=creative=10366294024network=g";
		
		System.out.println("origURL ::"+origURL);
		try {
			System.out.println("URLEncoder origURL ::"+URLDecoder.decode(origURL,"UTF-8"));
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		
		
		String keywords=StringUtils.substringBetween(origURL, "keyword=","placement=");
		String placement=StringUtils.substringBetween(origURL, "placement=", "creative=");
		String creative=StringUtils.substringBetween(origURL, "creative=", "network=");
		String network=StringUtils.substringAfterLast(origURL, "network=");
		
		String recipe=StringUtils.substring(origURL, origURL.indexOf("gad00")+5,origURL.indexOf("gad00")+6);
		
		
		System.out.println("placement:  "+placement);
		System.out.println("keyword:  "+keywords);
		System.out.println("creative:   "+creative);
		System.out.println("network:   "+network);
		System.out.println("recipe:   "+recipe);

	}

}
