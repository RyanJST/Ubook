package Ubook;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class BRTH {

	public void browseTHs(Statement stmt, String minPrice, String maxPrice, String city, String state, String category, String keywords, String sortBy, String params) {
		String[] parameterSplit = params.split(" ");
		
		String[] kws = keywords.split(" ");
		try{
			String sql = "SELECT DISTINCT H.*, A.priceNight, IFNULL(AVG(F.score), 0) as fbScore FROM TH H LEFT JOIN";
			if (sortBy.equals("average trusted score")) sql += " (Feedback F LEFT JOIN Trust T ON F.login = T.login2)";
			else sql += " Feedback F";
			sql += " ON F.hid = H.hid LEFT JOIN Available A ON H.hid = A.hid WHERE ";
			if (sortBy.equals("average trusted score")) sql += " (T.isTrusted OR T.isTrusted IS NULL) AND";
			sql += " A.priceNight <= ALL (SELECT A1.priceNight FROM Available A1 WHERE A1.hid = H.hid)";
			sql += " AND (";
			int j = 0;
			while(j < parameterSplit.length) {
				if (parameterSplit[j].equals("minimum_price")) {
					if (j > 0) sql += " " + parameterSplit[j-1] + " ";
					sql += "A.priceNight > " + minPrice;
					j+=2;
				}
				else if (parameterSplit[j].equals("maximum_price")) {
					if (j > 0) sql += " " + parameterSplit[j-1] + " ";
					sql += "A.priceNight < " + maxPrice;
					j+=2;
				}
				else if (parameterSplit[j].equals("state")) {
					if (j > 0) sql += " " + parameterSplit[j-1] + " ";
					sql += "H.state = '" + state + "'";
					j+=2;
				}
				else if (parameterSplit[j].equals("city")) {
					if (j > 0) sql += " " + parameterSplit[j-1] + " ";
					sql += "H.city = '" + city + "'";
					j+=2;
				}
				else if (parameterSplit[j].equals("category")) {
					if (j > 0) sql += " " + parameterSplit[j-1] + " ";
					sql += "H.category = '" + category + "'";
					j+=2;
				}
				else if (parameterSplit[j].equals("keywords")) {
					if (j > 0) sql += " " + parameterSplit[j-1] + " ";
					sql += "H.hid IN (SELECT H1.hid FROM TH H1 JOIN HasKeywords HK ON H1.hid = HK.hid WHERE HK.wid IN (SELECT K.wid FROM Keywords K WHERE ";
					for(int i = 0; i < kws.length; i++) {
						if(i != 0) sql += " OR ";
						sql += "K.word = '" + kws[i] + "'";
					}
					sql += ") GROUP BY H1.hid HAVING COUNT(*) = " + kws.length + ")";
					j+=2;
				}
			}
			sql += ") GROUP BY H.hid, A.priceNight ORDER BY ";
			if(sortBy.equals("average score") || sortBy.equals("average trusted score")) {
				sql += "fbScore DESC;";
			}
			else {
				sql += "A.priceNight ASC;";
			}
 			ResultSet rs = null;
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				System.out.println("House ID: " + rs.getString("H.hid") + ", Name: " + rs.getString("H.name") + ", Category: " + rs.getString("H.category"));
				if(rs.getString("A.priceNight") != null) System.out.println("  Price per Night as Low as: $" + rs.getString("A.priceNight"));
				else System.out.println("  This house currently has no availabilities");
				System.out.println("  Address: " + rs.getString("H.address") + ", " + rs.getString("H.city") + " " + rs.getString("H.state"));
				System.out.println("  Owner: " + rs.getString("H.login") + ", Phone #: " + rs.getString("H.phoneNumber") +  ", Website: " + rs.getString("url"));
				System.out.println("Average Feedback Score: " + rs.getString("fbScore"));
			}
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
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
			if(!oldFrom.equals(r.fromDate)) {
				sql = "INSERT INTO Period (fromDate, toDate) VALUES ('" + oldFrom + "', ('" + r.fromDate + "' - INTERVAL 1 DAY));";
				stmt.executeUpdate(sql);
				sql = "INSERT INTO Available (pid, hid, priceNight) VALUES (LAST_INSERT_ID(), " + r.hid + ", " + priceNight + ");";
				stmt.executeUpdate(sql);
			}
			if(!oldTo.equals(r.toDate)) {
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
