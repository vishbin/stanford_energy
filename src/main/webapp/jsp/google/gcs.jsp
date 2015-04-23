<!--

/*#
# Copyright 2010 Google Inc. All Rights Reserved.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
# This code is not supported by Google
#
*/
-->

<!--
Sample .jsp code for Google Commerce Search to submit ShoppingAPI-Search queries 
 then parse out the responses for display. Shows additive filtering, 
 page navigation, spelling suggestions and promotions.

Deploy on any servlet-engine (eg Tomcat) and enter in your GCS cxnumber, API key
https://code.google.com/apis/console/
http://www.google.com/cse/commercesearch/manage

--->

<%@ page import='java.util.*' %>
<%@ page import='java.io.*' %>
<%@ page import='java.net.*' %>
<%@ page import='javax.xml.parsers.*' %>
<%@ page import='org.w3c.dom.*' %>
<%@ page import='org.xml.sax.*' %>
<%@ page import='java.security.*' %>
<%@ page import='java.math.*' %>
<%@ page import='java.util.regex.*' %>
<%@ page import='javax.xml.xpath.*' %>
<%@ page import='javax.xml.namespace.*' %>
<%@ page import='javax.xml.parsers.*' %>


<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page session="true" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
    <title>GCS Search</title>

	<script language="javascript"> 
	//from http://blog.movalog.com/a/javascript-toggle-visibility/
	// some script to toggle the visibility of the snippet item details
	var state = 'none';
	function showhide(id) {
	var e = document.getElementById(id);
	if(e.style.display == 'block')
		e.style.display = 'none';
	else
		e.style.display = 'block';
	}
	</script>

    </head>

