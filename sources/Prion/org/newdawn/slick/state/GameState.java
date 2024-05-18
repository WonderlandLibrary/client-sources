package org.newdawn.slick.state;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.InputListener;
import org.newdawn.slick.SlickException;

public abstract interface GameState
  extends InputListener
{
  public abstract int getID();
  
  public abstract void init(GameContainer paramGameContainer, StateBasedGame paramStateBasedGame)
    throws SlickException;
  
  public abstract void render(GameContainer paramGameContainer, StateBasedGame paramStateBasedGame, Graphics paramGraphics)
    throws SlickException;
  
  public abstract void update(GameContainer paramGameContainer, StateBasedGame paramStateBasedGame, int paramInt)
    throws SlickException;
  
  public abstract void enter(GameContainer paramGameContainer, StateBasedGame paramStateBasedGame)
    throws SlickException;
  
  public abstract void leave(GameContainer paramGameContainer, StateBasedGame paramStateBasedGame)
    throws SlickException;
}
