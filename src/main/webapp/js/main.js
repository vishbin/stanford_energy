/*
 * Energy Gadget Javascript Custom Functions
 * 
 * 
 */


function listEntries(j) {
	// Clear any information displayed under the "data" div.
	removeOldResults();
	// alert(JSON.stringify(j.feed.entry));

	// Transform the JSON results into an unordered list of links.
	var ul = document.createElement("ul");
	ul.setAttribute("class", 'ww-bot-products products-list');

	for ( var i = 0; i < j.feed.entry.length; ++i) {
		var entry = j.entry[i];
		var alturl;
		alturl = "http://www.sears.com/shc/s/p_10153_12605_" + entry.id;

		var li = document.createElement("li");
		var wwbotitem = document.createElement("div");
		var imgwrapper = document.createElement("div");
		var wwbotdesc = document.createElement("div");

		wwbotitem.setAttribute("class", 'ww-bot-item');
		// wwbotitem.setAttribute("style",' font-size:12px; height:96px;
		// line-height:14px; overflow:hidden; width:220px;');

		imgwrapper.setAttribute("class", 'img-wrapper');
		wwbotdesc.setAttribute("class", 'ww-bot-desc');

		var img = document.createElement("img");
		img.setAttribute("class", 'ww-bot-img');
		img.setAttribute("alt", '');

		if (entry.ImageUrl) {
			img.setAttribute("src", entry.ImageUrl);
		}

		// img.setAttribute("src",entry.g$image_link[0].$t);

		imgwrapper.appendChild(img);

		var title = document.createElement("a");
		title.setAttribute("class", 'break-word');
		title.setAttribute("title", "More Info");

		var linkText = document.createTextNode((entry.Decription).substring(0,
				50)
				+ '...');

		title.appendChild(linkText);
		// alert(title.innerText);
		var ratingsinfo = document.createElement("p");
		ratingsinfo.setAttribute("class", 'ratings-info');

		var wwshowratings = document.createElement("span");
		wwshowratings.setAttribute("class", 'ww-show-ratings');

		var ratingmaskstar45 = document.createElement("span");
		ratingmaskstar45.setAttribute("class", 'rating-mask star-4-5');

		var wwbotvalue = document.createElement("p");
		wwbotvalue.setAttribute("class", 'ww-bot-value');

		var myUSDPrice = entry.Price;
		var myDollarPrice = myUSDPrice.replace("usd", " ");
		var priceText = document.createTextNode("$ " + myDollarPrice);

		wwbotvalue.appendChild(priceText);

		var wwbotbuy = document.createElement("a");
		wwbotbuy.setAttribute("href",
				"http://www.sears.com/shc/s/p_10153_12605_" + entry.id);
		wwbotbuy.setAttribute("target", '_blank');
		wwbotbuy.setAttribute("class", "");

		var buynow = document.createElement("span");
		var buynowtext = document.createElement("img");
		buynowtext.setAttribute("src", "images/shop.gif");
		buynow.appendChild(buynowtext);
		wwbotbuy.appendChild(buynow);

		wwbotdesc.appendChild(title);
		// wwbotdesc.appendChild(ratingsinfo);
		// ratingsinfo.appendChild(wwshowratings);
		// ratingsinfo.appendChild(ratingmaskstar45);
		wwbotdesc.appendChild(wwbotvalue);
		wwbotdesc.appendChild(wwbotbuy);
		// wwbotdesc.appendChild(buynow);

		wwbotitem.appendChild(imgwrapper);
		wwbotitem.appendChild(wwbotdesc);
		li.appendChild(wwbotitem)
		// li.appendChild(imgwrapper);
		// li.appendChild(wwbotdesc);
		ul.appendChild(li);
	}

	document.getElementById("results-content").appendChild(ul);
	// if (gadgets && gadgets.window && gadgets.window.adjustHeight) {
	// gadgets.window.adjustHeight()
	// }
	// alert(document.getElementById("sears-product").innerHTML);
}

/**
 * Adds a JSON script element which queries Google Base and calls the call-back
 * function.
 * 
 * @param {DOM
 *            object} query The form element containing the input parameters
 *            "bq" and "feed".
 */
