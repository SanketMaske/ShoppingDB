package logic;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.User;


@WebServlet("/confirm")
public class ConfirmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con;
	
	public void init(ServletConfig config) throws ServletException {
		con = (Connection)config.getServletContext().getAttribute("jdbccon");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out =response.getWriter();
		HttpSession session =request.getSession();
		out.print("<br/>Your order is confirmed");
		out.print("<br/>Thanks for shoppin gwith us");
		User user = (User)session.getAttribute("loggedinuser");
		out.print("<br/>Your e-bill will be mailed at : "+user.getEmail() );
		out.print("<br/>You will receive msg before delivery on "+user.getContact());
		//summary of shopping into shopping table
		out.print("<br/><a href='logout'> LOGOUT </a>");
		
		/*String uid = user.getUid();
		Date dt = 
		float total = (Float)session.getAttribute("tprice")*/ 
		PreparedStatement ps = null;
		try
		{
			ps = con.prepareStatement("insert into shopping() values(?,?,?)");
			/*ps.setString(1, uid );
			ps.setString(2, dt );
			ps.setString(3, price );
			ps.executeUpdate()*/
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				ps.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
