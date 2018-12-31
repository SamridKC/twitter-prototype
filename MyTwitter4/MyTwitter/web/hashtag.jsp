<%-- 
    Document   : home.jsp
    Created on : Sep 24, 2015, 6:47:02 PM
    Author     : xl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
    <c:if test="${user == null}">
        <c:redirect url="membership?action=login"></c:redirect>
    </c:if>

    <%@include file = "header.jsp"%>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="styles/hashtag.css" type="text/css" />
        <title>Mini Twitter</title>
    </head>
    <body>

        <div id ="MainDiv">

            <div id = "Left">
            </div>

            <div id = "Right">
            </div>

            <div id = "Middle">
                <div id = "topMiddle">
                    <h1> ${hashtag}</h1>
                </div>
                <div id = "bottomMiddle">                    
                    <c:forEach items="${hashTweets}" var="var">                       
                        <ul id="tweets" <span id ="tweetauthor"> ${var.getEmail()} </span><br>
                            <span id = "tweetdisplay">${var.getTweet()}
                            </span>
                        </ul>
                    </c:forEach>
                </div>
            </div>


        </div>    
        <%@include file = "footer.jsp"%>    
    </body>

</html>
