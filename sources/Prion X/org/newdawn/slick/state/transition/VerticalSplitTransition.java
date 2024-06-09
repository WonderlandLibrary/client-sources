package org.newdawn.slick.state.transition;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;








public class VerticalSplitTransition
  implements Transition
{
  protected static SGL GL = ;
  


  private GameState prev;
  


  private float offset;
  

  private boolean finish;
  

  private Color background;
  


  public VerticalSplitTransition() {}
  


  public VerticalSplitTransition(Color background)
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
    g.translate(0.0F, -offset);
    g.setClip(0, (int)-offset, container.getWidth(), container.getHeight() / 2);
    if (background != null) {
      Color c = g.getColor();
      g.setColor(background);
      g.fillRect(0.0F, 0.0F, container.getWidth(), container.getHeight());
      g.setColor(c);
    }
    GL.glPushMatrix();
    prev.render(container, game, g);
    GL.glPopMatrix();
    g.clearClip();
    g.resetTransform();
    
    g.translate(0.0F, offset);
    g.setClip(0, (int)(container.getHeight() / 2 + offset), container.getWidth(), container.getHeight() / 2);
    if (background != null) {
      Color c = g.getColor();
      g.setColor(background);
      g.fillRect(0.0F, 0.0F, container.getWidth(), container.getHeight());
      g.setColor(c);
    }
    GL.glPushMatrix();
    prev.render(container, game, g);
    GL.glPopMatrix();
    g.clearClip();
    g.translate(0.0F, -offset);
  }
  



  public void preRender(StateBasedGame game, GameContainer container, Graphics g)
    throws SlickException
  {}
  


  public void update(StateBasedGame game, GameContainer container, int delta)
    throws SlickException
  {
    offset += delta * 1.0F;
    if (offset > container.getHeight() / 2) {
      finish = true;
    }
  }
}
