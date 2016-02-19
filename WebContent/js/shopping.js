/**
 * 
 */
 
 var activeSalonRegNum;
 var activeUserRole = null;
 var fileNameForUpload = "";
 var picAndVidEdit = "";
 var globalPageName= "";
 var categoryList = [];
 
 var searchQuery = "";
 var searchQueryKind = "";
 var searchQueryType = "";
 var searchQuerySubType = "";
 var searchQueryValue = "";
 
 $(document).ready(function () {
	 initReports();
	 initPartials();
	 initCategories();	 	 

 });
 
 function initCategories(){
			$.ajax({
				type: 'POST',
				url: 'services/shopping/getCategories', 
				data: "normal",
				async: false,
				success: function(data) { 
					$.each(data, function() {
						$.each(this, function(name, value) {
							if(name == "name") {
								id = value;
								categoryList.push(value);
							} else if(name == "description") {
								description = value;
							}
						})
				});
				//alert('data: ' + data);  a 
			},
			contentType: 'text/plain',
			dataType: 'json'
		});		

		
		if($("#totalPriceDiv").text() != "Ukupno: 0.0 Rsd.") {
			$("#finishShoppingLink").css("display","block");
		}
		
 }
 
 
 function finishShopping() {
	$("#dashboardContent4").load("partials/bill.jsp",function( response, status, xhr ) {
		$("#billWrapperDiv").toggle(200);
	});
 }
 
 function saveShopping() {
	$("#billWrapperDiv table td").empty();
	$("#billWrapperDiv").toggle(200);
	$("#finishShoppingLink").css("display","none");
	$("#totalPriceDiv").text("Ukupno: 0.0 Rsd.");
	$("#totalItemsLink").text("0 porudžbina");
	
		$.ajax({
			type: 'GET',
			url: 'services/shopping/resetShoppingCart', 
			async: false,
			success: function(data) { 
				$().toastmessage('showSuccessToast',"Uspešno ste obavili Vašu kupovinu. Hvala!");
			}
		});		

}

