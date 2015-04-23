/*
 * Energy Gadget Javascript Custom Functions- v1.1 - 7/7/2011
 * This file has code for store user tracking  data .
 * Author vishbin
 */
var CONFIG = (function() {
var  private = {


		  'INITIAL_ENTRY'																:'Initial_Entry',
			'SORTPRICE_SEARCH_RESULTS'										:'SortPrice_Search_Results',
			'SORTKWH_SEARCH_RESULTS'											:'SortkWh_Search_Results',
			'SORTOTHER_SEARCH_RESULTS'										:'SortOther_Search_Results',
			'SORT_SAVEANDCOMPARE_RESULTS'									:'Sort_SaveAndCompare_Results',
			'SORTPRICE_SAVEANDCOMPARE_RESULTS'						:'SortPrice_SaveAndCompare_Results',
			'SORTKWH_SAVEANDCOMPARE_RESULTS'							:'SortkWh_SaveAndCompare_Results',
			'SORTOTHER_SAVEANDCOMPARE_RESULTS'						:'SortOther_SaveAndCompare_Results',
			'GO_TO_SAVEANDCOMPARE_PAGE'										:'Go_To_SaveAndCompare_Page',
			'CLOSING_SAVEANDCOMPARE_PAGE'									:'Closing_SaveAndCompare_Page',
			'CLICKED_SEE_AT_SEARS_FROM_MAIN_PAGE'					:'Clicked_See_At_Sears_From_Main_Page',
			'CLICKED_SEE_AT_SEARS_FROM_SAVEANDCOMPARE'		:'Clicked_See_At_Sears_From_SaveAndCompare',
			'CLOSE_MAINPAGE'															:'Close_MainPage',
			'FROM_CURRENT_APPLIANCE_SELECTIONS'						:'From_Current_Appliance_Selections',
			'SEARCH_FILTER_CHANGED'	:'Search_Filter_Changed'
};
return {
    get: function(name) { return private[name]; }
};
})();

