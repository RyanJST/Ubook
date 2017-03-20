package Ubook;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BRTH {

	public void browseTHs(String userName, Statement stmt) {
		// TODO Auto-generated method stub
		System.out.println("Here is where you set your filters and browse available THs.");
		
		boolean done = true;
		String startPrice = null;
		String endPrice = null;
		String city = null;
		String state = null;
		String category = null;
		List<String> keywords = new ArrayList<String>();
		
		while(!done){
			
		}
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
			System.out.println("Enter '-1' at any time to exit");
			System.out.println("Select House ID of desired house:");
			int id = Integer.parseInt(MainMenu.input.readLine());
			if (id < 0) return;
			sql = "SELECT P.fromDate, P.toDate, A.priceNight FROM Available A, Period P WHERE A.pid = P.pid AND hid = " + id + ";";
			rs = stmt.executeQuery(sql);
			System.out.println("Availability:");
			boolean empty = true;
			while(rs.next()){
				System.out.println("From: " + rs.getString("P.fromDate") + ", To: " + rs.getString("P.toDate") + ", Price: $" + rs.getString("A.priceNight"));
				empty = false;
			}
			if(empty) System.out.println("This house currently has no availabilities");
			else {
				System.out.println("Enter the date you would like to would like to start your stay (Format YYYY-MM-DD):");
				String from = MainMenu.input.readLine();
				if(from == "-1") return;
				System.out.println("Enter the date you would like to end your stay (Format YYYY-MM-DD):");
				String to = MainMenu.input.readLine();
				if(to == "-1") return;
				
				sql = "SELECT P.pid FROM Period P WHERE P.pid = (SELECT A.pid FROM Available A, Period P1 WHERE P1.pid = A.pid AND P1.fromDate <= '" + from + "' AND P1.toDate >= '" + to + "' AND A.hid = " + id + ");";
				rs = stmt.executeQuery(sql);
				String removePeriod = "";
				while(rs.next()){
					removePeriod = rs.getString("P.pid");
				}
				
				if(removePeriod == "") System.out.println("Those dates are not available");
				else{
					sql = "SELECT priceNight FROM Available WHERE pid = " + removePeriod + ";";
					rs = stmt.executeQuery(sql);
					int priceNight = 0;
					while(rs.next()) {
						priceNight = Integer.parseInt(rs.getString("priceNight"));
					}
					
					sql = "INSERT INTO Period (fromDate, toDate) VALUES ('" + from + "', '" + to +"')";
					stmt.executeUpdate(sql);
					
					sql = "SELECT DATEDIFF(toDate, fromDate) AS length FROM Period WHERE pid = LAST_INSERT_ID();";
					rs = stmt.executeQuery(sql);
					int length = 0;
					while(rs.next()) {
						length = Integer.parseInt(rs.getString("length"));
					}
					
					sql = "INSERT INTO Reserves (pid, hid, login, cost) VALUES (LAST_INSERT_ID(), " + id + ", '" + userName + "', " + (priceNight * length) + ");";
					stmt.executeUpdate(sql);
					
					sql = "DELETE FROM Available WHERE pid = " + removePeriod + ";";
					stmt.executeUpdate(sql);
					sql = "DELETE FROM Period WHERE pid = " + removePeriod + ";";
					stmt.executeUpdate(sql);
				}
			}
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
