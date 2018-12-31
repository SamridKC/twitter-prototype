<%-- 
    Replace this page with your own page.
    Replace main.css and main.jsp as well.
--%>


<!DOCTYPE html>
<html>

    <head>
        <meta charset="utf-8">
        <title>Mini Twitter</title>
        <link rel="stylesheet" href="styles/main.css" type="text/css" />
        <script src="includes/main.js"></script>
    </head>

    <body>
        <%@include file = "header.jsp"%>
        <div id="mainDiv">

            <img src="images/Twitter.png" alt="Italian Trulli">
           <!--<form action="membership" method="get" onsubmit="return validateForm();">-->
            <form action="membership" method="post" onsubmit="return validateForm();">

                <c:if test="${empty buttonVal}" >
                    <c:set var="buttonVal" value="Sign Up" />
                </c:if>
                

                <div id="errorMessage" class=" notVisible"></div>
                <h2> Join Twitter today.</h2>

                <!--      <label class="pad_top">Fullname:</label>-->
                <input type="text" id="fullname" name ="fullname" placeholder = 'Full Name' value ="${user.getFullName()}" required>
                <span id="fullName_error" class="notVisible"></span>
                <span id="_fullName_error" class="isVisible">${fullNameError}</span>
                <!-- <div id="errorDivFNspace" class="notVisible">Full name is not valid</div> -->
                <br>

                <!--<label class="pad_top">Username:</label>-->
                <c:choose>
                    <c:when test="${buttonVal == 'Update'}">
                        <input type="hidden" name="action" value="update">
                        <input type="text" id="username" name="username" placeholder = 'Username' value ="${user.getUserName()}" readonly>
                        <span id="userName_error" class="notVisible">*</span>
                        <span id="_userName_error" class="isVisible">${userNameError}</span>
                        <br>

                        <!--<label class="pad_top">Email:</label>-->
                        <input type="email" id="email" name="email" placeholder = 'Email' value ="${user.getEmail()}" readonly>
                        <span id="email_error" class="notVisible"></span>
                        <span id="_email_error" class="isVisible">${emailError}</span>
                        <br>
                    </c:when>
                    <c:otherwise>
                        <input type="hidden" name="action" value="signup">
                        <input type="text" id="username" name="username" placeholder = 'Username' value ="${user.getUserName()}" required>
                        <span id="userName_error" class="notVisible">*</span>
                        <span id="_userName_error" class="isVisible">${userNameError}</span>
                        <br>

                        <!--<label class="pad_top">Email:</label>-->
                        <input type="email" id="email" name="email" placeholder = 'Email' value ="${user.getEmail()}" required>
                        <span id="email_error" class="notVisible"></span>
                        <span id="_email_error" class="isVisible">${emailError}</span>
                        <br>
                    </c:otherwise>
                </c:choose>

                <!--<label class="pad_top">Password:</label>-->
                <input type="password" id="password" name="password" placeholder = 'Enter your Password' required>
                <span id="password_error" class="notVisible">*</span>
                <span id="_password_error" class="isVisible">${passwordError}</span>
                <br>

                <!--<label class="pad_top">Confirm Password:</label>-->
                <input type="password" id="confirmpassword" name="confirmpassword" placeholder = 'Confirm your Password' required >
                <span id="confirmpassword_error" class="notVisible"></span>
                <span id="_confirmpassword_error" class="isVisible">${confirmpasswordError}</span>
                <!-- <div id="errorDiv" class="notVisible"> Error! Password and confirm password do not match </div> -->
                <br>



                <!--<label class="pad_top">Date of Birth:</label>-->
                <input type="date" id="dateofbirth" name="dateofbirth" data-placeholder="Date of birth" aria-required="true" value ="${user.getBirthDate()}" required>
                <span id="dateofbirth_error" class="notVisible">*</span>
                <span id="_dateofbirth_error" class="isVisible">${dateofbirthError}</span>
                <br>

                <!--<label class="pad_top">Security Question</label>-->
                <select id="question" name="question" onchange = "questionBox();" value ="${user.getQuestionNo()}" onfocus="this.selectedIndex = -1" required>
                    <option value="" disabled selected>Select your security question</option>
                    <option value="1">What was the name of your first pet?</option>
                    <option value="2">What model was your first car?</option>
                    <option value="3">What was the name of your first school?</option>
                </select>
                <div class ="notVisible" id="boxdemo">
                    <input type='text' id = 'securityanswer' name = 'securityanswer' value="${user.answer}" required>
                    <span id="_answer_error" class="isVisible">${answerError}</span>
                </div>
                <br>

                <input type="submit" name="submit" value="${buttonVal}">
            </form>

        </div>

    </body>
    <%@include file = "footer.jsp"%>
</html>