<%

   String inbound_querystring =  request.getQueryString();
   if (request.getQueryString() != null)
      inbound_querystring =  new String(request.getQueryString().getBytes("ISO-8859-1"), "UTF-8");
   String outbound_shopping = "";
   String html_spelling_suggestion = "";
   String html_promotions = "";
   String remove_filters = "";
   String totalResults = "";
   String page_nav = "";
   String html_attributes = "";
   String html_results = "";
   String devkey = null;
   String cx = request.getParameter("cx");
   String q = request.getParameter("q");
   String startIndex = request.getParameter("startIndex");
   String restrictby = request.getParameter("restrictBy");
   String orderby = request.getParameter("orderBy");
   String sortorder = request.getParameter("sortOrder");
   String country = "";
   long time_taken = 0;

   // if country code exists in previous session, use it
   if (session.getAttribute("country") != null)
   {
      country = (String)session.getAttribute("country");
   }

   // if country parameter is given, we will use it instead 
   if (request.getParameter("country") != null)
   {
      country = (String)request.getParameter("country");
      if (country=="")
        country="us";
      session.setAttribute( "country", country);
   }

   // if no country code is found, we will default it to us
   if (country.equals(""))
   {
      country = "us";
      session.setAttribute( "country", country);
   }

   String shopping_base_query = "https://www.googleapis.com/shopping/search/v1/cx:" + cx + "/products?facets.enabled=true&country=" + country + "&maxResults=10&facets.useGcsConfig=true&facets.discover=20:10&alt=atom&spelling.enabled=true&promotions.enabled=true&promotions.useGcsConfig=true";

   if (q != null)
     q = "&q=" + URLEncoder.encode(q, "UTF-8");
   else
     q="";


   boolean cx_is_null = false;
   if (cx == null)
   {
	out.println("<h3>cx parameter cannot be null</h3>");
	out.flush();
	cx_is_null = true;
	cx = "";
   }
   
   devkey = (String)session.getAttribute("devkey");
   boolean dev_key_is_null = false;
   if (devkey == null && request.getParameter("devkey")==null)
   {
	out.println("<h3>DeveloperKey parameter cannot be null</h3>");
	out.flush();
	dev_key_is_null = true;
	devkey = "";
   }
   else
   {
	devkey = request.getParameter("devkey");
	if (devkey == null)
        	devkey = (String)session.getAttribute( "devkey"); 	
	else
	  session.setAttribute( "devkey", devkey );
   }


   if (startIndex != null)
     startIndex = "&startIndex=" + startIndex;
   else
     startIndex = "";

   if (restrictby != null)
     restrictby = "&restrictBy=" + URLEncoder.encode(restrictby, "UTF-8");
   else
     restrictby = "";

   if (orderby != "")
   {
     orderby = "";
     sortorder = "";
   }
   
   outbound_shopping = shopping_base_query + (startIndex + q + restrictby + orderby + sortorder) + "&key=" + devkey;
   if (!cx_is_null || !dev_key_is_null) 
   try
   {
	    URL url = new URL(outbound_shopping);
    	    Date starttime = new Date();
	    HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
	    InputStream inputStream = httpConnection.getInputStream();
	    Date endtime = new Date();
	    time_taken = endtime.getTime() - starttime.getTime();

	    DocumentBuilderFactory docBF = DocumentBuilderFactory.newInstance();
	    DocumentBuilder docBuilder = (DocumentBuilder) docBF.newDocumentBuilder();
	    Document doc = docBuilder.parse(inputStream);
	    doc.getDocumentElement().normalize();

		NamespaceContext ctx = new NamespaceContext()
		{
			public String getNamespaceURI(String prefix) {
				String uri;
				if (prefix.equals("openSearch"))
					uri = "http://a9.com/-/spec/opensearchrss/1.0/";
				else if (prefix.equals("s"))
					uri = "http://www.google.com/shopping/api/schemas/2010";
				else if (prefix.equals("gd"))
					uri = "http://schemas.google.com/g/2005";
				else if (prefix.equals("atom"))
					uri = "http://www.w3.org/2005/Atom";
				else
					uri = null;
				return uri;
			}
			public Iterator getPrefixes(String val) {
				return null;
			}         
			public String getPrefix(String uri) {
				return null;
			}
		};

            XPathFactory xpathFactory = XPathFactory.newInstance();
	    XPath xpath = xpathFactory.newXPath();
	    xpath.setNamespaceContext(ctx);

	    Node n_results = (Node) xpath.evaluate("/feed/totalResults", doc, XPathConstants.NODE);
	    if (n_results != null)
	       	totalResults =  n_results.getTextContent();

	        Node n_spelling = (Node) xpath.evaluate("/feed/spelling/suggestion", doc, XPathConstants.NODE);
	        if (n_spelling != null)
		{
			String  mod_qparam = "";
			Map  parms = request.getParameterMap();
			String correction = n_spelling.getTextContent();
			for (Iterator iterator = parms.entrySet().iterator(); iterator.hasNext();)  {  
			   Map.Entry entry = (Map.Entry) iterator.next();
			   String[] values = (String[]) entry.getValue();
			   if (entry.getKey().equals("q"))
				if (mod_qparam.equals(""))
					mod_qparam = mod_qparam + "q=" + correction;
				else
					mod_qparam = mod_qparam +  "&" + "q=" + correction;
			   else
				if (mod_qparam.equals(""))
					mod_qparam = mod_qparam +  entry.getKey() + "=" + values[0];
				else
					mod_qparam = mod_qparam +  "&" + entry.getKey() + "=" + values[0];
			 }  
			html_spelling_suggestion  += "<font color='blue'>Did you mean </font><a href='gcs.jsp?" + mod_qparam + "'>" + correction + "?</a>";
	        }

	        NodeList nl_promotions = (NodeList) xpath.evaluate("/feed/promotions/promotion", doc, XPathConstants.NODESET);
		    
	        ArrayList<Promotion> lst_promotions = new ArrayList<Promotion>();
	        if (nl_promotions.getLength() > 0)
	        {
	        	for (int i=0; i< nl_promotions.getLength(); i++)
	        	{
	        		Node n_promotion = nl_promotions.item(i);
	        		String pname =n_promotion.getAttributes().getNamedItem("name").getNodeValue();
	        		String pdesc =n_promotion.getAttributes().getNamedItem("description").getNodeValue();
	        		String pimagelink =n_promotion.getAttributes().getNamedItem("imageLink").getNodeValue();
	        		String plink =n_promotion.getAttributes().getNamedItem("link").getNodeValue();
	        		Promotion p = new Promotion(pname,pdesc,pimagelink,plink);
	        		lst_promotions.add(p);
	        	}
	        }

	
	        ArrayList<Entry> lst_entry = new ArrayList<Entry>();
	        ArrayList<Facet> lst_facet = new ArrayList<Facet>();
	        NodeList nl_product = (NodeList) xpath.evaluate("/feed/entry/product", doc, XPathConstants.NODESET);
	        if (nl_product.getLength() > 0)
	        {
	        	for (int i=0; i< nl_product.getLength(); i++)
	        	{
	        		Node n_entry = nl_product.item(i);
	        		NodeList nl_entry_nodes = n_entry.getChildNodes();
	        		Entry e = new Entry();
	        		for (int j=0; j<nl_entry_nodes.getLength(); j++)
	        		{
	        			Node n_attribute = nl_entry_nodes.item(j);
	        			if (n_attribute.getNodeName().equals(Entry.GOOGLE_ID))
	        				e.addAttribute(Entry.GOOGLE_ID, Entry.TYPE_TEXT, n_attribute.getTextContent());
	        			else if (n_attribute.getNodeName().equals(Entry.PROVIDER_ID))
	        				e.addAttribute(Entry.PROVIDER_ID, Entry.TYPE_TEXT, n_attribute.getTextContent());
	        			else if (n_attribute.getNodeName().equals(Entry.TITLE))
	        				e.addAttribute(Entry.TITLE, Entry.TYPE_TEXT, n_attribute.getTextContent());
	        			else if (n_attribute.getNodeName().equals(Entry.LINK))
	        				e.addAttribute(Entry.LINK, Entry.TYPE_TEXT, n_attribute.getTextContent());
	        			else if (n_attribute.getNodeName().equals(Entry.DESC))
	        				e.addAttribute(Entry.DESC, Entry.TYPE_TEXT, n_attribute.getTextContent());
	        			else if (n_attribute.getNodeName().equals("s:inventories"))
	        			{
						Node ninventory = (Node) xpath.evaluate("*", n_attribute, XPathConstants.NODE);
						String channel = ninventory.getAttributes().getNamedItem("channel").getNodeValue();
						Node nprice = (Node)xpath.evaluate("*", ninventory, XPathConstants.NODE);
						String currency = nprice.getAttributes().getNamedItem("currency").getNodeValue();
						String price = nprice.getTextContent();
						e.addAttribute(Entry.PRICE, Entry.TYPE_TEXT, price);
	        			}
	        			else if (n_attribute.getNodeName().equals("s:images"))
	        			{
						Node image = (Node) xpath.evaluate("*", n_attribute, XPathConstants.NODE);
	        				e.addAttribute(Entry.IMAGE_LINK, Entry.TYPE_TEXT, image.getAttributes().getNamedItem("link").getNodeValue());
	        			}
	        			else if (n_attribute.getNodeName().equals("s:attributes"))
	        			{
	        				NodeList nl_facets = (NodeList) xpath.evaluate("*", n_attribute, XPathConstants.NODESET); 
	        				for (int ql=0; ql<nl_facets.getLength();ql++)
	        				{
	        					Node facet = nl_facets.item(ql);
	        					String facet_name = facet.getAttributes().getNamedItem("name").getNodeValue();
	        					String facet_type = facet.getAttributes().getNamedItem("type").getNodeValue();
	        					String facet_value = facet.getTextContent();	
	        					e.addAttribute(facet_name, facet_type,facet_value);
	        				}	
	        			}

	        		}
	        		lst_entry.add(e);
	        	}

		        NodeList nl_facet = (NodeList) xpath.evaluate("/feed/facets/facet", doc, XPathConstants.NODESET);
		        if (nl_facet.getLength() > 0)
		        {	
				for (int i=0; i< nl_facet.getLength(); i++)
				{
					Node n_facet = nl_facet.item(i);
					Facet f = null;
				 	if (n_facet.getAttributes().getNamedItem("name") != null)
					{
						String name = n_facet.getAttributes().getNamedItem("name").getNodeValue();
						String type = n_facet.getAttributes().getNamedItem("type").getNodeValue();
						String count = n_facet.getAttributes().getNamedItem("count").getNodeValue();
						String unit = null;
						if (n_facet.getAttributes().getNamedItem("unit") != null)
							unit = n_facet.getAttributes().getNamedItem("unit").getNodeValue();
						f = new   Facet(name, type, count, unit);
					}
					else
					{
						String name = n_facet.getAttributes().getNamedItem("property").getNodeValue();
						String count = n_facet.getAttributes().getNamedItem("count").getNodeValue();
						f = new Facet(name, count);
					}
					NodeList nl_buckets = (NodeList) xpath.evaluate("*", n_facet, XPathConstants.NODESET); 
					for (int j=0; j<nl_buckets.getLength(); j++)
					{
						Node n_bucket = nl_buckets.item(j);
						if (n_bucket.getAttributes().getNamedItem("value") != null)
						{
							String value = null;
							String count = null;
							value = n_bucket.getAttributes().getNamedItem("value").getNodeValue();
							count = n_bucket.getAttributes().getNamedItem("count").getNodeValue();
							f.addDiscreteBucket(value,count);
						}
						else
						{
							String max = null;
							String min = null;
							String minExclusive = null;
							String maxExclusive = null;
							String count = null;
							if (n_bucket.getAttributes().getNamedItem("max") != null)
								max = n_bucket.getAttributes().getNamedItem("max").getNodeValue();
							if (n_bucket.getAttributes().getNamedItem("min") != null)
								min = n_bucket.getAttributes().getNamedItem("min").getNodeValue();
							if (n_bucket.getAttributes().getNamedItem("maxExclusive") != null)
								maxExclusive = n_bucket.getAttributes().getNamedItem("maxExclusive").getNodeValue();
							if (n_bucket.getAttributes().getNamedItem("minExclusive") != null)
								minExclusive = n_bucket.getAttributes().getNamedItem("minExclusive").getNodeValue();
							if (n_bucket.getAttributes().getNamedItem("count") != null)
								count = n_bucket.getAttributes().getNamedItem("count").getNodeValue();
							f.addHistogramBucket(min, max, minExclusive, maxExclusive, count);
						}
					}
					lst_facet.add(f);
				}

		        }	
	        	
	        }

		for (Promotion p : lst_promotions)
		{
			html_promotions =  html_promotions + "<td><img src='" + p.getImageLink() + "'/><br/><a href=' " + p.getLink() + "'>" + p.getName() + "</a></td>";
		}
		
		if (html_promotions !="")
			html_promotions = "<font color='green'>Promotions: </font><br/><table border=1><tr>" + html_promotions + "</tr></table>";

		String qstr = request.getParameter("q");
		if (qstr != null)
			qstr = "&q=" + qstr;
		else
			qstr = "";

		//first remove the q parameter
		String filter_q = request.getParameter("q");
		if (filter_q != null)
			remove_filters = "<a href='gcs.jsp?cx=" + cx + restrictby + "'>" + filter_q + "</a>&nbsp;&nbsp;&nbsp;";

		String filter_restricts = request.getParameter("restrictBy");
		Vector v  = new Vector();		
		int vcounter;
		if (filter_restricts != null)
		{
			v = getRestricts(filter_restricts);
			for (int i=0; i<v.size(); i++)
			{
				String curr = (String)v.get(i);
				String combined_filters="";
				for (int j=0; j<v.size(); j++)
				{
					String itr = (String)v.get(j);
					if (!itr.equals(curr))
						if (combined_filters != "")
							combined_filters = combined_filters + "," +  itr;
						else
							combined_filters += itr;
				}
			if (!combined_filters.equals(""))
				remove_filters = remove_filters + "<a href='gcs.jsp?cx=" + cx + qstr + "&restrictBy=" + combined_filters + "'>" + curr + "</a>&nbsp;&nbsp;&nbsp;";
			else
				remove_filters = remove_filters + "<a href='gcs.jsp?cx=" + cx  + qstr + "'>" + curr + "</a>&nbsp;&nbsp;&nbsp;";		
			}
		}


//now pagenav page_nav
		int aInt = Integer.parseInt(totalResults);
		
		//then calculate the number of pages to show if there are 10 items per page.
		int num_pages = (int)Math.ceil(aInt/10);
		
		int start_index=1;
		for (int jj=0; jj<num_pages; jj++) {
		
			//extract out all the original GET parameters and use them
			//to construct the URL for the next pages
			//add on the start-index to those urls
			Iterator ji = request.getParameterMap().keySet().iterator();
			String ret_qstr="";
					
			while ( ji.hasNext() )
			{
				String key = (String) ji.next();
				String value = ((String[]) request.getParameterMap().get( key ))[ 0 ];
				if (!key.equals("startIndex"))
					ret_qstr += key + "=" +  value + "&";
			}
			//for each page, increment the start-index by 10
			String page_url = ret_qstr + "&startIndex=" + start_index;
			int pnum = jj +1;
			page_nav += "<a href='gcs.jsp?" + page_url + "'>" + pnum + "</a>&nbsp;&nbsp;";
			start_index += 10;
		}

		
	        for (Entry e : lst_entry)
	        {
			String slink = e.getAttributeValue(Entry.LINK);
			String stitle = e.getAttributeValue(Entry.TITLE);
			String simage_link = e.getAttributeValue(Entry.IMAGE_LINK);
			String sprice = e.getAttributeValue("s:price");
			Hashtable hm_attributes = (Hashtable)e.getAttributeDictionary();
			//create a random div id number for the show/hide
			SecureRandom random = new SecureRandom();
			String div_id =  new BigInteger(130, random).toString(32);

			String strout =  "<img height=100 width=100 src='"+simage_link+"'/>"+"<br/><a href='"+slink+"'>"+stitle+"</a><br/> price: " + sprice + "<br/>";
			Iterator it = hm_attributes.entrySet().iterator();
			strout += "<div id='" + div_id + "' style=\"display:none\">";
			strout += "<table width='100%'>";
			while (it.hasNext()) {
				Map.Entry pairs = (Map.Entry)it.next();
				String[] nt = (String[])hm_attributes.get(pairs.getKey());
				strout += "<tr><td>[" + pairs.getKey() + " = " + nt[1] + " (" + nt[0] + ")]</td></tr>";
			}
			strout += "</table>";
			strout += "</div><p><a href='#' onclick=\"showhide('" + div_id + "');\"'>Show/Hide detail</a></p><br/>";
			html_results += strout + "<hr>";
	        }



	        for (Facet f : lst_facet)
	        {
			String attrib = null;

			if (f.getFacetType() == Facet.TYPE_FIRST_CLASS)
			{
				attrib = "<font color='green'>FirstClass: " + f.getName() + "</font><br/>";
			}
			else
			{
				attrib = "<font color='blue'>Custom: " + f.getName() + " (" + f.getPrimitive() + f.getUnit() + ")"  + " </font><br/>";
			}

			if (f.getBucketType() == Facet.HISTOGRAM)
			{
				for (int i=0; i< f.getBucketCount(); i++)
				{
					HashMap hm_row = f.getRowMap(i);
					String min = (String)hm_row.get("min");
					String max = (String)hm_row.get("max");
					String minExclusive = (String)hm_row.get("minExclusive");					
					String maxExclusive = (String)hm_row.get("maxExclusive");
					String count = (String)hm_row.get("count");
					if (minExclusive == null && min == null)
					{
						minExclusive = "false";
						min = "0";
					}
					if (maxExclusive == null && max == null)
					{
						maxExclusive = "false";
						max = "*";
					}

					if (minExclusive.equals("true"))
						minExclusive = "(";
					else
						minExclusive = "[";
	
					if (maxExclusive.equals("true"))
						maxExclusive = ")";
					else
						maxExclusive = "]";
					
					String range;
	
					if (max.equals(""))
						range = "<";
					else
						range = ",";
					
					//attrib = attrib + "&nbsp;&nbsp;" + minExclusive + min +  range + max + maxExclusive + " (" + count + ")<br/>";
					String rstr = request.getParameter("restrictBy");

					if (rstr != null)
						if (f.getFacetType() == Facet.TYPE_FIRST_CLASS)
							rstr = "restrictBy=" + rstr + "," + f.getName() + "=" + minExclusive + min +  range + max + maxExclusive;
						else
							rstr = "restrictBy=" + rstr + "," + f.getName() + "(" + f.getPrimitive() + ")/" + f.getUnit() + "=" + minExclusive + min +  range + max + maxExclusive;						
					else
						if (f.getFacetType() == Facet.TYPE_FIRST_CLASS)
							rstr = "restrictBy=" + f.getName() + "=" + minExclusive + min +  range + max + maxExclusive;
						else
							rstr = "restrictBy="  + f.getName() + "(" + f.getPrimitive() + ")/" + f.getUnit() + "=" + minExclusive + min +  range + max + maxExclusive;	

					String value = minExclusive + min +  range + max + maxExclusive;
					attrib = attrib + "&nbsp;&nbsp;<a href='gcs.jsp?cx=" + cx + "&" + qstr + "&" + rstr + "'/>" + value +  " (" + count + ")</a>" + "<br/>";
				}
			}
			else
			{
				for (int i=0; i< f.getBucketCount(); i++)
				{
					HashMap hm_row = f.getRowMap(i);
					String value = (String)hm_row.get("value");
					String count = (String)hm_row.get("count");
					String rstr = request.getParameter("restrictBy");
					if (rstr != null)
						rstr = "restrictBy=" + rstr + "," + f.getName() + "(" + f.getPrimitive() + ")=" + value;
					else
						rstr = "restrictBy=" + f.getName() + "(" + f.getPrimitive() + ")=" + value;
					attrib = attrib + "&nbsp;&nbsp;<a href='gcs.jsp?cx=" + cx + "&" + qstr + "&" + rstr + "'/>" + value +  " (" + count + ")</a>" + "<br/>";
				}
			}
			html_attributes += attrib;
	        }


    }
    catch (Exception ex)
    {
       out.write("Error " + ex);
    }

