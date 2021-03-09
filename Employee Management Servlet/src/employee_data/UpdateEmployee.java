package employee_data;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UpdateEmployee
 */
@WebServlet("/UpdateEmployee")
public class UpdateEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;
    Connection cn;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateEmployee() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		cn = DatabaseConnection.getDbConnection().getConnectionObject();
		
		String name = request.getParameter("name").trim();
		String dept = request.getParameter("dept").trim();
		char gender = request.getParameter("gender").trim().toUpperCase().charAt(0);
		int salary = Integer.parseInt(request.getParameter("salary").trim());
		int empId = Integer.parseInt(request.getParameter("empid").trim());
		
		try (PreparedStatement ps_updateStatement = cn.prepareStatement("update emp set ename=?,edept=?,egender=?,esalary=? where empid=?")){
			ps_updateStatement.setString(1, name);
			ps_updateStatement.setString(2, dept);
			ps_updateStatement.setString(3, gender+"");
			ps_updateStatement.setInt(4, salary);
			ps_updateStatement.setInt(5, empId);
			
			int res = ps_updateStatement.executeUpdate();
			
			System.out.println("\nUpdate Query, res : "+res);
			response.sendRedirect("Index");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
