import java.util.Arrays;

public class SaveWestros extends SearchProblem{

	int m, n, dragonGlass, maxWhiteWalkers;
	CellContent [][] grid;

	public SaveWestros(){
		GenGrid();
	}
	
	public void GenGrid()
	{
        m = (int)(Math.random()*20) + 1;
        n = (int)(Math.random()*20) + 1;

		maxWhiteWalkers = (int)(Math.random() * (m*n)) + 1;
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
		JonSnowState curr_state = (JonSnowState)node.state;
		int x = curr_state.x;
		int newX;
		int y = curr_state.y;
		int newY;
		int ww = curr_state.whiteWalkers;
		int dg = curr_state.dragonGlass;

		for(int i = 0; i<5; i++){
			JonSnowOperation curr_operation = operations[i];

			if(curr_operation == JonSnowOperation.LEFT){
				newX = x + 1;
				newY = y;
			}
			if(curr_operation == JonSnowOperation.RIGHT){
				newX = x - 1;
				newY = y;
			}
			if(curr_operation == JonSnowOperation.UP){
				newX = x;
				newY = y - 1;
			}
			if(curr_operation == JonSnowOperation.DOWN){
				newX = x;
				newY = y + 1;
			}
			else{ //Kill WW
				newX = x;
				newY=y;
				ww = killWW(ww);
				newState = new JonSnowState(newX, newY, (dg-1), ww);
			}
			
			if(newX<0 || newX>=m || newY<0 || newY>=n){
				children[i]=null;
			} 
			else{
				JonSnowState newState;
				int newCost;
				switch(grid[newX][newY]){
					case WHITEWALKER:
						// children[i]=null;
					case OBSTACLE:
						children[i]=null; break;
					case DRAGONSTONE:
						newState = new JonSnowState(newX, newY, dragonGlass, ww);
						// this.stateSpace.
						//TODO: calculate cost
						newCost = node.cost;
						children[i] = new Node(n, (Operation)curr_operation, newState, newCost); break;
					case EMPTY:
						newState = new JonSnowState(newX, newY, dg, ww);
						//TODO: calculate cost
						newCost = node.cost;
						children[i] = new Node(n, (Operation)curr_operation, newState, newCost); break;
					default: 
				}
			}


		}
		return children;
	}

	public int killWW(int ww){
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
