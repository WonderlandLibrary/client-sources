package org.newdawn.slick.state.transition;

import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.MaskUtil;









public class BlobbyTransition
  implements Transition
{
  protected static SGL GL = ;
  

  private GameState prev;
  
  private boolean finish;
  
  private Color background;
  
  private ArrayList blobs = new ArrayList();
  
  private int timer = 1000;
  
  private int blobCount = 10;
  





  public BlobbyTransition() {}
  




  public BlobbyTransition(Color background)
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
  {}
  


  public void preRender(StateBasedGame game, GameContainer container, Graphics g)
    throws SlickException
  {
    prev.render(container, game, g);
    
    MaskUtil.defineMask();
    for (int i = 0; i < blobs.size(); i++) {
      ((Blob)blobs.get(i)).render(g);
    }
    MaskUtil.finishDefineMask();
    
    MaskUtil.drawOnMask();
    if (background != null) {
      Color c = g.getColor();
      g.setColor(background);
      g.fillRect(0.0F, 0.0F, container.getWidth(), container.getHeight());
      g.setColor(c);
    }
  }
  


  public void update(StateBasedGame game, GameContainer container, int delta)
    throws SlickException
  {
    if (blobs.size() == 0) {
      for (int i = 0; i < blobCount; i++) {
        blobs.add(new Blob(container));
      }
    }
    
    for (int i = 0; i < blobs.size(); i++) {
      ((Blob)blobs.get(i)).update(delta);
    }
    
    timer -= delta;
    if (timer < 0) {
      finish = true;
    }
  }
  



  private class Blob
  {
    private float x;
    


    private float y;
    

    private float growSpeed;
    

    private float rad;
    


    public Blob(GameContainer container)
    {
      x = ((float)(Math.random() * container.getWidth()));
      y = ((float)(Math.random() * container.getWidth()));
      growSpeed = ((float)(1.0D + Math.random() * 1.0D));
    }
    




    public void update(int delta)
    {
      rad += growSpeed * delta * 0.4F;
    }
    




    public void render(Graphics g)
    {
      g.fillOval(x - rad, y - rad, rad * 2.0F, rad * 2.0F);
    }
  }
}