function initReports(){
			$.ajax({
			type: 'GET',
			url: 'services/shopping/initReports', 
			async: false,
			success: function(data) { 
				//$().toastmessage('showSuccessToast',"Uspešno ste obavili Vašu kupovinu. Hvala!");
			}
		});		

}

 function initPartials() {
	$("#menuDiv").load("partials/menu.jsp");
	
   	$("#salonDiv #adminPanelDiv #dashboardContent2").load("partials/addAddtionalService.jsp",function( response, status, xhr ) {
		if ( status == "error" ) {
				alert("erro");
		}
	});
	
	$("#salonDiv #adminPanelDiv #dashboardContent3").load("partials/addCategory.jsp",function( response, status, xhr ) {
		if ( status == "error" ) {
				alert("erro");
		}
	});
	
	$("dashboardContent6").load("partials/reportByDays.jsp",function( response, status, xhr ) {
		if ( status == "error" ) {
				alert("erro");
		}
	});


	$("#dashboardContent5").load("partials/report.jsp",function( response, status, xhr ) {
		if ( status == "error" ) {
				alert("erro");
		}
		
		$(".reportDate").datepicker({
			dateFormat: 'dd-mm-yy',
			inline: true,
			minDate: new Date()
		});
		
		//$('#reportCategorySelect').css('display', 'none');


	});

	
	$("#salonDiv #adminPanelDiv #dashboardContent1").load("partials/addFurniture.jsp", function() {
		
		$("input[type='file']").change(function(e){
			fileNameForUpload = e.target.files[0].name;
		});
		
		$('#addPictureDiv').jqxFileUpload({ width: 300, uploadUrl: 'services/shopping/uploadPicture', fileInputName: 'fileToUpload' });
				
		$('#addPictureDiv').on('select', function (event) {
                var fileName = event.args.file;
                setPicUploadName(fileName);
        });
				
	});
	
}
 
 function generateReport() {
	
 }
 
 function toggleReport() {
	$("#reportCategorySelect").toggle(200);
 }
 
 function setPicUploadName(name) {
	fileNameForUpload = name;
 }
 
 function openSalon(salonRegNum, salonName, query) {
	
	$("#salonHeadingDiv").text(salonName);
	$("#salonNamePath").text(salonName);
	globalPageName = "furnitureItems";
	
	var itemToHide = "";
	
	if($("#salonDiv").is(":visible")) {
		itemToHide = "salonDiv";
	} else {
		itemToHide = "salonsGridDiv";
	}
	
	$("#"+itemToHide).fadeOut('slow', function() { 
		
		$("#salonDiv").css("display","block"); 
		$("#addBtnDiv strong").text("Novi nameštaj");
		$("#addBtnDiv button").attr("onclick","toogleNewItem('addFurnitureDiv')");

		// saljem zahtev serveru da mi uruci sve iteme za dati salon   
		$.ajax({
			type: 'POST',
			url: 'services/shopping/getSalonItems', 
			async: false,
			data:  salonRegNum+","+query, // cast u string naziv maticnog broja salona..
			success: function(data) { 
				renderSalonItems(data,query);   
				//alert('data: ' + data);  a 
			},
			contentType: 'text/plain',
			dataType: 'json'
		});		
		
		$(this).replaceWith( $("#salonDiv") );

		scroll(0,0);
	});
	
	activeSalonRegNum = salonRegNum;
 }
 
 function clickedSalon(salonLinkId, salonRegNum, salonName) {
		$(salonLinkId).attr("href", "javascript:openSalon("+salonRegNum+','+"'"+salonName+"'"+",'normal')");
 }

 function renderSalonItems(data,query) {
	//alert("usao u render.."); 
	var listItems = "";
	
	var id = "";
	var nameItem = "";
	var color = "";
	var originCountry = "";
	var producerName = "";
	var price = ""; 
	var amountInStorage = "";
	var furnitureCategoryName = ""; 
	var yearBuilt = "";
	var sellingSalonRegNum = "";
	var picUrl = "";
	var description = "";
	var discountPercent = 0;
	var actionDateBegin = new Date(); 
	var actionDateEnd = new Date();
	var today = new Date();
	var vidUrl = "";
	
	setSessionUserName(activeUserRole);
		
	$.each(data, function() {
		$.each(this, function(name, value) {
			if(name == "id") {
				id = value;
			} else if(name == "name") {
				nameItem = value;
			} else if(name == "color") {
				color = value;	
			} else if(name == "originCountry") {
				originCountry = value;	
			} else if(name == "producerName") {
				producerName = value;
			} else if(name == "price") {
				price = value;
			} else if(name == "amountInStorage") {
				amountInStorage = value;
			} else if(name == "furnitureCategory") {
				$.each(this, function(name, value) {
					if(name == "name") {
						furnitureCategoryName = value;
					}
				});
			} else if(name == "yearBuilt") {
				yearBuilt = value;
			} else if(name == "sellingSalonRegNum") {
				sellingSalonRegNum = value;
			} else if(name == "pictureUrl") {
				picUrl = value;	
			} else if(name == "description") {
				description = value;
			} else if(name == "actionDateBegin") {
				actionDateBegin = value;
			} else if(name == "actionDateEnd") {
				actionDateEnd = value;
			} else if(name == "discountPercent") {
				discountPercent = value;
			} else if(name == "vidUrl") {
				vidUrl = value;
			}
			//console.log(name + '=' + value);
		});
		
		if(actionDateBegin != null) {										// konverzija u pravi datum iz milisekundi koje salje JAVA
			actionDateBegin = new Date(actionDateBegin);
		}
		if(actionDateEnd != null) {
			actionDateEnd = new Date(actionDateEnd);
		}
		
		var discountPrice = price-price*(discountPercent/100);
		
		var isActionTime = actionDateBegin != null && actionDateEnd != null
						&& actionDateBegin < today && today < actionDateEnd;
		
		picAndVidEdit = "";
		
		var picAndVid = "";
		
		var vidUrlTemp = clone(vidUrl);
		if(vidUrl != null) {
			vidUrl = vidUrl.replace('.com/watch?v=','.com/embed/');			// neophodno za embedded videos
		} else {
			vidUrl = "";
		}
		
		picAndVidEdit +=
		"<div id='mediaEditDiv"+id+"' class='mediaEditDiv editMedia' >"+
			"<div id='salonItemVidMediaEdit"+id+"' >"+
							"<input type='text' value='"+vidUrlTemp+"' />"+
			"</div>"+
			"<div id='salonItemPicMediaEdit"+id+"' class='salonItemPicEdit'>"+"</div>"+	
		"</div>";
		
		if(picUrl == null) {
			picUrl = "images/furnitureItems/";
		}
		
		if(picUrl == "images/furnitureItems/") {
			picAndVid +=
				"<div id='salonItemVidDiv"+id+"' class='salonItemVid'>"+
					"<iframe width='300' height='250' src='"+vidUrl+"' frameborder='0' allowfullscreen></iframe>"+
					"<div id='salonItemVidEdit"+id+"' class='salonItemVidEdit editMedia'>"+
						"<input type='text' value='"+vidUrlTemp+"' />"+
					"</div>"+
				"</div>";
				picAndVidEdit = "";
				
		} else if (vidUrl == "") {
			picAndVid +=
				"<div id='salonItemPicDiv"+id+"' class='salonItemPic'>"+
					"<img class='salonItemPicture' src='"+picUrl+"' data-zoom-image='"+picUrl+"' id='salonItemPicture"+id+"'' alt='slika nije učitana'/>"+
					"<div id='salonItemPicEdit"+id+"' class='salonItemPicEdit editMedia'>"+
					"</div>"+					
				"</div>";
				picAndVidEdit = "";
				
		} else if(picUrl != "images/furnitureItems/" && vidUrl != "") {
			picAndVid +=
				"<div id='salonItemVidDiv"+id+"' class='salonItemVid'>"+
					"<iframe width='300' height='250' src='"+vidUrl+"' frameborder='0' allowfullscreen></iframe>"+
				"</div>"+
				"<div class='smallPicHolder'>"+
					"<div id='salonItemSmallPicDiv"+id+"' class='salonItemSmallPic'>"+
						"<a><img class='salonItemSmallPicture' src='"+picUrl+"' id='salonItemPicture"+id+"'' alt='slika nije učitana'/></a>"+
					"</div>"+
				"</div>";
		}
		
		listItems += 
		"<div id='salonItemDiv"+id+"' class='salonItem'>"+
				picAndVid+
				"<div id='salonItemTitleDescDiv"+id+"' class='salonItemTitleDesc'>"+
					"<div id='salonItemTitleDiv"+id+"' class='salonItemTitle'>"+
						"<h1>"+nameItem+"</h1>"+
					"</div>"+
					"<div id='salonItemDescDiv"+id+"' clas='salonItemDesc'>"+
						"<p class='itemDescription'>"+
							description+
						"</p>"+
					"</div>"+
				"</div>"+
				"<div id='salonItemSpecDiv"+id+"' class='salonItemSpec'>"+
					"<table id='tableSpecifications"+id+"' class='specTable'>"+
						"<tr>"+
							"<th colspan='2'>Specifikacije</th>"+
						"</tr>"+
						"<tr>"+
							"<th>Oznaka:</th>"+
							"<td class='tableValue' id='itemId"+id+"'>"+id.replace("-","")+"</td>"+
						"</tr>"+
						"<tr>"+
							"<th>Boja:</th>"+
							"<td class='tableValue'>"+color+"</td>"+
						"</tr>"+
						"<tr>"+
							"<th>Naziv kategorije:</th>"+
							"<td id='furnitureItemCategory"+id+"' class='tableValue'>"+furnitureCategoryName.replace("-","")+"</td>"+
						"</tr>"+
						"<tr>"+
							"<th>Proizvođač:</th>"+
							"<td class='tableValue'>"+producerName+"</td>"+
						"</tr>"+
						"<tr>"+
							"<th>Zemlja porekla:</th>"+
							"<td class='tableValue'>"+originCountry+"</td>"+
						"</tr>"+
						"<tr>"+
							"<th>Količina na lageru:</th>"+
							"<td class='tableValue'>"+amountInStorage+"</td>"+
						"</tr>"+
						"<tr>"+
							"<th>Godina proizvodnje:</th>"+
							"<td class='tableValue'>" + yearBuilt + "</td>"+
						"</tr>"+
					"</table>"+
				"</div>"+
				"<div id='salonItemBuyDiv"+id+"' class='salonItemBuy'>"+
					"<table border='1' class='tableItemPrice'>"+
						"<tr>"+
							"<td>Cena:</td>"+
							"<td id='itemPriceValue"+id+"' class='itemPriceValue'><h1>"+price+"</h1><h2>Rsd.</h2></td>"+
						"</tr>"+
						(isActionTime || activeUserRole == 'manager' || activeUserRole == 'admin'?(
						"<tr id='discountRow"+id+"' class='discountRow'>"+
							"<td>Na popustu:</td>"+
							"<td id='lowedPriceCell"+id+"' class='lowedPriceCell'><h1>"+discountPrice+"</h1><h2>Rsd.</h2></td>"+
						"</tr>"+
						"<tr>"+
							"<td>"+
								"Akcija traje od: <b>" + (isActionTime?(getDate(actionDateBegin)):"")+"</b>"+
							"</td>"+
							"<td>"+
								", do: <b>" + (isActionTime?(getDate(actionDateEnd)):"")+"</b>"+
							"</td>"+
						"</tr>"
						):"")+
						(activeUserRole=='buyer'?(							// samo ako je kupac, omoguci mu da kupuje
						"<tr>"+
							"<td colspan='2'>"+
								"<label>Količina:</label>"+
								"<input type='text' class='amountItemsInput' id='itemToCart"+id+"' value='1' />"+
							"</td>"+
						"</tr>"+
						"<tr>"+
							"<td class='buyCell'>"+
								"<button class='buyButton' onclick='addToShoppingCart(\""+id+"\")'><img src='images/buttons/buyItem.png'></button>"+
							"</td>"+
						"</tr>"):"")+
					"</table>"+
					(activeUserRole != 'buyer'?(
						"<div id='actionDiv"+id+"' class='actionDiv'>"+
								"<h4>Akcija</h4>"+
								"<label>Od:</label><input class='datePickerItem' type='text' id='fromDate"+id+"'/>"+
								"<label>, do:</label><input class='datePickerItem' type='text' id='toDate"+id+"'/><br>"+
								"<label>Procenat sniženja:</label><input onkeyup='handlePercentChange(\""+id+"\")' id='actionPercent"+id+"' type='text'/><label>%</label><br>"+
								"<button onclick='setFurnitureItemAction(\""+id+"\")'>Pokreni akciju!</button>"+
						"</div>"+
						"<button onclick='handleActionToggle(\""+id+"\")'><img src='images/buttons/discountBtn.png'/><br>Akcija</button>"

					):"")+
					(activeUserRole=='admin'?(
						//"<br><br>"+
						"<button onclick='editItem(\""+id+"\")'><img src='images/editItem.png'/><br>Izmeni</button>"+
						"<button onclick='deleteItem(\""+id+"\")'><img src='images/deleteItem.png'/><br>Obriši</button>"
					):"")+
					/*(activeUserRole == 'manager'?(
						"<button><img src='images/buttons/discountBtn.png'/><br>Akcija</button>"
					):"")+*/
				"</div>"+
			"</div>"+
			picAndVidEdit;
			
	});
		
	//console.log(listItems);
	// dodaj sve u listu nazad...
	$("#salonItemsDiv").empty();
	
	if(query=="action") {
		$("#salonItemsDiv").append("<h2 class='subtitle'>Akcijski rezultati:</h2>");
	}
		
	$("#salonItemsDiv").append(listItems);
	
	if($('.salonItemPicEdit').length) {
		$('.salonItemPicEdit').jqxFileUpload({ width: 300, uploadUrl: 'services/shopping/uploadPicture', fileInputName: 'fileToUpload' });
		
		$('.salonItemPicEdit').on('select', function (event) {
                var fileName = event.args.file;
                setPicUploadName(fileName);
    });

	}
	

	addPictureHandler();

	$(".salonItemPicture").error(function (){
		$(this).attr("src","images/defaultImage.png");
	});
	
	initDatePicker();

 }
 
 function toogleNewItem(divName) {
	 /*if(divName != 'addFurnitureDiv' && $("#addFurnitureDiv").is(":visible")) {
		$("#addFurnitureDiv").toggle(200);
	 }
	 if(divName != 'addFurnitureCategoryDiv' && $("#addFurnitureCategoryDiv").is(":visible")) {
		$("#addFurnitureCategoryDiv").toggle(200);
	 } */

	 $("#"+divName).toggle(200);
 }
 
 function setSessionUserName() {
		$.ajax({
			type: 'GET',
			url: 'services/login/getSessionUserName', 
			async: false,
			success: function(data) { 
				activeUserRole = data;
				return data;
			}
			//dataType: 'text/plain'
		});		
 }
 
 function addPictureHandler() {
		$("img[data-zoom-image]").elevateZoom({
			zoomType: "inner",
			cursor: "crosshair",
			zoomWindowFadeIn: 500,
			zoomWindowFadeOut: 750
		}).click(function (){
			var imgUrl = $(this).attr('src');
			$.fancybox.open(imgUrl);
		});

		$(".salonItemSmallPicture").click(function(){
			var imgUrl = $(this).attr('src');
			$.fancybox.open(imgUrl);
		});

 }
 
 function addFurnitureItem() {
		
	var id = $("#addFurnitureId").val();	
	var name = $("#addFurnitureName").val();
	var color = $("#addFurnitureColor").val();
	var producerName = $("#addFurnitureProducerName").val();
	var producerCountry = $("#addFurnitureProducerCountry").val();
	var price = $("#addFurniturePrice").val();
	var amountInStorage = $("#addFurnitureLagerCount").val();
	var furnitureCategoryName = $( "#addFurnitureCategorySelect option:selected" ).text();
	var yearBuilt = $("#addFurnitureYearBuilt").val();
	var desc = $("#descTextBox").val();
	var vidUrl = $("#videoLink").val();
	var picUrl = "images/furnitureItems/"+fileNameForUpload.toLowerCase();
		
	var furnitureItemData = JSON.stringify({
		"id": "-"+id,
		"name": name,
		"color": color,
		"producerName": producerName,
		"originCountry": producerCountry,
		"price": price,
		"amountInStorage": amountInStorage,
		"furnitureCategoryName": furnitureCategoryName,
		"yearBuilt": yearBuilt,
		"sellingSalonRegNum": activeSalonRegNum,
		"description": desc,
		"pictureUrl": picUrl,
		"vidUrl": vidUrl
	});
		
	$.ajax({
			type: 'POST',
			url: 'services/shopping/addShoppingItem', 
			async: false,
			data: furnitureItemData,
			success: function(data) { 
				if(data == "1") {
					$().toastmessage('showErrorToast', 'Nameštaj sa tom oznakom već postoji!');
					return;
				}
				$().toastmessage('showSuccessToast', 'Uspešno dodat komad nameštaja!');
				$("#addFurnitureForm input[type='text']").val("");
				fileNameForUpload = "";
				refreshSalon();
			},
			dataType: 'json',
			contentType: "application/json"
	});		

 }
 
 function refreshSalon() {
	$("#addFurnitureDiv input").val("");	// reset input polja
	$("#addFurnitureDiv").toggle(200);
	$("#addFurnitureDiv textarea").val("");
	openSalon(activeSalonRegNum,$("#salonHeadingDiv").text(),'normal');//refresh
 }
 
 function deleteItem(id) {
	$.ajax({
			type: 'POST',
			url: 'services/shopping/deleteShoppingItem', 
			async: false,
			data: id+"",
			success: function(data) { 
				if(data == "0") {
					$().toastmessage('showNoticeToast', 'Komad nameštaja obrisan!');
					$("#salonItemDiv"+id).fadeOut();
					$("#salonItemDiv"+id).css("display","none");
				}
			},
			dataType: 'text',
			contentType: "text/plain"
	});		
 }
 
 function editItem(id) {		
	var idVal = $("#itemId"+id).text();
	
	$("#tableSpecifications"+id+" .tableValue").each(function(index, element) {
		if(index != 0 && index != 2) {													// ne diraj id, nije za izmenu, jedinstven je, kategorije cemo rucno
			var val = $(this).text();
			$(this).text("");
			$(this).html("<input type='text' value='"+val+"'/>");		
		}
    });
	
	$("#furnitureItemCategory"+id).html("<select id='categorySelect"+id+"'></select>");
	$.each(categoryList, function (i, item) {
		$('#categorySelect'+id).append($('<option>', { 
			value: i,
			text : categoryList[i] 
		}));
	});
	
	$('select[name=\"'+id+'\"]').find('option:contains(\"'+id+'\")').attr("selected",true);
	//$('select[name="options"]').find('option:contains("Blue")').attr("selected",true);
	
	var titleText = $("#salonItemTitleDiv"+id).text();
	$("#salonItemTitleDiv"+id).html("<input type='text' value='"+titleText+"'/>");
	
	var descText = $("#salonItemDescDiv"+id+" p").text();
	$("#salonItemDescDiv"+id).html("<textarea cols='30' rows='7'>"+descText+"</textarea>");
	
	var priceValue = $("#itemPriceValue"+id+" h1").text();
	$("#itemPriceValue"+id+" h1").html("<input type='text' value='"+priceValue+"'/>");
			
	$("#salonItemDiv"+id).append("<button id='editBtn"+id+"'>Sačuvaj izmene</button>");
	
	$("#salonItemPicEdit"+id).toggle(200);
	$("#salonItemVidEdit"+id).toggle(200);
	$("#mediaEditDiv"+id).toggle(200);
	
	$("#editBtn"+id).click(function(e) {
       saveItemChanges(id, idVal); 
    });
 }
 
 function saveItemChanges(id, idVal) {
	
	var editValues = [];
	
	var titleText = $("#salonItemTitleDiv"+id+" input").val();
	var descText = $("#salonItemDescDiv"+id+" textarea").val();
	var priceValue = $("#itemPriceValue"+id+" input").val();
	var pictureUrl = fileNameForUpload;
	var vidUrl = $("#salonItemVidEdit"+id+" input").val();
	
	if(vidUrl == null || vidUrl == "" || vidUrl == undefined) {			// ako je multimedia, nema vid
		vidUrl = $("#salonItemVidMediaEdit"+id+" input").val();
	}
	
	if(pictureUrl == "") {		// ako mozda nije dirana izmena, probaj naci njen source
		pictureUrl = $("#salonItemPicture"+id).attr("src");
	}
	
	editValues.push(idVal);		// ubacujem id koji je ostao isti
	editValues.push($( "#categorySelect"+id+" option:selected" ).text());	// selektovan

	$("#tableSpecifications"+id+" .tableValue input").each(function(index, element) {
        var val = $(this).val();
		editValues.push(val);
    });
	
	if(pictureUrl != undefined) {
		if(!(pictureUrl.contains("images/furnitureItems/"))){		// ako vec ima
			pictureUrl = "images/furnitureItems/" + pictureUrl.toLowerCase();
		}
	}
	
	editValues.push(titleText);
	editValues.push(descText);
	editValues.push(activeSalonRegNum);
	editValues.push(priceValue);
	editValues.push(pictureUrl);	// izmna
	
	editValues.push(vidUrl);
		
	editData = JSON.stringify({		// glo var
		"id": "-"+editValues[0],
		"furnitureCategoryName": editValues[1],		
		"color": editValues[2],
		"producerName": editValues[3],
		"originCountry": editValues[4],
		"amountInStorage": editValues[5],
		"yearBuilt": editValues[6],
		"name": editValues[7],
		"description": editValues[8],
		"sellingSalonRegNum": editValues[9],
		"price": editValues[10],
		"pictureUrl": editValues[11],
		"vidUrl": editValues[12]
	});	

	$.ajax({
			type: 'POST',
			url: 'services/shopping/editShoppingItem', 
			async: false,
			data: editData,
			success: function(data) { 
				if(data == "0") {
					$().toastmessage('showSuccessToast', 'Uspešno izmenjeni podaci!');
					fileNameForUpload="";
				}
			},
			dataType: 'text',
			contentType: "application/json"
	});
	 
 }
 
 
 function addNewCategory() {
		var name = $("#addCategoryName").val();
		var description = $("#addCategoryDesc").val();
		
		$.ajax({
			type: 'POST',
			url: 'services/shopping/addNewCategory', 
			async: false,
			data: JSON.stringify({
				"name": "-"+name,
				"description": description
			}),
			success: function(data) { 
				if(data == "0") {
					$().toastmessage('showSuccessToast', 'Uspešno dodata nova kategorija!');
				}
			},
			dataType: 'text',
			contentType: "application/json"
	});

 }
 
 function addToShoppingCart(id) {
	
	var furnitureId = id;
	var furnitureItemsAmount  = $("#itemToCart"+id).val();
	var price;
	var itemName;
	var totalPrice =  0;
	var totalItems  =  0;
	var discountPercent = 0;
	var shoppingData = "";
	var message = "";
	
	$("#finishShoppingLink").css("display","block");
	
	if(globalPageName == "furnitureItems"){
			shoppingData = JSON.stringify({
				"furnitureId": furnitureId,
				"furnitureItemsAmount": furnitureItemsAmount
		});
	} else if(globalPageName == "additionalServices"){
			shoppingData = JSON.stringify({
				"additionalServiceId": id
		});
	}
	
	$.ajax({
			type: 'POST',
			url: 'services/shopping/addToShoppingCart', 
			async: false,
			data: shoppingData,
			success: function(data) { 
				$.each(data, function(shoppCartName,shoppCartVal) {				// za svaki od elemenata shopping cart-a
					if(shoppCartName == "totalItems") {
						totalItems = shoppCartVal;
					} else if(shoppCartName == "totalPrice") {
						totalPrice = shoppCartVal;
					}
					$.each(this, function() {													// za svaki od elemenata liste shoppingItem-a
						$.each(this, function(name, value) {								// za svaki od vrednosti shoppingItem-a
							if(name == "furnitureItem") {
								$.each(this, function(furnItemName, furnItemVal) {	// za svaki od atributa furnitureItem
									if(furnItemName == "price") {
										price  = furnItemVal;
									} else if(furnItemName == "name") {
										itemName = furnItemVal;
									}
									//console.log("--- name: " + furnItemName + ", value: " + furnItemVal);
								});
							}
							//console.log("name: " + name + ", value: " + value);
						})
					})
				});
				if(globalPageName == "furnitureItems"){
					$().toastmessage('showSuccessToast','Dodato u korpu: '+ furnitureItemsAmount+'x '+itemName);
				} else {
					$().toastmessage('showSuccessToast','Dodato u korpu: '+ id.substring(1));					
				}
				$("#totalPriceDiv").html('Ukupno: ' + totalPrice + ' Rsd.');
				$("#totalItemsLink").text(totalItems+" predmeta");
			},
			dataType: 'json',
			contentType: "application/json"
	});
 }
 
