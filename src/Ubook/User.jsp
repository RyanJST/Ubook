<%@ page language="java" import= "Ubook.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

</head>
<body>
<% 

Connector con = new Connector();
User user = new User();

if(request.getParameter("login")){
String userName = request.getParameter("userName");
String password = request.getParameter("password");



session.setAttribute("user", user.loginUser(con.stmt, userName, password));

con.closeStatement();
con.closeConnection();


if(session.getAttribute("user") != null){
	out.println("Welcome back "+ (String)(session.getAttribute("user"))+ " <a href=\"MainMenu.jsp\">Back to main</a>");
}
}

else if(request.getParameter("registerUser") != null){
	%>
	Form1: login user name:
	<form action = "User.jsp" method=post onsubmit="return check_all_fields(this)">
	UserName:<input type = "text" name = "userName"/><br/><br/>
	Password:<input type = "text" name = "password"/><br/><br/>
	FullName:<input type = "text" name = "fullName"/><br/><br/>
	Address:<input type = "text" name = "address"/><br/><br/>
	phoneNumber:<input type = "text" name = "phoneNumber"/><br/><br/>
	admin:<input type = "checkbox" name = "admin"/><br/><br/>
	
	<input type = "submit" name="doneRegister" value="RegisterUser"/>
	</form>
<% 
}
else if(request.getParameter("doneRegister" != null)){

	String userName = request.getParameter("userName");
	String password = request.getParameter("password");
	String fullName = request.getParameter("FullName");
	String address = request.getParameter("address");
	String phoneNumber = request.getParameter("phoneNumber");
	String admin = "0";
	if(request.getParameterValues("admin") != null){
		admin = "1";
	}
	
	session.setAttribute("user", user.setUpUser(con.stmt, userName, password, fullName, address, phoneNumber, admin));


	con.closeStatement();
	con.closeConnection();

	if(session.getAttribute("user") != null){
		out.println("Welcome back "+ (String)(session.getAttribute("user"))+ " <a href=\"MainMenu.jsp\">Back to main</a>");
	}
	else{
		out.println("THere was an issue with your registration.  Do you want to try again?");
				
		%>
		
		<form action = "User.jsp" method=post>
		<input type = submit name = registerUser value= "Yes" />
		
		</form>
		<form action = "MainMenu.jsp" method=post>
		<input type = submit name = cancelRegister value= "No" />
		</form>
		<%
	}
}
 %>
<%

%>
</body>
</html>