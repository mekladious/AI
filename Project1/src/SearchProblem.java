import java.util.LinkedList;
public abstract class SearchProblem {

	// State[] stateSpace;
	State initialState;
	Operation[] operations;
	int pathCost;
	LinkedList<Node> queue;
	int idsDepth = 0;
	
	public SearchProblem(){
		
	}
	
	//add Operation[] operations,
	public SearchProblem(State initialState, Operation[] operations){
		this.initialState = initialState;
		this.operations = operations;
		this.pathCost = -1;
	}
	
	public abstract boolean isGoal(State state);

	public abstract Node [] expand(Node node);
	
	public Node searchProcedure(Strategy strategy){
		//create list to represent the queue
		queue = new LinkedList<Node>();
		
		//enqueue the initial state as a node(create Node(initial state)
		queue.add(new Node(initialState));
		
		//get enqueuing function based on strategy
		EnqueueFunction enqueueFn;
		if(strategy == Strategy.BFS)
			enqueueFn = (n) -> bfs(n);
		else if(strategy == Strategy.DFS)
			enqueueFn = (n) -> dfs(n);
		else if(strategy == Strategy.UCS)
			enqueueFn = (n) -> ucs(n);
		else if(strategy == Strategy.IDS)
			enqueueFn = (n) -> ids(n);
		else if(strategy == Strategy.GREEDY)
			enqueueFn = (n) -> greedy(n);
		else if(strategy == Strategy.ASTAR)	
			enqueueFn = (n) -> aStar(n);
		else return null;

		//loop on the queue while it is not empty popping the first element (currentNode) and assigning its cost to current cost
		while(!queue.isEmpty()){
			Node currentNode = queue.removeFirst();
			pathCost = currentNode.cost; 
			
			//if currentNode is goal then return currentNode
			if(isGoal(currentNode.state))
				return currentNode;
			
			//expand and get list of children nodes
			if(currentNode.state!=null){
				if((strategy == Strategy.IDS && currentNode.depth < idsDepth) || strategy != Strategy.IDS)
				{
					Node [] children = expand(currentNode);

					for(int i = 0; i<children.length; i++)
					{
						//enqueue the nodes (loop)
						enqueueFn.enqueue(currentNode);
					}
				}		
			}
		}
		return null;
	}
	
	private void bfs(Node n){
		queue.addLast(n);
	}
	
	private void dfs(Node n){
		queue.addFirst(n);
	}
	
	private void ids(Node n)
	{	
		if(n.depth <= idsDepth)
		{
			dfs(n);
		}
		else if (n.depth > idsDepth && queue.isEmpty())
		{
			idsDepth++;
			ids(new Node(initialState));
		}
	}
	
	private void ucs(Node n){
		Node prev = queue.get(0);
		Node curr;

		if(prev.cost<n.cost){
			queue.addFirst(n);
		}
		
		for(int i=0; i<queue.size(); i++)
		{
			curr = queue.get(i);
		}

	}
	
	private void greedy(Node n){
	}
	
	private void aStar(Node n){
	}

}
