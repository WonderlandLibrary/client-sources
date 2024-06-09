package org.newdawn.slick.opengl.pbuffer;

import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.InternalTextureLoader;
import org.newdawn.slick.opengl.SlickCallable;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.util.Log;






public class FBOGraphics
  extends Graphics
{
  private Image image;
  private int FBO;
  private boolean valid = true;
  




  public FBOGraphics(Image image)
    throws SlickException
  {
    super(image.getTexture().getTextureWidth(), image.getTexture().getTextureHeight());
    this.image = image;
    
    Log.debug("Creating FBO " + image.getWidth() + "x" + image.getHeight());
    
    boolean FBOEnabled = getCapabilitiesGL_EXT_framebuffer_object;
    if (!FBOEnabled) {
      throw new SlickException("Your OpenGL card does not support FBO and hence can't handle the dynamic images required for this application.");
    }
    
    init();
  }
  



  private void completeCheck()
    throws SlickException
  {
    int framebuffer = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
    switch (framebuffer) {
    case 36053: 
      break;
    case 36054: 
      throw new SlickException("FrameBuffer: " + FBO + 
        ", has caused a GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT_EXT exception");
    case 36055: 
      throw new SlickException("FrameBuffer: " + FBO + 
        ", has caused a GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT_EXT exception");
    case 36057: 
      throw new SlickException("FrameBuffer: " + FBO + 
        ", has caused a GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS_EXT exception");
    case 36059: 
      throw new SlickException("FrameBuffer: " + FBO + 
        ", has caused a GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER_EXT exception");
    case 36058: 
      throw new SlickException("FrameBuffer: " + FBO + 
        ", has caused a GL_FRAMEBUFFER_INCOMPLETE_FORMATS_EXT exception");
    case 36060: 
      throw new SlickException("FrameBuffer: " + FBO + 
        ", has caused a GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER_EXT exception");
    case 36056: default: 
      throw new SlickException("Unexpected reply from glCheckFramebufferStatusEXT: " + framebuffer);
    }
    
  }
  


  private void init()
    throws SlickException
  {
    IntBuffer buffer = BufferUtils.createIntBuffer(1);
    EXTFramebufferObject.glGenFramebuffersEXT(buffer);
    FBO = buffer.get();
    

    try
    {
      Texture tex = InternalTextureLoader.get().createTexture(image.getWidth(), image.getHeight(), image.getFilter());
      
      EXTFramebufferObject.glBindFramebufferEXT(36160, FBO);
      EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 
        36064, 
        3553, tex.getTextureID(), 0);
      
      completeCheck();
      unbind();
      

      clear();
      flush();
      

      drawImage(image, 0.0F, 0.0F);
      image.setTexture(tex);
    }
    catch (Exception e) {
      throw new SlickException("Failed to create new texture for FBO");
    }
  }
  


  private void bind()
  {
    EXTFramebufferObject.glBindFramebufferEXT(36160, FBO);
    GL11.glReadBuffer(36064);
  }
  


  private void unbind()
  {
    EXTFramebufferObject.glBindFramebufferEXT(36160, 0);
    GL11.glReadBuffer(1029);
  }
  


  protected void disable()
  {
    GL.flush();
    
    unbind();
    GL11.glPopClientAttrib();
    GL11.glPopAttrib();
    GL11.glMatrixMode(5888);
    GL11.glPopMatrix();
    GL11.glMatrixMode(5889);
    GL11.glPopMatrix();
    GL11.glMatrixMode(5888);
    
    SlickCallable.leaveSafeBlock();
  }
  


  protected void enable()
  {
    if (!valid) {
      throw new RuntimeException("Attempt to use a destroy()ed offscreen graphics context.");
    }
    SlickCallable.enterSafeBlock();
    
    GL11.glPushAttrib(1048575);
    GL11.glPushClientAttrib(-1);
    GL11.glMatrixMode(5889);
    GL11.glPushMatrix();
    GL11.glMatrixMode(5888);
    GL11.glPushMatrix();
    
    bind();
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
    
    IntBuffer buffer = BufferUtils.createIntBuffer(1);
    buffer.put(FBO);
    buffer.flip();
    
    EXTFramebufferObject.glDeleteFramebuffersEXT(buffer);
    valid = false;
  }
  


  public void flush()
  {
    super.flush();
    
    image.flushPixelData();
  }
}
