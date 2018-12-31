<%-- 
    Document   : footer.jsp
    Created on : Sep 24, 2015, 6:47:16 PM
    Author     : xl
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="styles/headFoot.css" type="text/css" />

    </head>
    <body>
        <c:set var="now" value="<%= new java.util.Date()%>"/>
        <p id="footer"> &copy; Copyright <fmt:formatDate type = "date" value = "${now}"/> Twitter </p>
    </body>
</html>



