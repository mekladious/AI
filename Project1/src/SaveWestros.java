
public class SaveWestros extends SearchProblem{

    int m, n, dragonGlass, maxWhiteWalkers, maxObstacles;
	boolean deadJon = false;
	Grid grid;

	public SaveWestros(Grid grid){
		super(new JonSnowState(grid.m-1, grid.n-1, 0, grid.maxWhiteWalkers, grid), JonSnowOperation.class.getEnumConstants());
		m = grid.m;
		n = grid.n;
		dragonGlass = grid.dragonGlass;
		maxWhiteWalkers = grid.maxWhiteWalkers;
		maxObstacles = grid.maxObstacles;
		this.grid = grid;
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
			if(currOperation == JonSnowOperation.KILL_WW){
				newCost = n*m;
			}
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
				ww = killWW(x, y, ww, currGrid);
				return new JonSnowState(x, y, dg-1, ww, currGrid);
			}
			else{
				return null;
			}
		}
		
		if(newX<0 || newX>=m || newY<0 || newY>=n){
			return null;
		} 
		else{
			//int newCost = 1;
			switch(currGrid.map[newX][newY]){
				case WHITEWALKER:
				case OBSTACLE:
					return null;
				case DRAGONSTONE:
					return new JonSnowState(newX, newY, dragonGlass, ww, currGrid);
				case EMPTY:
					return new JonSnowState(newX, newY, dg, ww, currGrid);
				default: 
			}
		}

		return null;
	}

	public int killWW(int x, int y, int ww, Grid grid){
		// 				[x, y-1]	
		//  [x-1, y]	[x,y]		[x+1, y]
		//				[x, y+1]
		
		if(!((y-1)<0) && grid.map[x][y-1]==CellContent.WHITEWALKER){
			grid.map[x][y-1] = CellContent.EMPTY;
			ww --;
		}
		if((y+1)<n && grid.map[x][y+1]==CellContent.WHITEWALKER){
			grid.map[x][y+1] = CellContent.EMPTY;
			ww --;
		}
		if(!((x-1)<0) && grid.map[x-1][y]==CellContent.WHITEWALKER){
			grid.map[x-1][y] = CellContent.EMPTY;
			ww --;
		}
		if((x+1)<m && grid.map[x+1][y]==CellContent.WHITEWALKER){
			grid.map[x+1][y] = CellContent.EMPTY;
			ww --;
		}
		// printGrid(this);
		return ww;
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

	@Override
	public void visualizePath(Node goalNode){

	}
	
	public static void printGrid(SaveWestros problem)
	{
		for(int i = 0; i<problem.m; i++)
		{
			for(int j = 0; j<problem.n; j++)
			{
				System.out.print(problem.grid.map[i][j]+"\t\t");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args)
	{
		Grid grid = new Grid();
		SaveWestros problem = new SaveWestros(grid);
		printGrid(problem);
		Node n = problem.searchProcedure(Strategy.DFS);
		System.out.println(n);
	}
}
