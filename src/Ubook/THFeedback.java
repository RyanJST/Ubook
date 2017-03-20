package Ubook;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class THFeedback {
	public void thReview(String userName, Statement stmt) {
		// TODO Auto-generated method stub

		System.out.println("Do you wish to rate a TH you stayed at? (Y/N)");
		
		String choice = null;
		
		Boolean done = false;
		ResultSet rs = null;
		try {
			choice = MainMenu.input.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String sql = null;
		if(choice.toLowerCase().equals("y")){
			while(!done){
				System.out.println("Here is the list of houses you stayed at.");
				//Visit visited = new Visit();
				//Method to showed houses userName stayed at.
				//visited.showStayedHouses(userName, stmt)
				
				System.out.println("Which house do you wish to leave feedback on?  NOTE:  You cannot review a house you own, nor a house you already reviewed, nor a house you haven't stayed at before.");
				String houseID = null;
				try {
					choice = MainMenu.input.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				houseID = choice;
				sql = "SELECT f.fid from Feedback F where hid = '" +houseID+ "' AND login = '" + userName+ "';";
				boolean ratedBefore = false;
				try {
					rs = stmt.executeQuery(sql);
					if(rs.isBeforeFirst()){
						System.out.println("You have rated that TH already.  Please select another TH to rate.");
						ratedBefore = true;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
				
				if(!ratedBefore){
					String text = null;
					String score = null;
					java.util.Date dt = new java.util.Date();
					
					java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
					String currentTime = sdf.format(dt);
					
					System.out.println("Please put in the score you rate this TH.  Rate from 0(poor) to 10(excellent)");
					
					try {
						choice = MainMenu.input.readLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					score = choice;
					
					System.out.println("Do you wish to write a short text review?  Leave blank if not, otherwise keep your review to 100 characters or less.");
					
					try {
						choice = MainMenu.input.readLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if(!choice.isEmpty()){
						text = choice;
					}
					
					
					System.out.println("Here is what your feedback will look like.");
					
					System.out.println("Score rating: "+score+", "
							+ "Text Review: "+text+", House ID"+houseID+", Feedback Date: '"+currentTime);
					
					System.out.println("Do you wish to post this review?  (Y/N)");
					
					try {
						choice = MainMenu.input.readLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if(!choice.toLowerCase().equals("y")){			
						sql = "INSERT INTO Feedback(score, text, hid, login, fbdate) VALUES('"+score+"', "
								+ "'"+text+"', '"+houseID+"', '"+userName+"', '"+currentTime+"')";
						
						try {
							stmt.executeUpdate(sql);
							System.out.println("Feedback updated!");
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
				System.out.println("Do you wish to rate another TH?  (Y/N)");
				try {
					choice = MainMenu.input.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
				if(!choice.toLowerCase().equals("y")){
					done = true;
				}
				
			}
		}
	}

	public void reviewFeedback(String userName, Statement stmt) {
		// TODO Auto-generated method stub
		
	}
}
