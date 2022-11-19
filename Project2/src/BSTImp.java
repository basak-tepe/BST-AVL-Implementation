import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class BSTImp {

	//i followed a similar approach to what we did in PS sessions. 
	
	//DATA FIELDS - ATTRIBUTES
	private Node root;
	private String BSTwriteDirectory;
	private FileWriter myWriter;

	
	//CONSTRUCTORS
	
	BSTImp(){
		this.root = null;
	}
	
	
	BSTImp(Node root){
		this.root = root;
	}
	
	/**
	 * Method that does the writing part
	 * @param string to be written 
	 */
		
	private void writeToFile(String s) {
		
		if(BSTwriteDirectory == null) {
			try {
				BSTwriteDirectory = Main.getBSTDirectory(); //obtaining the file address from main class.
				//files are created in main method.
				myWriter = new FileWriter(BSTwriteDirectory);
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
	 * a helper method to check whether a tree is empty.
	 * @return
	 */
	private boolean isEmpty() {
		if(this.root ==null) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	/**
	 * find the parent of a node in the subtree covered under some root.
	 * @param node the child whom parent is wanted
	 * @return parent
	 */
	private Node findParent(Node node) {
		return findParent(this.root,node); 
	}
	
	private Node findParent(Node parent,Node node) {
		
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
	
	
	public Node find(String data) {
		return find(this.root, data); //find some data in some subtree
	}
	
	/**
	 * method that finds a particular data
	 * @param node that roots our subtree
	 * @param data were looking for in a particular subtree
	 * @return
	 */
	
	public Node find(Node node,String data) {
		if(node ==null) {
			return node;
		}
		
		if(node.getData().compareTo(data) ==0) {
			return node;
		}
		else {
			//if larger go right
			if(node.getData().compareTo(data)<0) {
				return find(node.getRightChild(),data);
			}
			//if smaller go left
			else if(node.getData().compareTo(data)>0) {
				return find(node.getLeftChild(),data);
			}
		}
		return node;
	}
	
	
	/**
	 * obtaining the smallest element in our BST.
	 * i.e the leftmost item.
	 * @param root
	 * @return smallest node's value.
	 */
	
	
	public String getMin(Node root) {
		if(isEmpty()) {
			return "The list is empty.";
		}
		else {
			Node node = root;
			//traversing
			while(node.getLeftChild()!=null) {
				node = node.getLeftChild();
			}
			
			return node.getData();
		}
		
	}
	
	/**
	 * obtaining the largest element in our BST.
	 * i.e the rightmost item.
	 * @param root
	 * @return largest node's value.
	 */
	
	public String getMax(Node root) {
		if(isEmpty()) {
			return "The list is empty.";
		}
		else {
			Node node = root;
			//traversing
			while(node.getRightChild()!=null) {
				node = node.getRightChild();
			}
			return node.getData();
		}		
	}
	
	/**
	 * A base method for checking whether an item is present in the tree.
	 * I will modify this method to obtain SendMessage method.
	 * @param data
	 * @param node
	 * @return
	 */
	
	public boolean contains(String data, Node node) {
		if(node ==null) {
			return false;
		}
		
		if(data.compareTo(node.getData())<0) {
			return contains(data,node.getLeftChild());
			
		}

		else if(data.compareTo(node.getData())>0) {
			return contains(data,node.getLeftChild());
		}
		else {
			return true;
		}
	}
	

	public boolean contains(String data) {
		return contains(data,this.root);
	}
	
	/**
	 * A method to print the tree in order.
	 * For control purposes
	 * @param node (root)
	 */
	
	public void traverse(Node node) { //In order traversal
		if(node!=null) {
			traverse(node.getLeftChild());
			System.out.println(node.getData()); 
			traverse(node.getRightChild());
		}
	}
	
	
	/**
	 * Adding an item to our BST.
	 * @param data to be added
	 * @param node parent for addition
	 * @return new root of the subtree.
	 */
	
	private Node insert(String data, Node node) { 
		
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
		   return node;			
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
	 * Method to remove an item from our BST.
	 * @param data to be deleted.
	 * @param node parent for the subtree.
	 * @return new root for the subtree.
	 */
	
	private Node delete(String data,Node node) {
		
		/**
		 *I couldn't keep the parent as another variable during recursive calls to avoid losing it.
		 *So before the deleting actions, I used a findParent Method.
		 */
		
        if (node == null) { 
            return node;
        }
       
        if ((data.compareTo(node.getData()))<0){
            node.setLeftChild(delete(data,node.getLeftChild()));
        	
        }
        else if ((data.compareTo(node.getData()))>0) {
            node.setRightChild(delete(data,node.getRightChild()));       
        }
  
       //data = searched data situations 
        //2 children
        else if(node.getLeftChild()!=null && node.getRightChild()!=null) {
        	writeToFile(node.getData() + ": Non-leaf Node Deleted: " + data);
        	node.setData(getMin(node.getRightChild()));
        	node.setRightChild(delete(node.getData(),node.getRightChild()));
        
        }
        
        //one child or leaf node situations
        else {

        	//single child situations
        	if(node.getLeftChild()!=null) {
        		Node parent = findParent(node);
        		writeToFile(parent.getData() + ": Node with single child Deleted: " + data);
        		node = node.getLeftChild();
        	}
        	
        	else if(node.getRightChild()!=null) {
        		
        		Node parent = findParent(node);
        		writeToFile(parent.getData() + ": Node with single child Deleted: " + data);
        		node = node.getRightChild();
        	}
        	
        	//leaf situation
        	else { 
        		Node parent = findParent(node);
        		writeToFile(parent.getData() + ": Leaf Node Deleted: " + data);
        	}
        }
        
        
        return node;
	
	}

	public void delete(String data) {
		
		if(isEmpty()) { //no such element
			return; 
		}
		else {
			this.root = delete(data,this.root);
		}
	}
	

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
	 * method that find the path of a particular data
	 * @param node that roots our subtree
	 * @param data were looking for in a particular subtree
	 * @return
	 */
	
	public ArrayList<String> findPath(Node node,String data,ArrayList<String> path) {
		if(node ==null) {
			return path;
		}
		
		if(node.getData().compareTo(data) ==0) {
			path.add(data);
			return path;
		}
		else {
			//if larger go right
			if(node.getData().compareTo(data)<0) {
				path.add(node.getData());
				return findPath(node.getRightChild(),data,path);
			}
			//if smaller go left
			else if(node.getData().compareTo(data)>0) {
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
		// System.out.println(fromSenderToRoot);
		// System.out.println(fromRootToReciever);
		
		int str = fromSenderToRoot.size();
		int rtr = fromRootToReciever.size();
		
		boolean found = false;
		int sIndex = 0; //index of the shared element(rooting element for both) in sender path
		int rIndex = 0; //index of the shared element(rooting element for both) in reciever path
		
		String[] myPath = null; //sharedPath
		int pathSize = 0;
		
		for(int i = 0;i<str;i++) {
			if(found) {
				break;
			}
			String sending = fromSenderToRoot.get(i);
			for(int j=0;j<rtr;j++) {
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
		
		for(int x=0;x<sIndex;x++) {
			myPath[x] = fromSenderToRoot.get(x);
		}
		
		for(int x = rIndex;x<rtr;x++) {
				
			myPath[str-1+x-rIndex] = fromRootToReciever.get(x);
		}
		
		//System.out.println(Arrays.toString(myPath));
		

		for(int i = 0; i<pathSize-1;i++) {
			if(myPath[i+1].compareTo(reciever)!=0) {
			writeToFile(myPath[i+1] + ": Transmission from: "+ myPath[i] + " receiver: "+ reciever + " sender:" +sender);
			}
		}
		 
	
		writeToFile(reciever + ": Received message from: " + sender);
		return node;
		
	}
	
	
	//Other possible BST methods that I omitted for the stretch of this project.
	/*
	 * MakeEmpty
	 * 
	 */
}
