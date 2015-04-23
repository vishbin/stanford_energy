/*
 * Energy Gadget Javascript Custom Functions

 * 
 */
jQuery.cookie = function (key, value, options) {

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
            encodeURIComponent(key), '=',
            options.raw ? value : encodeURIComponent(value),
            options.expires ? '; expires=' + options.expires.toUTCString() : '', // use expires attribute, max-age is not supported by IE
            options.path ? '; path=' + options.path : '',
            options.domain ? '; domain=' + options.domain : '',
            options.secure ? '; secure' : ''
        ].join(''));
    }

    // key and possibly options given, get cookie...
    options = value || {};
    var result, decode = options.raw ? function (s) { return s; } : decodeURIComponent;
    return (result = new RegExp('(?:^|; )' + encodeURIComponent(key) + '=([^;]*)').exec(document.cookie)) ? decode(result[1]) : null;
};



function populateYear() {
	var d = document.getElementById("birthMonth");
	var b = document.getElementById("birthDay");
	var a = new Date();
	a.setDate(a.getDate());
	if ((d.value == 2) && (b.selectedIndex == 29)) {
		var c = document.getElementById("birthYear");
		j = 1;
		for (i = 0; i <= 100; i++) {
			if (((a.getFullYear() - i) % 4) == 0) {
				c.options[j] = new Option(a.getFullYear() - i, a.getFullYear()
				- i);
				j = j + 1
			}
		}
		if (a.getFullYear() % 4 == 0) {
			c.options.length = 27
		} else {
			c.options.length = 26
		}
	} else {
		if ((d.value == 0) || (b.selectedIndex == 0)) {
			var c = document.getElementById("birthYear");
			c.options.length = 1
		} else {
			var c = document.getElementById("birthYear");
			j = 1;
			for (i = 0; i <= 100; i++) {
				c.options[i + 1] = new Option(a.getFullYear() - i, a
					.getFullYear()
				- i)
			}
		}
	}
}


function writeCookies(data){
	//alert(data.cookieId);
	
	var cookieIdLoc=$.cookie('_StanfordSessionId');
	//alert(cookieIdLoc);
	
	if(cookieIdLoc==null){
		$.cookie('_StanfordSessionId',  data.cookieId, { expires: 30 });
		//alert("after write");
	}
	
		
		
		
	
	
}


function showMessage(d, g, n) {
	var f = document.getElementById("LT_" + d);
	f.style.display = "none";
	var b = g + "_" + d;
	var c = g + "_" + d + "_1";
	var k = "";
	var h = "no";
	var m = document.getElementById("shipMode_" + d);
	m.style.width = "300px";
	if (document.getElementById(addressCode + "_" + itemCode + "_1") == null) {
		var a = createRadioButton(b, c, k, h);
		m.appendChild(a);
		a.checked = true;
		a.style.display = "none"
	}
	document.getElementById("ship_message_" + d).innerHTML = n;
	document.getElementById("ship_message_" + d).style.display = ""
}
function showLeadTime(a) {
	var b = $("#LT_" + a).show()
}
function createRadioButton(f, g, c, d) {
	try {
		var a = document.createElement('<input type="radio" name= "' + f
		+ '" id = "' + g + '"   onClick = "' + c + '"  value = "' + d
		+ '" />')
	} catch (b) {
		var a = document.createElement("input");
		a.setAttribute("type", "radio");
		a.setAttribute("name", f);
		a.setAttribute("id", g);
		a.setAttribute("onClick", c);
		a.setAttribute("value", d)
	}
	return a
}
function formatCurrency(c) {
	var b, a;
	c = c.toString().replace(/\$|\,/g, "");
	if (isNaN(c)) {
		c = "0"
	}
	b = (c == (c = Math.abs(c)));
	c = Math.floor(c * 100 + 0.50000000001);
	a = c % 100;
	c = Math.floor(c / 100).toString();
	if (a < 10) {
		a = "0" + a
	}
	for ( var d = 0; d < Math.floor((c.length - (1 + d)) / 3); d++) {
		c = c.substring(0, c.length - (4 * d + 3)) + ","
		+ c.substring(c.length - (4 * d + 3))
	}
	return (c + "." + a)
}
function setShippingCharge(b, a) {
	$("#" + b).html(a)
}
function fnCheckAutoRenewal() {
	var b = $("#renew"), a = document.checkout.storeId.value;
	if (b.length) {
		document.checkout.autoRenewal.value = b.attr("checked") ? "true"
			: "false"
	}
	if (!!a) {
		$.cookie("autoRenewalFlag" + a, null)
	}
}
function fnAutoRenewalFlagUser(b) {
	var a = $("#renew");
	if (a.length) {
		$.cookie("autoRenewalFlag" + b, a.attr("checked"))
	}
}
function doGoal(that) { 
	   try { 
		//   alert("hi");
		_gaq.push(['gwo._trackPageview', '/2843019158/goal']);   
	    //_gaq.push(['gwo._trackPageview', '/YYYYYYYYY/goal']); 
	   // setTimeout('document.location = "' + that.href + '"', 100) ;
	   } 
	   catch(err){
		   alert(err);
	   } 
	  } 



