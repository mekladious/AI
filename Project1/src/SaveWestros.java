import java.util.Arrays;

public class SaveWestros extends SearchProblem{

	int m, n, dragonGlass, maxWhiteWalkers, maxObstacles;
	CellContent [][] grid;

	public SaveWestros(){
		GenGrid();
	}
	
	public void GenGrid()
	{
        m = /*(int)(Math.random()*20) +*/ 4;
        n = /*(int)(Math.random()*20) +*/  4;

		maxWhiteWalkers = (int)(Math.random() * (0.3*m*n)) + 1;
		maxObstacles = (int)(Math.random() *(0.3*m*n))+1;
        dragonGlass = (int)(Math.random() * (m*n)) + 1;

		grid = new CellContent[m][n];

		for (CellContent[] row : grid) {
			Arrays.fill(row, CellContent.EMPTY);
		}

		grid [m-1][n-1] = CellContent.JON;

		for (int ww = 0; ww < maxWhiteWalkers; ww++)
		{
			int x = (int)(Math.random()*m);
			int y = (int)(Math.random()*n);

			if(grid[x][y] == CellContent.EMPTY) grid[x][y] = CellContent.WHITEWALKER;
			else ww--;
		}
		
		for (int obs = 0; obs < maxObstacles; obs++)
		{
			int x = (int)(Math.random()*m);
			int y = (int)(Math.random()*n);

			if(grid[x][y] == CellContent.EMPTY) grid[x][y] = CellContent.OBSTACLE;
			else obs--;
		}

		while(true){
			int x = (int)(Math.random()*m);
			int y = (int)(Math.random()*n);

			if(grid[x][y] == CellContent.EMPTY) 
			{
				grid[x][y] = CellContent.DRAGONSTONE;
				break;
			}
		}
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
		int newCost = node.cost;

		for(int i = 0; i<operations.length; i++){
			JonSnowOperation currOperation = operations[i];
			if(currOperation == JonSnowOperation.KILL_WW){
				newCost += n*m;
			}
			children[i] = new Node(node, currOperation, nextState(currState, currOperation), newCost+1);
		}
		return children;
	}

	public State nextState (State currState, Operation o){

		int x = ((JonSnowState)currState).x;
		int y = ((JonSnowState)currState).y;
		CellContent currCell = grid[x][y];
		int ww = ((JonSnowState)currState).whiteWalkers;
		int dg = ((JonSnowState)currState).dragonGlass;
		JonSnowOperation currOperation = (JonSnowOperation)o;

		int newX = x;
		int newY = y;

		if(currOperation == JonSnowOperation.LEFT){
			newX = x + 1;
			newY = y;
		}
		if(currOperation == JonSnowOperation.RIGHT){
			newX = x - 1;
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
			newX = x;
			newY=y;
			if(dg>0){
				ww = killWW(newX, newY, ww);
				return new JonSnowState(newX, newY, dg-1, ww);
			}
			else{
				return null;
			}
		}
		
		if(newX<0 || newX>=m || newY<0 || newY>=n){
			grid[x][y] = currCell;
			return null;
		} 
		
		else{
			// int newCost;
			switch(grid[newX][newY]){
				case WHITEWALKER:
					// newState = new JonSnowState(newX, newY, dg, ww);
				case OBSTACLE:
					return null;
				case DRAGONSTONE:
					return new JonSnowState(newX, newY, dragonGlass, ww);
					//TODO: calculate cost
					// newCost = node.cost+1;
					// children[i] = new Node(node, (Operation)curr_operation, newState, newCost); break;
				case EMPTY:
					return new JonSnowState(newX, newY, dg, ww);
					//TODO: calculate cost
					// newCost = node.cost+1;
					// children[i] = new Node(node, (Operation)curr_operation, newState, newCost); break;
				default: 
			}
			grid[newX][newY] = CellContent.JON;
		}

		return null;
	}

	public int killWW(int x, int y, int ww){
		// 				[x, y-1]	
		//  [x-1, y]	[x,y]		[x+1, y]
		//				[x, y+1]
		
		if(!((y-1)<0) && grid[x][y-1]==CellContent.WHITEWALKER){
			grid[x][y-1] = CellContent.EMPTY;
			ww --;
		}
		if((y+1)<n && grid[x][y+1]==CellContent.WHITEWALKER){
			grid[x][y+1] = CellContent.EMPTY;
			ww --;
		}
		if(!((x-1)<0) && grid[x-1][y]==CellContent.WHITEWALKER){
			grid[x-1][y] = CellContent.EMPTY;
			ww --;
		}
		if((x+1)<m && grid[x+1][y]==CellContent.WHITEWALKER){
			grid[x+1][y] = CellContent.EMPTY;
			ww --;
		}
		return ww;
	} 

	@Override
	public boolean isGoal(State state) {
		return (((JonSnowState)state).whiteWalkers<=0);
	}
	
	public static void main(String[] args)
	{
		SaveWestros problem = new SaveWestros();
		
		for(int i = 0; i<problem.m; i++)
		{
			for(int j = 0; j<problem.n; j++)
			{
				System.out.print(problem.grid[i][j]+"\t");
			}
			System.out.println();
		}
	}


}
