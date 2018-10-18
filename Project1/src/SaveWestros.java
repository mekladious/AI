import java.util.Stack;

public class SaveWestros extends SearchProblem{

    int m, n, dragonGlass, maxWhiteWalkers, maxObstacles;
	boolean deadJon = false;
	Grid grid;
	boolean heuristicFunctionSimple = false;

	public SaveWestros(Grid grid){
		super(new JonSnowState(grid.m-1, grid.n-1, 0, grid.maxWhiteWalkers, grid), JonSnowOperation.class.getEnumConstants());
		m = grid.m;
		n = grid.n;
		dragonGlass = grid.dragonGlass;
		maxWhiteWalkers = grid.maxWhiteWalkers;
		maxObstacles = grid.maxObstacles;
		this.grid = new Grid(grid);
	}

	//TODO : MIRA
	//pos: update jon snoww pos, check for obs
	// kill : kills all surrounding check ds#
	// ww cell: return null
	// ds: update auto
	//apply all operations
	public Node[] expand(Node node){
		JonSnowOperation [] operations = JonSnowOperation.class.getEnumConstants();
		Node [] children = new Node[5];
		JonSnowState currState = (JonSnowState)node.state;
		int newCost=0;
		State next;
		
		for(int i = 0; i<operations.length; i++){
			JonSnowOperation currOperation = operations[i];

			newCost = currOperation==JonSnowOperation.KILL_WW ? n*m : 0;
			// System.out.println(newCost);
			next = nextState(currState, currOperation);
			children[i] = (next!=null && !isVisited(next))?new Node(node, currOperation, next, newCost+node.cost+1):null;
		}
		return children;
	}

	public State nextState (State currState, Operation o){

		int x = ((JonSnowState)currState).x;
		int y = ((JonSnowState)currState).y;
		
		int ww = ((JonSnowState)currState).whiteWalkers;
		int dg = ((JonSnowState)currState).dragonGlass;

		Grid currGrid = ((JonSnowState)currState).grid;

		JonSnowOperation currOperation = (JonSnowOperation)o;

		int newX = x;
		int newY = y;

		if(currOperation == JonSnowOperation.LEFT){
			newX = x - 1;
			newY = y;
		}
		if(currOperation == JonSnowOperation.RIGHT){
			newX = x + 1;
			newY = y;
		}
		if(currOperation == JonSnowOperation.UP){
			newX = x;
			newY = y - 1;
		}
		if(currOperation == JonSnowOperation.DOWN){
			newX = x;
			newY = y + 1;
		}
		if(currOperation == JonSnowOperation.KILL_WW){ 
			if(dg>0){
				Object [] ww_grid = killWW(x, y, ww, currGrid);
				int numWw = (Integer) ww_grid[0];
				return new JonSnowState(x, y, dg-1, numWw, (Grid)ww_grid[1]);
			}
			else{
				return null;
			}
		}
		
		if(newX<0 || newX>=m || newY<0 || newY>=n){
			return null;
		} 
		else{
			switch(currGrid.map[newY][newX]){
				case WWLKR:
				case OBSTC:
					return null;
				case DRGNS:
					return new JonSnowState(newX, newY, dragonGlass, ww, new Grid(currGrid));
				case EMPTY:
					return new JonSnowState(newX, newY, dg, ww, new Grid(currGrid));
				default: 
			}
		}

		return null;
	}

	public Object [] killWW(int x, int y, int ww, Grid grid){
		// 				[x, y-1]	
		//  [x-1, y]	[x,y]		[x+1, y]
		//				[x, y+1]
		Grid newGrid = new Grid(grid);
		if(!((y-1)<0) && newGrid.map[y-1][x]==CellContent.WWLKR){
			newGrid.map[y-1][x] = CellContent.EMPTY;
			ww --;
		}
		if((y+1)<n && newGrid.map[y+1][x]==CellContent.WWLKR){
			newGrid.map[y+1][x] = CellContent.EMPTY;
			ww --;
		}
		if(!((x-1)<0) && newGrid.map[y][x-1]==CellContent.WWLKR){
			newGrid.map[y][x-1] = CellContent.EMPTY;
			ww --;
		}
		if((x+1)<m && newGrid.map[y][x+1]==CellContent.WWLKR){
			newGrid.map[y][x+1] = CellContent.EMPTY;
			ww --;
		}
		// printGrid(this);

		Object [] toReturn = new Object[]{ww, newGrid};
		return toReturn;
	} 

