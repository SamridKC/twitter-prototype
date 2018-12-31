<%-- 
    Document   : header.jsp
    Created on : Sep 24, 2015, 6:47:09 PM
    Author     : xl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
        <link rel="stylesheet" href="styles/headFoot.css" type="text/css" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    </head>
    <body>
        <c:choose>
            <c:when test="${empty user}">
                <p id="header"> Mini Twitter </p>
            </c:when>
            <c:otherwise>
                <input type="hidden" name="action" value="signout">
                <p id="header"> Mini Twitter </p>
                <ul>
                    <li><a href="membership?action=login">Home</a></li>
                    <li><a href="membership?action=notification"> Notification</a></li>
                    <li><a href="membership?action=profile">Profile </a></li>
                    <li id = "signout"><a href="membership?action=signout">Sign out </a></li>
                    <li id = "signout">  
                        <form class="example" action="membership">
                            <button type="submit" ><i class="fa fa-search"></i></button>
                            <input type="hidden" name="action" value="search">
                            <input type="text" placeholder="Search.." name="search" >                           
                        </form>
                    </li>  
                </ul>
            </c:otherwise>
        </c:choose>           
    </body>
</html>
