
public class Node {
	Node parent;
	State state;
	JonSnowOperation previousOperator;
	int depth;
	int cost;
	
	// root constructor
	public Node(){
		this.parent = null;
		this.previousOperator = null;
		this.depth = 0;
		this.cost = 0;
		
		//state initialization
	}
	
	// node constructor
	public Node(Node parent, JonSnowOperation previousOperator){
		this.parent = parent;
		this.depth = parent.depth + 1;
		this.cost = parent.cost;
		this.previousOperator = previousOperator;
	}
}