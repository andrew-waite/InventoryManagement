package application.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseController 
{
	private Connection connection = null;
	
	public DatabaseController()
	{
		initialize();
	}

	private void initialize() 
	{
		try
		{
			Class.forName("org.sqlite.JDBC");
			this.connection = DriverManager.getConnection("jdbc:sqlite:inventory.db");
		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

	}
	
	public Connection getConnection()
	{
		return this.connection;
	}
}
