package Ubook;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TH {
	//

	public String verifyHouseID(String userName, String houseID,Statement stmt){
 
		ResultSet rs = null;
		
		
		if(houseID != null && !houseID.isEmpty()){
			try {
				houseID = MainMenu.input.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}

		}
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
	
	public void registerHouse(String userName, Statement stmt) {
		
		System.out.println("Here you register your temporary housing. Do you wish to continue? (Y/N)");
		
		String houseName = "start";
		boolean changed = false;
		try{
			if((MainMenu.input.readLine()).toLowerCase().equals("y")){
				houseName = null;	
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		if(houseName == null){
			while(houseName == null){
				System.out.println("Please set the name of your new temporary housing.");
				
				try {
					houseName = MainMenu.input.readLine();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if(houseName == null || houseName.isEmpty()){
					System.out.println("You need to name your temporary housing.  Please try again.");
					houseName = null;
				}
			}
			
			String address = null;
			while(address == null){
				System.out.println("Please set the street address of your new temporary housing.");
				
				try {
					address = MainMenu.input.readLine();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if(address == null || address.isEmpty()){
					System.out.println("You need to put in the street address of your temporary housing.  Please try again.");
					address = null;
				}
			}
			
			String city = null;
			while(city == null){
				System.out.println("Please set the city of your new temporary housing.");
				
				try {
					city = MainMenu.input.readLine();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if(city == null || city.isEmpty()){
					System.out.println("You need to put in the city of your temporary housing.  Please try again.");
					city = null;
				}
			}
			
			String state = null;
			while(state == null){
				System.out.println("Please set the state of your new temporary housing as a two letter abbreviation.");
				
				try {
					state = MainMenu.input.readLine();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if(state == null || state.length() != 2){
					System.out.println("You need to put in the state of your temporary housing as a two letter abbreviation.  Please try again.");
					state = null;
				}
			}
			String phoneNumber = null;
			while(phoneNumber == null){
				System.out.println("Please set the phone number of your new temporary housing in the format \"1234567890\".");
				
				try {
					phoneNumber = MainMenu.input.readLine();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(phoneNumber == null || phoneNumber.isEmpty()){
					System.out.println("You need to give a valid phone number. Please try again.");
					phoneNumber = null;
				}
			}
			
			String yearBuilt = null;
			System.out.println("Please set the year that your temporary housing was built. This field does not need to be filled in.");
			
			try {
				yearBuilt = MainMenu.input.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if(yearBuilt.isEmpty()){
				yearBuilt = null;
			}
			
			String category = null;
			System.out.println("Please input the category of your temporary housing.  Does not need to be filled in.");
			
			try {
				category = MainMenu.input.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if(category.isEmpty()){
				category = null;
			}
			
			String URL = null;
			System.out.println("Please input the web address of your temporary housing.  Does not need to be filled in.");
			
			try {
				URL = MainMenu.input.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if(URL.isEmpty()){
				URL = null;
			}
			
			System.out.println("This is what the new house listing will look like. \n");
			
			System.out.println("House Name: " + houseName + ",   Street Address: " + address +", City: " +city+", State: "
					+state+ "  House category: " + category +",   House URL: " + URL +",   House Year Built: " + yearBuilt + "\n");
			
			System.out.println("Do you want to keep these changes?  (Y/N)");
			String choice = null;
			try {
				choice = MainMenu.input.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if(choice.toLowerCase().equals("y")){
				System.out.println("Registering House");
				String sql = "INSERT INTO TH (category, name, address, URL, phoneNumber, yearBuilt, login, city, state)"
						+ " VALUES( '" + category + "', '" + houseName+"','" + address + "', '" + URL+"','" + phoneNumber + "',"
								+ "'" + yearBuilt + "','" + userName + "','" + city + "','" + state + "')";
				try{
		   		    stmt.executeUpdate(sql);		    
		   		    System.out.println("Your new house was registered! \n");
				 	}
				 	catch(SQLException e)
				 	{
				 		System.out.println("cannot execute the query");
				 		System.out.println(e.getMessage());
				 	}
			

			}
			
		}
		System.out.println("Exiting house registration \n");
	}

	public void listOwnedHouses(String userName, Statement stmt) {
		// TODO Auto-generated method stub
		String sql = "SELECT hid, name FROM TH WHERE login = '" + userName + "';";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			if(!rs.isBeforeFirst()){
				System.out.println("You do not have any houses registered in the system.");
			}
			while(rs.next()){
				
				System.out.println("House ID: " + rs.getString("hid") +",   House Name: " + rs.getString("name") );
					
				}
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}

	public int changeHouse(String userName, Statement stmt) {

		// TODO Auto-generated method stub
		String houseID = null;
		//BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String sql = null;
		ResultSet rs = null;
		String name = null;
		String category = null;
		String address = null;
		String URL = null;
		String phoneNumber = null;
		String yearBuilt = null;
		String city = null;
		String state = null;
		int result = 0;
		
		System.out.println("Here is a list of the houses you own.");
		listOwnedHouses(userName, stmt);
		
		System.out.println("\n");
		System.out.println("Select which house you wish to update");
		
		try {
			houseID = MainMenu.input.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		houseID = verifyHouseID(userName, houseID, stmt);
	
		
		
		boolean changed = false;
		if(houseID != null){
			sql = "SELECT * FROM TH WHERE login = '" + userName + "' AND hid = '" +houseID+ "';";
			try {
				rs = stmt.executeQuery(sql);
				if(!rs.isBeforeFirst()){
					System.out.println("You do not have a house registered with that HouseID. Please try again. \n");
					houseID = null;
					
				}
				else{
					while(rs.next()){
						name = rs.getString("name");
						category = rs.getString("category");
						address = rs.getString("address");
						URL = rs.getString("URL");
						phoneNumber = rs.getString("phoneNumber");
						yearBuilt = rs.getString("yearBuilt");
						city = rs.getString("city");
						state = rs.getString("state");
					}
				}
			
				}

			catch(SQLException e){
				e.printStackTrace();
			}
			
			if(houseID != null){
				System.out.println("Please select what you want the new name to be, leave blank to keep the name the same.");
				String choice = null;
				try {
					choice = MainMenu.input.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(!choice.isEmpty()){
					name = choice;
					choice = null;
				}
				
				System.out.println("Please select what you want the new category to be, leave blank to keep the category the same.");
				
				try {
					choice = MainMenu.input.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(!choice.isEmpty()){
					category = choice;
					choice = null;
				}
				
				System.out.println("Please select what you want the new street address to be, leave blank to keep the address the same.");
				
				try {
					choice = MainMenu.input.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(!choice.isEmpty()){
					address = choice;
					choice = null;
				}
				
				System.out.println("Please select what you want the new city to be, leave blank to keep the city the same.");
				
				try {
					choice = MainMenu.input.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(!choice.isEmpty()){
					city = choice;
					choice = null;
				}			
				
				System.out.println("Please select what you want the new state to be, leave blank to keep the state the same.");
				
				try {
					choice = MainMenu.input.readLine();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(!choice.isEmpty()){
					state = choice;
					choice = null;
				}
				
				System.out.println("Please select what you want the new URL to be, leave blank to keep the URL the same.");
				
				try {
					choice = MainMenu.input.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(!choice.isEmpty()){
					URL = choice;
					choice = null;
				}
				
				System.out.println("Please select what you want the new phone number to be, leave blank to keep the phone number the same.");
				
					try {
						choice = MainMenu.input.readLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if(!choice.isEmpty()){
						phoneNumber = choice;
						choice = null;
					}
				
				
	
				System.out.println("Please select what you want the new year built to be, leave blank to keep the year built the same.");
				
					try {
						choice = MainMenu.input.readLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if(!choice.isEmpty()){
						yearBuilt = choice;
						choice = null;
					}
				System.out.println("This is what the changed house listing will look like. \n");
				
				System.out.println("House Name: " + name + ",   Street Address: " + address +", City: " +city+", State: "
						+state+ "  House category: " + category +",   House URL: " + URL +",   House Year Built: " + yearBuilt + "\n");
				
				System.out.println("Do you want to keep these changes?  (Y/N)");
				choice = null;
				
				try{
					choice = MainMenu.input.readLine();
				}
				catch(IOException e){
					
				}
				
				if(choice.toLowerCase().equals("y")){
					changed = true;
				}
			}
			
			if(changed){
				sql = "UPDATE TH SET category = '" + category+ "',name = '" + name+ "',address = '" + address+ "',URL = '" + URL+ "',phoneNumber = '" + phoneNumber+ "',"
						+ "yearBuilt = '" + yearBuilt+ "', city = '"+city+"', state = '"+state+"' WHERE hid = '" + houseID + "' AND login = '" + userName + "';";
				
				
				try {
					result = stmt.executeUpdate(sql);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else{
			System.out.println("You do not own a house that is registered with that house ID.  Please try again.");
		}
		
		System.out.println("Exiting the update house menu.");
		
		return result;
	}
	
	public void updateAvailability(String userName, Statement stmt) {
		// TODO Auto-generated method stub
		
		
		System.out.println("Here is a list of the houses you own.\n");
		listOwnedHouses(userName, stmt);
		
		System.out.println("Select which house you wish to do availability operations on.\n");
		String houseID = null;
		
		try {
			houseID = MainMenu.input.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		houseID = verifyHouseID(userName, houseID,stmt);
		//BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		while(houseID != null){
			System.out.println("Press 1 to add a date of availability");
			System.out.println("Press 2 to change a date of availability");
			System.out.println("Press 3 to remove a date of availability");
			System.out.println("Press 4 to show all current availabilities of your house");
			System.out.println("Press 5 to exit.");
			String choice = null;
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
		
		System.out.println("\n");
		System.out.println("Select which house you wish to view/update/create keywords for.");
		
		try {
			houseID = MainMenu.input.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		houseID = verifyHouseID(userName, houseID,stmt);
		
		if(houseID != null){
			Keyword keys = new Keyword();
			
			keys.setUp(houseID, stmt);
		}
	}

	public void THSuggestions(String THID, String userName, Statement stmt){
		System.out.println("Here are some other houses you may like.");
		String sql = "SELECT DISTINCT V.hid, T.name, T.category, T.address, T.URL, T.phoneNumber, T.yearBuilt, T.city, T.state, T.login, COUNT(distinct V.login) AS guestCount FROM Visits V, TH T WHERE V.login = ANY(SELECT DISTINCT V2.login FROM Visits V2 WHERE V2.hid = '"+THID+"' AND V2.login != '"+userName+"') AND V.hid != '"+THID+"' GROUP BY  V.hid, T.name, T.category, T.address, T.URL, T.phoneNumber, T.yearBuilt, T.city, T.state, T.login ORDER BY guestCount DESC; ";
		
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

	
	