function search(query) {
	// alert("hi");
	// Delete any previous Google Base JSON queries.
	removeOldJSONScriptNodes();
	// Clear any old data to prepare to display the Loading... message.
	removeOldResults();

	// Show a "Loading..." indicator.
	var div = document.getElementById('data');
	var p = document.createElement('p');
	p.appendChild(document.createTextNode('Loading...'));
	div.appendChild(p);

	// Add a script element with the src as the users Google Base query.
	// JSON output is specified by including the alt=json-in-script argument
	// and the callback funtion is also specified as a URI argument.
	var scriptElement = document.createElement("script");
	var searchKeyword;
	if (query && query.bq && query.bq.value != null && query.bq.value != '') {
		searchKeyword = query.bq.value;
	} else {
		searchKeyword = "Refrigerators";
	}

	scriptElement.setAttribute("id", "jsonScript");
	// scriptElement.setAttribute("src",
	// "http://www.google.com/base/feeds/snippets" +
	// "?bq=[customer id(int):2677551]" +
	// escape(searchKeyword) +
	// "&alt=json-in-script&callback=listEntries&max-results=200");
	// getAllProducts();
	scriptElement.setAttribute("src",
			"http://localhost:8080/StanEnergy_DB/rrh/allproducts");

	scriptElement.setAttribute("type", "text/javascript");

	document.documentElement.firstChild.appendChild(scriptElement);
}

/**
 * Deletes any old script elements which have been created by previous calls to
 * search().
 */
function removeOldJSONScriptNodes() {
	var jsonScript = document.getElementById("jsonScript");
	if (jsonScript) {
		jsonScript.parentNode.removeChild(jsonScript);
	}
}

/**
 * Deletes pre-existing children of the data div from the page. The data div may
 * contain a "Loading..." message, or the results of a previous query. This old
 * data should be removed before displaying new data.
 */
function removeOldResults() {
	var div = document.getElementById("results-content");
	if (div && div.firstChild) {
		div.removeChild(div.firstChild);
	}
	var div1 = document.getElementById("data");
	if (div1.firstChild) {
		div1.removeChild(div1.firstChild);
	}
}

function getAllProducts() {

	// var state=document.getElementById("stateDD").value;
	// var refVol=document.getElementById("refVolDD").value;
	// var modelYear=document.getElementById("modelYearDD").value;
	// var refAngle=document.getElementById("refAngleDD").value;
	// var feature1Val=document.getElementById("feature1DD").value;
	// var feature2Val="";//document.getElementById("feature2DD").value;
	// var energyStarVal=document.getElementById("energyStarDD").value;

	var compserverurl = window.location + "";
	// var servurl=compserverurl.replace("/gad001.html","");
	// alert(state+refVol+modelYear+refAngle);

	var strURL = window.location.protocol
			+ window.location.host
			+ ((window.location.pathname).substring(0, window.location.pathname
					.lastIndexOf("/"))) + "/rrh/allproducts";

	// alert(strURL);
	// makeJSONRequest(strURL,afterCompute);

	$.ajax({
		cache : false,
		contentType : 'text/xml',

		dataType : 'json',
		processData : false,
		type : 'GET',
		url : strURL,
		success : function(data, textStatus, XMLHttpRequest) {
			// alert(data.version);
			// listEntries(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("errortext msg :  " + textStatus);
			alert("errortext msg :  " + errorThrown);
			// debugger;
		}
	});

}

jQuery.cookie = function(key, value, options) {

	// key and at least value given, set cookie...
	if (arguments.length > 1 && String(value) !== "[object Object]") {
		options = jQuery.extend({}, options);

		if (value === null || value === undefined) {
			options.expires = -1;
		}

		if (typeof options.expires === 'number') {
			var days = options.expires, t = options.expires = new Date();
			t.setDate(t.getDate() + days);
		}

		value = String(value);

		return (document.cookie = [
				encodeURIComponent(key),
				'=',
				options.raw ? value : encodeURIComponent(value),
				options.expires ? '; expires=' + options.expires.toUTCString()
						: '', // use expires attribute, max-age is not
								// supported by IE
				options.path ? '; path=' + options.path : '',
				options.domain ? '; domain=' + options.domain : '',
				options.secure ? '; secure' : '' ].join(''));
	}

	// key and possibly options given, get cookie...
	options = value || {};
	var result, decode = options.raw ? function(s) {
		return s;
	} : decodeURIComponent;
	return (result = new RegExp('(?:^|; )' + encodeURIComponent(key)
			+ '=([^;]*)').exec(document.cookie)) ? decode(result[1]) : null;
};

