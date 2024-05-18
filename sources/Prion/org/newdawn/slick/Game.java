package org.newdawn.slick;

public abstract interface Game
{
  public abstract void init(GameContainer paramGameContainer)
    throws SlickException;
  
  public abstract void update(GameContainer paramGameContainer, int paramInt)
    throws SlickException;
  
  public abstract void render(GameContainer paramGameContainer, Graphics paramGraphics)
    throws SlickException;
  
  public abstract boolean closeRequested();
  
  public abstract String getTitle();
}
