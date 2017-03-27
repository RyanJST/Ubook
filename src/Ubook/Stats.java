package Ubook;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Stats {

	public void popularTH(String userName, String amount, Statement stmt) {
		// TODO Auto-generated method stub
		String sql = "SELECT V.hid, T.name, T.category, T.address, T.URL, T.phoneNumber, T.yearBuilt, T.city, T.state, T.login, COUNT(V.hid) AS houseCount FROM "
				+ "Visits V, TH T WHERE V.hid = T.hid GROUP BY V.hid, T.name, T.category, T.address, T.URL, T.phoneNumber, T.yearBuilt, T.city, T.state, T.login ORDER BY houseCount DESC "
				+ "LIMIT " + amount + ";" ;
		
		ResultSet rs = null;
		
		try {
			rs = stmt.executeQuery(sql);
			if(!rs.isBeforeFirst()){
				System.out.println("There are no houses that have been stayed at yet in the system.");
				
				}
			
			while(rs.next()){
				System.out.println("House ID: " + rs.getString("V.hid")+ ", House Name: " + rs.getString("T.name")+", House Category: " + rs.getString("T.category")+", House Address: " + rs.getString("T.address")
				 + ", " +rs.getString("T.city")+", " + rs.getString("T.state")+", House Year Built: " + rs.getString("T.yearBuilt")+", House Phone Number: " + rs.getString("T.phoneNumber")+", House URL: " + rs.getString("T.URL")+
						", House Owner: " + rs.getString("T.login"));
			}
			}
		 catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("\n");
		
		System.out.println("Exiting popular TH menu");
		
		System.out.println("\n");
	}

	public void expensiveTH(String userName, String amount, Statement stmt) {
		// TODO Auto-generated method stub
		String sql = "SELECT V.hid, T.name, T.category, T.address, T.URL, T.phoneNumber, T.yearBuilt, T.city, T.state, T.login, AVG(V.cost) AS costAvg FROM "
				+ "Visits V, TH T WHERE V.hid = T.hid GROUP BY V.hid, T.name, T.category, T.address, T.URL, T.phoneNumber, T.yearBuilt, T.city, T.state, T.login ORDER BY costAVG DESC "
				+ "LIMIT " + amount + ";" ;
		
		ResultSet rs = null;
		
		try {
			rs = stmt.executeQuery(sql);
			if(!rs.isBeforeFirst()){
				System.out.println("There are no houses that have been stayed at yet in the system.");
				
				}
			
			while(rs.next()){
				System.out.println("House ID: " + rs.getString("V.hid")+ ", House Cost: " + rs.getString("costAVG")+ ", House Name: " + rs.getString("T.name")+", House Category: " + rs.getString("T.category")+", House Address: " + rs.getString("T.address")
				 + ", " +rs.getString("T.city")+", " + rs.getString("T.state")+", House Year Built: " + rs.getString("T.yearBuilt")+", House Phone Number: " + rs.getString("T.phoneNumber")+", House URL: " + rs.getString("T.URL")+
						", House Owner: " + rs.getString("T.login"));
			}
			}
		 catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("\n");
		
		System.out.println("Exiting most expensive TH menu");
		
		System.out.println("\n");
		
	}

	public void ratedTH(String userName, String amount, Statement stmt) {
		// TODO Auto-generated method stub
		String sql = "SELECT DISTINCT T.category FROM TH T ORDER BY T.category";
		List<String> categories = new ArrayList<String>();
		ResultSet rs = null;
		
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				categories.add(rs.getString("T.category"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 0; i < categories.size(); i++){
			sql = "SELECT F.hid, T.name, T.category, T.address, T.URL, T.phoneNumber, T.yearBuilt, T.city, T.state, T.login, AVG(F.score) AS scoreAVG FROM "
					+ "Feedback F, TH T WHERE F.hid = T.hid AND T.category = '"+categories.get(i)+"' GROUP BY F.hid, T.name, T.category, T.address, T.URL, T.phoneNumber, T.yearBuilt, T.city, T.state, T.login ORDER BY scoreAVG DESC "
					+ "LIMIT " + amount + ";" ;
			
			try {
				rs = stmt.executeQuery(sql);
				if(!rs.isBeforeFirst()){
					System.out.println("There are no houses that have been stayed at or reviewed yet in the system for the category: '" + categories.get(i) + "'.");
					System.out.println("");
					
					}
				
				while(rs.next()){
					System.out.println("House Category: " + rs.getString("T.category")+", House ID: " + rs.getString("F.hid")+", House Average Score: " + rs.getString("scoreAVG")+ ", House Name: "  + rs.getString("T.category")+", House Address: " + rs.getString("T.address")
					 + ", " +rs.getString("T.city")+", " + rs.getString("T.state")+", House Year Built: " + rs.getString("T.yearBuilt")+", House Phone Number: " + rs.getString("T.phoneNumber")+", House URL: " + rs.getString("T.URL")+
							", House Owner: " + rs.getString("T.login"));
					System.out.println("");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
			
		}
		
		System.out.println("Exiting rated category TH menu");
		
		System.out.println("");
	}

}
