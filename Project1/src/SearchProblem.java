import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
public abstract class SearchProblem {

	// State[] stateSpace;
	State initialState;
	Operation[] operations;
	int pathCost;
	LinkedList<Node> queue;
	int idsDepth;
	int depthLimit;
	ArrayList<State> visitedStates;
	int nodesExpanded;
	
	public SearchProblem(){
		
	}
	
	//add Operation[] operations,
	public SearchProblem(State initialState, Operation[] operations){
		this.initialState = initialState;
		this.operations = operations;
		this.pathCost = -1;
		visitedStates = new ArrayList();
		nodesExpanded = 0;
		this.idsDepth = 0;
		this.depthLimit = 100;
	}

	public abstract boolean isVisited(State state);
	
	public abstract boolean isGoal(State state);

	public abstract Node [] expand(Node node);

	public abstract void visualizePath(Node goalNode);
	
	public abstract int heuristic(Node n);
	
	public Node searchProcedure(Strategy strategy){
		//clear nodes expanded
		nodesExpanded=0;
		//create list to represent the queue
		queue = new LinkedList<Node>();
		
		//enqueue the initial state as a node(create Node(initial state)
		queue.add(new Node(initialState));
		//get enqueuing function based on strategy
		EnqueueFunction enqueueFn;
		/*if(strategy == Strategy.BFS)
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
*/
		//loop on the queue while it is not empty popping the first element (currentNode) and assigning its cost to current cost
		while(!queue.isEmpty()){

			Node currentNode = queue.removeFirst();
			pathCost = currentNode.cost; 
			
			//if currentNode is goal then return currentNode
			if(isGoal(currentNode.state))
			{
				nodesExpanded++;
				return currentNode;
			}
			//expand and get list of children nodes
			if(currentNode.state!=null){
				if((strategy == Strategy.IDS && currentNode.depth <= idsDepth) || strategy != Strategy.IDS)
				{
					nodesExpanded++;
					Node [] children = expand(currentNode);
					for(int i = 0; i<children.length; i++)
					{
						/**left right up down killww */
						if(children[i]!=null){
							if(children[i].state != null)
							{
								if(strategy == Strategy.BFS)
									bfs(children[i]);
								else if(strategy == Strategy.DFS)
									dfs(children[i]);
								else if(strategy == Strategy.IDS)
									ids(children[i]);
								else if(strategy == Strategy.UCS)
									ucs(children[i]);
								else if(strategy == Strategy.GREEDY)
									greedy(children[i]);
								else if(strategy == Strategy.ASTAR)
									aStar(children[i]);
							}						
						}
					}
				}		
			}
		}
		if(strategy == Strategy.IDS && idsDepth<depthLimit){
			idsDepth++;
			visitedStates = new ArrayList();
			return searchProcedure(Strategy.IDS);
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
			queue.addFirst(n);
		} 
	}
	
	private void ucs(Node n){
		if(queue.size()==0)
			queue.add(n);
		else
		{
			Node curr;
			for(int i=0; i<queue.size(); i++)
			{
				curr = queue.get(i);
				if(curr.cost>n.cost){
					queue.add(i,n);
					return;
				}
			}
			queue.addLast(n);
		}
	}
	
	private void greedy(Node n){
		int h = heuristic(n);
		if(queue.size()==0)
			queue.add(n);
		else
		{
			Node curr;
			for(int i=0; i<queue.size(); i++)
			{
				curr = queue.get(i);
				if(heuristic(curr)>h){
					queue.add(i,n);
					return;
				}
			}
			queue.addLast(n);
		}
	}
	
	private void aStar(Node n){
		int h = heuristic(n);
		if(queue.size()==0)
			queue.add(n);
		else
		{
			Node curr;
			for(int i=0; i<queue.size(); i++)
			{
				curr = queue.get(i);
				if(heuristic(curr)+curr.cost>h+n.cost){
					queue.add(i,n);
					return;
				}
	
			}
			queue.addLast(n);
		}
	}

}
