import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/InsertItems")
public class InsertItems extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public InsertItems() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String item = request.getParameter("item");
      int quantity = Integer.parseInt(request.getParameter("quantity"));
      double price = Double.parseDouble(request.getParameter("price"));
      String department = request.getParameter("department");
      String store = request.getParameter("store");

      Connection connection = null;
      String insertSql = " INSERT INTO list (id, ITEM, QUANTITY, PRICE, DEPARTMENT, STORE) values (default, ?, ?, ?, ?, ?)";

      try {
         ShoppingDB.getDBConnection();
         connection = ShoppingDB.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, item);
         preparedStmt.setLong(2, quantity);
         preparedStmt.setDouble(3, price);
         preparedStmt.setString(4, department);
         preparedStmt.setString(5, store);
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Insert Items into List";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //

            "  <li><b>Item</b>: " + item + "\n" + //
            "  <li><b>Quantity</b>: " + quantity + "\n" + //
            "  <li><b>Price per Item</b>: " + price + "\n" + //
            "  <li><b>Department of Store</b>: " + department + "\n" + //
            "  <li><b>Store</b>: " + store + "\n" + //
            "</ul>\n");
//changed
      out.println("<a href=/shoppinglist/show_items.html>Show Items</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
