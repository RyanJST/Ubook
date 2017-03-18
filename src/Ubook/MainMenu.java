package Ubook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


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
				shopping.reserveTHs(userName, con.stmt);
				break;
			case"3":
				done = false;
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
		
		while(!finished){
			System.out.println("Please press 1 to register a new favorite TH you like to stay at.");
			System.out.println("Please press 2 to view/change your profile");
			System.out.println("");
			try {
				choice = input.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			switch(choice){
			case"1":
				break;
			case"2":
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
}