import java.util.ArrayList;
import java.util.Arrays;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
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
		// m=10;
		// n=10;
		String JonSnowSentence;
		ArrayList<String> ObstaclesSentences = new ArrayList<String>();
		ArrayList<String> WhiteWalkersSentences = new ArrayList<String>();
		String DragonStoneSentence;

        m = (int)(Math.random()*20) + 3;
        n = (int)(Math.random()*20) + 3;

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

			if(map[y][x] == CellContent.EMPTY){
				map[y][x] = CellContent.WWLKR;
				WhiteWalkersSentences.add("wwlkr("+x+","+y+",s0)");
			}
			else 
				ww--;
		}
		
		for (int obs = 0; obs < maxObstacles; obs++)
		{
			int x = (int)(Math.random()*m);
			int y = (int)(Math.random()*n);

			if(map[y][x] == CellContent.EMPTY) {
				map[y][x] = CellContent.OBSTC;
				ObstaclesSentences.add("obstc("+x+","+y+")");
			}
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
				DragonStoneSentence = "drgns("+x+","+y+","+dragonGlass+")";
				break;
			}
		}
		map [n-1][m-1] = CellContent.EMPTY;
		JonSnowSentence = "jon("+(n-1)+","+(m-1)+","+dragonGlass+",s0)";
		GridSentence = "grid("+m+","+n+")";
		WriteLogicalSentences(GridSentence ,JonSnowSentence, ObstaclesSentences, WhiteWalkersSentences, DragonStoneSentence);
	}
	
	public void WriteLogicalSentences(String j, ArrayList<String> o, ArrayList<String> w, String d){
		try{
			PrintWriter writer = new PrintWriter("../../genfiles/grid.pl", "UTF-8");
			// writer.println("% format: predicate(x, y, additionalInfoOptional, situationOptional)");
			writer.println(g+'.'); writer.println();
			writer.println(j+'.'); writer.println();
			writer.println("ww_count("+maxWhiteWalkers+","+"s0)"+'.'); writer.println();
			for (String obs : o) { 		      
				writer.println(obs+'.');
			}
			writer.println();
			for (String ww : w) { 		      
				writer.println(ww+'.');
			}
			writer.println();	      
			writer.println(d+'.');
			writer.close();
		} catch(Exception e){
			System.err.println(e);
		} 
		
	}
	public static void main(String[] args) {
		Grid grid = new Grid();
	}
}