<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.*,java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Booking list for doctor</title>
</head>
<body>
<h1>Upcoming visits:</h1>
<%
try
	{
	Class.forName("com.mysql.jdbc.Driver");
	Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3307/employees","root","root");
	String query = "SELECT * FROM registration WHERE meeting_time > CURRENT_TIME() ORDER BY meeting_time";
	Statement myStatement = myConnection.createStatement();
	ResultSet resultSet = myStatement.executeQuery(query);
	
	while (resultSet.next()) 
		{
		out.println(resultSet.getString("meeting_time") + " | " + resultSet.getString("first_name") + " "  
		+ resultSet.getString("last_name") + "<br>");
		}
	myConnection.close();
	}
catch(Exception e)
	{
	System.out.print(e);
	e.printStackTrace();
	}
%>
</body>
</html> 