function initDatePicker() {
	$(".datePickerItem").datepicker({
		dateFormat: 'dd-mm-yy',
		inline: true,
		minDate: new Date()
	});
	
}

function handleActionToggle(id) {
	$("#actionDiv"+id).toggle(200);
	//$("#discountRow"+id).toggle(200);
	$(".actionDiv input").val("");		//reset
	
	handlePercentChange(id);
}

function handlePercentChange(id) {
	var percentVal = $("#actionPercent"+id).val();
	var currentPrice = $("#itemPriceValue"+id+" h1").text();
	

	currentPrice -= currentPrice*(percentVal/100);

	if(percentVal != "") {
		$("#itemPriceValue"+id+" h1").css("text-decoration","line-through");
	} else {
		$("#itemPriceValue"+id+" h1").css("text-decoration","none");
	}
	
	$("#lowedPriceCell"+id+" h1").text(currentPrice);
}

function setFurnitureItemAction(id){
	
	var actionData = JSON.stringify({
			id: id,
			actionDateBegin: $("#fromDate"+id).datepicker("getDate"),
			actionDateEnd: $("#toDate"+id).datepicker("getDate"),
			discountPercent: $("#actionPercent"+id).val()
	});
	
	$.ajax({
		type: 'POST',
		url: 'services/shopping/setFurnitureItemAction',
		async: false,
		data: actionData,
		success: function(data) {
			$().toastmessage("showSuccessToast","Uspešno pokrenuta akcija!");
			$("#discountRow"+id).css("visible","true");
		},
		contentType: "application/json"
		//data: 'text'
	});
}