%>

<%!
 private Vector getRestricts(String filter_restricts)
  {
	Vector v = new Vector();
			String[] fltrs = filter_restricts.split(",");
			for (int i=0; i< fltrs.length; i++)
			{
				String curr = fltrs[i];
				if ( (curr.endsWith(")") || curr.endsWith("]") ) )
				{
					String prev = (String)v.get(i-1);
					v.remove(i-1);
					v.add(i-1,prev+","+curr);
				}
				else
					v.add(curr);			
			}
	return v;
  }
%>




<body>

<center>
<h3>Google Commerce Search</h3>
<form name="search" id="search" action="gcs.jsp" method="GET">
  <table>
  <tr><td>query </td><td> <input type="text" name="q" /> eg  <font color=blue>shirt</font></td></tr>
  <tr><td>cx  </td><td>    <input type="text" name="cx" value="<%=cx%>" /> eg <font color=blue>016458501645884057912:dq_ixbwhuk8</font> </td></tr>
  <tr><td>developerKey for Shopping </td><td>    <input type="text" name="devkey" value="<%=devkey%>" /> eg <font color=blue>AItaQEBYZikN8HI1-fNsfUqMMCf9Brl7A9LXadv</font>&nbsp;&nbsp;<a href="https://code.google.com/apis/console/">API console</a> </td></tr>
  <tr><td>country (default=us) </td><td> <input type="text" name="country" value="<%=country%>" /> eg jp <br/>
  </table>
  <input type="submit" value="Submit" "/>
