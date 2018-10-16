
public class SaveWestros extends SearchProblem{

    int m, n, dragonGlass, maxWhiteWalkers, maxObstacles;
    CellContent [][] map;
    boolean deadJon = false;
    CellContent [][] mapBackup;

	public SaveWestros(Grid grid){
		super(new JonSnowState(grid.m-1, grid.n-1, 0, grid.maxWhiteWalkers), JonSnowOperation.class.getEnumConstants());
		m = grid.m;
		n = grid.n;
		dragonGlass = grid.dragonGlass;
		maxWhiteWalkers = grid.maxWhiteWalkers;
		maxObstacles = grid.maxObstacles;
		map = grid.map;
		mapBackup = map;
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

		//System.out.println(""+ currState+" "+ o);
		int x = ((JonSnowState)currState).x;
		int y = ((JonSnowState)currState).y;
		// CellContent currCell = map[x][y];
		int ww = ((JonSnowState)currState).whiteWalkers;
		int dg = ((JonSnowState)currState).dragonGlass;
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
			// newX = x;
			// newY = y;
			if(dg>0){
				ww = killWW(x, y, ww);
				// System.out.println("whitewalker"+ww);
				return new JonSnowState(x, y, dg-1, ww);
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
			switch(map[newX][newY]){
				case WWLKR:
				case OBSTC:
					return null;
				case DRGNS:
					return new JonSnowState(newX, newY, dragonGlass, ww);
				case EMPTY:
					//System.out.println("here");
					return new JonSnowState(newX, newY, dg, ww);
				default: 
			}
		}

		return null;
	}

	public int killWW(int x, int y, int ww){
		// 				[x, y-1]	
		//  [x-1, y]	[x,y]		[x+1, y]
		//				[x, y+1]
		
		if(!((y-1)<0) && map[x][y-1]==CellContent.WWLKR){
			map[x][y-1] = CellContent.EMPTY;
			ww --;
		}
		if((y+1)<n && map[x][y+1]==CellContent.WWLKR){
			map[x][y+1] = CellContent.EMPTY;
			ww --;
		}
		if(!((x-1)<0) && map[x-1][y]==CellContent.WWLKR){
			map[x-1][y] = CellContent.EMPTY;
			ww --;
		}
		if((x+1)<m && map[x+1][y]==CellContent.WWLKR){
			map[x+1][y] = CellContent.EMPTY;
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
	
	public static void printGrid(SaveWestros problem)
	{
		for(int j = 0; j<problem.m; j++)
		{
			// for(int j = 0; j<problem.n; j++)
			// {
			// 	System.out.print(i+" "+j+"\t");
			// }
			// System.out.println();
			for(int i = 0; i<problem.n; i++)
			{
				System.out.print(problem.map[i][j]+"\t\t");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args)
	{
		Grid grid = new Grid();
		SaveWestros problem = new SaveWestros(grid);
		printGrid(problem);
		Node n = problem.searchProcedure(Strategy.IDS);
		if(n!=null)
		{
			System.out.println(n);
			n.printActionSequence();
		}
		else{
			System.out.println("No Solution!");
		}
//		printGrid(problem);
	}
}
