<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Menu</title>

<style>
	#jqxMenu a {
		color: black !important;
	}
</style>

</head>
<body>
        <script type="text/javascript">
            $(document).ready(function () {
                // Create a jqxMenu
                $("#jqxMenu").jqxMenu({ width: '100%', height: '30px'});
                $("#jqxMenu").css('visibility', 'visible');
            });
        </script>
        
        <div id='jqxWidget' style='height: 100%;'>	
            <div id='jqxMenu' style='visibility: hidden;'>
            <div id="searchDiv">
               	<h3><b>Pretraga:</b></h3>
               	<select id="searchChoice" onchange="handleSearchCrit(this)">
               		<option value="1">Komadi nameštaja</option>
               		<option value="2">Dodatne usluge</option>
               	</select>
               	<select id="furnitureCriteriaSelect" class="furnitureSearch" onchange="handleSearchSubCrit(this)">
               		<option value="1">po nazivu</option>
               		<option value="2">po opsegu cene</option>
               		<option value="3">po raspoloživoj količini</option>
               		<option value="4">po opisu</option>
               		<option value="5">po tipu</option>
               		<option value="6">po zemlji proizvodnje</option>
               		<option value="7">po godini proizvodnje</option>
               		<option value="8">po boji</option>
               		<option value="9">po nazivu proizvođača</option>
               	</select>
               	<select id="additionalServSelect" class="addServSearch">
               		<option value="1">po nazivu</option>
               		<option value="2">po opisu</option>
               	</select>
               	<div id="fromToPriceDiv" class="furnitureSearch">
               		od: <input id="fromPriceRange" type="text" class="priceRangeInput">
               		, do: <input id="toPriceRange" type="text" class="priceRangeInput">Rsd.
               	</div>
               	<select id="ascDescSelect" class="furnitureSearch">
               		<option value="1">Od više ka manje</option>
               		<option value="2">Od manje ka više</option>
               	</select>
               	<input type="text" id="searchInput"/>  
               	<button id="searchBtn" onclick="buildQueryAndSearch()">
               		<img src="images/buttons/search.png" alt="slika nije ucitana">
               	</button>
            </div>
                <ul>
                    <li><a href=""><b>&#8962;</b> Saloni</a>
                    	<ul>
                    	     <c:forEach var="item" items="${sessionScope.salons.salons}" varStatus="status">
                    	     	<li>
                    	     		<a href="javascript:openSalon(${item.registrationNumber},'${item.name}','normal')">
                    	     			${item.name}
                    	     		</a>
                    	     	</li>
                    	     </c:forEach>
                    	</ul>
                    </li>
                    <li><a>Akcijska prodaja</a>
                    	<ul>
                    	     <c:forEach var="item" items="${sessionScope.salons.salons}" varStatus="status">
                    	     	<li>
                    	     		<a href="javascript:openSalon(${item.registrationNumber},'${item.name}','action')">
                    	     			${item.name}
                    	     		</a>
                    	     	</li>
                    	     </c:forEach>
                    	</ul>                    
                    </li>
                    <li><a href="javascript:openAdditionalServices('normal')">Dodatne usluge</a></li>
                    <li><a href="javascript:openCategories('normal')">Kategorije</a></li>
                </ul>
            </div>
        </div>
</body>
</html>