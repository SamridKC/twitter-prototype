<%-- 
    Document   : login.jsp
    Created on : Sep 24, 2015, 6:44:58 PM
    Author     : xl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <%--<c:if test="${user != null}">--%>
        <%--<c:redirect url="/membership?action=login"></c:redirect>--%>
    <%--</c:if>--%>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> Mini Twitter </title>
        <link rel="stylesheet" href="styles/login.css" type="text/css" />
    </head>
    <body>
        <%@include file = "header.jsp"%>
        <div id="mainDiv">
            <!--        <div id="loginDiv">-->
            <form method="post" action ="membership">
                <input type="hidden" name="action" value="login">
                <h2>
                    Log in            
                </h2>


                <input type="text" id="email" name="email" placeholder = 'Email' required value ="${cookie.emailRemember.value}" >
                <span id="emailOfFollowedName_error" class="notVisible"></span>
                <!-- <div id="errorDivFNspace" class="notVisible">Full name is not valid</div> -->
                <br>

                <input type="password" id="password" name="password" placeholder = 'Password' required >
                <span id="loginPassword_error" class="notVisible"></span>
                <!-- <div id="errorDivFNspace" class="notVisible">Full name is not valid</div> -->
                <br>
                <p id ="errorLogin"> ${errorMessage} </p> 
                <input type="submit" name="submit" value="Log in">

                <input type="checkbox" name="remember" value="Remember" > Remember me
        
                <a href="forgotpassword.jsp">Forgot password?</a><br>
                <label class="pad_top"> New to Twitter? </label>
                <a href="signup.jsp">Sign up now</a>
            </form>
        </div>    
    </body>
    <%@include file = "footer.jsp"%>
</html>
