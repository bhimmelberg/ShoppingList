import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ShoppingHimmelberg
 */
@WebServlet("/ShoppingHimmelberg")
public class ShoppingHimmelberg extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static String url = "jdbc:mysql://ec2-3-135-240-102.us-east-2.compute.amazonaws.com:3306/ShoppingList?enabledTLSProtocols=TLSv1.2";
	static String user = "bhimmelremote";
	static String password = "Ellamae2001!";
	static Connection connection = null;
	

    public ShoppingHimmelberg() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
	      try {
	         Class.forName("com.mysql.cj.jdbc.Driver"); //old:com.mysql.jdbc.Driver
	      } catch (ClassNotFoundException e) {
	         e.printStackTrace();
	         return;
	      }
	      connection = null;
	      try {
	         connection = DriverManager.getConnection(url, user, password);
	      } catch (SQLException e) {
	         e.printStackTrace();
	         return;
	      }
	      try {
	         String selectSQL = "SELECT * FROM employee";
	         PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
	         ResultSet rs = preparedStatement.executeQuery();
	         while (rs.next()) {
	            String id = rs.getString("ID");
	            String age = rs.getString("age");
	            String name = rs.getString("name");
	            response.getWriter().append("USER ID: " + id + ", ");
	            response.getWriter().append("AGE: " + age + ", ");
	            response.getWriter().append("NAME: " + name + "<br>");
	         }
	         
	         preparedStatement.close();
	         connection.close();
	      } catch (SQLException e) {
	         e.printStackTrace();
	      }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
