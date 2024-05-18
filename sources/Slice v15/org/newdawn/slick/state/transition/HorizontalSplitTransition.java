package org.newdawn.slick.state.transition;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;








public class HorizontalSplitTransition
  implements Transition
{
  protected static SGL GL = ;
  


  private GameState prev;
  


  private float offset;
  

  private boolean finish;
  

  private Color background;
  


  public HorizontalSplitTransition() {}
  


  public HorizontalSplitTransition(Color background)
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
    g.translate(-offset, 0.0F);
    g.setClip((int)-offset, 0, container.getWidth() / 2, container.getHeight());
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
    
    g.translate(offset * 2.0F, 0.0F);
    g.setClip((int)(container.getWidth() / 2 + offset), 0, container.getWidth() / 2, container.getHeight());
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
    g.translate(-offset, 0.0F);
  }
  



  public void preRender(StateBasedGame game, GameContainer container, Graphics g)
    throws SlickException
  {}
  


  public void update(StateBasedGame game, GameContainer container, int delta)
    throws SlickException
  {
    offset += delta * 1.0F;
    if (offset > container.getWidth() / 2) {
      finish = true;
    }
  }
}
