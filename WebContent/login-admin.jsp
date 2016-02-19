<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link rel="stylesheet" type="text/css" href="css/styles.css" />
<link rel="stylesheet" type="text/css" href="css/jquery.toastmessage.css" />


<script type="application/javascript" src="js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="js/login.js"></script>
<script type="text/javascript" src="js/jquery.toastmessage.js"></script>



</head>

<body>
   
<div id="loginDiv"> 
    <form method="get" id="loginForm">
        <table id="tableLogin">
            <tr class="windowHeading">
                <th id="tableHeadingCell" colspan="2">ADMIN-Prijava...</th>     
            </tr>
            <tr>
                <td>Korisničko ime:</td>
                <td><input type="text" id="uname"/></td>
            </tr>
            <tr>
                <td>Lozinka:</td>
                <td><input type="password" id="pass"/></td>
            </tr>
            <tr>
                <td>
                    <input type="submit" value="Pošalji" id="submitBtn"/>
                </td>
                <td>
                	<p id="loginMessage" class="error"><span>&nbsp;</span></p>
                </td>
            </tr>
        </table>
    </form>
</div>


</body>
</html>