function saveForCompare(obj1){
	   
	   if (obj1.currentTarget.checked){
	    	 
 		  // var pid=obj1.currentTarget.id.replace('jqg_toolbar_','');  
		   $("#savecompare").append(obj1.currentTarget.id.replace('jqg_toolbar_','') +',');
    	  
		  // alert($("#compareB").text());
		  // $("#compareB").css('background-color', '#4D90FE');

       }else{
    	   
    	   var pis=obj1.currentTarget.id.replace('jqg_toolbar_','') +',';
    	   var pis2=$("#savecompare").text().replace(pis,'');
    	   $("#savecompare").text(pis2);
    
     
    
       }
       
		 //alert($("#compareB").text());
		 //alert($("#savecompare").text());
	  
	  
	
}


function savecomparegrid(){
	
	$("#visitedsavecompare").text("YES");
	//alert($("#visitedsavecompare").text());
	var pids=$("#savecompare").text();
	
	//Tracking
	clickToBuySaveData(pids,CONFIG.get('GO_TO_SAVEANDCOMPARE_PAGE'));
	
	
	var getsavedataresturl = window.location.protocol
	+ "//"
	+ window.location.host
	+ (window.location.pathname).substring(0, window.location.pathname
			.lastIndexOf("/")) + "/rrh/getcomparedata"+"?pids="+pids+"&time="+Math.floor(Math.random() * 99999);
//alert(getsavedataresturl);
$("#savetoolbar").jqGrid('setGridParam',{datatype:'json',url:getsavedataresturl}).trigger('reloadGrid');
//savePidsWithRank('Entry_SaveAndCompare_Page');
$( "#savecomparedialog" ).dialog( "open" );
//window.scrollTo(0,document.body.scrollHeight);
//alert($("td[aria-describedby='toolbar_cb']")); 
//alert($("div[aria-describedby='ui-dialog-title-savecomparedialog']").html);
//$("div[aria-describedby='ui-dialog-title-savecomparedialog']").css("position", "top");
//position: absolute; height: auto; width: 964px; top: 1px; left: 219px;
}



