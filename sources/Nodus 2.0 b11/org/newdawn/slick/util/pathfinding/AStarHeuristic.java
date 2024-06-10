package org.newdawn.slick.util.pathfinding;

public abstract interface AStarHeuristic
{
  public abstract float getCost(TileBasedMap paramTileBasedMap, Mover paramMover, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.util.pathfinding.AStarHeuristic
 * JD-Core Version:    0.7.0.1
 */