	@Override
	public boolean isGoal(State state) {
		if(state!= null)
			return (((JonSnowState)state).whiteWalkers<=0);
		else
			return false;
	}

	@Override
	public boolean isVisited(State state) {
		if(state!= null){
			for(int i = 0; i<visitedStates.size(); i++){
				JonSnowState curr = (JonSnowState)visitedStates.get(i);
				JonSnowState lookfor = (JonSnowState)state;
				if(curr.x == lookfor.x && curr.y == lookfor.y && curr.dragonGlass == lookfor.dragonGlass && curr.whiteWalkers == lookfor.whiteWalkers)
					return true;
			}
			visitedStates.add(state);
			return false;
		}
		else
			return true;
	}
	
	public int heuristic(Node n){
		return heuristicFunctionSimple?heuristicSimple(n):heuristicComplex(n);
	}


	private int heuristicSimple(Node n){
		int ww = (((JonSnowState)n.state).whiteWalkers%3==0)?(((JonSnowState)n.state).whiteWalkers/3):(((JonSnowState)n.state).whiteWalkers/3+1);
		return ww * (this.n*this.m+1);
	}
	
	private int heuristicComplex(Node n){
		int ww = (((JonSnowState)n.state).whiteWalkers%3==0)?(((JonSnowState)n.state).whiteWalkers/3):(((JonSnowState)n.state).whiteWalkers/3+1);
		if(ww == 0)
			return 0;
		int h = 0;
		int myX = ((JonSnowState)n.state).x;
		int myY = ((JonSnowState)n.state).y;
		int dragonStoneX = ((JonSnowState)n.state).grid.dragonStoneX;
		int dragonStoneY = ((JonSnowState)n.state).grid.dragonStoneY;
		int dragonGlass = ((JonSnowState)n.state).dragonGlass;
		for(int i=0; i<ww; i++){
			if(dragonGlass <= 0)
			{
				//path from my x and y to dragonglass
				h += abs((dragonStoneX - myX)) + abs((dragonStoneY - myY));
				//my x and y are now the same as the the dragonstone's
				myX = dragonStoneX;
				myY = dragonStoneY;
				
				dragonGlass+=grid.dragonGlass;
			}
			//path from variable to max white walker
			h += max(((JonSnowState)n.state).grid, myX, myY);
			//dragonGlass is less by 1
			dragonGlass--;
			//add killing cost
			h += (this.n*this.m+1);
		}
		return h;
	}
	
	private int max(Grid grid, int myX, int myY){
		int maxPathCost = 0; 
		for(int j = 0; j<grid.n; j++)		//y
		{
			for(int i = 0; i<grid.m; i++)	//x
			{
				if(grid.map[j][i] == CellContent.WWLKR && (i-myX+j-myY)>maxPathCost){
					maxPathCost=(abs(i-myX)+abs(j-myY));
				}
			}
		}
		return maxPathCost;
	}
	
	private int abs(int x){
		if(x<0)
			return -x;
		return x;
	}
	@Override
	public void visualizePath(Node goalNode){
		Stack<Node> operationsStack = new Stack<Node>();
		Node currNode = goalNode;

		while(currNode!=null){
			operationsStack.push(currNode);
			currNode = currNode.parent;
		}
		while(!operationsStack.isEmpty()){
			Node n = operationsStack.pop();
			System.out.println(n.previousOperator);
		}
	}
	
	public static void printGrid(Grid grid)
	{
		for(int j = 0; j<grid.n; j++)		//y
		{
			for(int i = 0; i<grid.m; i++)	//x
			{
				System.out.print(grid.map[j][i]+"\t\t");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args)
	{
		Grid grid = new Grid();
		SaveWestros problem_ids = new SaveWestros(grid);
		SaveWestros problem_bfs = new SaveWestros(grid);
		problem_ids.heuristicFunctionSimple = false;
		problem_bfs.heuristicFunctionSimple = false;
		printGrid(grid);
		Node n_ids = problem_ids.searchProcedure(Strategy.IDS);
		Node n_bfs = problem_bfs.searchProcedure(Strategy.BFS);
		if(n_ids!=null)
		{
			System.out.println(n_ids);
			n_ids.printActionSequence();
		}
		else{
			System.out.println("No Solution!");
		}
		if(n_bfs!=null)
		{
			System.out.println(n_bfs);
			n_bfs.printActionSequence();
		}
		else{
			System.out.println("No Solution!");
		}
		printGrid(grid);

//		problem.visualizePath(n);
	}
}