function savecomparegrid1(){
	
	
	var pids=$("#savecompare").text();
	//alert(pids);
	var getsavedataresturl = window.location.protocol
	+ "//"
	+ window.location.host
	+ (window.location.pathname).substring(0, window.location.pathname
			.lastIndexOf("/")) + "/rrh/getcomparedata"+"?pids="+pids+"&time="+Math.floor(Math.random() * 99999);
//alert(getsavedataresturl);
	
jQuery("#savetoolbar").jqGrid(
	{
		url : getsavedataresturl,
		datatype : "json",
		colNames : [ 'Productid', ' <a>New Refrigerators<a> ', '<a>Price<a> <img src="images/sort_neutral_green.png"></img>',
						'<a>Annual<br>Electricity Use<img src="images/sort_neutral_green.png"></img> ', '<a>Lifetime Electricity<br> Cost</a> <img src="images/sort_neutral_green.png"></img>',
						'<a>Annual Cost<br>Savings for New Vs Old <img src="images/sort_neutral_green.png"></img></a>',
						'Color',
						'IceMaker',
						'EnergyStar',
						'Size'

				],
		colModel : [ {
			name : 'Id1',
			index : 'Id1',
			width : 10,
			sorttype : 'String',
			hidden : true
		}, {
			name : 'Product1',
			index : 'Product1',
			width : 275,
			sortable : false
		}, {
			name : 'Price1',
			index : 'Price1',
			width : 100,
			sorttype : 'int',
			sortable : true,formatter:'currency',formatoptions:{prefix:"&#36;",thousandsSeparator:",",decimalPlaces: 0}
		}, {
			name : 'AnnualEnergyUse1',
			index : 'AnnualEnergyUse1',
			width : 125,
			sorttype : 'int',
			sortable : true, formatter:kwhFormatter
		}, {
			name : 'AnnualCostSavings1',
			index : 'AnnualCostSavings1',
			width : 160,
			sorttype : 'int',
			sortable : true,formatter:'currency',formatoptions:{prefix:"&#36;",thousandsSeparator:",",decimalPlaces: 0}
		}, {
			name : 'AnnualCostSavingsforNewVsOld1',
			index : 'AnnualCostSavingsforNewVsOld1',
			width : 200,
			sorttype : 'int',
			sortable : true,formatter:'currency',formatoptions:{prefix:"&#36;",thousandsSeparator:",",decimalPlaces: 0}
		}
		
		, {
			name : 'Color1',
			index : 'Color1',
			width : 200,
			sortable : true,
			hidden : true
		}
		
		, {
			name : 'IceMaker1',
			index : 'IceMaker1',
			width : 200,
			sortable : true,
			hidden : true
		}
		, {
			name : 'EnergyStar1',
			index : 'EnergyStar1',
			width : 200,
			sortable : true,
			hidden : true
		}, {
			name : 'Size1',
			index : 'Size1',
			width : 200,
			sorttype : 'int',
			sortable : true,
			hidden : true
		}

		],
		rowNum : 5,
		rowTotal : 2000,
		rowList : [ 5,10,20],
		height : '100%',
		width : "964px",
		altRows : true,
		shrinkToFit : true,
		loadonce : true,
		mtype : "GET",
		gridview : true,
		pager : '#saveptoolbar',
		sortname : 'Price1',
		emptyrecords:"No appliances match your criteria. Please change your selections to get results � note that by selecting �Any� you can leave fields unrestricted",
		sortorder : "asc",
		rownumbers:true,
		rownumWidth:55,
		onSelectRow: function(rowid, status) {
		
		    $('#'+rowid).removeClass('ui-state-highlight');
		    alert($('#'+rowid));   
		},
		beforeSelectRow: function(rowid, e) {
		    return false;
		},
		loadComplete: function() {
			/*alert("hi");
			clickToBuySaveData("","RANKING_WHILE_GOING_TO_SAVE_AND_COMPARE");*/
			//Tracking
			//savePidsWithRank('Entry_Rank_Changed_SaveAndCompare_Page');
			//alert($("tr.jqgrow:odd").css);
			$("tr.jqgrow:odd").css("background", "#2f2f2");
			
			
			//Tracking		
			var sortColumnName = $("#savetoolbar").jqGrid('getGridParam','sortname');
			//alert(sortColumnName);
			
			
				if(sortColumnName=='Price1'){
					sortColumnName='Price';
				}else if(sortColumnName=='AnnualEnergyUse1'){
					sortColumnName='KWH';
				}else{
					sortColumnName='OTHER';
				}
			
			clickToBuySaveData("",'SORT_'+sortColumnName+'_SAVEANDCOMPARE_RESULTS');
			
		}
		
		
		
	});

//resize_the_grid();
//$(window).resize(resize_the_grid);
$(".ui-jqgrid-sortable").css('height', 'auto');
//jQuery("#savetoolbar").jqGrid('sortableRows');



	
$( "#savecomparedialog" ).dialog({
	autoOpen: false,
	show: "slide",
	hide: "explode",
	closeOnEscape: true,
	closeText:'Go Back ',
	modal: true,
	width:'974px',
	position:'top',
	title:" <a style='text-align: center;'> SAVE AND COMPARE  </a>"	
});

$("#jqgh_savetoolbar_rn").text('Rank');
$("#jqgh_savetoolbar_rn").css('height', 'auto');

$(window).resize(function() {
    $('body').css('height', $(this).height());
    $('#savecomparedialog').dialog( "option", "position", ['center','top'] );
}).scroll(function(){$(this).resize();});

$("#savecomparedialog").html($("#savecomparediv"));

$( "#savecomparedialog" ).bind( "dialogopen", function(event, ui) {
	//alert(getsavedataresturl);
	//alert($('body').css('height', $(this).height()));
	//$("#savetoolbar").jqGrid('setGridParam',{datatype:'json',url:getsavedataresturl}).trigger('reloadGrid'); 
	//$('body').css('height', $(this).height());
	//alert($("div[aria-describedby='ui-dialog-title-savecomparedialog']").html);
	//$("div[aria-describedby='ui-dialog-title-savecomparedialog']").css("position", "bottom" );
	//savePidsWithRank('Entry_SaveAndCompare_Page');
});	



$( "#savecomparedialog" ).bind( "dialogclose", function(event, ui) {
	//alert(getsavedataresturl);
	 //get total rows in you grid
	//jQuery("#toolbar").jqGrid('getGridParam').records==0
	//var rowNum=$("#grid1").getGridParam("selarrrow"); $("#grid1").getRowData(rowNum);
	  
	//Tracking
	//savePidsWithRank('Closing_SaveAndCompare_Page');
	savePidsWithRank(CONFIG.get('CLOSING_SAVEANDCOMPARE_PAGE'));
	
});	


	
}














