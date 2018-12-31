<%-- 
    Document   : home.jsp
    Created on : Sep 24, 2015, 6:47:02 PM
    Author     : xl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>




<!DOCTYPE html>
<html>
    <c:if test="${user == null}">
        <c:redirect url="membership?action=login"></c:redirect>
    </c:if>

    <%@include file = "header.jsp"%>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="styles/login.css" type="text/css" />
        <title>Mini Twitter</title>
    </head>
    <body>

        <div id ="MainDiv">
            <div id = "Left">
                <div id = "topLeft">
                    <br>
                    <br>
                    <br>
                    <p id = "Fname"> ${user.getFullName()}</p>  
                    <p> ${user.getEmail()} </p>
                    
                    <p>   
                    <a class = "disp"> Tweets </a>
                    <a class = "disp"> Following </a> 
                    <a class = "disp"> Followers </a> 
                    </p>
                    <p>
                        <a class = "disp"> ${numberT} </a>
                        <a class = "disp"> ${followed.size()}</a>
                        <a class = "disp"> ${followersCount} </a>
                    <p>
                </div>
                <div id = "bottomLeft">
                    <p id = "pTrend"> Trends </p>
                    <c:forEach items="${topTen}" var="var">
                        <p>
                            <a class = 'blueX' href="membership?action=gotoHashtag&hashtag=%23${var.substring(1)}"> ${var}
                            </a>
                        </p>

                    </c:forEach>
                </div>
            </div>

            <div id = "Right">
                <h4> Who to Follow? </h4>
                <c:set var="list" value= "${followed}"/>
                <c:forEach items="${allUsers}" var="var">
                    <c:if test = "${user.getEmail() != (var.getEmail())}">
                        <p> ${var.getFullName()} @${var.getUserName()}

                            <c:choose>
                                <c:when test='${fn:contains(list, var)}'>
                                    <a id="f" href="membership?action=unfollow&unfollowUser=${var.getEmail()}">Unfollow</a> </p>
                                </c:when>

                            <c:otherwise>
                                <a id="f" href="membership?action=follow&followUser=${var.getEmail()}">Follow</a> </p>
                            </c:otherwise>

                        </c:choose>
                    </c:if>


                </c:forEach>
            </div>

            <div id = "Middle">
                <div id = "topMiddle">
                    <form method="post" action ="membership">
                        <input type="hidden" name="action" value="tweetPost">
                        <textarea name="tweet" cols="50" rows="8" placeholder = "What's happening?" maxlength="280"></textarea>
                        <input type="submit" name="submitTweet" id = "tweetButton" value="Tweet">
                    </form>
                </div>

                <div id = "bottomMiddle">
                    <c:forEach items="${allTweets}" var="var">                       
                        <ul id="tweets" <span id ="tweetauthor"> ${var.getEmail()} </span><br>
                            <span id = "tweetdisplay">${var.getTweet()}
                                <c:if test="${user.getEmail() == (var.getEmail())}">
                                    <a id="d" href="membership?action=deleteTweet&amp;tweet=${var.getID()}">Delete</a>
                                </c:if>
                            </span>
                        </ul>
                    </c:forEach>
                </div>
            </div>

        </div>    
        <%@include file = "footer.jsp"%>    
    </body>

</html>
