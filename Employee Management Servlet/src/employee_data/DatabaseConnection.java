package employee_data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletContext;

public final class DatabaseConnection {
	private static DatabaseConnection dbInstance = null;
	private Connection cn;
	
	private DatabaseConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			cn = DriverManager.getConnection("jdbc:mysql://localhost/EmployeesManagement?user=root&password=Rohit@1997");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static DatabaseConnection getDbConnection() {
		if( dbInstance == null)
			dbInstance = new DatabaseConnection();
		
		
		return dbInstance;
	}
	
	//Returns the connection object
	public Connection getConnectionObject() {
		return cn;
	}
	

}
