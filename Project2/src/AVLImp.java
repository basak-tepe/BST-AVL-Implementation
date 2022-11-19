import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class AVLImp {
	 	
		//I followed a similar implementation to what we did in PS sessions.
		//I omitted some methods and created sendMessage using an analogue of a find method of BST.
	
		//DATA FIELDS
		private Node root;
		private String AVLwriteDirectory;
		private FileWriter myWriter;
		

		//CONSTRUCTORS
		
		AVLImp(){
			this.root = null;
		}
		
		AVLImp(Node root){
			this.root = root;
		}
		
		
		/**
		 * Method that does the writing part
		 * @param string to be written 
		 */
				
			
		private void writeToFile(String s) {
			
			if(AVLwriteDirectory == null) {
				try {
					AVLwriteDirectory = Main.getAVLDirectory(); 		//obtaining the file address from main class.
					//files are created in main method.
					myWriter = new FileWriter(AVLwriteDirectory);
					myWriter.write(s);
					myWriter.write("\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			else {
				try {
					myWriter.write(s);
					myWriter.write("\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		/**
		 * Method that closes the writer.
		 */
		
		public void closeWriter() {
			try {
				myWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	
	
	
		//METHODS
	

		/**
		 * Method that makes sure the tree is balanced after each insertion and deletion.
		 * @param node
		 * @return new root
		 */
		
		private Node balance(Node node){
			if (node == null) {
				return node;
			}
			
			// each node has a balance value
			// balance = left - right

				if (((getHeight(node.getLeftChild())) - (getHeight(node.getRightChild())))> 1) {
					
					if ((getHeight(node.getLeftChild().getLeftChild())) >= (getHeight(node.getLeftChild().getRightChild()))) {	

						writeToFile("Rebalancing: right rotation");
						node = rotateWithLeftChild(node);
					} else {			
						writeToFile("Rebalancing: left-right rotation");
						node = doubleWithLeftChild(node);
					}
				}

				else {
					if (((getHeight(node.getRightChild())) - (getHeight(node.getLeftChild())))> 1) { 
						if ((getHeight(node.getRightChild().getRightChild())) >= (getHeight(node.getRightChild().getLeftChild()))) {			
							writeToFile("Rebalancing: left rotation");
							node = rotateWithRightChild(node);
						} else {								
							writeToFile("Rebalancing: right-left rotation");
							node = doubleWithRightChild(node);
						}
					}
					
				}
				node.setHeight((Math.max(getHeight(node.getLeftChild()), getHeight(node.getRightChild()))+1));
				return node;
			//}
		}
			
	
		/**
		 * obtaining the height of a node.
		 * @param node
		 * @return
		 */

		public int getHeight(Node node) {
			if(node == null) {
				return -1;
			}
			else {
				return (Math.max(getHeight(node.getLeftChild()), getHeight(node.getRightChild()))+1);
			}	
		}


	
		
		/**
		 * I needed a findParent method to track the parent during recursive calls in delete method.
		 * @param node whom parent is to be found
		 * @return the parent
		 */
		
		public Node findParent(Node node) {
			return findParent(this.root,node); 
		}
		
		public Node findParent(Node parent,Node node) {
			
			if(node == null) {
				return null;
			}
			if(node == this.root) {
				return this.root;
			}
	 
			//if children are equal to our node, we have found the parent.
			if(parent.getLeftChild()==node || parent.getRightChild()==node) {
				return parent;
			}
			else {
				//if node has a greater value
				//go right
				if(parent.getRightChild()!=null) {
					if(parent.getData().compareTo(node.getData())<0) {
						return findParent(parent.getRightChild(),node);
					}
				}
				//go left
				if(parent.getLeftChild()!=null) {
					if (parent.getData().compareTo(node.getData())>0){
						return findParent(parent.getLeftChild(),node);
					}
				}
			}
			return parent;
		}
		
		//ROTATIONS 
		
		/**
		 * Rotating the node with left child.
		 * @param node 
		 * @return new root 
		 */
		
		private Node rotateWithLeftChild(Node node) { 		
			Node left = node.getLeftChild();
			node.setLeftChild(left.getRightChild());
			left.setRightChild(node);
			
			node.setHeight((Math.max(getHeight(node.getLeftChild()), getHeight(node.getRightChild())))+1);
			left.setHeight((Math.max(getHeight(left.getLeftChild()), getHeight(node)))+1);
			return left;
		}
		
		/**
		 * 
		 * @param node
		 * @return new root
		 */
		
		private Node rotateWithRightChild(Node node) {
			
			Node right = node.getRightChild();
			node.setRightChild(right.getLeftChild());
			right.setLeftChild(node);
			
			node.setHeight((Math.max(getHeight(node.getLeftChild()), getHeight(node.getRightChild())))+1);
			right.setHeight((Math.max(getHeight(right.getRightChild()), getHeight(node)))+1);
			return right;
		}
		
		/**
		 * double rotation left-right
		 * @param node
		 * @return new root
		 */
		
		private Node doubleWithLeftChild(Node node) {
			
			node.setLeftChild(rotateWithRightChild(node.getLeftChild()));
			return rotateWithLeftChild(node);
		}
		
		/**
		 * double rotation right-left
		 * @param node
		 * @return new root
		 */
		
		private Node doubleWithRightChild(Node node) {
	
			node.setRightChild(rotateWithLeftChild(node.getRightChild()));
			return rotateWithRightChild(node);
		}
		
		
		/**
		 * checking whether the tree is empty 
		 * @return
		 */

		public boolean isEmpty() {
			if(this.root ==null) {
				return true;
			}
			else {
				return false;
			}
		}
		
		/**
		 * leftmost element
		 * @param root
		 * @return
		 */
		
		public Node getMin(Node root) {
			if(isEmpty()) {
				return null;
			}
			
			else {
				Node node = root;
				while(node.getLeftChild()!=null) {
					node = node.getLeftChild();
				}
				return node;
			}
			
		}
		
		/**
		 * rightmost element
		 * @param root
		 * @return
		 */
		
		public Node getMax(Node root) {
			if(isEmpty()) {
				return null;
			}
			
			else {
				Node node = root;
				while(node.getRightChild()!=null) {
					node = node.getRightChild();
				}
				return node;
			}
			
		}
		
		
		/**
		 * method I used for control purposes.
		 * @param node
		 */
		
		public void traverse(Node node) { //in order traversal
			if(node!=null) {
				traverse(node.getLeftChild());
				System.out.println(node); 
				traverse(node.getRightChild());
			}
		}
		
		/**
		 * recursive insert
		 * @param data to be added
		 * @param node head of the subtree
		 * @return
		 */
		
		private Node insert(String data, Node node){
			
			  if (node == null) {
					node = new Node(data);
		            return node;
		        }
		 
			  else {
				 	
		        	
			        if (data.compareTo(node.getData())<0) {
			        	writeToFile(node.getData() + ": New node being added with IP:" +data);
			        	//if data to be added is smaller, go left
			            node.setLeftChild(insert(data, node.getLeftChild()));
			        }
			        else if (data.compareTo(node.getData())>0) {
			        	writeToFile(node.getData() + ": New node being added with IP:" +data);
			        	//if larger, go right
			        	node.setRightChild(insert(data, node.getRightChild()));
			        }
			        else {
			        	//nothing
			        }
			     
			  }
			   return balance(node);	
		}
		
		/**
		 * Method that calls the recursive insert method.
		 * @param data
		 */
		
		public void insert(String data){
			if(isEmpty()) {
				this.root = new Node(data);
			}
			else {
				this.root = this.insert(data,this.root);
			}
		}
		
		/**
		 * Method to remove an item from AVL.
		 * @param data to be deleted
		 * @param node root of the subtree we are in.
		 * @return  new root of the subtree.
		 */
		
		
		private Node delete(String data,Node node){ 
			
			/**
			 *I couldn't keep the parent as another variable during recursive calls to avoid losing it.
			 *So before the deleting actions, I used a findParent Method.
			 */
			
	        if (node == null) {
	            return node;
	        }
	  
	        if (data.compareTo(node.getData())<0) {
	        	//if smaller, go left
	        	if(node.getLeftChild()!=null) {
	            node.setLeftChild(delete(data,node.getLeftChild()));
	        	}
	        }
	        else if (data.compareTo(node.getData())>0) {
	        	//if larger, go right
	        	if(node.getRightChild()!=null) {
	            node.setRightChild(delete(data,node.getRightChild()));
	        	}
	        }
	        
	        // 2 children case
	        else if (node.getLeftChild()!=null && node.getRightChild()!= null) {
	        	writeToFile(node.getData() + ": Non-leaf Node Deleted: " + data);
	        	node.setData(getMin(node.getRightChild()).getData());
	        	node.setRightChild(delete(node.getData(),node.getRightChild()));
	        }
	        
	        //one or leaf
	        else {

	        	//single child situations
	        	if(node.getLeftChild()!=null) {
	        		Node parent = findParent(node);
	        		writeToFile(parent.getData() + ": Node with single child Deleted: " + data);
	        		node.setData(node.getLeftChild().getData());
	        		
	        	}
	        	
	        	else if(node.getRightChild()!=null) {
	        		Node parent = findParent(node);
	        		writeToFile(parent.getData() + ": Node with single child Deleted: " + data);
	        		node.setData(node.getRightChild().getData());
	        		
	        	}
	        	
	        	//leaf situation
	        	else { //rchild and rchild both null.
	        		Node parent = findParent(node);
	        		writeToFile(parent.getData() + ": Leaf Node Deleted: " + data);        		
	        		
	        	}    
	        }
	    	return balance(node);
		}
		
		
		public void delete(String data) throws IOException {
			if(isEmpty()) {
				return;
			}
			
			else {
				
				this.root = delete(data,this.root);
				
			}
		}
		
		
		/**
		 * Method where I call the recursive sendMessage
		 * @param sender
		 * @param reciever
		 */
		public void sendMessage(String sender, String reciever) {
			if(isEmpty()) {
				return;
			}
			else { 
		
				writeToFile(sender + ": Sending message to: " +reciever);
				sendMessage(this.root,sender,reciever);
			}
		}
		

		
		public ArrayList<String> findPath(String data) {
			ArrayList<String> path = new ArrayList<String>();
			return findPath(this.root, data,path); //find some data in some subtree
		}
		
		/**
		 * method that finds a path to a particular data
		 * @param node that roots our subtree
		 * @param data were looking for in a particular subtree
		 * @return
		 */
		
		public ArrayList<String> findPath(Node node,String data,ArrayList<String> path) {
			if(node ==null) {
				return path;
			}
			
			if(node.getData().compareTo(data) ==0) {
				//record final element
				path.add(data);
				return path;
			}
			else {
				//if larger go right
				if(node.getData().compareTo(data)<0) {
					//record the visited element
					path.add(node.getData());
					return findPath(node.getRightChild(),data,path);
				}
				//if smaller go left
				else if(node.getData().compareTo(data)>0) {
					//record the visited element
					path.add(node.getData());
					return findPath(node.getLeftChild(),data,path);
				}
			}
			return path;
		}
		
		/**
		 * I needed to reverse an arrayList for sendMessage.
		 * In order to combine 2 paths.
		 * root-sender and root-reciever
		 * sender-root-reciever.
		 * @param myList
		 * @return
		 */
		
		public ArrayList<String> reverse(ArrayList<String> myList) {
			ArrayList<String> reverseList = new ArrayList<String>();
			int size = myList.size();
		    for(int i = size-1; i>=0; i--) {
		        reverseList.add(myList.get(i));
		    }
		    return reverseList;
		}
		
		/**
		 * Recursive implementation
		 * An analog of find method.
		 * find paths for both element.
		 *  take the intersection
			go from root to sender
			go from root to receiver
			find the intersection element
			reverse the arrayList for the sender up until intersection.
			paste the remaining elements (after intersection) for the receiver.
		
		 * @param root of the subtree we are in
		 * @param sender
		 * @param reciever
		 */
		
		private Node sendMessage(Node node, String sender, String reciever) {
			
			 ArrayList<String> fromRootToSender = findPath(sender);
			 ArrayList<String> fromRootToReciever = findPath(reciever);
			 
			 ArrayList<String> fromSenderToRoot = reverse(fromRootToSender);
			
			int str = fromSenderToRoot.size();
			int rtr = fromRootToReciever.size();
			
			boolean found = false;
			int sIndex = 0; //index of the shared element(rooting element for both) in sender path
			int rIndex = 0; //index of the shared element(rooting element for both) in reciever path
			
			String[] myPath = null; //sharedPath
			int pathSize = 0;
			
			for(int i = 0;i<str;i++) { //for each element 
				if(found) {
					break;
				}
				String sending = fromSenderToRoot.get(i);
				
				for(int j=0;j<rtr;j++) { //try to match it from a intersecting element from root to reciever list.	
					String recieving = fromRootToReciever.get(j);
					
					if(sending.compareTo(recieving)==0) { 
						//if there is an intersecting element, we have found our path.
						//new size of the list will be (i+rtr-j-1)
						//from now on I know the precise size of the list
						//so I'll hop to using an array.
						myPath = new String[i+rtr-j];
						pathSize = i+rtr-j;
						sIndex = i;
						rIndex = j;
						found = true;
					}
				}
			}
			
			//copy the path from sender until root
			for(int x=0;x<sIndex;x++) {
				myPath[x] = fromSenderToRoot.get(x);
			}
			
			//copy the path from root to reciever
			for(int x = rIndex;x<rtr;x++) {	
				myPath[str-1+x-rIndex] = fromRootToReciever.get(x);
			}
			
			for(int i = 0; i<pathSize-1;i++) {
				if(myPath[i+1].compareTo(reciever)!=0) {
				writeToFile(myPath[i+1] + ": Transmission from: "+ myPath[i] + " receiver: "+ reciever + " sender:" +sender);
				}
			}
			
			writeToFile(reciever + ": Received message from: " + sender);
			return node;
			
		}

		/**
		 * other possible AVL methods 
		 * we did these in ps but i omitted them for the stretch of my project.
		 * makeempty
		 * contains
		 * printtree
		 */

		
}