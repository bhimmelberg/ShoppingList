import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ShowItems")
public class ShowItems extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public ShowItems() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      search(response);
   }

   void search(HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Items in List";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");

      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
         ShoppingDB.getDBConnection();
         connection = ShoppingDB.connection;
         //Order the table alphabetically based on store
         String selectSQL = "SELECT * FROM list ORDER BY Store";
         preparedStatement = connection.prepareStatement(selectSQL);
         ResultSet rs = preparedStatement.executeQuery();
         //Variable to hold total cost of specific item
         double itemCost = 0.0;
         //Variable to hold cost of all items
         double totalCost = 0.0;
         
         while (rs.next()) {
            String item = rs.getString("item").trim();
            int quantity = rs.getInt("quantity");
            String price = rs.getString("price").trim();
            String department = rs.getString("department").trim();
            String store = rs.getString("store").trim();
            double myPrice = Double.parseDouble(price);
            //Get cost of current item
            itemCost = myPrice * quantity;
            //Add to total cost
            totalCost = totalCost + itemCost;
            
            out.println("Item: " + item + " | ");
            out.println("Quantity: " + quantity + " | ");
            out.println("Price Per Item: $" + price + " | ");
            out.println("Store Department: " + department + " | ");
            out.println("Store: " + store + " | ");
            out.println("Cost of Item: $" + itemCost + "<br><br>");

         }
         out.println("Total Cost: $" + totalCost + "<br><br>");
         out.println("<a href=/shoppinglist/add_item.html>Add New Items</a> <br>");
         out.println("</body></html>");
         rs.close();
         preparedStatement.close();
         connection.close();
      } catch (SQLException se) {
         se.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (preparedStatement != null)
               preparedStatement.close();
         } catch (SQLException se2) {
         }
         try {
            if (connection != null)
               connection.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
