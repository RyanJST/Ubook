package Ubook;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Stay {
	public void showReservations(String userName, Statement stmt, List<String> pids) {
		try {
			String sql = "SELECT R.pid, R.cost, H.hid, H.name, P.fromDate, P.toDate  "
					+ "FROM Reserves R, TH H, Period P "
					+ "WHERE P.pid = R.pid AND R.hid = H.hid AND R.login = '" + userName + "'"
					+ "AND R.pid NOT IN (SELECT pid FROM Visits)"
					+ "AND R.pid NOT IN ('', ";
			String pidsString = "";
			for(String pid: pids) {
				pidsString += pid + ", ";
			}
			sql += pidsString;
			sql = sql.substring(0, sql.length() - 2);
			sql += ");";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				System.out.println("Id: " + rs.getString("R.pid")
				+ ", Name: " + rs.getString("H.name")
				+ ", From: "+ rs.getString("P.fromDate")
				+ ", To: " + rs.getString("toDate")
				+ ", Cost: " + rs.getString("cost"));
				
			}
		}
		catch (SQLException e) {
			
		} 
	}
	
	public void recordStay(String userName, Statement stmt, String pid) {
		try {
			String sql = "INSERT INTO Visits (pid, hid, login, cost)"
					+ " SELECT pid, hid, login, cost"
					+ " FROM Reserves"
					+ " WHERE pid = " + pid + ";";
			stmt.executeUpdate(sql);
			
		}
		catch (SQLException e) {
			
		} 
	}
	
	public void printSelected(String userName, Statement stmt, List<String> pids) {
		try {
			String sql = "SELECT R.pid, R.cost, H.hid, H.name, P.fromDate, P.toDate  "
					+ "FROM Reserves R, TH H, Period P "
					+ "WHERE P.pid = R.pid AND R.hid = H.hid AND R.login = '" + userName + "'"
					+ "AND R.pid IN ('', ";
			String pidsString = "";
			for(String pid: pids) {
				pidsString += pid + ", ";
			}
			sql += pidsString;
			sql = sql.substring(0, sql.length() - 2);
			sql += ");";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				System.out.println("Id: " + rs.getString("R.pid")
				+ ", Name: " + rs.getString("H.name")
				+ ", From: "+ rs.getString("P.fromDate")
				+ ", To: " + rs.getString("toDate")
				+ ", Cost: " + rs.getString("cost"));
			}
		}
		catch (SQLException e) {
			
		} 
	}
}