</form>
</center>
<font size="2px">
	<li>inbound querystring ---> <%=inbound_querystring%></li>

	<li><font color="blue">outbound shoppingAPI ---> </font> <a href='<%=outbound_shopping%>&prettyprint=true'><%=outbound_shopping%></a></li>
	<li><font color="red">Time taken: <%=time_taken%> ms</font></li>
</font>

<center>
		<div id="spelling" name="spelling">
			<%=html_spelling_suggestion%>
		</div>

		<div id="promotions" name="promotions">
			<%=html_promotions%>
		</div>
</center>

<div style="border: 1px solid rgb(0, 0, 0); width: 100%;  overflow: auto;"  id="filters" name="filters">Remove Filters: <%=remove_filters%></div>
<br/>
<td>Total Results:  <%=totalResults%></td>
<td>Page:  <%=page_nav%></td>

<hr/>
		<table width="100%" border=2>
		<tr align="top" >

		  <td width="25%" id="attributes" name="attributes"  valign="top"><b><u>Attributes</u></b><br/><%=html_attributes%></td>

		  <td width="75%" id="results" name="results"  valign="top" ><b><u>Results</u></b><br/><%=html_results%></td>
		</tr>
		</table>

</body>
</html>

<%!

final class Promotion
{
    private String name;
    private String desc;
    private String imageLink;
    private String link;
    
