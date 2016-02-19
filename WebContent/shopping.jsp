<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Prodavnica</title>

<link rel="stylesheet" type="text/css" href="css/styles.css" />
<link rel="stylesheet" type="text/css" href="css/default.css" />
<link rel="stylesheet" type="text/css" href="css/component.css" />
<link rel="stylesheet" type="text/css" href="css/jquery.toastmessage.css" />
<link rel="stylesheet" type="text/css" href="plugins/fbox/source/jquery.fancybox.css?v=2.1.5" media="screen" />
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">

<link rel="stylesheet" href="jqwidgets/styles/jqx.base.css" type="text/css" />


<script type="application/javascript" src="js/jquery-1.11.3.min.js"></script>
<script src="js/modernizr.custom.js"></script>
<script type="text/javascript" src="js/shopping.js"></script>
<script type="text/javascript" src="js/login.js"></script>
<script type="text/javascript" src="js/jquery.toastmessage.js"></script>
<script src='js/jquery.elevatezoom.js'></script>
<script type="text/javascript" src="plugins/fbox/source/jquery.fancybox.js?v=2.1.5"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<script type="text/javascript" src="jqwidgets/jqxcore.js"></script>
<script type="text/javascript" src="jqwidgets/jqxmenu.js"></script>
<script type="text/javascript" src="jqwidgets/jqxbuttons.js"></script>
<script type="text/javascript" src="jqwidgets/jqxfileupload.js"></script>
<script type="text/javascript" src="jqwidgets/jqxpanel.js"></script>
<script type="text/javascript" src="jqwidgets/jqxscrollbar.js"></script>


</head>
<body>

<c:if test="${empty sessionScope.user}">
	<c:redirect url="./"></c:redirect>
</c:if>

