package org.newdawn.slick.opengl.pbuffer;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.Pbuffer;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.opengl.RenderTexture;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.InternalTextureLoader;
import org.newdawn.slick.opengl.SlickCallable;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.util.Log;










public class PBufferGraphics
  extends Graphics
{
  private Pbuffer pbuffer;
  private Image image;
  
  public PBufferGraphics(Image image)
    throws SlickException
  {
    super(image.getTexture().getTextureWidth(), image.getTexture().getTextureHeight());
    this.image = image;
    
    Log.debug("Creating pbuffer(rtt) " + image.getWidth() + "x" + image.getHeight());
    if ((Pbuffer.getCapabilities() & 0x1) == 0) {
      throw new SlickException("Your OpenGL card does not support PBuffers and hence can't handle the dynamic images required for this application.");
    }
    if ((Pbuffer.getCapabilities() & 0x2) == 0) {
      throw new SlickException("Your OpenGL card does not support Render-To-Texture and hence can't handle the dynamic images required for this application.");
    }
    
    init();
  }
  


  private void init()
    throws SlickException
  {
    try
    {
      Texture tex = InternalTextureLoader.get().createTexture(image.getWidth(), image.getHeight(), image.getFilter());
      
      RenderTexture rt = new RenderTexture(false, true, false, false, 8314, 0);
      pbuffer = new Pbuffer(screenWidth, screenHeight, new PixelFormat(8, 0, 0), rt, null);
      

      pbuffer.makeCurrent();
      
      initGL();
      GL.glBindTexture(3553, tex.getTextureID());
      pbuffer.releaseTexImage(8323);
      image.draw(0.0F, 0.0F);
      image.setTexture(tex);
      
      Display.makeCurrent();
    } catch (Exception e) {
      Log.error(e);
      throw new SlickException("Failed to create PBuffer for dynamic image. OpenGL driver failure?");
    }
  }
  


  protected void disable()
  {
    GL.flush();
    

    GL.glBindTexture(3553, image.getTexture().getTextureID());
    pbuffer.bindTexImage(8323);
    try
    {
      Display.makeCurrent();
    } catch (LWJGLException e) {
      Log.error(e);
    }
    
    SlickCallable.leaveSafeBlock();
  }
  

  protected void enable()
  {
    
    
    try
    {
      if (pbuffer.isBufferLost()) {
        pbuffer.destroy();
        init();
      }
      
      pbuffer.makeCurrent();
    } catch (Exception e) {
      Log.error("Failed to recreate the PBuffer");
      throw new RuntimeException(e);
    }
    

    GL.glBindTexture(3553, image.getTexture().getTextureID());
    pbuffer.releaseTexImage(8323);
    TextureImpl.unbind();
    initGL();
  }
  


  protected void initGL()
  {
    GL11.glEnable(3553);
    GL11.glShadeModel(7425);
    GL11.glDisable(2929);
    GL11.glDisable(2896);
    
    GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
    GL11.glClearDepth(1.0D);
    
    GL11.glEnable(3042);
    GL11.glBlendFunc(770, 771);
    
    GL11.glViewport(0, 0, screenWidth, screenHeight);
    GL11.glMatrixMode(5888);
    GL11.glLoadIdentity();
    
    enterOrtho();
  }
  


  protected void enterOrtho()
  {
    GL11.glMatrixMode(5889);
    GL11.glLoadIdentity();
    GL11.glOrtho(0.0D, screenWidth, 0.0D, screenHeight, 1.0D, -1.0D);
    GL11.glMatrixMode(5888);
  }
  


  public void destroy()
  {
    super.destroy();
    
    pbuffer.destroy();
  }
  


  public void flush()
  {
    super.flush();
    
    image.flushPixelData();
  }
}
