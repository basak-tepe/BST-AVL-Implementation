import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main{
		
	/**
	 * @author Basak Tepe, Student ID:2020400117
	 * @since Date:09.11.2022 submission
	 */
	
	
	//I used following static variables to transfer writing directories to other class files.
	static String AVLwriteDirectory;
	static String BSTwriteDirectory;

	
	public static void main(String[] args) throws IOException {
		
		try {
			//file reading part
			String readFile = args[0];
			
			//file writing part
			String writeFile = args[1];
			AVLwriteDirectory = writeFile +"_AVL.txt";
			BSTwriteDirectory = writeFile +"_BST.txt";
			
			//file creation part
			File AVLFile = new File(AVLwriteDirectory);
			AVLFile.createNewFile();	
			File BSTFile = new File(BSTwriteDirectory);
			BSTFile.createNewFile();
			
			//variables that I will use to process input arguments.
			String methodName = null;
			String[] commands = null;
			
			//scanner to read the file
			Scanner scanner = new Scanner(new File(readFile));
			String data = scanner.nextLine();
			//creating our first node
			Node root = new Node(data);
		
			
			//creating our bst
			BSTImp myBST = new BSTImp(root);
			//creating our avl
			AVLImp myAVL = new AVLImp(root);
			
		
			
			//reading method calls from the input file
			while(scanner.hasNextLine()) {
				String methodCall = scanner.nextLine();   //each line
				commands = methodCall.split(" "); //line split into commands
				methodName = commands[0]; //char sequence of input corresponds to method names.
				
				
				//I used switch to check method calls of each line.
				switch (methodName) {
				
				case "ADDNODE":
					
					String addedData = commands[1];
					
					//BST PART
					myBST.insert(addedData);
					
					
					//AVL PART 
					myAVL.insert(addedData);
					
					break;
				
				
				case "DELETE":
					String deletedData = commands[1];
					
					//BST PART	
			
					myBST.delete(deletedData);
					
					
					//AVL PART 
					myAVL.delete(deletedData);
					 break;
					
				
			
				case "SEND":
				
					String sender = commands[1];
					String reciever = commands[2];
					
					
					//BST PART
					myBST.sendMessage(sender, reciever);
					
					
					//AVL PART 
					myAVL.sendMessage(sender, reciever);
					
					break;
				}
			}
			
			//closing writers
			myAVL.closeWriter();
			myBST.closeWriter();
			
			}
			
		
		catch(FileNotFoundException e) {
			System.out.println("File cannot be found.");
		}
	}
	
	
	//methods I used to transfer writing directory to other class files.
	public static String getAVLDirectory() {
		return AVLwriteDirectory;
	}
	
	public static String getBSTDirectory() {
		return BSTwriteDirectory;
	}
	
}
