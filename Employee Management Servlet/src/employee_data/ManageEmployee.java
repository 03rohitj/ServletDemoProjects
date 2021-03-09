package employee_data;

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

/**
 * Servlet implementation class ManageEmployee
 */
@WebServlet(description = "To insert a new or edit existing Employee details", urlPatterns = { "/ManageEmployee" })
public class ManageEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection cn;
	private String html;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageEmployee() {
        super();
        
        
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		html = "<html><body><br><h1>Register New Employee</h1><br>"
				+"<form action=\"RegisterEmployee\" method=\"POST\"><table>"
				+"<tr><th>Name</th><td><input type=\"text\" required name=\"name\"></td></tr>"
				+"<tr><th>Department</th><td><input type=\"text\" required name=\"dept\"></td></tr>"
				+"<tr><th>Gender</th><td><input type=\"text\" required name=\"gender\"></td></tr>"
				+"<tr><th>Salary</th><td><input type=\"number\" required name=\"salary\" min=\"0\"></td></tr>"
				+"<tr><th><input type=\"submit\" name=\"submit\" value=\"Submit\"></th></tr>"
				+"</table></form></body></html>";		
		
		out.println(html);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//get existing connection or create a new connection
        cn = DatabaseConnection.getDbConnection().getConnectionObject();
        
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		int empId = 0;
		String name = "";
		String dept = "";
		long salary = 0;
		char gender = ' ';
		
			
			if( request.getParameter("i_empid").trim().length() == 0)
				response.sendRedirect("Index");
			
			try {
				empId = Integer.parseInt(request.getParameter("i_empid").trim());
			}catch(NumberFormatException e) {
				System.err.println("NumberFormatException : "+e.getMessage());
				response.sendRedirect("Index");
				return;
			}
			
			
			Employee emp = getEmployee(empId);
			System.out.println("\nEmpId : "+empId);
			if(emp == null) {
				response.sendRedirect("Index");
				System.out.println("\nRedirecting to Index");
				return;
			}
			
			name = emp.getName();
			dept = emp.getDept();
			gender = emp.getGender();
			salary = emp.getSalary();
		
		
		html = "<html><body><br><h1>Update Employee</h1><br>"
				+"<form action=\"UpdateEmployee\" method=\"POST\"><table>"
				+"<tr><th>EmpId </th><td><input type=\"text\" readonly=\"readonly\" name=\"empid\" value=\""+empId+"\"></td></tr>"
				+"<tr><th>Name</th><td><input type=\"text\" required name=\"name\" value=\""+name+"\"></td></tr>"
				+"<tr><th>Department</th><td><input type=\"text\" required name=\"dept\" value=\""+dept+"\"></td></tr>"
				+"<tr><th>Gender</th><td><input type=\"text\" required name=\"gender\" value=\""+gender+"\"></td></tr>"
				+"<tr><th>Salary</th><td><input type=\"number\" required name=\"salary\" min=\"0\" value=\""+salary+"\"></td></tr>"
				+"<tr><th><input type=\"submit\" name=\"submit\" value=\"Update\"></th></tr>"
				+"</table></form></body></html>";
		
		out.print(html);

		
	}
	
	protected Employee getEmployee(int empId){
		Employee emp = null;
		try( PreparedStatement ps_getEmployee = cn.prepareStatement("select * from emp where empid="+empId)){
			
			//ps_getEmployee.setInt(1, empId);
			ResultSet rs = ps_getEmployee.executeQuery();
			if( rs.next() == false )	//table contains no record, so id will begin from 1
				return null;
	
			emp = new Employee();
			emp.setName(rs.getString(2));
			emp.setDepartment(rs.getString(3));
			emp.setGender(rs.getString(4).charAt(0));
			emp.setSalary(rs.getInt(5));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return emp;
	}

}
