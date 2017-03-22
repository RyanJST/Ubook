package Ubook;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class THFeedback {
	public void thReview(String userName, Statement stmt, String TH) {
		// TODO Auto-generated method stub

		System.out.println("Do you wish to rate a TH you stayed at? (Y/N)");
		
		String choice = null;
		
		
		ResultSet rs = null;
		try {
			choice = MainMenu.input.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String sql = null;
		if(choice.toLowerCase().equals("y")){
			
				//System.out.println("Here is the list of houses you stayed at.");
				//Visit visited = new Visit();
				//Method to showed houses userName stayed at.
				//visited.showStayedHouses(userName, stmt)
				
				//System.out.println("Which house do you wish to leave feedback on?  NOTE:  You cannot review a house you own, nor a house you already reviewed, nor a house you haven't stayed at before.");
				String houseID = TH;
				/*try {
					choice = MainMenu.input.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				*/
				houseID = choice;
				boolean stayedAt = true; //Used for determining if you stayed at a TH
				if(stayedAt){
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
				}
				

				
			
		}
	}
	
	public void viewMostUsefulFeedback(String amount, Statement stmt, String THID){
		
		String sql = "SELECT F.fid, F.score, F.text, F.login, F.fbdate, AVG(R.rating) avg_score FROM Feedback F, Rates R "
				+ "WHERE F.fid = R.fid AND F.hid = "+THID+" GROUP BY F.fid, F.score, F.text, F.login, F.fbdate ORDER BY avg_score DESC LIMIT "+amount+";";
		
		ResultSet rs = null;
		
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<reviewedFeedback> items = new ArrayList<reviewedFeedback>();
		
		System.out.println("Here are the top " + amount+ " feedbacks for this house.");
		try {
			while(rs.next()){
				items.add(new reviewedFeedback(rs.getString("fid"),rs.getString("score"), rs.getString("text"), rs.getString("login")
						, rs.getString("fbdate"), rs.getString("avg_score")));
				//System.out.println("Feedback ID: "+rs.getString("fid")+ ", TH Score: " +rs.getString("score")+ ", Feedback Text: "
						//+rs.getString("Text")+ ", TH Score: ");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(items.size() < 10){
			sql = "SELECT F.fid, F.score, F.text, F.login, F.fbdate FROM Feedback F "
					+ "WHERE F.hid = "+THID+" AND F.fid NOT IN (SELECT fid from 5530db34.Rates) group by F.fid, F.score, F.text, F.login, F.fbdate ORDER BY F.fid DESC "
					+ "LIMIT "+Integer.toString(Integer.parseInt(amount) - items.size())+";";
			
			try {
				rs = stmt.executeQuery(sql);
				while(rs.next()){
					items.add(new reviewedFeedback(rs.getString("fid"),rs.getString("score"), rs.getString("text"), rs.getString("login")
							, rs.getString("fbdate"), null));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for(int i = 0; i < items.size(); i ++){
			System.out.println("Feedback ID: "+items.get(i).lFid+ ", TH Score: " +items.get(i).lScore+ ", Feedback Text: "
					+items.get(i).lText+ ", Feedback Maker: "+items.get(i).lLogin+ ", Feedback Date:  "+items.get(i).lFBdate+ ", Average score" + items.get(i).lAvgScore);
		}
		System.out.println("\n");
	}

	public void reviewFeedback(String userName, Statement stmt, String feedbackID) {
		// TODO Auto-generated method stub
		
		String sql = "SELECT R.fid from Rates R where R.fid = '" +feedbackID+ "' AND R.login = '" + userName+ "';";
		ResultSet rs = null;
		boolean ratedBefore = false;
		try {
			rs = stmt.executeQuery(sql);
			if(rs.isBeforeFirst()){
				System.out.println("You have rated that Feedback already.  Please select another Feedback to rate.");
				ratedBefore = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!ratedBefore){		
			System.out.println("What do you wish to rate this feedback?");
			
			String choice = null;
			
			try {
				choice = MainMenu.input.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("This is the rating that you'll give the feedback.");
			
			System.out.println("Score: "+choice+", Feedback ID: "+ feedbackID);
			
			System.out.println("Do you wish to submit this review?  (Y/N)");
			
			try {
				choice = MainMenu.input.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(choice.toLowerCase().equals("y")){
				sql = "INSERT INTO Rates(login, fid, rating) VALUES('"+userName+"', "
						+ "'"+feedbackID+"', '"+choice+"')";
				
				try {
					stmt.executeUpdate(sql);
					System.out.println("Your review has been created!");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("Exitting feedback review.");
	}
}
