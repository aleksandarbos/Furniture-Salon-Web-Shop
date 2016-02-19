<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>report</title>
</head>

<body>

<div id="reportWrapper">
	<div id="innerReportWrapper">
	<h1>Izveštaj</h1><hr><br>
	
	<select onchange="toggleReport()"> 
		<option>Izveštaj o prodaji po kategorijama</option>
		<option>Izveštaj o ukupnoj prodaji po danima</option>
	</select>
	<br><br>
	<div id="datePickDiv">
		OD:<input type="text" id="fromReportDate" class='reportDate'>, 
		DO:<input type="text" id="toReportDate" class='reportDate'>
	</div><br>
	<div id="reportCategorySelect">
	Kategorija: <select id="reportCategorySelect">
		<c:forEach items="${sessionScope.categories.furnitureCategories}" var="item" varStatus="status">
			<option value="item">${item.name}</option>
		</c:forEach>
	</select>
	<br><br>
	</div>
	
	<button onclick="generateReport()">Generiši izveštaj!</button> 
	
	<div id="reportByDay">
	</div>

	<div id="reportByCategory">
	
	</div>
	
	</div>
</div>
	
	
	

</body>
</html>