function getDate(now) {
    var year    = now.getFullYear();
    var month   = now.getMonth()+1; 
    var day     = now.getDate();
    var hour    = now.getHours();
    var minute  = now.getMinutes();
    var second  = now.getSeconds(); 
    
	if(month.toString().length == 1) {
        var month = '0'+month;
    }
    if(day.toString().length == 1) {
        var day = '0'+day;
    }   
	
    var date = day+'/'+month+'/'+year;   
    return date;
}

function handleSearchCrit(select) {
	if($(select).val() == 2) {											// dodatne usluge
		
		searchQueryKind = "furnitureItems";
		searchQueryType = "name";
		
		if($("#fromToPriceDiv").is(":visible")) {
			$("#fromToPriceDiv").toggle(200);
		}
		if($("#ascDescSelect").is(":visible")) {
			$("#ascDescSelect").toggle(200);
		}
		if($("#furnitureCriteriaSelect").is(":visible")) {
			$("#furnitureCriteriaSelect").toggle(200);
		}
		$("#additionalServSelect").toggle(200);
		
		if($("#searchInput").is(":hidden")) {
			$("#searchInput").toggle(200);
		}

	} else {
		
		searchQueryKind = "additionalServices";
		searchQueryType = "name";		

		$("#additionalServSelect").toggle(200);
		$("#furnitureCriteriaSelect").val(1);		// reset
		$("#furnitureCriteriaSelect").toggle(200);
	}
}

