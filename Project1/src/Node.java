import java.util.ArrayList;


public class Node{
	Node parent;
	State state;
	Operation previousOperator;
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
	public Node(Node parent, Operation previousOperator, State state, int cost){
		this.parent = parent;
		this.depth = parent.depth + 1;
		this.previousOperator = previousOperator;
		this.state = state;
		this.cost = cost;
	}
	
	// action sequence print
	public void printActionSequence()
	{
		ArrayList<Operation> actions = new ArrayList();
		Node childNode = this;
		
		while(childNode!=null)
		{
			Node parent = childNode.parent;
			if(parent == null)
			{
				break;
			}
			else if (childNode.previousOperator!= null)
			{
				actions.add(childNode.previousOperator);
				childNode = parent;
			}
		}
		
		for(int i = actions.size()-1; i >= 0 ;i--)
		{
			System.out.print(actions.get(i)+",");
		}
		System.out.println();
	}
}