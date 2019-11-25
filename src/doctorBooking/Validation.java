package doctorBooking;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Validation")
public class Validation extends HttpServlet {
	private static final long serialVersionUID = 1L;     
   	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{		
		response.setContentType("text/html");  
	    PrintWriter out = response.getWriter();  
	    
	    String meeting_time = request.getParameter("meeting_time");
	    String first_name = request.getParameter("first_name");
	    String last_name = request.getParameter("last_name");
	    String currentTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(Calendar.getInstance().getTime());
	    
	    if (first_name.isEmpty()|| last_name.isEmpty()) 
	    	{
	    	RequestDispatcher rd = request.getRequestDispatcher("registration_form.jsp");
	    	out.println("<font color=red>Please fill all fields</font>");
	    	rd.include(request, response);
	    	}
	    else if (currentTime.compareTo(meeting_time)>=0)
	    	{
	    	RequestDispatcher rd = request.getRequestDispatcher("registration_form.jsp");
	    	out.println("<font color=red>Invalid time. You entered past time.</font>");
	    	rd.include(request, response);
	    	}	    	
	    else 
	    	{
	    	try 
	    		{
	    		Class.forName("com.mysql.jdbc.Driver");
	    		Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3307/employees","root","root");	    		

	    		String query = "SELECT * FROM registration WHERE meeting_time = ? OR first_name = ? AND last_name = ? AND "
	    					 + "WEEK(meeting_time) = WEEK(STR_TO_DATE(?, '%Y-%m-%dT%H:%i'))";
	    		PreparedStatement preparedStatement = myConnection.prepareStatement(query);
	    		preparedStatement.setString(1, meeting_time);
	    		preparedStatement.setString(2, first_name);
	    		preparedStatement.setString(3, last_name);
	    		preparedStatement.setString(4, meeting_time);	    	
	    		ResultSet resultSet = preparedStatement.executeQuery();
	    		
	    		if(resultSet.next()) 
	    			{	    			 
	    			RequestDispatcher rd = request.getRequestDispatcher("registration_form.jsp");
	    			out.println("<font color=red>Sorry, time already reserved or "
	    					+ "you have another appointment in the same week. Please try again.</font>");
	    			rd.include(request, response);  
	    			}
	    		else
	    			{
	    			String insertQuery = "INSERT INTO registration (meeting_time, first_name, last_name) VALUES (?, ?, ?)";
	    			PreparedStatement preparedStatement1 = myConnection.prepareStatement(insertQuery);
	    			preparedStatement1.setString(1, meeting_time);
		    		preparedStatement1.setString(2, first_name);
		    		preparedStatement1.setString(3, last_name);
		    		preparedStatement1.executeUpdate();
		    		out.println("<font color=green>Registration successful! Please visit doctor at " + meeting_time);	    			
	    			}
	    		myConnection.close();
	    		}
	    	catch(Exception e)
	    		{
	    		System.out.print(e);
	    		e.printStackTrace();
	    		}	
	    	}
	}
}
