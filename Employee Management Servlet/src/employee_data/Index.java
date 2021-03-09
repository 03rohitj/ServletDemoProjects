package employee_data;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Index
 */
@WebServlet("/Index")
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String html, htmlTail;
    
    //A singleton class to connect database;
    private DatabaseConnection dbConnection;
    
    //An object referencing to database
    private Connection cn;
    
//    //Statement to fetch all the records
//    private PreparedStatement ps_fetchEmployees;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Index() {
        super();
        
        
    }
    
    @Override
    public void init() throws ServletException {
    	super.init();
    	
    	//Just for knowledge basis
    	ServletContext context = getServletContext();
    	System.out.println("\nDriver : "+context.getInitParameter("dname"));
    }

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		try {
			cn.close();
			System.out.println("\n\ndestroy() IS CALLED!!!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		
		
		//returns the object
    	dbConnection = DatabaseConnection.getDbConnection();
    	
        html = "<!DOCTYPE html>"
        		+ "<html>"
        		+ "<head>"
        		+ "<meta charset=\"UTF-8\">"
        		+ "<title>Index</title>"
        		+ "</head>"
        		+ "<body>"
        		+ "	<center><h1>Employees Admin Panel</h1></center>"
        		+ "	<br>"
        		+ "	"
        		+ "	<table>"
        		+ "		<tr><form method=\"POST\"><th><label>Employee Id</label></th>"
        		+ "			<th><input type=\"text\" required name=\"i_empid\" placeholder=\"Enter Employee Id\"/></th>"
        		+ "			<th><input type=\"submit\" value=\"Edit\" onclick=\"form.action=\'ManageEmployee\';\"></th>"
        		+ "			<th><input type=\"submit\" value=\"Delete\" onclick=\"form.action=\'DeleteEmployee\';\"></th>"
        		+ "		</form></tr>"
        		+ "		<tr><th colspan=2 ><form action=\"ManageEmployee\">"
        		+ "		<input type=\"submit\" style=\"margin-top:10px;\" value=\"Insert New Record\">"
        		+ "		</form></th></tr>"
        		+ "	</table><br><br>";
        
        
        cn = dbConnection.getConnectionObject();
        try( PreparedStatement ps_fetchEmployees = cn.prepareStatement("select * from emp")){
        	
        	ResultSet rs = ps_fetchEmployees.executeQuery();
        	
        	if( (!rs.isBeforeFirst()) && (rs.getRow() == 0))
        		html +="<center><h3>No Records to display</h3></center>";
        	else {
        		
        		html +="<table>";
        		
        		while(rs.next()) {
        			html += "<tr><td style=\"padding:5px;\">"+rs.getInt(1)+"</td>"
        					+"<td style=\"padding:5px;\">"+rs.getString(2)+"</td>"	
        					+"<td style=\"padding:5px;\">"+rs.getString(3)+"</td>"
        					+"<td style=\"padding:5px;\">"+rs.getString(4)+"</td>"
        					+"<td style=\"padding:5px;\">"+rs.getInt(5)+"</td></tr>";
        		}
        		
        		html += "</table>";
        		
        	}
        	
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        html +=  "</body>"
    			+"</html>";	
		
		out.print(html);
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
