
public class Node {
	Node parent;
	State state;
	JonSnowOperation previousOperator;
	int depth;
	int cost;
	
	// root constructor
	public Node(State state){
		this.parent = null;
		this.previousOperator = null;
		this.depth = 0;
		this.cost = 0;
		this.state = state;
	}
	
	// node constructor
	public Node(Node parent, JonSnowOperation previousOperator, State state, int cost){
		this.parent = parent;
		this.depth = parent.depth + 1;
		this.previousOperator = previousOperator;
		this.state = state;
		this.cost = cost;
	}
}