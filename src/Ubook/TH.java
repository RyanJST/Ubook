package Ubook;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TH {
	//

	public String verifyHouseID(String userName, String houseID,Statement stmt){
 
		ResultSet rs = null;
		
	/*	
		if(houseID != null && !houseID.isEmpty()){
			try {
				houseID = MainMenu.input.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}

		}
		*/
		String sql = "SELECT * FROM TH WHERE login = '" + userName + "' AND hid = '" +houseID+ "';";
		try {
			rs = stmt.executeQuery(sql);
			if(!rs.isBeforeFirst()){
				//System.out.println("You do not have a house registered with that HouseID. Please try again. \n");
				houseID = null;
				}
			}

			catch(SQLException e){
				e.printStackTrace();
			}
		
	return houseID;
	}
	
	public String registerHouse(String userName, Statement stmt, String houseName, String address, String city, String state, String phoneNumber, String yearBuilt, String category, String URL) {
		String sql = "INSERT INTO TH (category, name, address, URL, phoneNumber, yearBuilt, login, city, state)"
				+ " VALUES( '" + category + "', '" + houseName+"','" + address + "', '" + URL+"','" + phoneNumber + "',"
						+ "'" + yearBuilt + "','" + userName + "','" + city + "','" + state + "')";
		try{
   		    stmt.executeUpdate(sql);
   		    return "Success!";
	 	}
	 	catch(SQLException e){
	 		return e.getMessage();
	 	}
	}

	public String listOwnedHouses(String userName, Statement stmt) {
		// TODO Auto-generated method stub
		String sql = "SELECT hid, name FROM TH WHERE login = '" + userName + "';";
		ResultSet rs = null;
		StringBuilder output = new StringBuilder();
		try {
			rs = stmt.executeQuery(sql);
			if(!rs.isBeforeFirst()){
				output.append("<p>You do not have any houses registered in the system.</p>");
				return output.toString();
			}
			output.append("<ul>");
			while(rs.next()){
				output.append("<li>House Name: " + rs.getString("name") + "<a href = \"EditTH.jsp?id=" + rs.getString("hid") +"\"> Edit!</a></li>");
			}
			output.append("</ul>");
			return output.toString();
		}
		catch(SQLException e){
			return e.getMessage();
		}
	}

	public String changeHouse(String userName, Statement stmt, String hid, String houseName, String address, String city, String state, String phoneNumber, String yearBuilt, String category, String URL) {
		String houseID = verifyHouseID(userName, hid, stmt);
		String sql;
		if(houseID == null){
			return "<p>You don't own that house!</p>";
		}		
		sql = "UPDATE TH SET category = '" + category+ "',name = '" + houseName+ "',address = '" + address+ "',URL = '" + URL+ "',phoneNumber = '" + phoneNumber+ "',"
				+ "yearBuilt = '" + yearBuilt+ "', city = '"+city+"', state = '"+state+"' WHERE hid = '" + hid + "' AND login = '" + userName + "';";
		try {
			stmt.executeUpdate(sql);
			return "<p>Your TH was edited!</p>";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return e.getMessage();
		}
	}
	
	public void updateAvailability(String userName, Statement stmt) {
		// TODO Auto-generated method stub
		
		
		System.out.println("Here is a list of the houses you own.\n");
		listOwnedHouses(userName, stmt);
		
		System.out.println("Select which house you wish to do availability operations on.\n");
		String houseID = null;
		
		String choice = null;
		try {
			choice = MainMenu.input.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		houseID = verifyHouseID(userName, choice, stmt);
		//BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		if(houseID == null){
			System.out.println("You do not own the house in question.");
		}
		while(houseID != null){
			System.out.println("Press 1 to add a date of availability");
			System.out.println("Press 2 to change a date of availability");
			System.out.println("Press 3 to remove a date of availability");
			System.out.println("Press 4 to show all current availabilities of your house");
			System.out.println("Press 5 to exit.");
			choice = null;
			try {
				choice = MainMenu.input.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			dateInfo infoDate = new dateInfo();
			
			switch(choice ){
			case "1":
				infoDate.createAvailability(houseID, stmt);
				break;
			case "2":
				infoDate.changeAvailability(houseID, stmt);
				break;
			case "3":
				infoDate.removeAvailability(houseID, stmt);
				break;
			case "4":
				infoDate.showAvailability(houseID, stmt);
				break;
			case "5":
				houseID = null;
				break;
			default:
				System.out.println("That is not a valid option.  Please try again.");
				break;
			}
		}
		
		
		
	}

	public void keywords(String userName, Statement stmt) {
		// TODO Auto-generated method stub
		
		String houseID =null;
		System.out.println("Here is a list of the houses you own.");
		listOwnedHouses(userName, stmt);
		
		
		System.out.println("Select which house you wish to view/update/create keywords for.");
		
		String choice = null;
		try {
			choice = MainMenu.input.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		houseID = verifyHouseID(userName, choice,stmt);
		
		if(houseID != null){
			Keyword keys = new Keyword();
			
			keys.setUp(houseID, stmt);
		}
		else{
			System.out.println("You do not own that house.");
		}
	}

	public void THSuggestions(String THID, String userName, Statement stmt){
		System.out.println("Here are some other houses you may like.");
		String sql = "SELECT DISTINCT V.hid, T.name, T.category, T.address, T.URL, T.phoneNumber, T.yearBuilt, T.city, T.state, T.login, COUNT(distinct V.login) AS guestCount FROM Visits V, TH T WHERE V.login = ANY(SELECT DISTINCT V2.login FROM Visits V2 WHERE V2.hid = '"+THID+"' AND V2.login != '"+userName+"') AND V.hid != '"+THID+"' AND V.hid = T.hid GROUP BY  V.hid, T.name, T.category, T.address, T.URL, T.phoneNumber, T.yearBuilt, T.city, T.state, T.login ORDER BY guestCount DESC; ";
		//String sql = "SELECT DISTINCT V.hid, COUNT(distinct V.login) AS guestCount FROM  Visits V WHERE V.login = ANY(SELECT DISTINCT V2.login FROM 5530db34.Visits V2 WHERE V2.hid = '"+THID+"' AND V2.login != '"+userName+"') AND V.hid != '"+THID+"' GROUP BY  V.hid ORDER BY guestCount DESC; ";
		ResultSet rs = null;
		
		try {
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				System.out.println("House ID: " + rs.getString("V.hid")+ ", House Visits by Other Users who stayed at this house: " + rs.getString("guestCount")+ ", House Name: " + rs.getString("T.name")+", House Category: " + rs.getString("T.category")+", House Address: " + rs.getString("T.address")
				 + ", " +rs.getString("T.city")+", " + rs.getString("T.state")+", House Year Built: " + rs.getString("T.yearBuilt")+", House Phone Number: " + rs.getString("T.phoneNumber")+", House URL: " + rs.getString("T.URL")+
						", House Owner: " + rs.getString("T.login"));
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Exiting TH Suggestions");
		System.out.println("");
	}

	
}

	
	

