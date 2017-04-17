package Ubook;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Stay {
	public String showReservations(String userName, Statement stmt, List<String> pids) {
		try {
			StringBuilder output = new StringBuilder();
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
			output.append("<ul>");
			while(rs.next()) {
				output.append("<li><a href = \"StayConfirm.jsp?id=" + rs.getString("R.pid") + "\">"
				+ "Name: " + rs.getString("H.name")
				+ ", From: "+ rs.getString("P.fromDate")
				+ ", To: " + rs.getString("toDate")
				+ ", Cost: " + rs.getString("cost")
				+ "</a></li>");
				
			}
			output.append("</ul>");
			return output.toString();
		}
		catch (SQLException e) {
			return e.getMessage();
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
	
	public String printSelected(String userName, Statement stmt, List<String> pids) {
		try {
			StringBuilder output = new StringBuilder();
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
			output.append("<li>"
					+ "Name: " + rs.getString("H.name")
					+ ", From: "+ rs.getString("P.fromDate")
					+ ", To: " + rs.getString("toDate")
					+ ", Cost: " + rs.getString("cost")
					+ "</li>");
			}
			return output.toString();
		}
		catch (SQLException e) {
			return e.toString();
		} 
	}
	
	public String viewStays(String userName, Statement stmt, String targetDoc){
		StringBuilder output = new StringBuilder();
		String sql = "SELECT V.pid, V.hid, V.cost, T.name, P.fromDate, P.toDate FROM Visits V, TH T, Period P WHERE P.pid = V.pid AND T.hid = V.hid AND  V.login = '"+userName+"';";
		ResultSet rs = null;
		
		try {
			rs = stmt.executeQuery(sql);
			if(rs.isBeforeFirst()){
				return null;
			}
			output.append("<ul>");
			while(rs.next()){
				output.append("<li><a href = \"" + targetDoc + "?id=" + rs.getString("V.hid") + "\">"
						+ "House Name: " + rs.getString("T.name")
						+ ", Cost of Stay: " + rs.getString("V.cost")
						+ ", Start Date of Stay: " +rs.getString("p.fromDate")
						+ ", End date of Stay: " + rs.getString("P.toDate")
						+ "</a></li>");
			}
			output.append("</ul>");
			return output.toString();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return e.getMessage();
		}
		
	}
	
	public String verifyStay(String userName, String houseID, Statement stmt){
		String result = null;
		
		String sql = "SELECT V.hid FROM Visits V WHERE V.login = '"+userName+"' AND V.hid = '"+houseID+"';";
		ResultSet rs = null;
		 
		try {
			rs= stmt.executeQuery(sql);
			if(rs.isBeforeFirst()){
				result = houseID;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
}
