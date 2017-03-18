package Ubook;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Feedback {

	public void thReview(String userName, Statement stmt) {
		// TODO Auto-generated method stub
		
	}

	public void reviewFeedback(String userName, Statement stmt) {
		// TODO Auto-generated method stub
		
	}

	public void reviewUsers(String userName, Statement stmt) {
		// TODO Auto-generated method stub
		boolean done = false;
		while(!done){
			String ratedUser = null;
			int trusted = 0;
			String sql = null;
			System.out.println("Please put the UserName of the user you wish to review.");
			
			try{
				ratedUser = MainMenu.input.readLine();
			}
			catch(IOException e){
				e.printStackTrace();
			}
			
			System.out.println("Do you trust this user?  Press Y for yes, and N for no.");
			
			try{
				if(MainMenu.input.readLine().toLowerCase().equals("y")){
					trusted = 1;
				}
			}
			catch(IOException e){
				e.printStackTrace();
			}
			
			sql = "INSERT INTO Trust VALUES('"+userName+"','"+ratedUser+"','"+Integer.toString(trusted)+"');";
			
			try{
				stmt.executeUpdate(sql);
			}
			catch(SQLException e1){
				e1.printStackTrace();
			}
			
			System.out.println("DO you wish to review another user?  (Y/N)");
			
			try{
				if(!MainMenu.input.readLine().toLowerCase().equals("y")){
					done = true;
				}
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	
	public int viewUserFeedback(String reviewName, Statement stmt){
		int result = 0;
		ResultSet rs = null;
		String sql = "Select isTrusted from Trust where login2 = '"+reviewName+"'";
		
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				if(rs.getInt(1) == 1){
					result++;
				}
				else{
					result--;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
}
