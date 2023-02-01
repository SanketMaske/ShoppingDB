package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/LoginCheck")
public class LoginCheckServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	Connection con;

	
	public void init(ServletConfig config) throws ServletException 
	{
		con = (Connection)config.getServletContext().getAttribute("jdbccon");	
	}

	


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doPost(request,response);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		PrintWriter out=response.getWriter();
		
		
		try 
		{
			String uid = request.getParameter("uname");
			String pwd = request.getParameter("pswd");
			ps = con.prepareStatement("select * from users where u_id = ? and password = ?");
			ps.setString(1, uid);
			ps.setString(2, pwd);
			rs=ps.executeQuery();
			if(rs.next())
			{
				RequestDispatcher rd = request.getRequestDispatcher("/home");
				rd.forward(request, response);
			}
			else 
			{
				out.print("sorry, username or password error!");
				request.getRequestDispatcher("login.jsp").include(request, response);
			}
		} 
		catch (Exception e) 
		{		
			e.printStackTrace();
		}
		finally
		{
			try {
				rs.close();
				ps.close();
			} 
			catch (Exception e) 
			{
				
				e.printStackTrace();
			}
		}
		
	}

}
