/*
 * Copyright (C) 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.stanford.base.batch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;


public class RawCollectionsExample {
//  public static final String json = "['hello',5,{name:'GREETINGS',source:'guest'}]";
	  public static final String json1 = "['hello',5,{\"imageid\": \"04651103000\",\"numreview\": \"12\",\"cutprice\": \"1269.99\",\"storeorigin\": \"S\",\"beantype\": \"ProductBean\",\"directdelivery\": \"true\",\"mfgpartnumber\": \"5110\",\"sellercount\": \"0\",\"catentryid\": \"42741860\",\"rating\": \"4.00\",\"promoind\": \"1\",\"ksnvalue\": \"04651103000\",\"partnumber\": \"04651103000P\",\"automotivedivision\": \"false\",\"skupartnumber\": \"04651103000\",\"image\": \"04651103000\",\"spuind\": \"true\",\"displayprice\": \"952.49\",\"pbtype\": \"NONVARIATION\",\"saleindicator\": \"1\",\"clearanceindicator\": \"0\",\"resind\": \"1\",\"stockindicator\": \"1\",\"name\": \"Kenmore 25.1 cu. ft. Side-by-Side Refrigerator w/ Ice/Water Dispenser\",\"defaultfullfillment\": \"DDC\",\"brandname\": \"Kenmore\" }]";
	
	  public static final String json = "[{ \"mercadoresult\": { \"products\": {\"product\": [ true,[{    \"imageid\": \"04651103000\",    \"numreview\": \"12\",    \"cutprice\": \"1269.99\",    \"storeorigin\": \"S\",    \"beantype\": \"ProductBean\",    \"directdelivery\": \"true\",    \"mfgpartnumber\": \"5110\",    \"sellercount\": \"0\",    \"catentryid\": \"42741860\",    \"rating\": \"4.00\",    \"promoind\": \"1\",    \"ksnvalue\": \"04651103000\",    \"partnumber\": \"04651103000P\",    \"automotivedivision\": \"false\",    \"skupartnumber\": \"04651103000\",    \"image\": \"04651103000\",    \"spuind\": \"true\",    \"displayprice\": \"952.49\",    \"pbtype\": \"NONVARIATION\",    \"saleindicator\": \"1\",    \"clearanceindicator\": \"0\",    \"resind\": \"1\",    \"stockindicator\": \"1\",    \"name\": \"Kenmore 25.1 cu. ft. Side-by-Side Refrigerator w/ Ice/Water Dispenser\",    \"defaultfullfillment\": \"DDC\",    \"brandname\": \"Kenmore\"},{    \"imageid\": \"04639012000\",    \"numreview\": \"15\",    \"cutprice\": \"899.99\",    \"storeorigin\": \"S\",    \"beantype\": \"ProductBean\",    \"directdelivery\": \"true\",    \"mfgpartnumber\": \"ED5KVEXV\",    \"sellercount\": \"0\",    \"catentryid\": \"42188473\",    \"rating\": \"4.00\",    \"promoind\": \"0\",    \"ksnvalue\": \"04639012000\",    \"partnumber\": \"04639012000P\",    \"automotivedivision\": \"false\",    \"skupartnumber\": \"04639012000\",    \"image\": \"04639012000\",    \"spuind\": \"true\",    \"displayprice\": \"809.99\",    \"pbtype\": \"NONVARIATION\",    \"saleindicator\": \"1\",    \"clearanceindicator\": \"0\",    \"resind\": \"1\",    \"stockindicator\": \"1\",    \"name\": \"Whirlpool 25.1 cu. ft. Side-By-Side Refrigerator w/ Ice and Water Dispenser (ED5KVEXVQ)\",    \"defaultfullfillment\": \"DDC\",    \"brandname\": \"Whirlpool\"}}                ]            ]        },        \"productcount\": \"309\",        \"status\": \"0\",        \"errormessage\": \"\"    },    \"ApiTracking\": [        \"Server: BETA-SERVER-3|Tracking ID: {1321044408656}|API Client Session Key: null|Time : Fri Nov 11 14:46:48 CST 2011\"    ]}";	

	  
	  
	  
  @SuppressWarnings("unchecked")
  public static void main(String[] args) throws IOException, JsonParseException, URISyntaxException {
	
	
	String[] subcatArr={"Side-by-Side+Refrigerators","Compact+Refrigerators",
			"Freezerless+Refrigerators",
			"French+Door+Refrigerators",
			//"Refrigerators Parts & Accessories",
			
			"Single+Door+Bottom+Freezer+Refrigerators",
			"Top+Freezer+Refrigerators"};
	
	/*//Freezerless Refrigerators
	//
	Compact Refrigerators
	Freezerless Refrigerators
	French Door Refrigerators
	Refrigerators Parts & Accessories
	Side-by-Side Refrigerators
	Single Door Bottom Freezer Refrigerators
	Top Freezer Refrigerators*/
	  
	for (int i = 0; i < subcatArr.length; i++) {
		String subcat = subcatArr[i];
		System.out.println(subcat);
		pullSearsProducts(subcat);	
	}
	
    //Product prod = gson.fromJson(prodArray.get(1), Product.class);
    
    
    
   /* String message = gson.fromJson(array.get(0), String.class);
    String number = gson.fromJson(array.get(1), String.class);
    Event event = gson.fromJson(array.get(2), Event.class);
    System.out.printf("Using Gson.fromJson() to get: %s, %s, %s", message, number, event);*/
  }




