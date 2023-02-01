package logic;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/getProducts")
public class GetProductsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con;
	public void init(ServletConfig config) throws ServletException {
		con = (Connection)config.getServletContext().getAttribute("jdbccon");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//read cid from request parameter
		//fire the query = select * from product where cat_id = ?
		//get the resultset
		/*out.println("<form>");
		out.println("Select product : ");
		out.println("<select name='p'>");
		while(rs.next())
		{
			out.println("<option>"+ rs.getString(2)+"<option>");
		}*/
		
		PrintWriter out = response.getWriter();
		int cid = Integer.parseInt(request.getParameter("cid"));
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			ps = con.prepareStatement("select * from product where cat_id = ?");
			ps.setInt(1, cid);
			rs = ps.executeQuery();
			out.print("<form action='addtocart'>");
			out.print("Select product : ");
			out.print("<select name='p'>");
			out.print("<option> Select products </option>");
			while(rs.next())
			{
				out.print("<option value='"+ rs.getInt(1)+ "'>"+rs.getString(2)+"</option>");
			}
			out.print("</select> <br/>");
			out.print("<input type='submit' value='Add to cart' />");
			out.print("</form>");
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
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
