import java.util.Arrays;
public class Grid {
    int m, n, dragonGlass, maxWhiteWalkers, maxObstacles;
    CellContent [][] map;
    
    public Grid(){
        m = /*(int)(Math.random()*20) +*/ 3;
        n = /*(int)(Math.random()*20) +*/  3;

		maxWhiteWalkers = (int)(Math.random() * (0.3*m*n)) + 1;
		maxObstacles = (int)(Math.random() *(0.3*m*n))+1;
        dragonGlass = (int)(Math.random() * (m*n)) + 1;

		map = new CellContent[m][n];

		for (CellContent[] row : map) {
			Arrays.fill(row, CellContent.EMPTY);
		}

		map [m-1][n-1] = CellContent.JON__;

		for (int ww = 0; ww < maxWhiteWalkers; ww++)
		{
			int x = (int)(Math.random()*m);
			int y = (int)(Math.random()*n);

			if(map[x][y] == CellContent.EMPTY) map[x][y] = CellContent.WWLKR;
			else ww--;
		}
		
		for (int obs = 0; obs < maxObstacles; obs++)
		{
			int x = (int)(Math.random()*m);
			int y = (int)(Math.random()*n);

			if(map[x][y] == CellContent.EMPTY) map[x][y] = CellContent.OBSTC;
			else obs--;
		}

		while(true){
			int x = (int)(Math.random()*m);
			int y = (int)(Math.random()*n);

			if(map[x][y] == CellContent.EMPTY) 
			{
				map[x][y] = CellContent.DRGNS;
				break;
			}
		}
		map [m-1][n-1] = CellContent.EMPTY;
    }
}