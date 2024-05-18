package org.newdawn.slick.util.pathfinding;

public abstract interface TileBasedMap
{
  public abstract int getWidthInTiles();
  
  public abstract int getHeightInTiles();
  
  public abstract void pathFinderVisited(int paramInt1, int paramInt2);
  
  public abstract boolean blocked(PathFindingContext paramPathFindingContext, int paramInt1, int paramInt2);
  
  public abstract float getCost(PathFindingContext paramPathFindingContext, int paramInt1, int paramInt2);
}
