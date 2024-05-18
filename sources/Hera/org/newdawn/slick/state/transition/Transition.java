package org.newdawn.slick.state.transition;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

public interface Transition {
  void update(StateBasedGame paramStateBasedGame, GameContainer paramGameContainer, int paramInt) throws SlickException;
  
  void preRender(StateBasedGame paramStateBasedGame, GameContainer paramGameContainer, Graphics paramGraphics) throws SlickException;
  
  void postRender(StateBasedGame paramStateBasedGame, GameContainer paramGameContainer, Graphics paramGraphics) throws SlickException;
  
  boolean isComplete();
  
  void init(GameState paramGameState1, GameState paramGameState2);
}


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\state\transition\Transition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */