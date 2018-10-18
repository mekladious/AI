import java.util.Arrays;
public class Grid {
    int m, n, dragonGlass, dragonStoneX, dragonStoneY, maxWhiteWalkers, maxObstacles;
    CellContent [][] map;
    
    public Grid(Grid grid){
		this.m = grid.m;
		this.n = grid.n;
		this.dragonGlass = grid.dragonGlass;
		this.maxWhiteWalkers = grid.maxWhiteWalkers;
		this.maxObstacles = grid.maxObstacles;
		this.map = new CellContent[n][m];
		for(int j = 0; j<grid.n; j++)		//y
		{
			for(int i = 0; i<grid.m; i++)	//x
			{
				this.map[j][i] = grid.map[j][i];
			}
		}
	}
    public Grid(){
		m=10;
		n=10;
        // m = (int)(Math.random()*20) + 4;
        // n = (int)(Math.random()*20) + 4;

		maxWhiteWalkers = (int)(Math.random() * (0.3*m*n)) + 1;
		maxObstacles = (int)(Math.random() *(0.3*m*n))+1;
        dragonGlass = (int)(Math.random() * (m*n)) + 1;

		map = new CellContent[n][m];

		for (CellContent[] row : map) {
			Arrays.fill(row, CellContent.EMPTY);
		}
		
		map [n-1][m-1] = CellContent.JON__;

		for (int ww = 0; ww < maxWhiteWalkers; ww++)
		{
			int x = (int)(Math.random()*m);
			int y = (int)(Math.random()*n);

			if(map[y][x] == CellContent.EMPTY) 
				map[y][x] = CellContent.WWLKR;
			else 
				ww--;
		}
		
		for (int obs = 0; obs < maxObstacles; obs++)
		{
			int x = (int)(Math.random()*m);
			int y = (int)(Math.random()*n);

			if(map[y][x] == CellContent.EMPTY) 
				map[y][x] = CellContent.OBSTC;
			else
				obs--;
		}

		while(true){
			int x = (int)(Math.random()*m);
			int y = (int)(Math.random()*n);

			if(map[y][x] == CellContent.EMPTY) 
			{
				map[y][x] = CellContent.DRGNS;
				dragonStoneX = x;
				dragonStoneY = y;
				break;
			}
		}
		map [n-1][m-1] = CellContent.EMPTY;
    }
}