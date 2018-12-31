/* 
 All your JS code must be here.
 */


function validateForm() {
  var valid = true;
  // check 1: Check to see if password and confirm password matches
  var password = document.getElementById("password");
  var confirmpassword = document.getElementById("confirmpassword");
  if (password.value !== confirmpassword.value) {
    document.getElementById("confirmpassword_error").innerHTML = "* Error! Password and confirm password do not match!";
    confirmpassword.style.backgroundColor = "yellow";
    //		error_password.style.display = "inline";
    confirmpassword_error.className = "isVisible";
    if (valid === true) {
      valid = false;
    }
  } else {
    confirmpassword_error.className = "notVisible";
    confirmpassword.style.backgroundColor = "white";
  }

  var getName = document.getElementById('fullname').value;
  if (getName.indexOf(' ') !== -1) {
    fullName_error.className = "notVisible";
    fullname.style.backgroundColor = "white";
  } else {
    document.getElementById("fullName_error").innerHTML = "* Full name is not valid";
    fullname.style.backgroundColor = "yellow";
    fullName_error.className = "isVisible";
    if (valid === true) {
      valid = false;
    }
  }
  // check 3: check to see if any input has single ' quotation mark in it
  if (getName.indexOf("'") !== -1) {
    document.getElementById("fullName_error").innerHTML = "* Full name has invalid characters!";
    fullname.style.backgroundColor = "yellow";
    fullName_error.className = "isVisible";
    //       error_message.innerHTML="Full name has invalid characters!";
    if (valid === true) {
      valid = false;
    }
  }

  var getUserName = document.getElementById('username').value;
  if (getUserName.indexOf("'") !== -1) {
    document.getElementById("userName_error").innerHTML = "* username has invalid characters!";
    username.style.backgroundColor = "yellow";
    userName_error.className = "isVisible";
    //       error_message.innerHTML="Full name has invalid characters!";
    if (valid === true) {
      valid = false;
    }
  }

  var getEmail = document.getElementById('email').value;

  if (getEmail.indexOf("'") !== -1) {
    document.getElementById("email_error").innerHTML = "* email has invalid characters!";
    email.style.backgroundColor = "yellow";
    email_error.className = "isVisible";
    //       error_message.innerHTML="Full name has invalid characters!";
    if (valid === true) {
      valid = false;
    }
  } else {
    email_error.className = "notVisible";
    email.style.backgroundColor = "white";
  }

  if (valid === false) {
    return valid;
  }

  // check 4

  var str = "* Error! Password must contain ";
  passwordStr = password.value;
  if (/[A-Z]/.test(passwordStr)) {
    confirmpassword_error.className = "notVisible";
    confirmpassword.style.backgroundColor = "white";
  } else {
    str += "uppercase,";
    document.getElementById("confirmpassword_error").innerHTML = str;
    confirmpassword.style.backgroundColor = "yellow";
    //		error_password.style.display = "inline";
    confirmpassword_error.className = "isVisible";
    valid = false;
  }
  if (/[a-z]/.test(passwordStr)) {
    if (valid !== false) {
      confirmpassword_error.className = "notVisible";
      confirmpassword.style.backgroundColor = "white";
    }
  } else {
    str += "lowercase,";
    document.getElementById("confirmpassword_error").innerHTML = str;
    confirmpassword.style.backgroundColor = "yellow";
    //		error_password.style.display = "inline";
    confirmpassword_error.className = "isVisible";
    if (valid === true) {
      valid = false;
    }
  }
  if (/[0-9]/.test(passwordStr)) {
    if (valid !== false) {
      confirmpassword_error.className = "notVisible";
      confirmpassword.style.backgroundColor = "white";
    }
  } else {
    str += "number";
    document.getElementById("confirmpassword_error").innerHTML = str;
    confirmpassword.style.backgroundColor = "yellow";
    //		error_password.style.display = "inline";
    confirmpassword_error.className = "isVisible";
    if (valid === true) {
      valid = false;
    }
  }
  return valid;
}

function questionBox() {
  document.getElementById("boxdemo").style.display = "block";
}