function compute() {

	var state = document.getElementById("stateDD").value;
	var refVol = document.getElementById("refVolDD").value;
	var modelYear = document.getElementById("modelYearDD").value;
	var refAngle = document.getElementById("refAngleDD").value;
	var feature1Val = document.getElementById("feature1DD").value;
	var feature2Val = "";// document.getElementById("feature2DD").value;
	var energyStarVal = document.getElementById("energyStarDD").value;

	var appContext = (window.location.pathname).substring(0,
			window.location.pathname.lastIndexOf("/"));

	var strURL = window.location.protocol + "//" + window.location.host
			+ appContext + "/GetEnergyData?state=" + state + "&refType="
			+ refAngle + "&manufactureYear=" + modelYear + "&energyStarVal="
			+ energyStarVal + "&feature1Val=" + feature1Val + "&feature2Val="
			+ feature2Val + "&tvol=" + refVol + "&respFormat=json";
	// $.cookie('currentappvalues', strURL, { expires: 30 });
	/*
	 * _gaq.push(['_setCustomVar', 1, // This custom var is set to slot #1.
	 * Required parameter. 'User Type', // The name of the custom variable.
	 * Required parameter. 'Member', // Sets the value of "User Type" to
	 * "Member" or "Visitor" depending on status. Required parameter. 2 // Sets
	 * the scope to session-level. Optional parameter. ]);
	 */
	// alert(strURL);
	// makeJSONRequest(strURL,afterCompute);
	$.ajax({
		cache : false,
		contentType : 'text/xml',

		dataType : 'json',
		processData : false,
		type : 'GET',
		url : strURL,
		success : function(data, textStatus, XMLHttpRequest) {
			// alert(data.version);
			output1 = data;
			afterCompute(data);
			callUserData(data, state, refVol, modelYear, refAngle, feature1Val,
					energyStarVal);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("errortext msg :  " + textStatus);
			alert("errortext msg :  " + errorThrown);
			// debugger;
		}
	});

}

function callUserData(response, state, refVol, modelYear, refAngle,
		feature1Val, energyStarVal) {

	// alert(response.feed.entry);
	if (state != "" && refVol != "" && modelYear != "") {

		var currentApp = "State Rate :" + state + " , Type:" + refVol
				+ " , ModelYear: " + modelYear + " , RefType:" + refAngle
				+ " , IceMaker :" + feature1Val + " , EnergyStar :"
				+ energyStarVal + " , Annual kwh :" + response.feed.entry;
		var cookieId = new Date().getTime();

		callSaveUserData(cookieId, currentApp, "", "");
	}

}

function afterCompute(response) {

	if (response.feed.entry == " Please Select Above Options ") {
		
		var energyConsumption = getURLParameters('energyConsumption');
	//	alert("inside 1"+energyConsumption);
		if(energyConsumption=='No Parameters Found' ||   !energyConsumption ){
		//	alert("inside"+energyConsumption);

			document.getElementById("kWhPerYear002").innerHTML = response.feed.entry;	
		}else{
			

			var kwh = "<div class='boxtext1'>Electricity Consumption of Your Refrigerator: <b>"
					+ energyConsumption + " kWh/yr</b> </div>";
		/*	var state = "<div class='boxtext1'>Average Electricity Price in Your State: <b>$"
					+ response.feed.entry.substring(response.feed.entry
							.indexOf("$") + 1) + "/kWh </b></div>";
		*/	var heading = "<h2>Your Current Refrigerator Energy Usage Results</h2><div class='rule'></div>";
			document.getElementById("kWhPerYear002").innerHTML = heading + kwh;
//					+ state;
			
			filterResults('urlparams');
			
		}
	} else {
		var kwh = "<div class='boxtext1'>Electricity Consumption of Your Refrigerator: <b>"
				+ response.feed.entry.substring(0, response.feed.entry
						.indexOf("$")) + " kWh/yr</b> </div>";
		var state = "<div class='boxtext1'>Average Electricity Price in Your State: <b>$"
				+ response.feed.entry.substring(response.feed.entry
						.indexOf("$") + 1) + "/kWh </b></div>";
		var heading = "<h2>Your Current Refrigerator Energy Usage Results</h2><div class='rule'></div>";
		document.getElementById("kWhPerYear002").innerHTML = heading + kwh
				+ state;

	}

	calOldVsNew();

}

function aftercallprocess(state, refVol, modelYear, refAngle) {
	alert("after call");

}

function makeJSONRequest(reqURL, response) {
	var params = {};

}
