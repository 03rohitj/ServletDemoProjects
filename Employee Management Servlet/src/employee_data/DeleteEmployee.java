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
 * Servlet implementation class DeleteEmployee
 */
@WebServlet("/DeleteEmployee")
public class DeleteEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Connection cn;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteEmployee() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		cn = DatabaseConnection.getDbConnection().getConnectionObject();
		
		try (PreparedStatement ps_deleteStatement = cn.prepareStatement("delete from emp where empid=?")){
			int empId = Integer.parseInt(request.getParameter("i_empid").trim());
			ps_deleteStatement.setInt(1, empId);
			
			int res = ps_deleteStatement.executeUpdate();
			System.out.println("Delete query, res : "+res);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(NumberFormatException e) {
			System.err.println("NumberFormatException : "+e.getMessage());
		}
		finally {
			response.sendRedirect("Index");
		}
	}
}
