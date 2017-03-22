package Ubook;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class User {
	//BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	public String setUpUser(Statement stmt) {
		// TODO Auto-generated method stub
		int gottenResults = 0;
		
		//BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String sql = null;
		System.out.println("Please put in your preffered user name");
		String userName = null;
		while(userName == null){
			try {
				userName = MainMenu.input.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			sql = "SELECT login FROM Users WHERE login = '" + userName + "';";
			ResultSet rs = null;
			try {
				rs = stmt.executeQuery(sql);
					if(rs.isBeforeFirst()){
						System.out.println("That username has already been taken.  Please select a different one.\n");
					}
				}
			
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();	
				}
			}

		System.out.println("Please put in your password");
		String password = null;
		
		try {
			password = MainMenu.input.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("Please put in your full name");
		String name = null;
		
		try {
			name = MainMenu.input.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("Please put in your address");
		String address = null;
		
		try {
			address = MainMenu.input.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("Please put in your phone number");
		String phoneNumber = null;
		
		try {
			phoneNumber = MainMenu.input.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		sql = "INSERT INTO Users (login, name, password, address, phoneNumber)"
				+ " VALUES( '" + userName + "', '" + name+"','" + password + "', '" + address+"','" + phoneNumber + "')";
		try{
   		 	gottenResults=stmt.executeUpdate(sql);		     
		 	}
		 	catch(Exception e)
		 	{
		 		System.out.println("cannot execute the query");
		 		System.out.println(e.getMessage());
		 	}
		if(gottenResults == 0){
			userName = null;
		}
		
	    return userName;
	}

	public String loginUser(Statement stmt) {
		//BufferedReader MainMenu.input = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Please put in your user name");
		String userName = null;
		
		try {
			userName = MainMenu.input.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("Please put in your password");
		
		String password = null;
		try {
			password = MainMenu.input.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String sql = "SELECT password FROM Users WHERE login = '" + userName + "';";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			if(rs.isBeforeFirst()){
				while(rs.next()){
					
					if(!rs.getString("password").equals(password)){
						userName = null;
						System.out.println("That is the wrong password "
								+ "for the username you entered");
						
					}
				}
			}
			else{
				userName = null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return userName;
	}

	public void setAdmin(String userName, Statement stmt) {
		// TODO Auto-generated method stub
		
		String sql = "UPDATE Users Set userType = '1' WHERE login = '" + userName + "' AND userType = '0';";
		
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setFavoriteTH(String userName, Statement stmt) {
		// TODO Auto-generated method stub
		boolean done = false;
		String choice = null;
		String sql = null;
		ResultSet rs = null; 
		int haveFavorite = -1;
		while(!done){
			
			haveFavorite = viewFavoriteTH(userName, stmt, true);
			
			System.out.println("Here is the list of THs you have stayed at.");
			
			//Show Houses user has stayed at.
			
			System.out.println("Do you wish to register a new favorite or remove your current favorite?(Y/N)");
			
			try {
				choice = MainMenu.input.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(!choice.toLowerCase().equals("y")){
				done = true;
				
			}
			else{
				java.util.Date dt = new java.util.Date();
				
				java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
				String currentTime = sdf.format(dt);
				System.out.println("What house ID do you wish to register as your favorite?  Leave blank to remove/not register a favorite.");
				
				try {
					choice = MainMenu.input.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				String output = null;
				
				if(choice.isEmpty()){
					sql = "DELETE FROM Favorites WHERE login = '" + userName + "';";
					output = "You will have no favorite after this.";
				}
				else{
					if(haveFavorite != -1){
						sql = "UPDATE Favorites SET hid = '" +choice+ "', fvdate = '"+currentTime+"' WHERE login = '" + userName + "';";
					}
					else{
						sql = "INSERT INTO Favorites(hid, fvdate, login) VALUES('" +choice + "','"+currentTime+"', '"+userName+"');";
					}
					
					output = "Your new Favorite will be, House ID: " + choice + ", Favorite Date: "+currentTime;
				}
				
				
				System.out.println(output);
				System.out.println("Do you wish to keep this change?  (Y/N)");
				
				try {
					choice = MainMenu.input.readLine();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if(choice.toLowerCase().equals("y")){
					try {
						stmt.executeUpdate(sql);
						System.out.println("Your Favorite has been updated!");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
			
			System.out.println("Do you wish to change/remove your favorite again? (Y/N)");
			try {
				choice = MainMenu.input.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if(!choice.toLowerCase().equals("y")){
				done = true;
			}
			
		}
		System.out.println("Exitting the Favorite TH menu.");
		
	}

	public int viewFavoriteTH(String userName, Statement stmt, boolean viewResult){
		ResultSet rs = null;
		int haveFavorite = -1;
		String sql = "Select F.hid, T.name FROM Favorites F, TH T WHERE F.login = '"+userName+"' AND F.hid = T.hid";
		
		try {
			rs = stmt.executeQuery(sql);
			if(!rs.isBeforeFirst()){
				System.out.println("You do not have a favorite house currently selected.");
			}
			else{
				if(viewResult){
					while(rs.next()){
						System.out.println("Your current favorite place to stay is:");
						System.out.println("House ID: "+ rs.getString("F.hid") + ", House name: " + rs.getString("T.name"));
						haveFavorite = rs.getInt("F.hid");
					}
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return haveFavorite;
	}
	public void setViewProfile(String userName, Statement stmt) {
		// TODO Auto-generated method stub
		
	}

}
