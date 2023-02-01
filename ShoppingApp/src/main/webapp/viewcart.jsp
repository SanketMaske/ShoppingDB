<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*,java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
	
		Connection con = (Connection)application.getAttribute("jdbccon");
		List<Integer> products = (List<Integer>)session.getAttribute("cart");
		if(products == null){
	%>
		<%= "No products selected" %>
		<% }
			else
			{
				PreparedStatement ps = null;
				ResultSet rs = null;
				
				%>
					<table border="1">
					  <tr>
					  	<th> Sr no </th>
					  	<th> Name </th>
					  	<th> Desc </th>
					  	<th> Price </th>
					  </tr>
					<% 
					ps = con.prepareStatement("select * from product where p_id = ?");
					float total = 0;
					int x = 0;
					for(int n : products)
					{
						ps.setInt(1, n);
						rs = ps.executeQuery();
						
						if(rs.next())
						{
							%>
							<tr>
							  <td> <%= (++x) %> </td>
							  <td> <%= rs.getString(2) %> </td>
							  <td> <%= rs.getString(3) %> </td>
							  <td> <%= rs.getString(4) %> </td>
							</tr>
							<%
								total+=Float.parseFloat(rs.getString(4));
								
							
						}
					}
					%>
					<tr>
						<td colspan="3"> Total price </td>
						<td> <%= total %> </td>
					</tr>
					</table>
					<% 
					session.setAttribute("tprice",total);
			}
				
		%>
		<br/>
		<a href="confirm"> Confirm Cart </a> <br/>
		<a href="home"> Go for more selection </a>
		
</body>
</html>