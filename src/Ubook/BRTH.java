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
	
	public void showTHs(Statement stmt) {
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
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public boolean showTHAvails(String hid, Statement stmt) {
		try{
			String sql = "SELECT P.fromDate, P.toDate, A.priceNight FROM Available A, Period P WHERE A.pid = P.pid AND hid = " + hid + ";";
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println("Availability:");
			boolean empty = true;
			while(rs.next()){
				System.out.println("From: " + rs.getString("P.fromDate") + ", To: " + rs.getString("P.toDate") + ", Price: $" + rs.getString("A.priceNight"));
				empty = false;
			}
			if(empty){
				System.out.println("This house currently has no availabilities");
				return false;
			}
			else return true;
		}
		catch(SQLException E) {
			
		}
		return false;
	}
	
	public void printSelected(Statement stmt, List<Reserve> reserves) {
		try {
			for(Reserve r: reserves) {
				String sql = "SELECT H.name, A.priceNight "
						+ "FROM Available A, TH H, Period P "
						+ "WHERE A.hid = H.hid AND P.pid = A.pid "
						+ "AND H.hid = " + r.hid + " AND P.fromDate <= '" + r.fromDate + "' AND P.toDate >= '" + r.toDate + "';";
				ResultSet rs = stmt.executeQuery(sql);
				while(rs.next()) {
					System.out.println("Name: " + rs.getString("H.name")
					+ ", From: "+ r.fromDate
					+ ", To: " + r.toDate
					+ ", Price per Night: " + rs.getString("A.priceNight"));
				}
			}
		}
		catch (SQLException e) {
			
		} 
	}

	public void insertReserve(String userName, Reserve r, Statement stmt) {
		try {
			String sql = "SELECT P.pid, P.fromDate, P.toDate FROM Period P WHERE P.pid = (SELECT A.pid FROM Available A, Period P1 WHERE P1.pid = A.pid AND P1.fromDate <= '" + r.fromDate + "' AND P1.toDate >= '" + r.toDate + "' AND A.hid = " + r.hid + ");";
			ResultSet rs = stmt.executeQuery(sql);
			String removePeriod = "";
			String oldFrom = "";
			String oldTo = "";
			while(rs.next()){
				removePeriod = rs.getString("P.pid");
				oldFrom = rs.getString("P.fromDate");
				oldTo = rs.getString("P.toDate");
			}
			sql = "SELECT priceNight FROM Available WHERE pid = " + removePeriod + ";";
			rs = stmt.executeQuery(sql);
			int priceNight = 0;
			while(rs.next()) {
				priceNight = Integer.parseInt(rs.getString("priceNight"));
			}
			
			sql = "INSERT INTO Period (fromDate, toDate) VALUES ('" + r.fromDate + "', '" + r.toDate +"')";
			stmt.executeUpdate(sql);
			
			sql = "SELECT DATEDIFF(toDate, fromDate) AS length, fromDate, toDate FROM Period WHERE pid = LAST_INSERT_ID();";
			rs = stmt.executeQuery(sql);
			int length = 0;
			while(rs.next()) {
				length = Integer.parseInt(rs.getString("length"));
			}
			
			sql = "INSERT INTO Reserves (pid, hid, login, cost) VALUES (LAST_INSERT_ID(), " + r.hid + ", '" + userName + "', " + (priceNight * length) + ");";
			stmt.executeUpdate(sql);
			if(oldFrom != r.fromDate) {
				sql = "INSERT INTO Period (fromDate, toDate) VALUES ('" + oldFrom + "', ('" + r.fromDate + "' - INTERVAL 1 DAY));";
				stmt.executeUpdate(sql);
				sql = "INSERT INTO Available (pid, hid, priceNight) VALUES (LAST_INSERT_ID(), " + r.hid + ", " + priceNight + ");";
				stmt.executeUpdate(sql);
			}
			if(oldTo != r.toDate) {
				sql = "INSERT INTO Period (fromDate, toDate) VALUES (('" + r.toDate + "' + INTERVAL 1 DAY), '" + oldTo + "');";
				stmt.executeUpdate(sql);
				sql = "INSERT INTO Available (pid, hid, priceNight) VALUES (LAST_INSERT_ID(), " + r.hid + ", " + priceNight + ");";
				stmt.executeUpdate(sql);
			}
			sql = "DELETE FROM Available WHERE pid = " + removePeriod + ";";
			stmt.executeUpdate(sql);
			sql = "DELETE FROM Period WHERE pid = " + removePeriod + ";";
			stmt.executeUpdate(sql);
			
			TH suggest = new TH();
			
			suggest.THSuggestions(r.hid, userName, stmt);
		}
		catch (SQLException e) {
			
		}
	}
}
