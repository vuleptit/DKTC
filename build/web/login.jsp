<%-- 
    Document   : header
    Created on : Mar 21, 2019, 8:37:48 PM
    Author     : Win7
--%>

<%@page import="java.util.List"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
    if(session.getAttribute("userName")!=null)
        response.sendRedirect("test.jsp");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="css/login.css">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body style="background: #97B7ED">
	<div id="login-text">Đăng nhập</div>
        <%if(session.getAttribute("isValid")!=null){
                        if(session.getAttribute("isValid").equals("false")){
                    %>
                    <div id="caution">Sai tên tài khoản hoặc mật khẩu</div>
                    <%}}%>
	<div id="login-form">
		<form action="login" method="post">
			 
			<div id="name">
				Tên đăng nhập:
				<input type="text" name="userName" required 
                                       oninvalid="this.setCustomValidity('Tên đăng nhập không được để trống')" oninput="setCustomValidity('')">
			</div> 
			<div id="pass">
				Mật khẩu:
				<input type="password" name="password" required 
                                       oninvalid="this.setCustomValidity('Mật khẩu không được để trống')" oninput="setCustomValidity('')">
			</div>
			<div id="button">
				<input type="submit" name="submit" value="Đăng nhập">
			</div>
		</form>
	</div>
	
	<div id="info">
		version 1.0 - nhóm 13
	</div>

</body>
</html>

<%
    session.removeAttribute("isValid");
%>