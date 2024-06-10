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


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.Game
 * JD-Core Version:    0.7.0.1
 */