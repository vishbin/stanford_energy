/*
 * Energy Gadget Javascript Custom Functions
 * 
 * 
 */




function log(obj){
	var output = '';
	for (property in obj) {
	  output += property + ': ' + obj[property]+'; ';
	 // alert(property + ': ' + obj[property]+'; ');
	}
	console.log(obj);
	alert(output);	
}


function showall(){
	var getdataresturl = window.location.protocol
	+ "//"
	+ window.location.host
	+ (window.location.pathname).substring(0, window.location.pathname
			.lastIndexOf("/")) + "/rrh/allproducts?type=showall";
	//alert(getdataresturl);
	$("#toolbar").jqGrid('setGridParam',{datatype:'json',url:getdataresturl}).trigger('reloadGrid');
	//alert($("td[aria-describedby='toolbar_cb']").text());
	
}




function filterResults(ui) {
	$("#noResultsDiv").hide();
	//alert(ui);
	//var amount = document.getElementById("amount").innerHTML;
	var amount = document.getElementById("energyConsumptionNewDD").value;
//	alert(amount);    
	var brandDDVal = document.getElementById("brandDD").value;
		var priceRangeDDVal = document.getElementById("priceRangeDD").value;
		var refTypeDDVal = document.getElementById("refTypeDD").value;
		var colorDDVal = document.getElementById("colorDD").value;
		var refVolNewDDVal = document.getElementById("refVolNewDD").value;
		var feature1NewVal = document.getElementById("featureNew1DD").value;

		var energyStarNewVal = document.getElementById("energyStarNewDD").value;
		
		var kwhRangeDDVal1 ;
		var kwhRangeDDVal2 ;
		//kwhRangeDDVal1 = ui.values[ 0 ];
		//kwhRangeDDVal2 = ui.values[ 1 ];
//alert(amount.indexOf(":")+""+ amount.indexOf("-"));

		//kwhRangeDDVal1 = amount.substring(amount.indexOf(":")+6, amount.indexOf("-"));
		//kwhRangeDDVal2 = amount.substring((amount.indexOf("-")+1), amount.length);
		
		kwhRangeDDVal1 = 0;
		kwhRangeDDVal2 = amount;
		
		
		if(ui=="inload"){
			kwhRangeDDVal1=" 999999999 ";
			kwhRangeDDVal2=" 0 ";
		}
	//	alert (kwhRangeDDVal1+"   "+kwhRangeDDVal2);
		
		var refVolNewDDVal1 ="";
		var refVolNewDDVal2="";
		if (refVolNewDDVal=='25.5'){
			refVolNewDDVal1='25.5';
		}else{
		
			refVolNewDDVal1 = refVolNewDDVal.substring(0, refVolNewDDVal
					.indexOf("-"));
			refVolNewDDVal2 = refVolNewDDVal.substring((refVolNewDDVal
					.indexOf("-") + 1), refVolNewDDVal.length);
		}
		
		//alert(kwhRangeDDVal1+" xxxxxxxxxx "+kwhRangeDDVal2);
		
		
	//	kwhRangeDDVal1 = values[ 0 ];
		//kwhRangeDDVal2 = values[ 1 ];
			
		
		
		
		
		var priceRangeDDVal1 ="";var priceRangeDDVal2 ="";
		if (priceRangeDDVal=='4000'){
			priceRangeDDVal1='4000';
		}else{
			 priceRangeDDVal1 = priceRangeDDVal.substring(0, priceRangeDDVal
					.indexOf("-"));
			 priceRangeDDVal2 = priceRangeDDVal.substring((priceRangeDDVal
					.indexOf("-") + 1), priceRangeDDVal.length);
		}
		
		//alert(priceRangeDDVal1);
		//alert(priceRangeDDVal2);
		
		
		var colorvar ="";var iceMakervar ="";var priceFilvar="";var priceFilvar1 ="";
		var energyStarvar ="";	var brandDDValvar ="";var refVolFilVar2="";
		var refVolFilVar1="";
		
		var kwhFilvar="";var kwhFilvar1 ="";
		
		
		var postdata = jQuery("#toolbar").jqGrid('getGridParam', 'postData');

		var filvar = "{\"groupOp\":\"AND\",\"rules\":[{\"field\":\"Product\",\"op\":\"cn\",\"data\":\""
				+ refTypeDDVal + "\"}";
		
		if(priceRangeDDVal1 && priceRangeDDVal1 !=""){
			priceFilvar = "{\"field\":\"Price\",\"op\":\"gt\",\"data\":\""
				+ priceRangeDDVal1 + "\"}";
		 }
		
		if(priceRangeDDVal2 && priceRangeDDVal2 !=""){
			priceFilvar1 = "{\"field\":\"Price\",\"op\":\"lt\",\"data\":\""
				+ priceRangeDDVal2 + "\"}";
		}
		//kwh filter change start
		
		if(ui=="urlparams"){
			
			var energyConsumption = getURLParameters('energyConsumption');
		//	alert("energyConsumption:: "+energyConsumption);
			if(energyConsumption != 'No Parameters Found' &&  energyConsumption ){
			//	alert("inside energyConsumption:: "+energyConsumption);
	
				kwhRangeDDVal2=""+energyConsumption+"";
				//kwhRangeDDVal1=" 0 ";
				colorDDVal=" ";
				
			}
			
		}
		
		if(kwhRangeDDVal1 && kwhRangeDDVal1 !=""){
			kwhFilvar = "{\"field\":\"AnnualEnergyUse\",\"op\":\"gt\",\"data\":\""
				+ kwhRangeDDVal1 + "\"}";
		 }
		
		if(kwhRangeDDVal2 && kwhRangeDDVal2 !=""){
			kwhFilvar1 = "{\"field\":\"AnnualEnergyUse\",\"op\":\"lt\",\"data\":\""
				+ kwhRangeDDVal2 + "\"}";
		}
		
		//kwh filter change end
		
		if(refVolNewDDVal1 && refVolNewDDVal1 !=""){
			refVolFilVar1 = "{\"field\":\"Size\",\"op\":\"gt\",\"data\":\""
				+ refVolNewDDVal1 + "\"}";
		 }
		
		if(refVolNewDDVal2 && refVolNewDDVal2 !=""){
			refVolFilVar2 = "{\"field\":\"Size\",\"op\":\"lt\",\"data\":\""
				+ refVolNewDDVal2 + "\"}";
		}
		
		
		
		
		
		
		if(colorDDVal && colorDDVal !=" "){
			colorvar = "{\"field\":\"Color\",\"op\":\"cn\",\"data\":\""
			+ colorDDVal + "\"}";
		}
		
		if(feature1NewVal && feature1NewVal !=" "){
		 iceMakervar = "{\"field\":\"IceMaker\",\"op\":\"cn\",\"data\":\""
			+ feature1NewVal + "\"}";
		}
		
		
		if(energyStarNewVal && energyStarNewVal !=" "){
		 energyStarvar = "{\"field\":\"EnergyStar\",\"op\":\"cn\",\"data\":\""
			+ energyStarNewVal + "\"}";
		}
		if(brandDDVal && brandDDVal !=" "){
			brandDDValvar = "{\"field\":\"Product\",\"op\":\"cn\",\"data\":\""
				+ brandDDVal + "\"}";
			}
		
		
		
		
		
		
		
		var filvarend = "]}";

		/*if (priceRangeDDVal == "4000") {
			priceFilvar1 = priceFilvar = "{\"field\":\"Price\",\"op\":\"gt\",\"data\":\""
					+ priceRangeDDVal + "\"}";
		}*/

		if (kwhFilvar != "") {
			filvar = filvar + "," + kwhFilvar; 
					
		}
		if (kwhFilvar1 != "") {
			filvar = filvar + "," + kwhFilvar1;
					
		}
		
		if (priceFilvar != "") {
			filvar = filvar + "," + priceFilvar; 
					
		}
		if (priceFilvar1 != "") {
			filvar = filvar + "," + priceFilvar1;
					
		}
		if (refVolFilVar1 != "") {
			filvar = filvar + "," + refVolFilVar1; 
					
		}
		if (refVolFilVar2 != "") {
			filvar = filvar + "," + refVolFilVar2;
					
		}
		
		
		if (colorvar != "") {
			filvar = filvar + "," + colorvar;
					
		}
		if (iceMakervar != "") {
			filvar = filvar + "," + iceMakervar;
					
		}
		
		if (energyStarvar != "") {
			filvar = filvar + "," + energyStarvar;
					
		}
		if (brandDDValvar != "") {
			filvar = filvar + "," + brandDDValvar;
					
		}
		
		
		
		filvar = filvar + filvarend;
		
		
		/*if(ui=="inload"){
			filvar='{"groupOp":"AND","rules":[{"field":"Product","op":"cn","data":""},{"field":"AnnualEnergyUse","op":"gt","data":" 1588 "},{"field":"AnnualEnergyUse","op":"lt","data":" 2250"}]}';
		}else{
			//showall();
			//alert("hi");
		}*/
		
		
		
		//alert(filvar);
		$.extend(postdata, {
			filters : filvar
		});

		//$.extend(postdata,{filters:'{"groupOp":"AND","rules":[{"field":"Product","op":"cn","data":"Ken"},{"field":"Price","op":"bw","data":"$5"}]}'});
		//                             {"groupOp":"AND","rules":[{"field":"Product","op":"cn","data":" Ken"},{"field":"Price","op":"bw","data":"$5"}]}

		//$.extend(postdata,{filters:'',searchField: 'name', searchOper: 'bw', searchString: text});

		jQuery("#toolbar").jqGrid('setGridParam', {
			search : true,
			postData : postdata
		});

		jQuery("#toolbar").jqGrid().trigger("reloadGrid", [ {
			page : 1
		} ]);
		calOldVsNew();
		//remove comment after testing 
		var rangekwh=kwhRangeDDVal1+"-"+kwhRangeDDVal2;
		callSaveUserDataFromFilter(brandDDVal,priceRangeDDVal,refTypeDDVal,colorDDVal,refVolNewDDVal,feature1NewVal,energyStarNewVal,'','Search_Filter_Changed',rangekwh);
		
		//  $("#toolbar").jqGrid().setGridParam({sortname: 'Price,', sortorder:'desc'}).trigger("reloadGrid"); 
		
	}



