package HORIZON-6-0-SKIDPROTECTION;

public class ManhattanHeuristic implements AStarHeuristic
{
    private int HorizonCode_Horizon_È;
    
    public ManhattanHeuristic(final int minimumCost) {
        this.HorizonCode_Horizon_È = minimumCost;
    }
    
    @Override
    public float HorizonCode_Horizon_È(final TileBasedMap map, final Mover mover, final int x, final int y, final int tx, final int ty) {
        return this.HorizonCode_Horizon_È * (Math.abs(x - tx) + Math.abs(y - ty));
    }
}
