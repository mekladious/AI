
public class JonSnowState implements State{
    int x;
    int y;
    int dragonGlass;             //dragon glass
    int whiteWalkers;             //white walkers
    Grid grid;

    public JonSnowState(int x, int y, int dragonGlass, int whiteWalkers, Grid grid) {
        this.x = x;
        this.y = y;
        this.dragonGlass = dragonGlass;
        this.whiteWalkers = whiteWalkers;
        this.grid = grid;
    }
    
    public int getX() {
        return x;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public int getY() {
        return y;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public int getDragonGlass() {
        return dragonGlass;
    }

    public int getWhiteWalkers() {
        return whiteWalkers;
    }
}
