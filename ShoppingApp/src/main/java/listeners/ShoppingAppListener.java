package listeners;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class ShoppingAppListener implements ServletContextListener {
	Connection con;

    public void contextDestroyed(ServletContextEvent sce)  { 
         //close the connection
    	try
    	{
    		con.close();    		
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }


    public void contextInitialized(ServletContextEvent sce)  { 
         //create the connection
    	 ServletContext ctx = sce.getServletContext();
    	 String driver = ctx.getInitParameter("driverclass");
    	 String url = ctx.getInitParameter("jdbcurl");
    	 String user = ctx.getInitParameter("user");
    	 String pwd = ctx.getInitParameter("password");
    	 
    	 try
    	 {
    		Class.forName(driver);
    		con = DriverManager.getConnection(url, user, pwd);
    		System.out.println("connection established");
    		ctx.setAttribute("jdbccon", con);
    		System.out.println("set con as ctx level attribute");
    	 }
    	 catch(Exception e)
    	 {
    		 e.printStackTrace();
    	 }
    	
    }
	
}
