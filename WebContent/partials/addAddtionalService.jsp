<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Add additional service</title>
</head>

<body>
<div id="addASWrapperDiv">
	<div id="addAdditionalService" >
		<table border="1">
			<tr>
			<td>
				<table border="1">
					<tr>
						<th>Naziv:</th>
						<td><input type="text" name="id" id="addAdditionalServiceId"></td>
					</tr>
					<tr>
						<th>Cena:</th>
						<td><input type="text" name="color" id="addAdditionalServicePrice"></td>
					</tr>
				</table>
			</td>
			<td>
				<span>Opis:</span>
				<textarea rows="4" cols="30" id="additionalServiceDescTextBox"></textarea>
				<hr>
				<button onclick="addAdditionalService()">Dodaj dodatnu uslugu!</button>
				
			</td>
		</tr>
		</table>	
	</div>
	<div class="dashboardButtonDiv additionalServicesBtn">
		<button >
			<img src="images/additionalServices.png"/>
		</button>
		<br>
		<strong>Nova dodatna usluga</strong>
	</div>										
</div>	

</body>
</html>