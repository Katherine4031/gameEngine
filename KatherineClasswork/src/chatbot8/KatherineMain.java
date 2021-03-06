package chatbot8;

import java.util.Scanner;

public class KatherineMain {
	
	static String response;
	static boolean inMainLoop;
	static Scanner input;
	static String user;
	//list all the chatbots available under this class
	static Chatbot school;

	public static void main(String[] args) {
		
		createFields();
		promptName();
		promptForever();

	}
	
	public static void promptName(){
		print("Enter your name " );
		user = input.nextLine();
		print("Glad to meet you, " + user + ". For the rest of time I will call you " + user + "."
				+ " You may call me Computer. We should become friends. ");
	}
	
	public static void promptForever(){
		
		inMainLoop = true;
		while(inMainLoop){
			
			print("Hi, " + user + ". How are you?");
			response = promptInput();
			
			//response to how you feel
			if(findKeyWord(response, "good", 0) >= 0){
			print("That's wonderful. So glad you feel good.");
			}
			//response to liking school
			else if(response.indexOf("school") >= 0){
				print("School is great! Tell me about school");
				//stop while loop
				inMainLoop = false;
				// go to school talk method
				school.talk();
			}
			else{
				print("I don't understand.");
			}
			
		}
		
	}
	
	public static int findKeyWord(String searchString, String keyword, int startPsn) {
		
		//delete white space
		searchString = searchString.trim();
		
		//make lowercase
		searchString = searchString.toLowerCase();
		keyword = keyword.toLowerCase();
		
		System.out.println("The phrase is " + searchString);
		System.out.println("The keyword is " + keyword);
		
		//find first position of keyword
		int psn = searchString.indexOf(keyword);
		System.out.println("The keyword was found at " + psn);
		
		//keep searching until context keyword found
		while(psn >= 0){
			
			//assume preceeded and followed by space
			String before = " ";
			String after = " ";
			
			//check character in front if it exists
			if(psn > 0){
				
				before = searchString.substring(psn - 1, psn);
				System.out.println("The character before is " + before);
				
			}
			//check if there is a character after the keyword
			if(psn + keyword.length() < searchString.length()){
				
				after = searchString.substring(psn + keyword.length(), psn + keyword.length()+1);
				System.out.println("The character after is " + after);
				
			}
			if(before.compareTo("a") < 0 && after.compareTo("a") < 0 && noNegations(searchString, psn)){
				
				System.out.println("Found " + keyword + " at " + psn);
				return psn;
				
			}
			else{
				
				//psn + 1 is one space after our current psn, so this finds the NEXT word
				psn = searchString.indexOf(keyword, psn + 1);
				System.out.println("Did not find keyword " + keyword + ", checking position " + psn);
				
			}
			
		}
		
		return -1;
		
	}
	
	/**
	 * This is a HELPER METHOD. A helper method is a method designed for "helping" a larger method.
	 * Because of this, helper methods are generally private because they are only used buy the methods they
	 * are helping. ALSO, when you do your project, I expect to see helper methods because they also make code
	 * more READABLE
	 * @param searchString
	 * @param psn
	 * @return "true" if there is no negation words in front of psn
	 */
	
	private static boolean noNegations(String searchString, int psn){
		
		//check to see if the word "no " is in front of psn
		//check to see if there are 3 spaces in front
		
		//then check to see if "no " is there
		if(psn - 3 >= 0 && searchString.substring(psn - 3, psn).equals("no ")){
			return false;
		}
		
		//check for "not "
		if(psn - 4 >= 0 && searchString.substring(psn - 4, psn).equals("not ")){
			return false;
		}
		
		//check for "never "
		if(psn - 6 >= 0 && searchString.substring(psn - 6, psn).equals("never ")){
			return false;
		}
		
		return true;
		
	}

	public static String promptInput(){
		
		String userInput = input.nextLine();
		return userInput;
		
	}

	public static void createFields(){
		
		input = new Scanner(System.in);
		user = "";
		school = new KatherineSchool();
		
	}

	public static void print(String s){
		
		String printString = "";
		int cutoff = 45;
		
		//check for words to add
		//in other words s has a length > 0
		while(s.length() > 0){
			
			String cut = "";
			String nextWord = "";
			
			//check to see if the next word will fit on the line AND there must still be words to add
			while(cut.length() + nextWord.length() < cutoff && s.length() > 0){
				
				//add the next word to the line
				cut += nextWord;
				
				s = s.substring(nextWord.length());
				
				//identify the following word without the space
				int endOfWord = s.indexOf(" ");
				
				//if there are no more spaces, it's the last word, so add the whole thing
				if(endOfWord == -1){
					endOfWord = s.length() - 1; //for index
				}
				
				nextWord = s.substring(0, endOfWord + 1);
				
			}
			
			printString += cut + "\n";
			
		}
		
		System.out.print(printString);
		
	}
	
}