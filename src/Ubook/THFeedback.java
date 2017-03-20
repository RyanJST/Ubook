package Ubook;

import java.io.IOException;
import java.sql.Statement;

public class THFeedback {
	public void thReview(String userName, Statement stmt) {
		// TODO Auto-generated method stub

		System.out.println("Do you wish to rate a TH you stayed at? (Y/N)");
		
		String choice = null;
		Boolean done = false;
		
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
				
				try {
					choice = MainMenu.input.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				sql = "SELECT f.fid from Feedback F where hid = '" +choice+ "' AND login = '" + userName+ "';";
				
			}
		}
	}

	public void reviewFeedback(String userName, Statement stmt) {
		// TODO Auto-generated method stub
		
	}
}
