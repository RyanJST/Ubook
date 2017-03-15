package Ubook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class User {

	public String setUpUser(Statement stmt) {
		// TODO Auto-generated method stub
		int gottenResults = 0;
		
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String sql = null;
		System.out.println("Please put in your preffered user name");
		String userName = null;
		while(userName == null){
			try {
				userName = input.readLine();
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
			password = input.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("Please put in your full name");
		String name = null;
		
		try {
			name = input.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("Please put in your address");
		String address = null;
		
		try {
			address = input.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("Please put in your phone number");
		String phoneNumber = null;
		
		try {
			phoneNumber = input.readLine();
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
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Please put in your user name");
		String userName = null;
		
		try {
			userName = input.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("Please put in your password");
		
		String password = null;
		try {
			password = input.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String sql = "SELECT password FROM Users WHERE login = '" + userName + "';";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				
				if(!rs.getString("password").equals(password)){
					userName = null;
					System.out.println("That is the wrong password "
							+ "for the username you entered");
					
				}
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

}
