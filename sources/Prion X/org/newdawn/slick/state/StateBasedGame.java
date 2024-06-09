package org.newdawn.slick.state;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.InputListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.Transition;





public abstract class StateBasedGame
  implements Game, InputListener
{
  private HashMap states = new HashMap();
  

  private GameState currentState;
  

  private GameState nextState;
  

  private GameContainer container;
  

  private String title;
  
  private Transition enterTransition;
  
  private Transition leaveTransition;
  

  public StateBasedGame(String name)
  {
    title = name;
    
    currentState = new BasicGameState() {
      public int getID() {
        return -1;
      }
      

      public void init(GameContainer container, StateBasedGame game)
        throws SlickException
      {}
      
      public void render(StateBasedGame game, Graphics g)
        throws SlickException
      {}
      
      public void update(GameContainer container, StateBasedGame game, int delta)
        throws SlickException
      {}
      
      public void render(GameContainer container, StateBasedGame game, Graphics g)
        throws SlickException
      {}
    };
  }
  
  public void inputStarted() {}
  
  public int getStateCount()
  {
    return states.keySet().size();
  }
  




  public int getCurrentStateID()
  {
    return currentState.getID();
  }
  




  public GameState getCurrentState()
  {
    return currentState;
  }
  





  public void setInput(Input input) {}
  




  public void addState(GameState state)
  {
    states.put(new Integer(state.getID()), state);
    
    if (currentState.getID() == -1) {
      currentState = state;
    }
  }
  





  public GameState getState(int id)
  {
    return (GameState)states.get(new Integer(id));
  }
  




  public void enterState(int id)
  {
    enterState(id, new EmptyTransition(), new EmptyTransition());
  }
  






  public void enterState(int id, Transition leave, Transition enter)
  {
    if (leave == null) {
      leave = new EmptyTransition();
    }
    if (enter == null) {
      enter = new EmptyTransition();
    }
    leaveTransition = leave;
    enterTransition = enter;
    
    nextState = getState(id);
    if (nextState == null) {
      throw new RuntimeException("No game state registered with the ID: " + id);
    }
    
    leaveTransition.init(currentState, nextState);
  }
  

  public final void init(GameContainer container)
    throws SlickException
  {
    this.container = container;
    initStatesList(container);
    
    Iterator gameStates = states.values().iterator();
    
    while (gameStates.hasNext()) {
      GameState state = (GameState)gameStates.next();
      
      state.init(container, this);
    }
    
    if (currentState != null) {
      currentState.enter(container, this);
    }
  }
  



  public abstract void initStatesList(GameContainer paramGameContainer)
    throws SlickException;
  



  public final void render(GameContainer container, Graphics g)
    throws SlickException
  {
    preRenderState(container, g);
    
    if (leaveTransition != null) {
      leaveTransition.preRender(this, container, g);
    } else if (enterTransition != null) {
      enterTransition.preRender(this, container, g);
    }
    
    currentState.render(container, this, g);
    
    if (leaveTransition != null) {
      leaveTransition.postRender(this, container, g);
    } else if (enterTransition != null) {
      enterTransition.postRender(this, container, g);
    }
    
    postRenderState(container, g);
  }
  






  protected void preRenderState(GameContainer container, Graphics g)
    throws SlickException
  {}
  






  protected void postRenderState(GameContainer container, Graphics g)
    throws SlickException
  {}
  





  public final void update(GameContainer container, int delta)
    throws SlickException
  {
    preUpdateState(container, delta);
    
    if (leaveTransition != null) {
      leaveTransition.update(this, container, delta);
      if (leaveTransition.isComplete()) {
        currentState.leave(container, this);
        GameState prevState = currentState;
        currentState = nextState;
        nextState = null;
        leaveTransition = null;
        currentState.enter(container, this);
        if (enterTransition != null) {
          enterTransition.init(currentState, prevState);
        }
      } else {
        return;
      }
    }
    
    if (enterTransition != null) {
      enterTransition.update(this, container, delta);
      if (enterTransition.isComplete()) {
        enterTransition = null;
      } else {
        return;
      }
    }
    
    currentState.update(container, this, delta);
    
    postUpdateState(container, delta);
  }
  







  protected void preUpdateState(GameContainer container, int delta)
    throws SlickException
  {}
  







  protected void postUpdateState(GameContainer container, int delta)
    throws SlickException
  {}
  






  private boolean transitioning()
  {
    return (leaveTransition != null) || (enterTransition != null);
  }
  


  public boolean closeRequested()
  {
    return true;
  }
  


  public String getTitle()
  {
    return title;
  }
  




  public GameContainer getContainer()
  {
    return container;
  }
  


  public void controllerButtonPressed(int controller, int button)
  {
    if (transitioning()) {
      return;
    }
    
    currentState.controllerButtonPressed(controller, button);
  }
  


  public void controllerButtonReleased(int controller, int button)
  {
    if (transitioning()) {
      return;
    }
    
    currentState.controllerButtonReleased(controller, button);
  }
  


  public void controllerDownPressed(int controller)
  {
    if (transitioning()) {
      return;
    }
    
    currentState.controllerDownPressed(controller);
  }
  


  public void controllerDownReleased(int controller)
  {
    if (transitioning()) {
      return;
    }
    
    currentState.controllerDownReleased(controller);
  }
  


  public void controllerLeftPressed(int controller)
  {
    if (transitioning()) {
      return;
    }
    
    currentState.controllerLeftPressed(controller);
  }
  


  public void controllerLeftReleased(int controller)
  {
    if (transitioning()) {
      return;
    }
    
    currentState.controllerLeftReleased(controller);
  }
  


  public void controllerRightPressed(int controller)
  {
    if (transitioning()) {
      return;
    }
    
    currentState.controllerRightPressed(controller);
  }
  


  public void controllerRightReleased(int controller)
  {
    if (transitioning()) {
      return;
    }
    
    currentState.controllerRightReleased(controller);
  }
  


  public void controllerUpPressed(int controller)
  {
    if (transitioning()) {
      return;
    }
    
    currentState.controllerUpPressed(controller);
  }
  


  public void controllerUpReleased(int controller)
  {
    if (transitioning()) {
      return;
    }
    
    currentState.controllerUpReleased(controller);
  }
  


  public void keyPressed(int key, char c)
  {
    if (transitioning()) {
      return;
    }
    
    currentState.keyPressed(key, c);
  }
  


  public void keyReleased(int key, char c)
  {
    if (transitioning()) {
      return;
    }
    
    currentState.keyReleased(key, c);
  }
  


  public void mouseMoved(int oldx, int oldy, int newx, int newy)
  {
    if (transitioning()) {
      return;
    }
    
    currentState.mouseMoved(oldx, oldy, newx, newy);
  }
  


  public void mouseDragged(int oldx, int oldy, int newx, int newy)
  {
    if (transitioning()) {
      return;
    }
    
    currentState.mouseDragged(oldx, oldy, newx, newy);
  }
  

  public void mouseClicked(int button, int x, int y, int clickCount)
  {
    if (transitioning()) {
      return;
    }
    
    currentState.mouseClicked(button, x, y, clickCount);
  }
  


  public void mousePressed(int button, int x, int y)
  {
    if (transitioning()) {
      return;
    }
    
    currentState.mousePressed(button, x, y);
  }
  


  public void mouseReleased(int button, int x, int y)
  {
    if (transitioning()) {
      return;
    }
    
    currentState.mouseReleased(button, x, y);
  }
  


  public boolean isAcceptingInput()
  {
    if (transitioning()) {
      return false;
    }
    
    return currentState.isAcceptingInput();
  }
  



  public void inputEnded() {}
  



  public void mouseWheelMoved(int newValue)
  {
    if (transitioning()) {
      return;
    }
    
    currentState.mouseWheelMoved(newValue);
  }
}
