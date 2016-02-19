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

	<c:forEach items="${sessionScope.reports.salons}" var="item" varStatus="status">
		<table border="1">a
			<tr>
				<th clospan='2'><h2>${item.name}</h2></th>
				<c:forEach items="${sessionScope.reports.reportsList}" var="bill">
					${bill.date}
				</c:forEach>				
			</tr>
		</table>
	</c:forEach>


</body>
</html>