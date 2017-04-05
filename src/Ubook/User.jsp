<%@ page language="java" import= "Ubook.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

</head>
<body>
<% 

Connector con = new Connector();
User user = new User();

if(request.getParameter("login") != null){
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
else if(request.getParameter("doneRegister") != null){

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

else if(request.getParameter("viewProfile") != null){
	String[] result = user.setViewProfile((String)session.getAttribute("user"), con.stmt);
		
	out.println("User Name: " + session.getAttribute("user") + "\n");
	out.println("Full Name: " + result[1] + "\n");
	out.println("Admin(1 for yes, 0 for no): " + result[2] + "\n");
	out.println("Address: " +result[3] + "\n");
	out.println("Phone Number: " + result[4] + "\n");
	out.println("<a href=\"MainMenu.jsp\">Back to main</a>" );
}

else if(request.getParameter("changeProfile") != null){
	%>
	
	Form1: login user name:
	<form action = "User.jsp" method=post>
	FullName:<input type = "text" name = "fullName" value = "Full Name"/><br/><br/>
	Password:<input type = "password" name = "password" value = "password"/><br/><br/>
	Admin:<input type = "checkBox" name = "admin" value = "Is Admin?"/><br/><br/>
	Address:<input type = "text" name = "address" value = "Address"/><br/><br/>
	PhoneNumber:<input type = "text" name = "phoneNumber" value = "Phone Number"/><br/><br/>
	<input type = "submit" name="updateProfile" value="updateProfile"/>
	</form>
	
	<%
}

else if(request.getParameter("updateProfile") != null){
	String admin = null;
	if(request.getParamter("admin") != null){
		admin = "1";
	}
	else{
		admin = "0";
	}
	String[] result = user.alterProfile((String)session.getAttribute("user"), con.stmt, request.getParameter("password"), admin, request.getParameter("address"),request.getParameter("fullName"), request.getParameter("phoneNumber")); 
}
 %>
<%

%>
</body>
</html>