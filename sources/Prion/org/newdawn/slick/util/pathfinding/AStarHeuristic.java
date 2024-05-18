package org.newdawn.slick.util.pathfinding;

public abstract interface AStarHeuristic
{
  public abstract float getCost(TileBasedMap paramTileBasedMap, Mover paramMover, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
}
