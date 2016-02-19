<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Add category</title>
</head>

<body>
<div id="addCATWrapperDiv">
	<div id="addFurnitureCategoryDiv" class="dashboardBodyItem">
			<table border="1">
				<tr>
					<th>Naziv kategorije:</th>
					<td><input type="text" id="addCategoryName"></td>																	
				</tr>
				<tr>
					<th>Opis kategorije:</th>
					<td><input type="text" id="addCategoryDesc"></td>																	
				</tr>
				<!-- <tr>
					<th>Podkategorija:</th>
					<td><input type="text" id="addSubCategory"></td>																	
				</tr>	 -->							
			</table> 
			<button onclick="addNewCategory()">Dodaj novu kategoriju!</button>
	</div>
</div>	

</body>
</html>