function callSaveUserData(id,currentApp,futureApp,searchResults){

	var id=$.cookie('_StanfordSessionId');
	var getdataresturl = window.location.protocol
	+ "//"
	+ window.location.host
	+ (window.location.pathname).substring(0, window.location.pathname
			.lastIndexOf("/")) + "/rrh/storeuserdata";
	//alert(window.location.href);
	var origURL=window.location.href.replace(/&/i, "");
	origURL=origURL.replace(/#/i, "");
	//alert(origURL);
	var strURL=getdataresturl+"?cookieId="+id+"&currentApp="+currentApp+"&futureApp="+futureApp+"&searchResults="+searchResults+"&selectedPID="+"&origURL="+origURL+"&type=From_Current_Appliance_Selections"
	+"&placement="+getURLParameters('placement')
	+"&creative="+getURLParameters('creative')
	+"&keyword="+getURLParameters('keyword')
	+"&network="+getURLParameters('network');
	
	
	callSaveTrackingData(strURL);
	
	/*
	$.ajax({
	    cache: false,
	    contentType: 'text/xml',
	    
	    dataType: 'json',
	    processData: false,
	    type: 'POST',
	    url: strURL,
	    success: function(data, textStatus, XMLHttpRequest) {
	    	
	    	writeCookies(data);
	 
	    },
	    error: function(XMLHttpRequest, textStatus, errorThrown) {
	        alert("errortext callSaveUserData msg :  "+textStatus);
	        alert("errortext callSaveUserData msg :  "+errorThrown);
	        //debugger;
	    }
	}); 
	*/
	
	



}

function clickToMagnify(type){
	clickToBuySaveData('',type);
}
function clickNergyStarQuestion(type){
	clickToBuySaveData('',type);
}
function clickToSeeHideValues(type){
	clickToBuySaveData('',type);
}


function clickToBuySaveData(selectedPID,type){
	//alert(selectedPID);
	if(selectedPID !=""){
		//doGoal(this);
	}
	//var amount = document.getElementById("amount").innerHTML;
	//alert(amount);    
	var brandDDVal = document.getElementById("brandDD").value;
		var priceRangeDDVal = document.getElementById("priceRangeDD").value;
		var refTypeDDVal = document.getElementById("refTypeDD").value;
		var colorDDVal = document.getElementById("colorDD").value;
		var refVolNewDDVal = document.getElementById("refVolNewDD").value;
		var feature1NewVal = document.getElementById("featureNew1DD").value;
		
		//var amt = amount.substring(amount.indexOf(":")+6, amount.indexOf("-")) +"-"+amount.substring((amount.indexOf("-")+1), amount.length);
		
		var amt = document.getElementById("energyConsumptionNewDD").value;;
		var energyStarNewVal = document.getElementById("energyStarNewDD").value;
		//alert(amt);
	    //alert(brandDDVal+""+priceRangeDDVal+"  "+refTypeDDVal+"  "+colorDDVal+"  "+refVolNewDDVal+"  "+feature1NewVal+"  "+energyStarNewVal+"  "+selectedPID);	
		callSaveUserDataFromFilter(brandDDVal,priceRangeDDVal,refTypeDDVal,colorDDVal,refVolNewDDVal,feature1NewVal,energyStarNewVal,selectedPID,type,amt);
}


	
function callSaveUserDataFromFilter(brandDDVal,priceRangeDDVal,refTypeDDVal,colorDDVal,refVolNewDDVal,feature1NewVal,energyStarNewVal,selectedPID,type,amount){
	var pids=$("#savecompare").text();
	var state=document.getElementById("stateDD").value;
	var refVol=document.getElementById("refVolDD").value;
	var modelYear=document.getElementById("modelYearDD").value;
	var refAngle=document.getElementById("refAngleDD").value;
	var feature1Val=document.getElementById("feature1DD").value;
	var energyStarVal=document.getElementById("energyStarDD").value;
	var annualEnergykwh2 = getKwh();
	//alert(annualEnergykwh2);
	
	var currentApp="State Rate :"+state+" , Type:"+refVol +" , ModelYear: "+modelYear+ " , RefType:"+refAngle+" , IceMaker :"+feature1Val+" , EnergyStar :"+energyStarVal+	" , Annual kwh :"+annualEnergykwh2;
	
	var futureApp="Brand:"+brandDDVal+"  Price:"+priceRangeDDVal+"  Type:"+refTypeDDVal+"  Color:"+colorDDVal+ "  Size:"+refVolNewDDVal+" IceMaker:"+feature1NewVal+"  EnerygStar:"+energyStarNewVal+" kwh Range :"+amount;
	
	var searchResults=jQuery("#toolbar").jqGrid('getDataIDs');
	
	var cookieId=$.cookie('_StanfordSessionId');
	
	//alert(window.location.href.replace(/&/i, ""));
	var getdataresturl = window.location.protocol
	+ "//"
	+ window.location.host
	+ (window.location.pathname).substring(0, window.location.pathname
			.lastIndexOf("/")) + "/rrh/storeuserdata";
	
	var origURL=window.location.href.replace(/&/gi, "");
	origURL=origURL.replace(/#/i, "");
	var strURL=getdataresturl+"?cookieId="+cookieId+"&currentApp="+currentApp+"&futureApp="+futureApp+"&searchResults="+searchResults+"&selectedPID="+selectedPID+"&origURL="+origURL+"&type="+type+"&pids="+pids
	+"&placement="+getURLParameters('placement')
	+"&creative="+getURLParameters('creative')
	+"&keyword="+getURLParameters('keyword')
	+"&network="+getURLParameters('network');
	
	//alert(strURL);

	//alert(origURL);
	//alert(strURL);
	callSaveTrackingData(strURL);	
	
	/*$.ajax({
	    cache: false,
	    contentType: 'text/xml',
	    
	    dataType: 'json',
	    processData: false,
	    type: 'POST',
	    url: strURL,
	    success: function(data, textStatus, XMLHttpRequest) {
	    	
	    	writeCookies(data);
	    },
	    error: function(XMLHttpRequest, textStatus, errorThrown) {
	        //alert("errortext from callSaveUserDataFromFilter msg :  "+textStatus);
	        //alert("errortext callSaveUserDataFromFilter  msg :  "+errorThrown);
	        //debugger;
	    }
	}); */
	
	
	



}





function savePidsWithRank(type){
	
	
	var pidsArr = jQuery("#savetoolbar").jqGrid('getDataIDs');
	 var len=pidsArr.length;
	 var rankedPids="";
	 for(var i=0; i<len; i++) {
	 	var value = pidsArr[i];
	 	//alert(jQuery("#savetoolbar").jqGrid('getInd',value));
	 	rankedPids+="("+jQuery("#savetoolbar").jqGrid('getInd',value)+")"+value +",";
	 	//alert(i +" : "+value);
	 }
	clickToBuySaveData(rankedPids,type);
	
}


function callSaveTrackingData(strURL){
	//alert(strURL);
	$.ajax({
	    cache: false,
	    contentType: 'text/xml',
	    
	    dataType: 'json',
	    processData: false,
	    type: 'POST',
	    url: strURL,
	    success: function(data, textStatus, XMLHttpRequest) {
	    	
	    	writeCookies(data);
	    },
	    error: function(XMLHttpRequest, textStatus, errorThrown) {
	        //alert("errortext from callSaveUserDataFromFilter msg :  "+textStatus);
	        //alert("errortext callSaveUserDataFromFilter  msg :  "+errorThrown);
	        //debugger;
	    }
	}); 
	
	
}


function getURLParameters(paramName) 
{
       var sURL = window.document.URL.toString();  
   if (sURL.indexOf("?") > 0)
   {
      var arrParams = sURL.split("?");         
      var arrURLParams = arrParams[1].split("&");      
      var arrParamNames = new Array(arrURLParams.length);
      var arrParamValues = new Array(arrURLParams.length);     
      var i = 0;
      for (i=0;i<arrURLParams.length;i++)
      {
       var sParam =  arrURLParams[i].split("=");
       arrParamNames[i] = sParam[0];
       if (sParam[1] != "")
           arrParamValues[i] = unescape(sParam[1]);
       else
           arrParamValues[i] = "No Value";
      }

      for (i=0;i<arrURLParams.length;i++)
      {
               if(arrParamNames[i] == paramName){
           //alert("Param:"+arrParamValues[i]);
               return arrParamValues[i];
            }
      }
      return "No Parameters Found";
   }
}

   function getKwh() {
   	var annualEnergy=document.getElementById("kWhPerYear002").innerHTML.replace("&nbsp;","");
   	var annualEnergykwh=annualEnergy.replace('<h2>Your Current Refrigerator Energy Usage Results</h2><div class="rule"></div><div class="boxtext1">Electricity Consumption of Your Refrigerator: <b>','');
   	var annualEnergykwh1=annualEnergykwh.replace('</b> </div><div class="boxtext1">Average Electricity Price in Your State: <b>$',' , State Rate:');
   	var annualEnergykwh2=annualEnergykwh1.replace('</b></div>','');
   	return annualEnergykwh2;
   }
   
function trackEntryBrowser(){
	//alert("closing");
	clickToBuySaveData('',CONFIG.get('INITIAL_ENTRY'));
}


function trackBrowserClose(){
	
//alert($("#visitedsavecompare").text());
	/*if($("#visitedsavecompare").text()=="YES"){
		clickToBuySaveData($("#savecompare").text(),'Close_After_Looking_At_Saved_Products');		
	}else{
		clickToBuySaveData($("#savecompare").text(),'Close_Without_Looking_At_Saved_Products');
	}
	*/
	//just track closing of the window
	clickToBuySaveData($("#savecompare").text(),CONFIG.get('CLOSE_MAINPAGE'));
}
