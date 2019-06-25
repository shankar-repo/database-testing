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
				ResultSet rs = st.executeQuery(
						"select cus.company_name as \"Company Name\",count(cus.company_name) as \"Num of customers\"  from customers cus\r\n"
								+ "inner join orders odr on cus.customer_id = odr.customer_id \r\n"
								+ "group by cus.company_name \r\n"
								+ "having count(cus.company_name) between 10 and 20 \r\n"
								+ "order by count(cus.company_name) desc;")) {
			//loop thru data
			int rowsProcessed = 0;
			while (rs.next()) {
				rowsProcessed++;
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					if (rowsProcessed == 1) {
						System.out.print(rs.getMetaData().getColumnName(i) + "                     |");
					} else {
						System.out.print(rs.getString(i) + "                |");
					}
				}
				System.out.println();
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

}
