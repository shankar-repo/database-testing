package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseTesting {

	public static void main(String[] args) throws ClassNotFoundException {
		Class.forName("org.postgresql.Driver");//load driver the jar

		String url = "jdbc:postgresql://localhost:5432/northwind";
		String user = "postgres";
		String password = "postgres";
		
		try (Connection con = DriverManager.getConnection(url, user, password);
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM CUSTOMERS")) {
			//loop thru data
			int rowsProcessed = 0;
			while (rs.next()) {
				rowsProcessed++;
				System.out.println("Row number - " + rowsProcessed);
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					System.out.print(rs.getMetaData().getColumnName(i) + "                     |");
					System.out.print(rs.getString(i));
				}
				System.out.println();
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

}
