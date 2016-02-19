<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Bill</title>
</head>
<body>

<div id="billWrapperDiv">
	<div id="billTableDiv">
	<table border="1">
		<tr>
			<th colspan="2"><h3>RAČUN</h3></th>
		</tr>
		<c:forEach items="${sessionScope.bill.shoppingCart.shoppingCartItems }" var="item" varStatus="status">
			<tr>
				<c:choose>
					<c:when test="${empty item.furnitureItem.name }">
						<th>Dodatna usluga:</th>
						<td>Naziv predmeta: <b>${item.additionalService.name}</b>, Cena: <b>${item.additionalService.price}</b> </td>					
					</c:when>
					<c:otherwise>
						<th>Predmet kupovine:</th>
						<td>Naziv predmeta: <b>${item.furnitureItem.name}</b>, salon:<b>Salon ${fn:substringBefore(item.furnitureItem.sellingSalonRegNum,'223344556')}</b> , količina:<b>${item.furnitureItemsAmount}</b> </td>
					</c:otherwise>
				</c:choose>
			</tr>
		</c:forEach>
		<tr>
			<th>Porez:</th>
			<td id="billTax"><b>${sessionScope.bill.tax*100 }%</b></td>
		</tr>
		<tr>
			<th>Ukupna cena:</th>
			<td id="billTotalPrice"><b>${sessionScope.shoppingCart.totalPrice+sessionScope.shoppingCart.totalPrice*sessionScope.bill.tax }</b> Rsd.<b>(sa PDV-om)</b></td>
		</tr>
		<tr>
			<th>Datum i vreme:</th>
			<td id="billDateAndTime"><b>${sessionScope.bill.date }</b></td>
		</tr>
		<tr>
			<th>Kupac:</th>
			<td><b>${sessionScope.user.name} ${sessionScope.user.surname }</b></td>
		</tr>
	</table>
	<button onclick="saveShopping()">Potvrdi kupovinu!</button> 
	</div>
</div>


</body>
</html>