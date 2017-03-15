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
		
		System.out.println("Here is a list of the availabilities you have on this house.");
		showAvailability(houseID, stmt);
		
		System.out.println("Select which availability you wish to update using the period ID.");
		
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		
		String pid = null;
		
		try {
			pid = input.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String choice = null;
		String fromDate = null;
		String toDate = null;
		String priceNight = null;
		
		System.out.println("Select the new from date. Put in the format 'YYYY-MM-DD'. Leave blank to keep the same.");
		
		try {
			choice = input.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!choice.isEmpty()){
			fromDate = choice;
		}
		
		System.out.println("Select the new end date. Put in the format 'YYYY-MM-DD'. Leave blank to keep the same.");
		
		try {
			choice = input.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!choice.isEmpty()){
			toDate = choice;
		}
		
		System.out.println("Select the new price per night. Put in dollar amount. Leave blank to keep the same.");
		
		try {
			choice = input.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!choice.isEmpty()){
			priceNight = choice;
		}
		
		System.out.println("The new availability and price will be:");
		System.out.println("Period ID: "+pid+", House ID: "+houseID+", From Date: " + fromDate + ", To Date: " + toDate + 
				", Price per Night: " + priceNight);
		System.out.println("Do you want to keep these changes?  (Y/N)");
		
		choice = null;
		boolean changed = false;
		try{
			choice = input.readLine();
		}
		catch(IOException e){
			
		}
		
		if(choice.equals("y") || choice.equals("Y")){
			changed = true;
		}
		
		if(changed){
			String sql = "UPDATE Available SET priceNight = '" + priceNight + "' WHERE hid = '" +houseID + "', pid = '" + pid+"';" ;
			
			try {
				stmt.executeUpdate(sql);
				sql = "UPDATE Period SET fromDate = '" + fromDate + "', toDate = '" +toDate+"' WHERE pid = '" + pid+"';" ;
				try{
					stmt.executeUpdate(sql);
				}
				catch(SQLException e1){
					e1.printStackTrace();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}

	public void removeAvailability(String houseID, Statement stmt) {
		// TODO Auto-generated method stub
		System.out.println("Here is a list of the availabilities you have on this house.");
		showAvailability(houseID, stmt);
		
		System.out.println("Select which availability you wish to remove using the period ID.");
		
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		
		String pid = null;
		
		try {
			pid = input.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("This will remove the availability of " + pid+ "Do you want to continue?");
		
		String choice = null;
		boolean changed = false;
		try{
			choice = input.readLine();
		}
		catch(IOException e){
			
		}
		
		if(choice.equals("y") || choice.equals("Y")){
			changed = true;
		}
		
		if(changed){
			String sql = "DELETE FROM Available WHERE hid = '" +houseID + "', pid = '" + pid+"';" ;
			
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}
	
	public void showAvailability(String houseID, Statement stmt){
		String sql = "SELECT pid, priceNight FROM Available WHERE hid = '" + houseID + "';";
		
		ResultSet rs = null;
		ResultSet rs2 = null;
		String pid = null;
		String priceNight = null;
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				pid = rs.getString("pid");
				priceNight = rs.getString("priceNight");
				
				String sql2 = "SELECT fromDate,toDate FROM Period WHERE pid = '" + pid + "';";
				
				try{
					rs2 = stmt.executeQuery(sql2);
					while(rs2.next()){
						System.out.println("Period ID: "+pid+", House ID: "+houseID+", From Date: " + rs2.getDate("fromDate").toString() + ", To Date: " 
								+ rs2.getDate("toDate").toString() +	", Price per Night: " + priceNight);
					}
				}
				catch(SQLException e1){
					e1.printStackTrace();
				}
				//System.out.println("House ID: " + rs.getString("hid") +",   House Name: " + rs.getString("name") );
					
				}
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}

}
