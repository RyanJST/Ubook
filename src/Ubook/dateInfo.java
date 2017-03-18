package Ubook;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class dateInfo {
	
	public void createAvailability(String houseID, Statement stmt) {
		String startDate = null;
		String endDate = null;
		String priceNight = null;
		
		boolean changed = false;
		
		System.out.println("What starting date do you wish to have at? Put in the format 'YYYY-MM-DD'.");
		try {
			startDate = MainMenu.input.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("What ending date do you wish to have at? Put in the format 'YYYY-MM-DD'.");
		try {
			endDate = MainMenu.input.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("What price per night do you want?  Put in dollar amount only.");
		
		try {
			priceNight = MainMenu.input.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println("Here is what your new availability will look like. \n");
		
		System.out.println("Start Date: " + startDate + ", End Date: " +endDate+ ", Price Per Night: "+priceNight);
		
		System.out.println("Do you wish to have this new date of availability?  (Y/N)");
		
		String choice = null;
		try{
			choice = MainMenu.input.readLine();
		}
		catch(IOException e){
			
		}
		
		if(choice.toLowerCase().equals("y")){
			changed = true;
		}
		
		if(changed){
		
		String sql = "INSERT INTO Period (fromDate, toDate) VAlUES ('" + startDate+ "', '"+ endDate+ "');";
		
		int result = 0;
		
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try(ResultSet generatedKeys = stmt.getGeneratedKeys()){
			if(generatedKeys.next()){
				result = generatedKeys.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		sql = "INSERT INTO Available(pid, hid, priceNight) Values('" + result+ "', '"+ houseID+ "', '" + priceNight + "')";
	
		try {
			stmt.executeUpdate(sql);
			System.out.println("New availability has been created!  \n");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		}
		
		
	}

	public void changeAvailability(String houseID, Statement stmt) {
		// TODO Auto-generated method stub
		
		System.out.println("Here is a list of the availabilities you have on this house.");
		showAvailability(houseID, stmt);
		
		System.out.println("Select which availability you wish to update using the period ID.");
		
		//BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		
		String pid = null;
		
		try {
			pid = MainMenu.input.readLine();
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
			choice = MainMenu.input.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!choice.isEmpty()){
			fromDate = choice;
		}
		
		System.out.println("Select the new end date. Put in the format 'YYYY-MM-DD'. Leave blank to keep the same.");
		
		try {
			choice = MainMenu.input.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!choice.isEmpty()){
			toDate = choice;
		}
		
		System.out.println("Select the new price per night. Put in dollar amount. Leave blank to keep the same.");
		
		try {
			choice = MainMenu.input.readLine();
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
			choice = MainMenu.input.readLine();
		}
		catch(IOException e){
			
		}
		
		if(choice.toLowerCase().equals("y")){
			changed = true;
		}
		
		if(changed){
			createChangeAvailability(houseID, stmt, pid, fromDate, toDate, priceNight);
		}
	}

	public void removeAvailability(String houseID, Statement stmt) {
		// TODO Auto-generated method stub
		System.out.println("Here is a list of the availabilities you have on this house.");
		showAvailability(houseID, stmt);
		
		System.out.println("Select which availability you wish to remove using the period ID.");
		
		//BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		
		String pid = null;
		
		try {
			pid = MainMenu.input.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("This will remove the availability of " + pid+ "Do you want to continue?");
		
		String choice = null;
		boolean changed = false;
		try{
			choice = MainMenu.input.readLine();
		}
		catch(IOException e){
			
		}
		
		if(choice.toLowerCase().equals("y") ){
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
	
	public void createChangeAvailability(String houseID, Statement stmt, String pid, String fromDate, String toDate, String priceNight){
		String sql = "UPDATE Available SET priceNight = '" + priceNight + "' WHERE hid = '" +houseID + "', pid = '" + pid+"';" ;
		
		try {
			stmt.executeUpdate(sql);
			sql = "UPDATE Period SET fromDate = '" + fromDate + "', toDate = '" +toDate+"' WHERE pid = '" + pid+"';" ;
			try{
				stmt.executeUpdate(sql);
				System.out.println("The changed availability has been made!\n");
			}
			catch(SQLException e1){
				e1.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public void showAvailability(String houseID, Statement stmt){
		String sql = "SELECT A.pid, A.priceNight, P.fromDate, P.toDate FROM Available A, Period P WHERE A.hid = '"+houseID+"' AND P.pid = A.pid ;";
		ResultSet rs = null;
		
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				System.out.println("Period ID: "+rs.getString("A.pid")+", House ID: "+houseID+", From Date: " + rs.getDate("P.fromDate").toString() + ", To Date: " 
						+ rs.getDate("P.toDate").toString() +	", Price per Night: " + rs.getString("A.priceNight"));
				}
				
			}
			
		
		catch(SQLException e){
			e.printStackTrace();
		}
	}

}