function handleSearchSubCrit(select){
	if($(select).val() == 2) {
		
		searchQuerySubType = "price";		
		
		$("#fromToPriceDiv").toggle(200);
		if($("#ascDescSelect").is(":visible")) {	
			$("#ascDescSelect").toggle(200);
		}
		if($("#searchInput").is(":visible")) {
			$("#searchInput").toggle(200);
		}
		
	} else if($(select).val() == 3) {
		
		searchQuerySubType = "amountInStorage";		

		$("#ascDescSelect").toggle(200);
		if($("#fromToPriceDiv").is(":visible")) {			
			$("#fromToPriceDiv").toggle(200);
		}
		if($("#searchInput").is(":visible")) {
			$("#searchInput").toggle(200);
		}
	} else {
		if($("#ascDescSelect").is(":visible")) {	
			$("#ascDescSelect").toggle(200);
		}
		if($("#fromToPriceDiv").is(":visible")) {			
			$("#fromToPriceDiv").toggle(200);
		}
		if($("#searchInput").is(":hidden")) {
			$("#searchInput").toggle(200);
		}
	}
		
}

function handleMediaToggle(inputField) {
	if(inputField.id == "pictureCBox") {
		$("#addPictureDiv").toggle(200);
	} else {
		$("#addVideoDiv").toggle(200);
	}
}

