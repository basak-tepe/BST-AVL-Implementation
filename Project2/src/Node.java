
public class Node {
	
	//DATA FIELDS-ATTRIBUTES
	
	private String data; //IP address
	private Node leftChild;
	private Node rightChild;
	
	//we need height for AVL
	private int height;
	

	//CONSTRUCTORS

	Node(String data){
		this.data = data;
		this.leftChild = null;
		this.rightChild = null;
	}
	
	//METHODS
	
	public int getHeight(Node node) {
		if(node ==null) {
			return -1;
		}
		else {
		return 1+Math.max(getHeight(node.getLeftChild()), getHeight(node.getRightChild()));
		}
	}
	
	public void setHeight(int i) {
		this.height = i;
	}
	
	public Node getLeftChild() {
		return this.leftChild;
	}
	
	public Node getRightChild() {
		return this.rightChild;
	}

	public void setLeftChild(Node left) {
		this.leftChild = left;
	}
	
	public void setRightChild(Node right) {
		this.rightChild = right;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	public String getData() {
		return this.data;
	}
	
}
