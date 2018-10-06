import java.util.LinkedList;


public abstract class SearchProblem {

	Object[] stateSpace;
	State initialState;
	Operation[] operations;
	int pathCost;
	
	public SearchProblem(){
		
	}
	
	//add Operation[] operations,
	public SearchProblem(Object[] stateSpace, State initialState, Operation[] operations){
		this.stateSpace = stateSpace;
		this.initialState = initialState;
		this.operations = operations;
		this.pathCost = -1;
	}
	
	public abstract boolean isGoal(State state);
	
	public Node searchProcedure(Strategy stratetgy){
		//create list to represent the queue
		LinkedList<Node> queue = new LinkedList<Node>();
		
		//enqueue the initial state as a node(create Node(initial state)
		queue.add(new Node(initialState));
		
		//loop on the queue while it is not empty popping the first element (currentNode) and assigning its cost to current cost
		while(!queue.isEmpty()){
			Node currentNode = queue.getFirst();
			pathCost = currentNode.cost; 
			
			//if currentNode is goal then return currentNode
			if(isGoal(currentNode.state))
				return currentNode;
			
			//queue = expansion function from that current Node enqueing the children according to the search strategy
			// inside: if search strategy one of the 6, go do different private enqueing searching methods
				switch(stratetgy){
					case BFS:;
					case DFS:;
					case UCS:;
					case IDS:;
					case GREEDY:;
					case ASTAR:;
				}
		}
		return null;
	}
	
	private LinkedList<Node> bfs(){
		return null;
	}
	
	private LinkedList<Node> dfs(){
		return null;
	}
	
	private LinkedList<Node> ids(){
		return null;
	}
	
	private LinkedList<Node> ucs(){
		return null;
	}
	
	private LinkedList<Node> greedy(){
		return null;
	}
	
	private LinkedList<Node> aStar(){
		return null;
	}
	
}
