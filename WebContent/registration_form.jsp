<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Patient Registration System</title>
</head>
<body>
<h1>Patient registration form</h1>
<form action="Validation" method="post">
Please enter visit date and time:<br>
<input type="datetime-local" name="meeting_time" step="3600">
<br>          
First Name:<br>
<input type="text" name="first_name">
<br>
Last Name:<br>
<input type="text" name="last_name">
<br><br>
<input type="submit" value="Submit">    
</form>
</body>
</html>      