package com.servelt;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ecommerce.DBConnection;

@WebServlet("/ProductDetails")
public class ProductDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		String pId = request.getParameter("productId");// user input
		String sql = "select productid, name, description, price, category from product where productid=" + pId + "";

		try {

			out.println("<html><body>");

			InputStream in = getClass().getClassLoader().getResourceAsStream("config.properties");
			Properties props = new Properties();
			props.load(in);

			DBConnection conn = new DBConnection(props.getProperty("url"), props.getProperty("userid"),
					props.getProperty("password"));
			Statement stmt = conn.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			ResultSet rst = stmt.executeQuery(sql);

			if (rst.next() == false) {
				System.out.println("ResultSet in empty in Java");
				out.println("Sorry we couldn't find that product id, please try again");
			} else {
				do {
					out.println("<h1> Here is the product detail</h1>" + "<hr>" + "Id: " + rst.getInt("productid")
							+ "<Br>" + "<em>Name: <em>" + rst.getString("name") + "<Br>" + "Description: "
							+ rst.getString("description") + "<Br>" + "Price: " + rst.getString("price") + "<Br>"
							+ "Category: " + rst.getString("category") + "<Br>");

				} while (rst.next());
			}
			stmt.close();

			out.println("</body></html>");
			conn.closeConnection();

		} catch (ClassNotFoundException e) {
			out.println("Invaild input, please try again");
		} catch (SQLException e) {
			out.println("Invaild input, please try again");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