<div id="wrapper">
	<div id="headerDiv">
			<div>
				<a href="">
					<img src="images/logo.png" id="logo"/>				
				</a>
				<div id="headerInfoDiv">
				<table border="0">
					<tr>    
						<td style="text-align: right;">
							<span>Dobrodošli: </span>
						</td>
						<td>
							<b>${sessionScope.user.name} ${sessionScope.user.surname } &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|</b>
						</td>
						<td>
							<a href="javascript:logOut()">Odjavi se!</a> 
						</td>						
						<td>
							&nbsp;&nbsp;
							<c:choose>
								<c:when test="${sessionScope.user.role == 'admin' }">
									<img src="images/roles/admin.jpg" class="avatarHeader"/>
								</c:when>
								<c:when test="${sessionScope.user.role == 'manager' }">
									<img alt="" src="images/roles/manager.jpg" class="avatarHeader">
								</c:when>
								<c:when test="${sessionScope.user.role == 'buyer' }">
									<img src="images/roles/buyer.jpg" class="avatarHeader">
								</c:when>
							</c:choose>
						</td>					
					</tr>
					<tr id="shoppingCartRow">
						<c:if test="${sessionScope.user.role == 'buyer' }">
							<td style="text-align: right;"> 
								<img alt="" src="images/shoppingCart.png" id="shoppingCartImage">						
							</td>
							<td> 
								Porudžbine:
								<a href="#" id="totalItemsLink">${sessionScope.shoppingCart.totalItems } porudžbina</a>&nbsp;&nbsp;&nbsp;|
							</td>
							<td colspan="2">
								<div id="totalPriceDiv">Ukupno: ${sessionScope.shoppingCart.totalPrice } Rsd.</div>
								<a id="finishShoppingLink" href="javascript:finishShopping()">Završi kupovinu!</a>
							</td>
						</c:if>
					</tr>
				</table>
			</div>
		</div>
	</div>
	
	<div id="menuDivSplit"></div>
	<div id="menuDiv"></div>
	<div id="dashboardContent4"></div>														
	<div id="dashboardContent5"></div>	
	<div id="dashboardContent6"></div>		
		
	<div class="main" id="salonsGridDiv"> 
		<br><br>
		<div class="salonsHeadingDiv">
			SALONI
		</div>
		<c:if test="${sessionScope.user.role != 'kupac'}">
			<div id="adminPanelHomeDiv" class="dashboardDiv">
				<div id="dashboardHomeButtons">
					<c:choose>
						<c:when test="${sessionScope.user.role == 'admin'}">
							<!--<div class="dashboardButtonDiv">
								<button id="addFurnitureDashBtn" onclick="toogleNewItem('addFurnitureCategoryDiv')">
									<img src="images/furnitureCategory.png"/>
								</button>
								<br>
								<strong>Nova kategorija</strong>
							</div>
							  <div class="dashboardButtonDiv">
								<button >
									<img src="images/additionalServices.png"/>
								</button>
								<br>
								<strong>Nova dodatna usluga</strong>
							</div>			-->							
							<div id="dashboardContent">
								<div id="dashAddItemDiv"></div>
								<div id="dashAddAdditServDiv"></div>
								<div id="dashboardCategory"></div>
							</div>
						</c:when>
						<c:when test="${sessionScope.user.role == 'manager' }">
							<div class="dashboardButtonDiv">
								<button onclick="toogleNewItem('dashboardContent5')">
									<img src="images/reportManager.png"/>
								</button>
								<br>
								<strong>Generiši izveštaj</strong>
							</div>
						</c:when>
					</c:choose>
				</div>
			</div>
		</c:if>
		<ul id="og-grid" class="og-grid">
			<c:forEach var="item" items="${sessionScope.salons.salons}" varStatus="status">
				<li>
					<a onclick="clickedSalon(salonLink${status.index+1}, ${item.registrationNumber}, '${item.name}');return false;" id="salonLink${status.index+1}" href="#"  data-largesrc="images/salons/${status.index+1}.jpg" data-title="${item.name}" data-description="Naziv salona: <b>${item.name}</b>.<br/> Adresa salona: <b>${item.address}</b>.<br/> Broj telefona: <b>${item.cellNumber}</b>.<br/> Email adresa: <b>${item.email}</b>.<br/> Adresa zvaničnog web-sajta: <b>${item.websiteAddress}</b>.">
						<img src="images/salons/thumbs/${status.index+1}.jpg" alt="img${status.index+1}"/>
					</a>
					<span id="salonName">${item.name}</span>
				</li>						
			</c:forEach>
		</ul>
	</div>

	<div id="salonDiv">
		<div id="salonsPathDiv">
				<span><a href=""><img src="images/homeIcon.png" id="urlIconHome"/></a></span>
				<span>&nbsp;&nbsp;/&nbsp;&nbsp;</span>
				<span id="salonNamePath">NAZIV OTVORENOG SALONA</span>
		</div>
		<br>
		<div class="salonsHeadingDiv" id="salonHeadingDiv">
				<span>NAZIV OTVORENOG SALONA</span>
		</div>
		<c:choose>
			<c:when test="${sessionScope.user.role == 'admin'}">
				<div id="adminPanelDiv" class="dashboardDiv">
					<div id="dashboardButtons">
						<div id="addBtnDiv" class="dashboardButtonDiv">
							<button onclick="toogleNewItem('addFurnitureDiv')">
								<img src="images/furnitureItemAdd.png"/>
							</button>
							<br>
							<strong>Novi nameštaj</strong>
						</div>
					</div>
					<div id="dashboardContent1">
					</div>
					<div id="dashboardContent2">
					</div>
					<div id="dashboardContent3">
					</div>		
				</div>
			</c:when>
			<c:when test="${sessionScope.user.role == 'manager'}">
				<div id="managerPanelDiv" class="dashboardDiv">
				</div>
			</c:when>
		</c:choose>
		<div id="salonItemsDiv"> <!-- ovde upadaju itemi iz js-a -->
			
		</div>
		<br>
	</div>
	<div id="footerDiv">
		<h5>Aleksandar Bošnjak. Web-projekat 2015.</h5>
	</div>
</div>
	<script src="js/grid.js"></script>
	<script>		
		$(function() {		// automatski se poziva na kraju loada
			Grid.init();
		});
	</script>
</body>
</html>