this.vtip = function() {    
    this.xOffset = -10; // x distance from mouse
    this.yOffset = 10; // y distance from mouse       
    
    $(".vtip").unbind().hover(    
        function(e) {
            this.t = this.title;
            this.title = ''; 
            this.top = (e.pageY + yOffset); this.left = (e.pageX + xOffset);
            
            $('body').append( '<p id="vtip"><img id="vtipArrow" />' + this.t + '</p>' );
                        
            $('p#vtip #vtipArrow').attr("src", 'images/vtip_arrow.png');
            $('p#vtip').css("top", this.top+"px").css("left", this.left+"px").fadeIn("fast");
            
        },
        function() {
            this.title = this.t;
            $("p#vtip").fadeOut("slow").remove();
        }
    ).mousemove(
        function(e) {
            this.top = (e.pageY + yOffset);
            this.left = (e.pageX + xOffset);
                         
            $("p#vtip").css("top", this.top+"px").css("left", this.left+"px");
        }
    );            
    
};

jQuery(document).ready(function($){vtip();}) ;

function preloadDropdowns(){
	
	var stateDDValue=getURLParameters('state');
	var refAngleDDValue=getURLParameters('reftype');
	var modelYearDDValue=getURLParameters('modelYear');
	var refVolDDValue=getURLParameters('size');
	var feature1DDValue=getURLParameters('icemaker');
	var energyStarDDValue=getURLParameters('energystar');
	
	var energyConsumption = getURLParameters('energyConsumption');
	//alert(energyConsumption);
	//alert(refAngleDDValue);
	$("#stateDD").val(statesrates.get(stateDDValue));
	$("#refAngleDD").val(refAngleDDValue);
	$("#modelYearDD").val(modelYearDDValue);
	$("#refVolDD").val(refVolDDValue);
	$("#feature1DD").val(feature1DDValue);
	$("#energyStarDD").val(energyStarDDValue);
//	document.getElementById("kWhPerYear002").innerHTML=energyConsumption;
	//$("#kWhPerYear002").innerHTML(energyConsumption);
//alert(document.getElementById("kWhPerYear002").innerHTML);
	
	
	$("#colorDD").val('White');
	$("#refVolNewDD").val('19-21.4');
//	$('#energyConsumptionNewDD').val(energyConsumption);
	
	compute();
	filterResults();	

}

