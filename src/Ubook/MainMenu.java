package Ubook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class MainMenu {

	
	public static void main(String[] args){
		try{
			Connector con = new Connector();
			System.out.println("Hello!  Please select the option you want for UBook!");
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
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
					System.out.println("Please press 99 to exit");
				}
				
			}
			
			System.out.println("Thank you for using UBook!  Have a great day!");
		}
		
		catch( Exception e){
			e.printStackTrace();
		}
	}

	private static String signup(Connector con) {
		// TODO Auto-generated method stub
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
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
				
				try {
					if(input.readLine() != "y"){
						result = true;
						
					}
					else{
						name = newUser.setUpUser(con.stmt);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			}
		return name;
	}

	private static String login(Connector con) {
		// TODO Auto-generated method stub
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
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
					if(input.readLine() != "y"){
						result = true;
						
					}
					else{
						name = loginUser.setUpUser(con.stmt);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			}
		return name;
		
	}

	private static void TH(Connector con) {
		// TODO Auto-generated method stub
		System.out.println("Please press 1 to register a new house");
		System.out.println("NOTE!  You must log in first if not logged in.");
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String choice = null;
		
	}

	private static void userAccounts(Connector con) {
		System.out.println("Please press 1 to register a new user");
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String choice = null;
		try {
			choice = input.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		User newUser = new User();
		switch(choice){
		
		case"1":
				
				break;
				default:
					break;
			}
		}
	}