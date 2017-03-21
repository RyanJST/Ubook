package Ubook;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Stay {
	public void recordStay(String userName, Statement stmt) {
		try {
			String sql = "SELECT R.pid, R.cost, H.hid, H.name, P.fromDate, P.toDate,  "
					+ "FROM Reserves R, TH H, Period P "
					+ "WHERE P.pid = R.pid AND R.hid = H.hid AND login = '" + userName + "';";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				System.out.println("Id: " + rs.getString("R.pid")
				+ ", Name: " + rs.getString("H.name")
				+ ", From: "+ rs.getString("P.fromDate")
				+ ", To: " + rs.getString("toDate")
				+ ", Cost: " + rs.getString("cost"));
				
			}
			System.out.println("Enter the Id of a reservation to record your stay:");
			String pid = MainMenu.input.readLine();
			
			sql = "INSERT INTO Visits (pid, hid, login, cost)"
					+ " SELECT Reserves pid, hid, login, cost"
					+ " WHERE pid = " + pid + ";";
			stmt.executeUpdate(sql);
			
		}
		catch (SQLException e) {
			
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