    public Promotion(String in_name, String in_desc, String in_imageLink, String in_link)
    {
        name = in_name;
        desc = in_desc;
        imageLink = in_imageLink;
        link = in_imageLink;
    }
    public String getName()
    {
        return name;
    }
    public String getDesc()
    {
        return desc;
    }
    public String getImageLink()
    {
        return imageLink;
    }
    public String getLink()
    {
        return link;
    }
}

final class Entry
{
    public final static String GOOGLE_ID = "s:googleId";
    public final static String PROVIDER_ID = "s:providedId";
    public final static String TITLE = "s:title";
    public final static String LINK = "s:link";
    public final static String DESC = "s:description";
    public final static String PRICE = "s:price";
    public final static String IMAGE_LINK = "s:images/@link";
    
    public final static String TYPE_TEXT = "text";
    public final static String TYPE_FLOAT = "float";
    public final static String TYPE_INT = "int";
    
    Dictionary<String,String[]> dict_entry_attributes = new Hashtable<String, String[]>();
    
    public Entry() {}
    
    public void addAttribute(String name, String type, String value)
    {
    	String[] ent_pair = new String[2];
        ent_pair[0] = type;
        ent_pair[1] = value;
        dict_entry_attributes.put(name, ent_pair);
    }

    public String getAttributeType(String name)
    {
    	Hashtable ht_dict_entry_attributes = (Hashtable)dict_entry_attributes;
        if ( (ht_dict_entry_attributes.containsKey(name)))
        {
        	String[] ent_pair = (String[])dict_entry_attributes.get(name);
            return ent_pair[0];
        }
        else
        {
            return null;
        }
    }