function buildQueryAndSearch() {
	if($("#searchChoice").val() == 1) {		// furn item
		searchQueryKind = "furnitureItems";
		
	if($("#furnitureCriteriaSelect").val() == 1) {
		searchQueryType = "name";
		searchQueryValue = $("#searchInput").val();
	} else if($("#furnitureCriteriaSelect").val() == 2) {
		searchQueryType = "price";
		searchQueryValue = $("#searchInput").val();
		
		searchQueryValue = $("#fromPriceRange").val()+"-"+
		$("#toPriceRange").val();
		
	} else if($("#furnitureCriteriaSelect").val() == 3) {
		searchQueryType = "amountInStorage";
		
		if($("#ascDescSelect").val() == 1) {
			searchQueryValue = "DESC";
		}else {
			searchQueryValue = "ASC";
		}
			
	} else if($("#furnitureCriteriaSelect").val() == 4) {
		searchQueryType = "description";	
		searchQueryValue = $("#searchInput").val();
	} else if($("#furnitureCriteriaSelect").val() == 5) {
		searchQueryType = "category";
		searchQueryValue = $("#searchInput").val();
	} else if($("#furnitureCriteriaSelect").val() == 6) {
		searchQueryType = "producerCountry";	
		searchQueryValue = $("#searchInput").val();
	} else if($("#furnitureCriteriaSelect").val() == 7) {
		searchQueryType = "yearBuilt";	
		searchQueryValue = $("#searchInput").val();
	} else if($("#furnitureCriteriaSelect").val() == 8) {
		searchQueryType = "color";	
		searchQueryValue = $("#searchInput").val();
	}else if($("#furnitureCriteriaSelect").val() == 9) {
		searchQueryType = "producerName";
		searchQueryValue = $("#searchInput").val();

		} 
	} else {													 // add serv
		searchQueryKind = "additionalServices";
		searchQueryValue = $("#searchInput").val();
		
		if($("#additionalServSelect").val() == 1) {	// po nazivu
			searchQueryType = "name";
		} else {
			searchQueryType = "description";		// po opisu
		}
	}
	searchQuery = searchQueryType+";"+searchQueryValue;
	search();		// call search
}

function search() {
	if(searchQueryKind == "furnitureItems") {
		openSalon('PRETRAGA NAMEŠTAJA','PRETRAGA NAMEŠTAJA',searchQuery);
	} else if(searchQueryKind == "additionalServices") {
		openAdditionalServices(searchQuery);
	}
}

