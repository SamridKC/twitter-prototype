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
        <%--<%@include file = "header.jsp"%>--%>
        <div id="mainDiv">

            <img src="images/Twitter.png" alt="Italian Trulli">
            <form action="membership" method="post">

                <input type="hidden" name="action" value= "forgotpassword">
                <input type="email" id="email" name="email" placeholder = 'Email' value ="" required>
                <span id="email_error" class="notVisible"></span>
                <span id="_email_error" class="isVisible">${emailError}</span>
                <br>

                <!--<label class="pad_top">Security Question</label>-->
                <select id="question" name="question" onchange = "questionBox();" value ="" onfocus="this.selectedIndex = -1" required>
                    <option value="" disabled selected>Select your security question</option>
                    <option value="1">What was the name of your first pet?</option>
                    <option value="2">What model was your first car?</option>
                    <option value="3">What was the name of your first school?</option>
                </select>
                <div class ="notVisible" id="boxdemo">
                    <input type='text' id = 'securityanswer' name = 'securityanswer' value="" required>
                    <span id="_answer_error" class="isVisible">${answerError}</span>
                </div>
                <span id="_userName_error" class="isVisible">${message}</span>
                <br>
                <input type="submit" name="submit" value="Submit">
            </form>

        </div>

    </body>
    <%@include file = "footer.jsp"%>
</html>