    public String getAttributeValue(String name)
    {
    	Hashtable ht_dict_entry_attributes = (Hashtable)dict_entry_attributes;
        if (ht_dict_entry_attributes.containsKey(name))
        {
        	String[] ent_pair = (String[])ht_dict_entry_attributes.get(name);
            return ent_pair[1];
        }
        else
        {
            return null;
        }
    }

    public Dictionary<String, String[]> getAttributeDictionary()
    {
        return dict_entry_attributes;
    }

}


final class Facet
{
    public final static int TYPE_FIRST_CLASS = 0;
    public final static int TYPE_CUSTOM = 1;
    private String name;
    private String primitive;
    private String count;
    private String unit;
    private int FACET_TYPE;
    private int BUCKET_TYPE;
    private final static int HISTOGRAM = 0;
    private final static int DISCRETE = 1;

    private ArrayList<HashMap> lst_ranges = new ArrayList<HashMap>();

    public Facet(String facet_name, String primitive, String count, String unit) 
    {
        this.name = facet_name;
        this.primitive = primitive;
        this.count = count;
	this.unit = unit;
	this.FACET_TYPE = TYPE_CUSTOM;
    }

    public Facet(String facet_name, String count) 
    {
        this.name = facet_name;
        this.count = count;
	this.FACET_TYPE = TYPE_FIRST_CLASS;
    }

