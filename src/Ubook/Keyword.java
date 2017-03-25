package Ubook;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Keyword {

	public void setUp(String houseID, Statement stmt) {
		// TODO Auto-generated method stub
		String ID = houseID;
		while(ID != null){
			System.out.println("Press 1 to view keywords for the house");
			System.out.println("Press 2 add keywords for the house");
			System.out.println("Press 3 to remove keywords for the house.");
			System.out.println("Press 4 to exit.");
			String choice = null;
			try {
				choice = MainMenu.input.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			switch(choice){
			case "1":
				viewKeyWords(ID, stmt);
				break;
			case "2":
				addKeyWords(ID,stmt);
				break;
			case "3":
				removeKeyWords(ID,stmt);
				break;
			case "4":
				ID = null;
				break;
			default:
				System.out.println("That is not a valid choice, please try again.");
				break;
			}
		
		}
		System.out.println("Exiting Keyword Menu. \n");
	}

	private void removeKeyWords(String ID, Statement stmt) {
		// TODO Auto-generated method stub
		
		boolean done = false;
		while(!done){
		System.out.println("Here are the keywords associated with your house.");
		
		viewKeyWords(ID, stmt);
		
		System.out.println("Do you wish to continue with removing one?  (Y/N)");
		
		
		String choice = null;				
			
				
			try {
				choice = MainMenu.input.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		if(!choice.toLowerCase().equals("y")){
			done = true;
		}
		else{
		System.out.println("Which one do you wish to remove? ");
		
		choice = null;				
			
				
			try {
				choice = MainMenu.input.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			if(choice == null || choice.isEmpty()){
				System.out.println("You need to put in a keyword.");
				done = true;
			}
			if(!done){
				String sql = "Select word, language from Keywords WHERE wid = '"+choice+"';";
				ResultSet rs2;
				try {
					rs2 = stmt.executeQuery(sql);
					while(rs2.next()){
						System.out.println("This will be the keyword.");
						System.out.println("House ID: "+ID+", Keyword ID: "+choice+", keyword: " +rs2.getString(1)+", Language: "+rs2.getString(2));
						
					
					}
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				System.out.println("Do you wish to remove this keyword? (Y/N)");
					
				String wid = choice;
				
				try {
					choice = MainMenu.input.readLine();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(choice.equals("Y")||choice.equals("y")){
					sql = "DELETE FROM HasKeywords WHERE wid = '"+wid+"' AND hid = '"+ID+"';";
					
					try {
						stmt.executeUpdate(sql);

						}
						
					 catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				System.out.println("Do you wish to continue? (Y/N)");
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
			System.out.println("Exiting remove keyword menu.");
			System.out.println("\n");
		
			}
		}
		}
	}

	private void addKeyWords(String ID, Statement stmt) {
		// TODO Auto-generated method stub
	
		boolean done = false;
		while(!done){
			String language = null;
			String word = null;
			
			System.out.println("Please select the language the keyword will be in.");
			
			try {
				language = MainMenu.input.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("Please select the keyword.");
			
			try {
				word = MainMenu.input.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("This will be the keyword.");
			System.out.println("Keyword: " + word + ", Language: " + language);
			System.out.println("Do you wish to keep these changes? (Y/N)");
			
			try {
				String choice = MainMenu.input.readLine();
				String wid = null;
				if(choice.toLowerCase().equals("y")){
					String sql = "Select wid from Keywords WHERE word = '"+word+"' AND language = '"+language+"';";
					ResultSet rs;
					try {
						rs = stmt.executeQuery(sql);
						if(rs.isBeforeFirst()){
							while(rs.next()){
								wid = rs.getString(1);
							}
						}
						else{
							sql = "INSERT INTO Keywords(word, language) VALUES('"+word+"', '"+language+"');";
							
							try {
								stmt.executeUpdate(sql);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							try(ResultSet generatedKeys = stmt.getGeneratedKeys()){
								if(generatedKeys.next()){
									wid = generatedKeys.getString(1);
								}
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
						sql = "INSERT INTO HasKeywords(hid, wid) VALUES('"+ID+"','"+wid+ "');";
						
						try {
							stmt.executeUpdate(sql);
							
							System.out.println("Key word Added!");
							System.out.println("Do you wish to keep adding keywords?(Y/N)");
							
							choice = MainMenu.input.readLine();
							
							if(!choice.toLowerCase().equals("y")){
								done = true;
							}
							else{
							choice = null;
							}
							
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		}
		System.out.println("Exiting Add keyword menu.");
		System.out.println("\n");
	}

	private void viewKeyWords(String ID, Statement stmt) {
		String sql = "SELECT H.wid, K.word, K.language FROM HasKeywords H, Keywords K WHERE H.wid = K.wid AND H.hid = '" + ID + "';";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);

				while(rs.next()){
					
					System.out.println("House ID: "+ID+", Keyword ID: "+rs.getString("wid")+", keyword: " +rs.getString("word")+", Language: "+rs.getString("language"));
					
					}
			}
			catch(SQLException e){
				e.printStackTrace();
			}
		
		System.out.println("Exiting view keywords menu.");
		System.out.println("");
		}
	
}