// -------------------- additionlServices


 function openAdditionalServices(query) {
		
	var itemToHide = "";
	globalPageName = "additionalServices";

	
	if($("#salonDiv").is(":visible")) {
		itemToHide = "salonDiv";
	} else {
		itemToHide = "salonsGridDiv";
	}
	
	$("#"+itemToHide).fadeOut('slow', function() { 
		
		$("#salonDiv").css("display","block"); 
		$("#addBtnDiv strong").text("Nova dodatna usluga");
		$("#addBtnDiv button").attr("onclick","toogleNewItem('addASWrapperDiv')");
		
		// saljem zahtev serveru da mi uruci sve iteme za dati salon   
		$.ajax({
			type: 'POST',
			url: 'services/shopping/getAdditionalServices', 
			data: query,
			async: false,
			success: function(data) { 
				renderAdditionalServices(data);   
				//alert('data: ' + data);  a 
			},
			contentType: 'text/plain',
			dataType: 'json'
		});		
		
		$(this).replaceWith( $("#salonDiv") );

		scroll(0,0);
	});
}


 function renderAdditionalServices(data) {
	//alert("usao u render.."); 
	
	$("#salonHeadingDiv").text("Dodatne usluge");
	$("#salonNamePath").text("Dodatne usluge");

	
	
	var listItems = "";
	
	var id = "";			//  name za additionalService
	var description = "";
	var price = 0;
	
	setSessionUserName(activeUserRole);
		
	$.each(data, function() {
		$.each(this, function(name, value) {
			if(name == "name") {
				id = value;
			} else if(name == "description") {
				description = value;
			} else if(name == "price") {
				price = value;	
			} 
		});
		
		
		listItems += 
		"<div id='additionalService"+id+"' class='salonItem'>"+
				"<div id='additionalServiceTitleDescDiv"+id+"' class='salonItemTitleDesc'>"+
					"<div id='additionalServiceTitleDiv"+id+"' class='additionalServiceTitle'>"+
						"<h1>"+id.substr(1)+"</h1>"+
					"</div>"+
					"<div id='additionalServiceDescDiv"+id+"' clas='salonItemDesc'>"+
						"<p class='additionalServiceDescription'>"+
							description+
						"</p>"+
					"</div>"+
				"</div>"+
				"<div id='additionalServiceBuyDiv"+id+"' class='salonItemBuy'>"+
					"<table border='1' class='tableItemPrice'>"+
						"<tr>"+
							"<td>Cena:</td>"+
							"<td id=addSerPrice"+id+"' class='itemPriceValue'><h1 id='asPrice"+id+"'>"+price+"</h1><h2>Rsd.</h2></td>"+
						"</tr>"+
						(activeUserRole=='buyer'?(							// samo ako je kupac, omoguci mu da kupuje
						"<tr>"+
							"<td colspan='2' class='addServAmount'>"+
								"<label>Količina:</label>"+
								"<input type='text' class='amountItemsInput' id='additionalServiceToCart"+id+"' value='1' />"+
							"</td>"+
						"</tr>"+
						"<tr>"+
							"<td class='buyCell'>"+
								"<button class='buyButton' onclick='addToShoppingCart(\""+id+"\")'><img src='images/buttons/buyItem.png'></button>"+
							"</td>"+
						"</tr>"):"")+
					"</table>"+
					(activeUserRole=='admin'?(
						"<button onclick='editAdditionalService(\""+id+"\")'><img src='images/editItem.png'/><br>Izmeni</button>"+
						"<button onclick='deleteAdditionalService(\""+id+"\")'><img src='images/deleteItem.png'/><br>Obriši</button>"
					):"")+
				"</div>"+
			"</div>";
	});

	
	$("#salonItemsDiv").empty();
			
	$("#salonItemsDiv").append(listItems);
	
}

function editAdditionalService(id) {
	
	var idVal = $("#itemId"+id).text();
			
	var descText = $("#additionalServiceDescDiv"+id+" p").text();
	$("#additionalServiceDescDiv"+id).html("<textarea cols='70' rows='7'>"+descText+"</textarea>");
	
	var priceValue = $("#asPrice"+id).text();
	$("#asPrice"+id).html("<input type='text' value='"+priceValue+"'/>");
			
	$("#additionalService"+id).append("<button id='editBtn"+id+"' class='editAdditionalServiceBtn'>Sačuvaj izmene</button>");
	
	$("#editBtn"+id).click(function(e) {
       saveAdditionalServicesChanges(id, idVal); 
    });

}

function saveAdditionalServicesChanges(id, idVal) {
	
	var editValues = [];
	
	var titleText = $("#additionalServiceTitleDiv"+id+" h1").text();
	var descText = $("#additionalServiceDescDiv"+id+" textarea").val();
	var priceValue = $("#asPrice"+id+" input").val();
	
	editValues.push(titleText);
	editValues.push(descText);
	editValues.push(priceValue);
	
	editData = JSON.stringify({		// glo var
		"name": "-"+editValues[0],
		"description": editValues[1],
		"price": editValues[2]
	});	

	$.ajax({
			type: 'POST',
			url: 'services/shopping/editAdditionalService', 
			async: false,
			data: editData,
			success: function(data) { 
				if(data == "0") {
					$().toastmessage('showSuccessToast', 'Uspešno izmenjeni podaci!');
				}
			},
			dataType: 'text',
			contentType: "application/json"
	});

}

 function deleteAdditionalService(id) {
	$.ajax({
			type: 'POST',
			url: 'services/shopping/deleteAdditionalService', 
			async: false,
			data: id+"",
			success: function(data) { 
				if(data == "0") {
					$().toastmessage('showNoticeToast', 'Komad nameštaja obrisan!');
					$("#additionalService"+id).fadeOut(200);
					$("#additionalService"+id).css("display","none");
				}
			},
			dataType: 'text',
			contentType: "text/plain"
	});		
 }

 function addAdditionalService() {
		
	var id = $("#addAdditionalServiceId").val();	
	var description = $("#additionalServiceDescTextBox").val();
	var price = $("#addAdditionalServicePrice").val();
		
	var furnitureItemData = JSON.stringify({
		"name": "-"+id,
		"description": description,
		"price": price
	});
		
	$.ajax({
			type: 'POST',
			url: 'services/shopping/addAdditionalService', 
			async: false,
			data: furnitureItemData,
			success: function(data) { 
				if(data == "1") {
					$().toastmessage('showErrorToast', 'Dodatna usluga sa tom oznakom već postoji!');
					return;
				}
				$().toastmessage('showSuccessToast', 'Uspešno dodata dodatna usluga!');
				refreshAdditionalService();
				},
			dataType: 'json',
			contentType: "application/json"
	});		

 }

 function refreshAdditionalService() {
	$("#addAdditionalService input").val("");
	$("#addAdditionalService textarea").val("");
	$("#addAdditionalService").toggle(200);
	openAdditionalServices("normal");
 }
 
