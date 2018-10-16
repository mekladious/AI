import java.util.Arrays;
public class Grid {
    int m, n, dragonGlass, maxWhiteWalkers, maxObstacles;
    CellContent [][] map;
    
    public Grid(){
        m = /*(int)(Math.random()*20) +*/ 4;
        n = /*(int)(Math.random()*20) +*/  3;

		maxWhiteWalkers = (int)(Math.random() * (0.3*m*n)) + 1;
		maxObstacles = (int)(Math.random() *(0.3*m*n))+1;
        dragonGlass = (int)(Math.random() * (m*n)) + 1;

		map = new CellContent[n][m];

		for (CellContent[] row : map) {
			Arrays.fill(row, CellContent.EMPTY);
		}

		map [n-1][m-1] = CellContent.JON;

		for (int ww = 0; ww < maxWhiteWalkers; ww++)
		{
			int x = (int)(Math.random()*m);
			int y = (int)(Math.random()*n);

			if(map[y][x] == CellContent.EMPTY) map[y][x] = CellContent.WHITEWALKER;
			else ww--;
		}
		
		for (int obs = 0; obs < maxObstacles; obs++)
		{
			int x = (int)(Math.random()*m);
			int y = (int)(Math.random()*n);

			if(map[y][x] == CellContent.EMPTY) map[y][x] = CellContent.OBSTACLE;
			else obs--;
		}

		while(true){
			int x = (int)(Math.random()*m);
			int y = (int)(Math.random()*n);

			if(map[y][x] == CellContent.EMPTY) 
			{
				map[y][x] = CellContent.DRAGONSTONE;
				break;
			}
		}
		map [n-1][m-1] = CellContent.EMPTY;
    }
}