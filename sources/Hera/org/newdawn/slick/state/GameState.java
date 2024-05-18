package org.newdawn.slick.state;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.InputListener;
import org.newdawn.slick.SlickException;

public interface GameState extends InputListener {
  int getID();
  
  void init(GameContainer paramGameContainer, StateBasedGame paramStateBasedGame) throws SlickException;
  
  void render(GameContainer paramGameContainer, StateBasedGame paramStateBasedGame, Graphics paramGraphics) throws SlickException;
  
  void update(GameContainer paramGameContainer, StateBasedGame paramStateBasedGame, int paramInt) throws SlickException;
  
  void enter(GameContainer paramGameContainer, StateBasedGame paramStateBasedGame) throws SlickException;
  
  void leave(GameContainer paramGameContainer, StateBasedGame paramStateBasedGame) throws SlickException;
}


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\state\GameState.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */