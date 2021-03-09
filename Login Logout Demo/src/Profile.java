

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Profile
 */
@WebServlet("/Profile")
public class Profile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Profile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		request.getRequestDispatcher("link.html").include(request, response);
		String user = request.getParameter("username").trim();
		String password = request.getParameter("password").trim();
		
		if( user.equals("root") && password.equals("toor") ) {
			Cookie c_username = new Cookie("username", user);
			Cookie c_password = new Cookie("password", password);
			
			response.addCookie(c_username);
			response.addCookie(c_password);
			
			out.println("Successfully logged-in, Welcome to profile page");
		}
		else {
			out.println("Invalid username or password, please login again");
			RequestDispatcher rd = request.getRequestDispatcher("Login");
			rd.forward(request, response);
			
		}
		
		out.close();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		Cookie ck[] = req.getCookies();
		
		if( ( ck == null ) || ck[0].getValue().equals("") )
			req.getRequestDispatcher("Login").forward(req, resp);
		else {
			req.getRequestDispatcher("link.html").include(req, resp);
			out.print("Welcome to profile page");
		}

		out.close();
	}

}
