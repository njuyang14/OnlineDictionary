import java.sql.*;
import java.util.*;
import org.apache.commons.dbcp2.BasicDataSource;  
import org.apache.commons.dbcp2.BasicDataSourceFactory; 

public final class Dbc {

	private static BasicDataSource mds = null;
	private Dbc(){}
	static
	{
		try
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Properties properties = new Properties();
			properties.load(Dbc.class.getClassLoader().getResourceAsStream("dbcp.properties"));
			mds = BasicDataSourceFactory.createDataSource(properties);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException
	{
		return mds.getConnection();
	}
}
