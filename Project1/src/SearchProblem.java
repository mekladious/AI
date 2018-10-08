import java.util.LinkedList;
public abstract class SearchProblem {

	Object[] stateSpace;
	State initialState;
	Operation[] operations;
	int pathCost;
	LinkedList<Node> queue;
	
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
	
	public Node searchProcedure(SearchProblem sp, Strategy stratetgy){
		//create list to represent the queue
		queue = new LinkedList<Node>();
		
		//enqueue the initial state as a node(create Node(initial state)
		queue.add(new Node(initialState));
		
		//get enqueuing function based on strategy
		EnqueueFunction enqueueFn;
		if(stratetgy == Strategy.BFS)
			enqueueFn = (n) -> bfs(n);
		else if(stratetgy == Strategy.DFS)
			enqueueFn = (n) -> dfs(n);
		else if(stratetgy == Strategy.UCS)
			enqueueFn = (n) -> ucs(n);
		else if(stratetgy == Strategy.IDS)
			enqueueFn = (n) -> ids(n);
		else if(stratetgy == Strategy.GREEDY)
			enqueueFn = (n) -> greedy(n);
		else if(stratetgy == Strategy.ASTAR)	
			enqueueFn = (n) -> aStar(n);
		else return null;

		//loop on the queue while it is not empty popping the first element (currentNode) and assigning its cost to current cost
		while(!queue.isEmpty()){
			Node currentNode = queue.getFirst();
			pathCost += currentNode.cost; 
			
			//if currentNode is goal then return currentNode
			if(isGoal(currentNode.state))
				return currentNode;
			
			//expand and get list of children nodes
			//enqueue the nodes (loop)
			enqueueFn.enqueue(currentNode);

			//queue = expansion function from that current Node enqueing the children according to the search strategy
			// inside: if search strategy one of the 6, go do different private enqueing searching methods
				// switch(stratetgy){
				// 	case BFS:;
				// 	case DFS:;
				// 	case UCS:;
				// 	case IDS:;
				// 	case GREEDY:;
				// 	case ASTAR:;
				// }
		}
		return null;
	}
	
	private void bfs(Node n){
		queue.addLast(n);
	}
	
	private void dfs(Node n){
		queue.addFirst(n);
	}
	
	private void ids(Node n){
	}
	
	private void ucs(Node n){
	}
	
	private void greedy(Node n){
	}
	
	private void aStar(Node n){
	}

}
