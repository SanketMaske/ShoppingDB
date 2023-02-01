package logic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/addtocart")
public class AddToCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		int selectedP =Integer.parseInt(request.getParameter("p"));
		HttpSession session = request.getSession();
		List<Integer> products = null;		
		products = (List<Integer>)session.getAttribute("cart");
		/*if(products != null) //subsequent selections
		{
			products.add(selectedP);
		}
		else //first selection
		{
			products = new ArrayList<>();
			products.add(selectedP);
		}*/
		if(products == null)
			products = new ArrayList<>();
		products.add(selectedP);
		session.setAttribute("cart", products);
		out.print("Select product id : "+selectedP+" is added in the cart <br/>");
		out.print("No of selected products : "+products.size());
		out.print("<br/><a href='viewcart.jsp'> View Cart </a>");
		out.print("<br/><a href='home'> Go For Firther Selection </a>");
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
