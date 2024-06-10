package org.newdawn.slick.util.pathfinding;

public abstract interface PathFindingContext
{
  public abstract Mover getMover();
  
  public abstract int getSourceX();
  
  public abstract int getSourceY();
  
  public abstract int getSearchDistance();
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.util.pathfinding.PathFindingContext
 * JD-Core Version:    0.7.0.1
 */