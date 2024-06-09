package org.newdawn.slick.state.transition;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;






public class FadeInTransition
  implements Transition
{
  private Color color;
  private int fadeTime = 500;
  


  public FadeInTransition()
  {
    this(Color.black, 500);
  }
  




  public FadeInTransition(Color color)
  {
    this(color, 500);
  }
  





  public FadeInTransition(Color color, int fadeTime)
  {
    this.color = new Color(color);
    colora = 1.0F;
    this.fadeTime = fadeTime;
  }
  


  public boolean isComplete()
  {
    return color.a <= 0.0F;
  }
  


  public void postRender(StateBasedGame game, GameContainer container, Graphics g)
  {
    Color old = g.getColor();
    g.setColor(color);
    
    g.fillRect(0.0F, 0.0F, container.getWidth() * 2, container.getHeight() * 2);
    g.setColor(old);
  }
  


  public void update(StateBasedGame game, GameContainer container, int delta)
  {
    color.a -= delta * (1.0F / fadeTime);
    if (color.a < 0.0F) {
      color.a = 0.0F;
    }
  }
  
  public void preRender(StateBasedGame game, GameContainer container, Graphics g) {}
  
  public void init(GameState firstState, GameState secondState) {}
}
