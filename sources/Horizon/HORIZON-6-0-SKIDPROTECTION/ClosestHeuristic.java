package HORIZON-6-0-SKIDPROTECTION;

public class ClosestHeuristic implements AStarHeuristic
{
    @Override
    public float HorizonCode_Horizon_È(final TileBasedMap map, final Mover mover, final int x, final int y, final int tx, final int ty) {
        final float dx = tx - x;
        final float dy = ty - y;
        final float result = (float)Math.sqrt(dx * dx + dy * dy);
        return result;
    }
}
