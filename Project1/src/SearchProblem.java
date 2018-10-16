import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
public abstract class SearchProblem {

	// State[] stateSpace;
	State initialState;
	Operation[] operations;
	int pathCost;
	LinkedList<Node> queue;
	int idsDepth = 0;
	ArrayList<State> visitedStates;
	
	public SearchProblem(){
		
	}
	
	//add Operation[] operations,
	public SearchProblem(State initialState, Operation[] operations){
		this.initialState = initialState;
		this.operations = operations;
		this.pathCost = -1;
		visitedStates = new ArrayList();
	}

	public abstract boolean isVisited(State state);
	
	public abstract boolean isGoal(State state);

	public abstract Node [] expand(Node node);

	public abstract void visualizePath(Node goalNode);
	
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
				if((strategy == Strategy.IDS && currentNode.depth <= idsDepth) || strategy != Strategy.IDS)
				{
					Node [] children = expand(currentNode);
					for(int i = 0; i<children.length; i++)
					{
						/**left right up down killww */
						//enqueue the nodes (loop)
						if(children[i]!=null){
							// System.out.println("oper "+children[i].previousOperator);
							// System.out.println("State: "+((JonSnowState)children[i].state).x);
							// System.out.println(((JonSnowState)children[i].state).y);
							// System.out.println(((JonSnowState)children[i].state).dragonGlass);
							// System.out.println(((JonSnowState)children[i].state).whiteWalkers);
							enqueueFn.enqueue(children[i]);
						}
							// System.out.println(children[i]);
						// System.out.println(((JonSnowState)currentNode.state).x);
						// System.out.println(((JonSnowState)currentNode.state).y);
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
		// System.out.println("added");
		queue.addFirst(n);
	}
	
	private void ids(Node n)
	{	
		if(n.depth <= idsDepth)
		{
			queue.addFirst(n);
		}
		else if ( n==null || queue.isEmpty())
		{
			idsDepth++;
			queue.addFirst(new Node(initialState));
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
	}
	
	private void aStar(Node n){
	}

}
