package Ubook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class MainMenu {

	public static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	public static void main(String[] args){
		try{
			Connector con = new Connector();
			System.out.println("Hello!  Please select the option you want for UBook!");
			//BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			String userName = null;
			boolean done = false;
			//String innerChoice = null;
			//String choice = "1";
			while(!done){
				System.out.println("Please press 1 to log in");
				System.out.println("Please press 2 to sign up");
				System.out.println("Please press 3 to exit");
				String choice = input.readLine();
				switch(choice){
				case "1":
					userName = login(con);
					if(userName != null){
						done = true;
					}
					break;
				case"2":
					userName = signup(con);
					if(userName != null){
						done = true;
					}
					break;		
				case"3":
					done = true;
					break;
				default:
					System.out.println("That is not a valid option, please try again");
					break;
				}
			}
			
			boolean signedDone = false;
			if(userName != null){
				System.out.println("Hello, " + userName + "!");
				System.out.println("What would you like to do today?");
				while(!signedDone){
					System.out.println("Please press 1 to access your user account settings");
					System.out.println("Please press 2 to Temporary Housing Settings");
					System.out.println("Please press 3 to look for and reserve a TH.");
					System.out.println("Please press 4 to record a stay at a TH.");
					System.out.println("Please press 5 to create/view feedback for THs and users");
					
					System.out.println("Please press 99 to exit");
					String choice = input.readLine();
					switch(choice){
					case "1":
						userSettings(userName, con);
						break;
					case"2":
						TH(userName, con);
						break;		
					case"3":
						browseReserve(userName, con);
						break;
					case"4":
						recordStay(userName, con);
						break;
					case"5":
						feedback(userName, con);
						break;
					case"99":
						signedDone = true;
						break;
					default:
						System.out.println("That is not a valid option, please try again");
						break;
					}
				}
				
			}
			
			System.out.println("Thank you for using UBook!  Have a great day!");
			con.closeConnection();
			input.close();
		}
		
		catch( Exception e){
			e.printStackTrace();
		}
	}

	private static void feedback(String userName, Connector con) {
		// TODO Auto-generated method stub
		UserFeedback createdFeedback = new UserFeedback();
		THFeedback THReview = new THFeedback();
		boolean done = false;
		String choice = null;
		while(!done){
			choice = null;
			String result = null;
			System.out.println("Press 1 to create feedback for a TH you stayed at.");
			System.out.println("Press 2 to create feedback for a feedback on a TH.");
			System.out.println("Press 3 to create a trusted review on another User.");
			System.out.println("Press 4 to see the trusted level of a User.");
			System.out.println("Press 5 to exit");
			
			try{
				choice = input.readLine();
			}
			catch(IOException e){
				e.printStackTrace();
			}
			switch(choice){
			
			case "1":
				System.out.println("Which house do you wish to leave feedback on?  NOTE:  You cannot review a house you own, nor a house you already reviewed, nor a house you haven't stayed at before.");
				
				try {
					result = MainMenu.input.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				THReview.thReview(userName,con.stmt, result);
				break;
			case"2":
				System.out.println("Which Feedback ID do you wish to leave feedback on?  NOTE:  You cannot review a feedback you made.");
				
				try {
					result = MainMenu.input.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				THReview.reviewFeedback(userName, con.stmt, result);
				break;		
			case"3":
				createdFeedback.reviewUsers(userName, con.stmt);
				break;
			case"4":
				String reviewUser = null;
				System.out.println("Put in the user name of the user you wish to see the trusted level of.");
				try{
					reviewUser = input.readLine();
				}
				catch(IOException e1){
					e1.printStackTrace();
				}
				System.out.println("The trusted level is: " + Integer.toString(createdFeedback.viewUserFeedback(reviewUser, con.stmt)));
				break;
			case"5":
				done = true;
				break;
			default:
				System.out.println("That is not a valid option, please try again");
				break;
			}
			
		}
	}

	private static void browseReserve(String userName, Connector con) {
		// TODO Auto-generated method stub
		//BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		boolean done = false;
		BRTH shopping = new BRTH();
		while(!done){
			System.out.println("Press 1 to browse temporary houses");
			System.out.println("Press 2 to reserve a temporary house");
			System.out.println("Press 3 to exit");
			String choice = null;
			try {
				choice = input.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		switch(choice){

			case"1":
				shopping.browseTHs(userName, con.stmt);
				break;
			case"2":
				reserveTHs(userName, con, shopping);
				break;
			case"3":
				done = true;
				break;
			default:
					break;
		}

		}
	}

	private static String signup(Connector con) {
		// TODO Auto-generated method stub
		//BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		User newUser = new User();
		String name = null;
		boolean result = false;
		name = newUser.setUpUser(con.stmt);
		while(!result){
			if(name != null){
				result = true;
			}
			else{
				System.out.println("There was an issue with your registration, "
						+ "Do you want to try again?(Y/N) ");
				
				String choice = null;
				try {
					choice = input.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(!choice.toLowerCase().equals("y")){
					result = true;
					
				}
				else{
					name = newUser.setUpUser(con.stmt);
				}
			}
			}
		return name;
	}

	private static String login(Connector con) {
		// TODO Auto-generated method stub
		//BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		User loginUser = new User();
		String name = null;
		boolean result = false;
		name = loginUser.loginUser(con.stmt);
		while(!result){
			if(name != null){
				result = true;
			}
			else{
				System.out.println("There was an issue with your login, "
						+ "Do you want to try again?(Y/N) ");
				
				try {
					String choice = input.readLine();
					if(!choice.toLowerCase().equals("y")){
						result = true;
						
					}
					else{
						name = loginUser.loginUser(con.stmt);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			}
		return name;
		
	}

	private static void userSettings(String userName, Connector con) {
		// TODO Auto-generated method stub
		//BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String choice = null;
		
		boolean finished = false;
		User userItem = new User();
		while(!finished){
			System.out.println("Please press 1 to register a new favorite TH you like to stay at.");
			System.out.println("Please press 2 to view/change your profile");
			System.out.println("Please press 3 to exit the user settings menu");
			try {
				choice = input.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			switch(choice){
			case"1":
				userItem.setFavoriteTH(userName, con.stmt);
				break;
			case"2":
				userItem.setViewProfile(userName, con.stmt);
				break;
			case"3":
				finished = true;
				break;
			}
		}
	}
	
	private static void TH(String userName, Connector con) {
		// TODO Auto-generated method stub
		
		//BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String choice = null;
		
		TH house = new TH();
		boolean finished = false;
		
		while(!finished){
			System.out.println("Please press 1 to register a new house");
			System.out.println("Please press 2 to see the houses you have listed.");
			System.out.println("Please press 3 to change information on a house you own.");
			System.out.println("Please press 4 to view/create keywords for your THs.");
			System.out.println("Please press 5 to set up/change the availability of a TH you own.");
			System.out.println("Please press 99 to exit the temporary housing menu.");
			
			try {
				choice = input.readLine();
				
				switch(choice){
				case "1":
					house.registerHouse(userName, con.stmt);
					break;
				
				case "2":
					house.listOwnedHouses(userName, con.stmt);
					break;
				case "3":
					house.changeHouse(userName, con.stmt);
					break;
				case "4":
					house.keywords(userName, con.stmt);
					break;
				case "5":
					house.updateAvailability(userName, con.stmt);
					break;
				case "99":
					finished = true;
					break;
				default:
					System.out.println("You selected an incorrect option.  Please try again.");
					break;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private static void recordStay(String userName, Connector con) {
		Stay stay = new Stay();
		boolean finished = false;
		List<String> pids = new ArrayList<String>();
		while(!finished) {
			stay.showReservations(userName, con.stmt, pids);
			System.out.println("Enter the id of the reservation you would like to record your stay for:");
			String pid = "";
			try {
				pid = input.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pids.add(pid);
			System.out.println("Would you like to record another stay?(y/n)");
			String response = "";
			try {
				response = input.readLine().toLowerCase();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (response.charAt(0) == 'n') {
				finished = true;
			}
		}
		stay.printSelected(userName, con.stmt, pids);
		System.out.println("Confirm these stay records?(y/n)");
		String response = "";
		try {
			response = input.readLine().toLowerCase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (response.charAt(0) == 'y') {
			for(String pid: pids) {
				stay.recordStay(userName, con.stmt, pid);
			}
		}
	}
	
	private static void reserveTHs(String userName, Connector con, BRTH shopping) {
		boolean finished = false;
		List<Reserve> reserves = new ArrayList<Reserve>();
		while(!finished) {
			try{
				shopping.showTHs(con.stmt);
				System.out.println("Enter 'e' at any time to exit.");
				System.out.println("Select House ID of desired house:");
				String hid = input.readLine();
				if (hid.charAt(0) == 'e') return;
				if (shopping.showTHAvails(hid, con.stmt)){
					System.out.println("Enter the date you would like to would like to start your stay (Format YYYY-MM-DD):");
					String from = MainMenu.input.readLine();
					if(from.charAt(0) == 'e') return;
					System.out.println("Enter the date you would like to end your stay (Format YYYY-MM-DD):");
					String to = MainMenu.input.readLine();
					if(to.charAt(0) == 'e') return;
					Reserve r = new Reserve(hid, from, to);
					reserves.add(r);
				}
				System.out.println("Would you like to reserve another stay?(y/n)");
				String response = input.readLine();
				if(response.charAt(0) == 'n') {
					finished = true;
				}
			}
			catch (IOException e) {
				
			}
		}
		shopping.printSelected(con.stmt, reserves);
		for(Reserve r: reserves) {
			shopping.insertReserve(userName, r, con.stmt);
		}
	}
}