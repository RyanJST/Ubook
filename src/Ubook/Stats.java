package Ubook;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Stats {

	public void popularTH(String userName, String amount, Statement stmt) {
		// TODO Auto-generated method stub
		String sql = "SELECT V.hid, T.name, T.category, T.address, T.URL, T.phoneNumber, T.yearBuilt, T.city, T.state, T.login, COUNT(V.hid) AS houseCount FROM "
				+ "Visits V, TH T WHERE V.hid = T.hid GROUP BY V.hid, T.name, T.category, T.address, T.URL, T.phoneNumber, T.yearBuilt, T.city, T.state, T.login ORDER BY houseCount DESC "
				+ "LIMIT" + amount + ";" ;
		
		ResultSet rs = null;
		
		try {
			rs = stmt.executeQuery(sql);
			if(!rs.isBeforeFirst()){
				System.out.println("There are no houses that have been stayed at yet in the system.");
				
				}
			
			while(rs.next()){
				System.out.println("House ID: " + rs.getString("V.hid")+ "House ID: " + rs.getString("T.name")+"House ID: " + rs.getString("T.category")+"House ID: " + rs.getString("T.address")+
						"House ID: " + rs.getString("T.URL")+"House ID: " + rs.getString("T.phoneNumber")+"House ID: " + rs.getString("T.yearBuilt")+"House ID: " + rs.getString("T.city")+"House ID: " + rs.getString("T.state")+
						"House ID: " + rs.getString("T.login"));
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
		
	}

	public void ratedTH(String userName, String amount, Statement stmt) {
		// TODO Auto-generated method stub
		
	}

}