// --------------------- additionalServices

//--------------------- category

 function openCategories(query) {
		
	var itemToHide = "";
	globalPageName = "categories";

	
	if($("#salonDiv").is(":visible")) {
		itemToHide = "salonDiv";
	} else {
		itemToHide = "salonsGridDiv";
	}
	
	$("#"+itemToHide).fadeOut('slow', function() { 
		
		$("#salonDiv").css("display","block"); 
		$("#addBtnDiv strong").text("Nova kategorija");
		$("#addBtnDiv button").attr("onclick","toogleNewItem('addFurnitureCategoryDiv')");

		// saljem zahtev serveru da mi uruci sve iteme za dati salon   
		$.ajax({
			type: 'POST',
			url: 'services/shopping/getCategories', 
			data: query,
			async: false,
			success: function(data) { 
				renderCategories(data);				
				//alert('data: ' + data);  a 
			},
			contentType: 'text/plain',
			dataType: 'json'
		});		
		
		$(this).replaceWith( $("#salonDiv") );

		scroll(0,0);
	});
}

 function renderCategories(data) {
	//alert("usao u render.."); 
	
	$("#salonHeadingDiv").text("Kategorije");
	$("#salonNamePath").text("Kategorije");

	
	
	var listItems = "";
	
	var id = "";			//  name za additionalService
	var description = "";
	var price = 0;
	var tempList=[];
	
	setSessionUserName(activeUserRole);
		
	$.each(data, function() {
		$.each(this, function(name, value) {
			if(name == "name") {
				id = value;
			} else if(name == "description") {
				description = value;
			}
		});
		
		tempList.push(id);
		
		listItems += 
		"<div id='category"+id+"' class='salonItem'>"+
				"<div id='categoryTitleDescDiv"+id+"' class='salonItemTitleDesc'>"+
					"<div id='categoryTitleDiv"+id+"' class='additionalServiceTitle'>"+
						"<h1>"+id.substr(1)+"</h1>"+
					"</div>"+
					"<div id='categoryDescDiv"+id+"' clas='salonItemDesc'>"+
						"<p class='categoryDescription'>"+
							description+
						"</p>"+
					"</div>"+
				"</div>"+
				"<div id='categoryBuyDiv"+id+"' class='salonItemBuy'>"+
					"<table border='1' class='tableItemPrice'>"+
						"<tr>"+
							"<td colspan='2'><button onclick='setAndSearchCategories(\""+id+"\")'>Ovori kategoriju</button></td>"+
						"</tr>"+
					"</table>"+
					(activeUserRole=='admin'?(
						"<button onclick='editCategory(\""+id+"\")'><img src='images/editItem.png'/><br>Izmeni</button>"+
						"<button onclick='deleteCategory(\""+id+"\")'><img src='images/deleteItem.png'/><br>Obriši</button>"
					):"")+
				"</div>"+
			"</div>";
	});

	$("#salonItemsDiv").empty();
			
	$("#salonItemsDiv").append(listItems);
	
	categoryList = tempList;
	
}

function setAndSearchCategories(id) {
	searchQueryKind="furnitureItems";
	searchQueryType = "category";
	searchQueryValue = id;
	searchQuery = searchQueryType+";"+searchQueryValue;
	search();
}

 function deleteCategory(id) {
	$.ajax({
			type: 'POST',
			url: 'services/shopping/deleteCategory', 
			async: false,
			data: id+"",
			success: function(data) { 
				if(data == "0") {
					$().toastmessage('showNoticeToast', 'Kategorija obrisana!');
					$("#category"+id).fadeOut(200);
					$("#category"+id).css("display","none");
				}
			},
			dataType: 'text',
			contentType: "text/plain"
	});		
 }

 
 function editCategory(id) {
	
	var idVal = "-"+$("#categoryTitleDiv"+id).text();
			
	var descText = $("#categoryDescDiv"+id+" p").text();
	$("#categoryDescDiv"+id).html("<textarea cols='70' rows='7'>"+descText+"</textarea>");
	
	$("#category"+id).append("<button id='editBtn"+id+"' class='editCategoryBtn'>Sačuvaj izmene</button>");
	
	$("#editBtn"+id).click(function(e) {
       saveCategoryChanges(id, idVal); 
    });

}

 
 function saveCategoryChanges(id, idVal) {
	
	var editValues = [];
	
	var titleText = $("#categoryTitleDiv"+id+" h1").text();
	var descText = $("#categoryDescDiv"+id+" textarea").val();
	
	editValues.push(titleText);
	editValues.push(descText);
	
	editData = JSON.stringify({		// glo var
		"name": "-"+editValues[0],
		"description": editValues[1]
	});	

	$.ajax({
			type: 'POST',
			url: 'services/shopping/editCategory', 
			async: false,
			data: editData,
			success: function(data) { 
				if(data == "0") {
					$().toastmessage('showSuccessToast', 'Uspešno izmenjeni podaci!');
				}
			},
			dataType: 'text',
			contentType: "application/json"
	});

}


//------------------------category
function clone(obj) {
    if (null == obj || "object" != typeof obj) return obj;
    var copy = obj.constructor();
    for (var attr in obj) {
        if (obj.hasOwnProperty(attr)) copy[attr] = obj[attr];
    }
    return copy;
}

String.prototype.contains = function(it) { return this.indexOf(it) != -1; };		// provera da li sadrzi drugi string..