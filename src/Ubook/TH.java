package Ubook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TH {

	public void registerHouse(String userName, Statement stmt) {
		// TODO Auto-generated method stub
		
		System.out.println("Here you register your temporary housing.  If you do not wish to do so, press e now.  Otherwise, press any other key to continue.");
		
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String houseName = null;
		
		try{
			if(input.readLine().equals("e")){
				houseName = "e";
						
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		if(houseName == null){
			while(houseName == null){
				System.out.println("Please set the name of your new temporary housing.");
				
				try {
					houseName = input.readLine();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if(houseName == null || houseName.isEmpty()){
					System.out.println("You need to name your temporary housing.  Please try again.");
				}
			}
			
			String address = null;
			while(address == null){
				System.out.println("Please set the address of your new temporary housing.");
				
				try {
					address = input.readLine();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if(address == null || address.isEmpty()){
					System.out.println("You need to put in the address of your temporary housing.  Please try again.");
				}
			}
			int test = 0;
			String phoneNumber = null;
			while(phoneNumber == null){
				System.out.println("Please set the phone number of your new temporary housing in the format \"1234567890\".");
				
				try {
					phoneNumber = input.readLine();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try{
					test = Integer.parseInt(phoneNumber);
				}
				catch(NumberFormatException n){
					phoneNumber = null;
				}
				if(phoneNumber == null || phoneNumber.isEmpty()){
					System.out.println("You need to give a valid phone number. Please try again.");
				}
			}
			
			String yearBuilt = null;
			while(yearBuilt == null){
				System.out.println("Please set the year that your temporary housing was built. This field does not need to be filled in.");
				
				try {
					yearBuilt = input.readLine();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try{
					test = Integer.parseInt(yearBuilt);
				}
				catch(NumberFormatException n){
					yearBuilt = null;
				}
				
				if(yearBuilt == null){
					System.out.println("You need to give a valid year built. Please try again.");
				}
			}
			
			String category = null;
			System.out.println("Please input the category of your temporary housing.  Does not need to be filled in.");
			
			try {
				category = input.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			String URL = null;
			System.out.println("Please input the web address of your temporary housing.  Does not need to be filled in.");
			
			try {
				URL = input.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			String sql = "INSERT INTO TH (category, name, address, URL, phoneNumber, yearBuilt, login)"
					+ " VALUES( '" + category + "', '" + houseName+"','" + address + "', '" + URL+"','" + phoneNumber + "',"
							+ "'" + yearBuilt + "','" + userName + "')";
			try{
	   		    int gottenResults = stmt.executeUpdate(sql);		     
			 	}
			 	catch(Exception e)
			 	{
			 		System.out.println("cannot execute the query");
			 		System.out.println(e.getMessage());
			 	}
		}
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

	public void changeHouse(String userName, Statement stmt) {
		// TODO Auto-generated method stub
		String houseID = null;
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String sql = null;
		ResultSet rs = null;
		String name = null;
		String category = null;
		String address = null;
		String URL = null;
		String phoneNumber = null;
		String yearBuilt = null;
		
			System.out.println("Please input the Housing ID of the house you wish to update. If you wish to stop, press e now.");
			
			try{
					houseID = input.readLine();
				}
			
			catch(IOException e){
				e.printStackTrace();
			}
		
		if(!houseID.equals("e")){
			while(houseID == null){
				try {
					houseID = input.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
				
				try{
					int test = 0;
					test = Integer.parseInt(houseID);
				}
				catch(NumberFormatException n){
					
					houseID = null;
				}
				
				if(houseID == null){
					System.out.println("Please put in a valid HouseID.");
				
				}
	
			}
			sql = "SELECT hid, name FROM TH WHERE login = '" + userName + "' AND hid = '" +houseID+ "';";
			try {
				rs = stmt.executeQuery(sql);
				if(!rs.isBeforeFirst()){
					System.out.println("You do not have a house registered with that HouseID. Please try again. \n");
					houseID = null;
					
				}
				else{
					while(rs.next()){
						
					}
				}
			
		}

			catch(SQLException e){
				e.printStackTrace();
			}
	}
		
		if(houseID != null){
			
			System.out.println("Please select what you want the new name to be, leave blank to keep the name the same.");
		}
	}
}

	
	