function mapPrice() {
	var a = $("#srchToolTip");
	var d = $("#srchToolTipCont");
	var c = $(this);

	var g = "";
	if ($(this).text().trim() == "See price") {
		g += "<span>Price Details</span>";
		if ($("body").attr("id") == "compare") {
			var b = $(this).parent().prev().text();
			if (b.indexOf("From") >= 0 && b.indexOf("To") >= 0) {
				g += "<br/><div>" + f + "</div><br/>"
			} else {
				if ($(this).parent().parent().next().attr("id") == "salePrice"
					&& $(this).parent().parent().next().val() != "") {
					g += "<br/><div> $"
					+ $(this).parent().parent().next().val()
					+ "</div><br/>"
				}
			}
		} else {
			if ($("body").hasClass("collection") == true) {
				var b = $(this).parent().prev().text();
				if (b.indexOf("From") >= 0 && b.indexOf("To") >= 0) {
					g += "<br/><div>" + e + "</div><br/>"
				} else {
					if ($(this).parent().parent().parent().next().attr("id") == "salePrice"
						&& $(this).parent().parent().parent().next().val() != "") {
						g += "<br/><div> $"
						+ parseFloat($(this).parent().parent().parent()
							.next().val(), 10) + "</div><br/>"
					} else {
						if ($(this).parent().parent().next().attr("id") == "salePrice"
							&& $(this).parent().parent().next().val() != "") {
							g += "<br/><div> $"
							+ parseFloat($(this).parent().parent()
								.next().val(), 10) + "</div><br/>"
						}
					}
				}
			} else {
				if ($("body").attr("id") == "product"
					&& $("body").hasClass("softline")) {
					if (($(this).parent().prev().text().indexOf("From") >= 0 && $(
							this).parent().prev().text().indexOf("To") >= 0)
						&& (document.addToCart.catEntryId_1.value == "undefined" || document.addToCart.catEntryId_1.value == "")) {
						g += "<br/><div>" + e + "</div><br/>"
					} else {
						g += "<br/><div> $" + $("#numPrdSalePrice").val()
						+ "</div><br/>"
					}
				} else {
					if ($("body").attr("id") == "subcategory"
						&& (typeof sName == "undefined" || sName == "")) {
						var b = $(this).parent().prev().text();
						if (b.indexOf("From") >= 0 && b.indexOf("To") >= 0) {
							g += "<br/><div>" + f + "</div><br/>"
						} else {
							if ($(this).parent().parent().next().attr("id") == "salePrice"
								&& $(this).parent().parent().next().val() != "") {
								g += "<br/><div> $"
								+ $(this).parent().parent().next()
									.val() + "</div><br/>"
							}
						}
					} else {
						if ($("div.qvPopUpLayer").length) {
							var k = 0;
							var b = $(this).parent().prev().text();
							if ($("input#variant").val() == 1
								&& b.indexOf("From") >= 0
								&& b.indexOf("To") >= 0) {
								$("div.qvdropdowns").children("div").each(
									function() {
										if ($(this).find(".opt_sel")
												.hasClass("default")) {
											k = 1
										}
									});
								if (k == 1) {
									g += "<br/><div>" + e + "</div><br/>"
								}
								if (k == 0) {
									if (document.addToCart.catEntryId_1.value == "") {
										g += "<br/><div>" + e + "</div><br/>"
									}
								}
							} else {
								g += "<br/><div> $"
								+ $("#numPrdSalePrice").val()
								+ "</div><br/>"
							}
						} else {
							if ($(this)
									.parents("div#productUpsellModule:first").length != 0) {
								if ($(this).parents("div.saveStory:first")
										.next().attr("id") == "upsellSalePrice") {
									g += "<br/><div> $"
									+ $(this).parents(
										"div.saveStory:first")
										.next().val()
									+ "</div><br/>"
								}
							} else {
								if ($("input#numPrdSalePrice").length == 0) {
									g += "<br/><div> $"
									+ parseFloat($("input#salePrice")
										.val(), 10) + "</div><br/>"
								} else {
									g += "<br/><div> $"
									+ parseFloat($(
										"input#numPrdSalePrice")
										.val(), 10) + "</div><br/>"
								}
							}
						}
					}
				}
			}
		}

	} else {

	}
	g += '<div id="srchToolTipClose">';
	g += '<span id="clsBtn"></span></div><div style="display: block;" id="srchToolTipTopup"></div>';
	var j = c.offset();
	d.html(g);
	a.css({
		"top" : j.top + 20,
		"left" : j.left - 20
	}).show()
}



