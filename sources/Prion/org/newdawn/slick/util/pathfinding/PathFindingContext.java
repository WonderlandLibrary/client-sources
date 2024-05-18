package org.newdawn.slick.util.pathfinding;

public abstract interface PathFindingContext
{
  public abstract Mover getMover();
  
  public abstract int getSourceX();
  
  public abstract int getSourceY();
  
  public abstract int getSearchDistance();
}
