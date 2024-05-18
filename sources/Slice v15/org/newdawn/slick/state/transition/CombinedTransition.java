package org.newdawn.slick.state.transition;

import java.util.ArrayList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;







public class CombinedTransition
  implements Transition
{
  private ArrayList transitions = new ArrayList();
  





  public CombinedTransition() {}
  




  public void addTransition(Transition t)
  {
    transitions.add(t);
  }
  


  public boolean isComplete()
  {
    for (int i = 0; i < transitions.size(); i++) {
      if (!((Transition)transitions.get(i)).isComplete()) {
        return false;
      }
    }
    
    return true;
  }
  

  public void postRender(StateBasedGame game, GameContainer container, Graphics g)
    throws SlickException
  {
    for (int i = transitions.size() - 1; i >= 0; i--) {
      ((Transition)transitions.get(i)).postRender(game, container, g);
    }
  }
  

  public void preRender(StateBasedGame game, GameContainer container, Graphics g)
    throws SlickException
  {
    for (int i = 0; i < transitions.size(); i++) {
      ((Transition)transitions.get(i)).postRender(game, container, g);
    }
  }
  

  public void update(StateBasedGame game, GameContainer container, int delta)
    throws SlickException
  {
    for (int i = 0; i < transitions.size(); i++) {
      Transition t = (Transition)transitions.get(i);
      
      if (!t.isComplete()) {
        t.update(game, container, delta);
      }
    }
  }
  
  public void init(GameState firstState, GameState secondState) {
    for (int i = transitions.size() - 1; i >= 0; i--) {
      ((Transition)transitions.get(i)).init(firstState, secondState);
    }
  }
}
