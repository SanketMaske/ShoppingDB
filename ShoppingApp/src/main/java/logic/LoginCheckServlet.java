package logic;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.User;

@WebServlet("/logincheck")
public class LoginCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con;
	
	public void init(ServletConfig config) throws ServletException {
		 con = (Connection)config.getServletContext().getAttribute("jdbccon");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String uid = request.getParameter("uname");
			String pwd = request.getParameter("pswd");
			ps = con.prepareStatement("select * from users where u_id = ? and password = ?");
			ps.setString(1, uid);
			ps.setString(2, pwd);
			rs = ps.executeQuery();
			if(rs.next()) //success
			{
				//deleting cookie
				Cookie [] all = request.getCookies();
				if(all != null)
				{
					for(Cookie c : all)
					{
						if(c.getName().equals("loginerror"))
						{
							c.setMaxAge(0);
							//modified cookie should be send back
							response.addCookie(c);
						}
					}
				}	
				HttpSession session = request.getSession();
				/*session.setAttribute("name", rs.getString(2)+" "+rs.getString(4) );
				session.setAttribute("email", rs.getString(5));
				session.setAttribute("contact", rs.getString(6));
				*/
				User u = new User(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7));
				session.setAttribute("loggedinuser", u);
				
				RequestDispatcher rd = request.getRequestDispatcher("/home");
				rd.forward(request, response);
			}
			else //failed
			{
				//created and added in the response
				Cookie c = new Cookie("loginerror","Wrong_ID/PWD");
				response.addCookie(c);
				response.sendRedirect("/ShoppingApp/login.jsp");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				rs.close();
				ps.close();
				//do not close connection here
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}

}
