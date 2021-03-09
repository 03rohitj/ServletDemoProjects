package employee_data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterEmployee
 */
@WebServlet(description = "Registers a new employee and add it to the database", urlPatterns = { "/RegisterEmployee" })
public class RegisterEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection cn;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterEmployee() {
        super();
        cn = DatabaseConnection.getDbConnection().getConnectionObject();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name").trim();
		String dept = request.getParameter("dept").trim();
		char gender = request.getParameter("gender").trim().toUpperCase().charAt(0);
		int salary = Integer.parseInt(request.getParameter("salary").trim());
		
		try (PreparedStatement ps_insertStatement = cn.prepareStatement("insert into emp(ename,edept,egender,esalary) values(?,?,?,?)")){
			ps_insertStatement.setString(1, name);
			ps_insertStatement.setString(2, dept);
			ps_insertStatement.setString(3, gender+"");
			ps_insertStatement.setInt(4, salary);
			
			System.out.println("Insert Query : "+ps_insertStatement.executeUpdate());
			
			response.sendRedirect("Index");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
