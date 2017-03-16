package Ubook;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BRTH {

	public void browseTHs(String userName, Statement stmt) {
		// TODO Auto-generated method stub
		
	}

	public void reserveTHs(String userName, Statement stmt) {
		// TODO Auto-generated method stub
		String sql = "SELECT hid, name FROM TH;";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			if(!rs.isBeforeFirst()){
				System.out.println("There are no houses in the system.");
				return;
			}
			while(rs.next()){
				
				System.out.println("House ID: " + rs.getString("hid") +",   House Name: " + rs.getString("name") );
					
			}
			System.out.println("Select House ID of desired house:");
			int id = Integer.parseInt(MainMenu.input.readLine());
			sql = "SELECT P.fromDate, P.toDate, A.priceNight FROM Available A, Period P WHERE A.pid = P.pid AND hid = " + id + ";";
			rs = stmt.executeQuery(sql);
			System.out.println("Availability:");
			while(rs.next()){
				System.out.println("From: " + rs.getString("P.fromDate") + ", To: " + rs.getString("P.toDate") + ", Price: $" + rs.getString("A.priceNight"));
			}
			System.out.println("Enter the date you would like to would like to start your start:");
			String from = MainMenu.input.readLine();
			System.out.println("Enter that date you would like to end your stay:");
			String to = MainMenu.input.readLine();
			sql = "INSERT INTO Period (fromDate, toDate) VALUES (" + from + ", " + to +")";
			stmt.executeUpdate(sql);
			
			sql = "SELECT LAST_INSERT_ID() AS last_id FROM Period;";
			rs = stmt.executeQuery(sql);
			String pid = rs.getString("last_id");
			
			sql = "INSERT INTO Reserves (pid, hid, login) VALUES (" + pid + ", " + id + ", " + userName + ");";
			stmt.executeUpdate(sql);
			
			sql = "SELECT P.pid FROM Period P WHERE P.pid = (SELECT A.pid FROM Available A WHERE A.fromDate <= " + from + " AND A.toDate >= " + to + " AND A.hid = " + id + ");";
			rs = stmt.executeQuery(sql);
			String removePeriod = "";
			while(rs.next()){
				removePeriod = rs.getString("P.pid");
			}
			
			sql = "DELETE FROM Period WHERE pid = " + removePeriod + ";";
			stmt.executeUpdate(sql);
			sql = "DELETE FROM Available WHERE pid = " + removePeriod + ";";
			stmt.executeUpdate(sql);
		}
		catch(SQLException e){
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
