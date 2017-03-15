package Ubook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class dateInfo {

	public void createAvailability(String houseID, Statement stmt) {
		// TODO Auto-generated method stub
		
		
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String startDate = null;
		String endDate = null;
		String priceNight = null;
		
		boolean changed = false;
		
		System.out.println("What starting date do you wish to have at? Put in the format 'YYYY-MM-DD'.");
		try {
			startDate = input.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("What ending date do you wish to have at? Put in the format 'YYYY-MM-DD'.");
		try {
			endDate = input.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("What price per night do you want?  Put in dollar amount only.");
		
		try {
			priceNight = input.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println("Here is what your new availability will look like. \n");
		
		System.out.println("Start Date: " + startDate + ", End Date: " +endDate+ ", Price Per Night: "+priceNight);
		
		System.out.println("Do you wish to have this new date of availability?  (Y/N)");
		

		try{
			if(input.readLine().equals("y") || input.readLine().equals("Y")){
				changed = true;
			}
		}
		catch(IOException e){
			
		}
		
		
		
		if(changed){
		
		String sql = "INSERT INTO Period (fromDate, toDate) Values('" + startDate+ "', "+ endDate+ "');";
		
		int result = 0;
		
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try(ResultSet generatedKeys = stmt.getGeneratedKeys()){
			if(generatedKeys.next()){
				result = generatedKeys.getInt("pid");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		sql = "INSERT INTO Availability (pid, hid, priceNight) Values('" + result+ "', "+ houseID+ "', " + priceNight + ")";
	
		}
	}

	public void changeAvailability(String houseID, Statement stmt) {
		// TODO Auto-generated method stub
		
	}

	public void removeAvailability(String houseID, Statement stmt) {
		// TODO Auto-generated method stub
		
	}

}
