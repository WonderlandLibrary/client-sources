package org.newdawn.slick;

import org.newdawn.slick.opengl.SlickCallable;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;












public class CachedRender
{
  protected static SGL GL = ;
  

  private Runnable runnable;
  
  private int list = -1;
  





  public CachedRender(Runnable runnable)
  {
    this.runnable = runnable;
    build();
  }
  


  private void build()
  {
    if (list == -1) {
      list = GL.glGenLists(1);
      
      SlickCallable.enterSafeBlock();
      GL.glNewList(list, 4864);
      runnable.run();
      GL.glEndList();
      SlickCallable.leaveSafeBlock();
    } else {
      throw new RuntimeException("Attempt to build the display list more than once in CachedRender");
    }
  }
  



  public void render()
  {
    if (list == -1) {
      throw new RuntimeException("Attempt to render cached operations that have been destroyed");
    }
    
    SlickCallable.enterSafeBlock();
    GL.glCallList(list);
    SlickCallable.leaveSafeBlock();
  }
  


  public void destroy()
  {
    GL.glDeleteLists(list, 1);
    list = -1;
  }
}