    public void addHistogramBucket(String min, String max, String minExclusive, String maxExclusive, String count) 
    {
	BUCKET_TYPE = HISTOGRAM;
	HashMap hm_histo = new HashMap();
	hm_histo.put("min",min);
	hm_histo.put("max",max);
	hm_histo.put("minExclusive",minExclusive);
	hm_histo.put("maxExclusive",maxExclusive);
	hm_histo.put("count",count);
	lst_ranges.add(hm_histo);
    }

    public void addDiscreteBucket(String name, String count) 
    {
	BUCKET_TYPE = DISCRETE;
	HashMap hm_discrete = new HashMap();
	hm_discrete.put("value",name);
	hm_discrete.put("count",count);
	lst_ranges.add(hm_discrete);
    }

   public int getBucketCount()
   {
	return lst_ranges.size();
   }

   public HashMap getRowMap(int index)
   {
	return (HashMap)lst_ranges.get(index);
   }


    public int getBucketType()
    {
	return BUCKET_TYPE;
    }

    public int getFacetType()
    {
	return FACET_TYPE;
    }

    public String getPrimitive()
    {
	return primitive;
    }

    public String getUnit()
    {
	if (unit == null)
		return "";
	return unit;
    }
	
    public String getName()
    {
	return name;
    }


}
%>