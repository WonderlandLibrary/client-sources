package org.newdawn.slick.state.transition;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;











public class RotateTransition
  implements Transition
{
  private GameState prev;
  private float ang;
  private boolean finish;
  private float scale = 1.0F;
  



  private Color background;
  



  public RotateTransition() {}
  



  public RotateTransition(Color background)
  {
    this.background = background;
  }
  


  public void init(GameState firstState, GameState secondState)
  {
    prev = secondState;
  }
  


  public boolean isComplete()
  {
    return finish;
  }
  

  public void postRender(StateBasedGame game, GameContainer container, Graphics g)
    throws SlickException
  {
    g.translate(container.getWidth() / 2, container.getHeight() / 2);
    g.scale(scale, scale);
    g.rotate(0.0F, 0.0F, ang);
    g.translate(-container.getWidth() / 2, -container.getHeight() / 2);
    if (background != null) {
      Color c = g.getColor();
      g.setColor(background);
      g.fillRect(0.0F, 0.0F, container.getWidth(), container.getHeight());
      g.setColor(c);
    }
    prev.render(container, game, g);
    g.translate(container.getWidth() / 2, container.getHeight() / 2);
    g.rotate(0.0F, 0.0F, -ang);
    g.scale(1.0F / scale, 1.0F / scale);
    g.translate(-container.getWidth() / 2, -container.getHeight() / 2);
  }
  



  public void preRender(StateBasedGame game, GameContainer container, Graphics g)
    throws SlickException
  {}
  


  public void update(StateBasedGame game, GameContainer container, int delta)
    throws SlickException
  {
    ang += delta * 0.5F;
    if (ang > 500.0F) {
      finish = true;
    }
    scale -= delta * 0.001F;
    if (scale < 0.0F) {
      scale = 0.0F;
    }
  }
}
