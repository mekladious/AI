import java.util.Arrays;

// import com.sun.org.apache.xpath.internal.operations.String;

public class SaveWestros extends SearchProblem{

	int m, n, dragonGlass, whiteWalkers;
	CellContent [][] grid;

	public SaveWestros(){
		GenGrid();
	}
	
	public void GenGrid()
	{
        m = (int)(Math.random()*20) + 1;
        n = (int)(Math.random()*20) + 1;

        whiteWalkers = (int)(Math.random() * (m*n)) + 1;
        dragonGlass = (int)(Math.random() * (m*n)) + 1;

		grid = new CellContent[m][n];

		for (CellContent[] row : grid) {
			Arrays.fill(row, CellContent.EMPTY);
		}

		grid [m-1][n-1] = CellContent.JON;
		for (int ww = 0; ww < whiteWalkers; ww++)
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

	@Override
	public boolean isGoal(State state) {
		// TODO Auto-generated method stub
		return false;
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