/**
 * @param subcat
 * @throws IOException
 * @throws HttpException
 * @throws JsonParseException
 * @throws URISyntaxException 
 */
public static void pullSearsProducts(String subcat) throws IOException,
		HttpException, JsonParseException, URISyntaxException {
	StringBuffer reponse= new StringBuffer();
	  //http://api.developer.sears.com/v1/productsearch?apikey=09b9aeb25ff985a9c85d877f8a9c4dd9&store=Sears&verticalName=Appliances&categoryName=Refrigerators&searchType=category&productsOnly=1&contentType=json
	  //String request = "http://api.developer.sears.com/v1/productsearch?apikey=09b9aeb25ff985a9c85d877f8a9c4dd9&store=Sears&verticalName=Appliances&categoryName=Refrigerators&subCategoryName=Side-by-Side+Refrigerators&searchType=subcategory&productsOnly=1&endIndex=1000&startIndex=1&contentType=json";
	  String request = "http://api.developer.sears.com/v1/productsearch?apikey=09b9aeb25ff985a9c85d877f8a9c4dd9&store=Sears&verticalName=Appliances&categoryName=Refrigerators&subCategoryName="
	  +subcat+
	  "&searchType=subcategory&productsOnly=1&contentType=json&endIndex=1000&startIndex=1";
	  
	  
	  URI uri = new URI(request);
	  URL url = uri.toURL();
	  //Compact+Refrigerators
        System.out.println(url);
        HttpClient client = new HttpClient();
        GetMethod method = new GetMethod(request);
        
        // Send GET request
        int statusCode = client.executeMethod(method);
        
        if (statusCode != HttpStatus.SC_OK) {
        	System.err.println("Method failed: " + method.getStatusLine());
        }
        InputStream rstream = null;
        
        // Get the response body
        rstream = method.getResponseBodyAsStream();
        
        // Process the response from Yahoo! Web Services
        BufferedReader br = new BufferedReader(new InputStreamReader(rstream));
        String line;
        while ((line = br.readLine()) != null) {
        	reponse.append(line);
            System.out.println(line);
        }
        br.close();
    Gson gson = new Gson();
/*   // gson.registerTypeAdapter(Event.class, new MyInstanceCreator());
    Collection collection = new ArrayList();
    collection.add("hello");
    collection.add(5);
    collection.add(new Event("GREETINGS", "guest"));
    String json2 = gson.toJson(collection);
    System.out.println("Using Gson.toJson() on a raw collection: " + json2);*/
    JsonParser parser = new JsonParser();
    //JsonArray array = parser.parse(json1).getAsJsonArray();
    
    String products=StringUtils.remove(reponse.toString(), "{\"mercadoresult\":{\"products\":{\"product\":[true,");
    //System.out.println(products);
    String productsList=StringUtils.substring(products, 0,StringUtils.indexOf(products, "productcount")-3);
   // System.out.println(productsList);
    productsList="["+StringUtils.replaceOnce(productsList, "}}]]","}]]");
    //System.out.println(productsList);
    List<SearsProduct> prodList=new ArrayList<SearsProduct>();
    
   // Reader reader = new InputStreamReader(productsList);
    Gson gson1 = new GsonBuilder().create();
    JsonArray array1 = parser.parse(productsList).getAsJsonArray();
    
    JsonArray prodArray=(JsonArray)array1.get(0);
    	
   // prodList= gson1.fromJson(array1.get(2), ArrayList.class);
    
    for (JsonElement jsonElement : prodArray) {
    	
    	prodList.add(gson.fromJson(jsonElement, SearsProduct.class));
    	//System.out.println(gson.fromJson(jsonElement, SearsProduct.class));
    	
	}
    
    PullSearsProductsDAO pullSearsProductsDAO= new PullSearsProductsDAO();
    
    try {
		pullSearsProductsDAO.pullAllProducts(prodList);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}