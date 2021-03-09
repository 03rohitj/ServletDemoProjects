

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Logout
 */
@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Logout() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		Cookie ck[] = request.getCookies();
		
		request.getRequestDispatcher("link.html").include(request, response);
		
		if( (ck == null) || (ck[0].getValue().trim().equals("")) ) {
			out.println("\nAlready Logged out");
		}
		else {
			Cookie username = new Cookie("username","");
			username.setMaxAge(0);
			response.addCookie(username);
			
			Cookie password = new Cookie("password","");
			password.setMaxAge(0);
			response.addCookie(password);
			
			out.println("Logged out");
			System.out.println("Is cookie empty? : "+( username.getValue().trim().equals("") ));
		}
		
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