function removeItem(f, d, c) {
	var b = $("#removeFlag"), a = document.checkout.storeId.value;
	if (d == c) {
		if (!!a) {
			$.cookie("autoRenewalFlag" + a, null)
		}
	}
	if (b.val() == "true") {
		b.val("false");
		document.location.href = f
	}
}
function removeItemReview(a) {
	if ($("#removeFlag").val() == "true") {
		$("#removeFlag").val("false");
		document.location.href = a
	}
}
function UpdateQuantity(d, b, f, c, a) {
	var g = f.value;
	if (!FED.Util.isNumeric(g)) {
		$("#" + c + "_" + a)
			.html(
			"The value in the Quantity field is not valid.The value must be numeric.  Please enter a numeric value and try again.")
			.addClass("error").show();
		f.focus();
		f.select();
		return false
	} else {
		document.location.href = b + "&quantity_1=" + g
	}
	return true
}
function clearText(a, b, c) {
	a.value = "";
	b.value = "";
	c.value = ""
}
function validation(a) {
	if (a.length) {
		if (/^[a-zA-Z\s]+$/.test(a)) {
			return true
		} else {
			$("#errorMessage")
				.html(
				'The "To" and "From" fields cannot contain special characters or numbers. Please try again.')
		}
	} else {
		$("#errorMessage")
			.html(
			'The "To" and "From" fields must be filled in. Please try again.')
	}
	return false
}
function validateMessage(a) {
	if (a.length > 48) {
		alert("The message field can not have more than 48 characters");
		return false
	} else {
		if (a == " - add your personal message - ") {
			a.value = "";
			return true
		} else {
			return true
		}
	}
}

	function calOldVsNew() {
		var ids = jQuery("#toolbar").jqGrid('getDataIDs');
		//alert(JSON.stringify(ids));
		for ( var i = 0; i < ids.length; i++) {
			var cl = ids[i];
			//		alert(cl);

			var rowdata = jQuery("#toolbar").jqGrid('getRowData', ids[i]);
			//alert(JSON.stringify(rowdata));
			var rowids = rowdata.Id;
			var newkWh = rowdata.AnnualEnergyUse;
			//Your Current Appliance :: Annual Kwh : 660.0 (M) Annual Costs : $71.214
			var oldkWhDiv = document.getElementById("kWhPerYear002").innerHTML;
			var state = document.getElementById("stateDD").value;
			
			if(!(state) || state=="" || isNaN(state) || state==0){
				//alert(state);
				state=(0.117)*100;
			}
			
			//alert(oldkWhDiv);
			
			var oldkWhDiv=oldkWhDiv.toLowerCase();
			//alert(oldkWhDiv);
			//alert(oldkWhDiv.indexOf("refrigerator:<b>"));
			//alert(oldkWhDiv.indexOf("(M)"));
			//var oldkWh = oldkWhDiv.substring(oldkWhDiv.indexOf("Kwh :") + 6,oldkWhDiv.indexOf("(M)"));
			var oldkWh = oldkWhDiv.substring(oldkWhDiv.indexOf("refrigerator: <b>") + 17,oldkWhDiv.indexOf("kwh/yr"));
			
			//alert(oldkWh);
			

			newkWh = newkWh.replace(/kWh/i, "");

			var ce = (oldkWh - newkWh) * (state/100);
			//alert("oldkWh:: " +oldkWh+ "  newkWh "+ newkWh+ "  state " +state+" ce::: "+ce);
			if(!(oldkWh) || oldkWh=="" || isNaN(oldkWh) || oldkWh==0){
				//alert(oldkWh);
				ce="N.A.";
			}
			/*if(isNaN(ce) || ce==0){
				ce=(800-newkWh)*0.117;
			//	alert(ce);
			}*/
			
			jQuery("#toolbar").jqGrid('setRowData', ids[i], {
				AnnualCostSavingsforNewVsOld : Math.round(ce)
			});
			//alert(cl);

			var lifeCycleCost=newkWh * (state/100) * 11.626;
			//emp.getAnnualKwh())* 0.112 * 11.626
			//alert(newkWh+"  state "+state +"lifeCycleCost " + lifeCycleCost);
			
			/*if(lifeCycleCost==0){
				lifeCycleCost=newkWh * 0.117 * 11.626;
			}*/
			
			jQuery("#toolbar").jqGrid('setRowData', ids[i], {
				AnnualCostSavings :  Math.round(lifeCycleCost)
			});	
			
		}
	}

	//ce = "<input title='Click here to disable alerts for this order'  id="+disablealerts+" style='height:22px;width:100px;' type='button' value="+buttonvalue+"  onclick=\"callPostOrder('"+cl+"','"+ordersid+"','"+disablealerts+"' ,'"+buttonvalue+"');\"  />"; 

	//ruleGroup = "{\"groupOp\":\"" + group_op + "\",\"rules\":[";
	/*
	 eq=equal 
	 ne=not equal 
	 lt=less 
	 le=less or equal 
	 gt=greater 
	 ge=greater or equal 
	 nu=is null 
	 nn=is not null 
	 in=is in 
	 ni=is not in 
	 bw =begins with
	 bn">does not begin with

	 ew">ends with  
	 en">does not end with  
	 cn">contains  
	 nc">does not contain  
	 */
	
	$(function() {
		$( "#slider-range" ).slider({
			range: true,
			min:0,
			max:1500,
			values: [0,1500],
			slide: function( event, ui ) {
				$( "#amount" ).html( "<a>Electricity Consumption(Select Min & Max kWh/year) : </a>" + ui.values[ 0 ] + " - " + ui.values[ 1 ] );
				//filterResults();
				
			}
		});
		$( "#amount" ).html( "<a>Electricity Consumption(Select Min & Max kWh/year) :  </a> " + $( "#slider-range" ).slider( "values", 0 ) +
			" - " + $( "#slider-range" ).slider( "values", 1 ) );
		
		$( "#slider-range" ).slider({
			   change: function(event, ui) {
				   filterResults(ui); 
				   }
			});
		$( "#slider-range" ).slider({ animate: true });
		
		
		
	});

