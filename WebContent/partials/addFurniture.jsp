<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Add Furniture Item</title>
</head>

<body>
	<div id="addFurnitureDiv" class="dashboardBodyItem">
		
		<table border="1">
			<tr>
			<td>
				<table border="1">
					<tr>
						<th>Oznaka:</th>
						<td><input type="text" name="id" id="addFurnitureId"></td>
					</tr>
					<tr>
						<th>Naziv:</th>
						<td><input type="text" name="name" id="addFurnitureName"></td>
					</tr>
					<tr>
						<th>Boja:</th>
						<td><input type="text" name="color" id="addFurnitureColor"></td>
					</tr>
					<tr>
						<th>Naziv proizvođača:</th>
						<td><input type="text" name="producerName" id="addFurnitureProducerName"></td>
					</tr>
					
					<tr>
						<th>Zemlja proizvođača:</th>
						<td><input type="text" name="originCountry" id="addFurnitureProducerCountry"></td>
					</tr>
					<tr>
						<th>Cena</th>
						<td><input type="text" name="price" id="addFurniturePrice"></td>
					</tr>
					<tr>
						<th>Količina na lageru:</th>
						<td><input type="text" name="amountInStorage" id="addFurnitureLagerCount"></td>
					</tr>
					<tr>
						<th>Kategorija:</th>
						<td>
							<select id="addFurnitureCategorySelect">
								<c:forEach items="${sessionScope.categories.furnitureCategories}" var="item" varStatus="status">
									<option value="item">${item.name}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th>Godina proizvodnje:</th>
						<td><input type="text" name="yearBuilt" id="addFurnitureYearBuilt"></td>
					</tr>
				</table>
			</td>
			<td>
				<span>Opis:</span>
				<textarea rows="4" cols="30" id="descTextBox"></textarea>
				<hr>
				<input type="checkbox" id="pictureCBox" onchange="handleMediaToggle(this)">Slika</<input>
				&nbsp;i/ili&nbsp;
				<input type="checkbox" id="videoCBox" onchange="handleMediaToggle(this)">Video</<input>
				<div id="addPictureDiv" class="addMediaDiv">
					<h3>Dodaj sliku:</h3>
				</div>
				
				<div id="addVideoDiv" class="addMediaDiv">
					<h3>Dodaj video link:</h3>
					<input type="text" id="videoLink" placeholder="https://www.youtube.com/watch?v=8hcj9PTxdDk">
				</div>
				<br>
				<button onclick="addFurnitureItem()">Dodaj nameštaj!</button>
				
			</td>
		</tr>
		</table>	
	</div>

</body>
</html>