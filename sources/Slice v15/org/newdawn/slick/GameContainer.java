package org.newdawn.slick;

import java.io.IOException;
import java.util.Properties;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Cursor;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.Drawable;
import org.lwjgl.opengl.Pbuffer;
import org.lwjgl.opengl.PixelFormat;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.opengl.CursorLoader;
import org.newdawn.slick.opengl.ImageData;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;







public abstract class GameContainer
  implements GUIContext
{
  protected static SGL GL = ;
  

  protected static Drawable SHARED_DRAWABLE;
  
  protected long lastFrame;
  
  protected long lastFPS;
  
  protected int recordedFPS;
  
  protected int fps;
  
  protected boolean running = true;
  

  protected int width;
  

  protected int height;
  

  protected Game game;
  
  private Font defaultFont;
  
  private Graphics graphics;
  
  protected Input input;
  
  protected int targetFPS = -1;
  
  private boolean showFPS = true;
  
  protected long minimumLogicInterval = 1L;
  
  protected long storedDelta;
  
  protected long maximumLogicInterval = 0L;
  
  protected Game lastGame;
  
  protected boolean clearEachFrame = true;
  

  protected boolean paused;
  
  protected boolean forceExit = true;
  

  protected boolean vsync;
  

  protected boolean smoothDeltas;
  

  protected int samples;
  

  protected boolean supportsMultiSample;
  

  protected boolean alwaysRender;
  
  protected static boolean stencil;
  

  protected GameContainer(Game game)
  {
    this.game = game;
    lastFrame = getTime();
    
    getBuildVersion();
    Log.checkVerboseLogSetting();
  }
  
  public static void enableStencil() {
    stencil = true;
  }
  




  public void setDefaultFont(Font font)
  {
    if (font != null) {
      defaultFont = font;
    } else {
      Log.warn("Please provide a non null font");
    }
  }
  





  public void setMultiSample(int samples)
  {
    this.samples = samples;
  }
  




  public boolean supportsMultiSample()
  {
    return supportsMultiSample;
  }
  





  public int getSamples()
  {
    return samples;
  }
  





  public void setForceExit(boolean forceExit)
  {
    this.forceExit = forceExit;
  }
  






  public void setSmoothDeltas(boolean smoothDeltas)
  {
    this.smoothDeltas = smoothDeltas;
  }
  




  public boolean isFullscreen()
  {
    return false;
  }
  




  public float getAspectRatio()
  {
    return getWidth() / getHeight();
  }
  





  public void setFullscreen(boolean fullscreen)
    throws SlickException
  {}
  




  public static void enableSharedContext()
    throws SlickException
  {
    try
    {
      SHARED_DRAWABLE = new Pbuffer(64, 64, new PixelFormat(8, 0, 0), null);
    } catch (LWJGLException e) {
      throw new SlickException("Unable to create the pbuffer used for shard context, buffers not supported", e);
    }
  }
  




  public static Drawable getSharedContext()
  {
    return SHARED_DRAWABLE;
  }
  






  public void setClearEachFrame(boolean clear)
  {
    clearEachFrame = clear;
  }
  




  public void reinit()
    throws SlickException
  {}
  



  public void pause()
  {
    setPaused(true);
  }
  



  public void resume()
  {
    setPaused(false);
  }
  




  public boolean isPaused()
  {
    return paused;
  }
  






  public void setPaused(boolean paused)
  {
    this.paused = paused;
  }
  




  public boolean getAlwaysRender()
  {
    return alwaysRender;
  }
  




  public void setAlwaysRender(boolean alwaysRender)
  {
    this.alwaysRender = alwaysRender;
  }
  



  public static int getBuildVersion()
  {
    try
    {
      Properties props = new Properties();
      props.load(ResourceLoader.getResourceAsStream("version"));
      
      int build = Integer.parseInt(props.getProperty("build"));
      Log.info("Slick Build #" + build);
      
      return build;
    } catch (Exception e) {
      Log.error("Unable to determine Slick build number"); }
    return -1;
  }
  





  public Font getDefaultFont()
  {
    return defaultFont;
  }
  




  public boolean isSoundOn()
  {
    return SoundStore.get().soundsOn();
  }
  




  public boolean isMusicOn()
  {
    return SoundStore.get().musicOn();
  }
  




  public void setMusicOn(boolean on)
  {
    SoundStore.get().setMusicOn(on);
  }
  




  public void setSoundOn(boolean on)
  {
    SoundStore.get().setSoundsOn(on);
  }
  



  public float getMusicVolume()
  {
    return SoundStore.get().getMusicVolume();
  }
  



  public float getSoundVolume()
  {
    return SoundStore.get().getSoundVolume();
  }
  



  public void setSoundVolume(float volume)
  {
    SoundStore.get().setSoundVolume(volume);
  }
  



  public void setMusicVolume(float volume)
  {
    SoundStore.get().setMusicVolume(volume);
  }
  





  public abstract int getScreenWidth();
  





  public abstract int getScreenHeight();
  




  public int getWidth()
  {
    return width;
  }
  




  public int getHeight()
  {
    return height;
  }
  







  public abstract void setIcon(String paramString)
    throws SlickException;
  






  public abstract void setIcons(String[] paramArrayOfString)
    throws SlickException;
  






  public long getTime()
  {
    return Sys.getTime() * 1000L / Sys.getTimerResolution();
  }
  




  public void sleep(int milliseconds)
  {
    long target = getTime() + milliseconds;
    while (getTime() < target) {
      try { Thread.sleep(1L);
      }
      catch (Exception localException) {}
    }
  }
  










  public abstract void setMouseCursor(String paramString, int paramInt1, int paramInt2)
    throws SlickException;
  









  public abstract void setMouseCursor(ImageData paramImageData, int paramInt1, int paramInt2)
    throws SlickException;
  









  public abstract void setMouseCursor(Image paramImage, int paramInt1, int paramInt2)
    throws SlickException;
  









  public abstract void setMouseCursor(Cursor paramCursor, int paramInt1, int paramInt2)
    throws SlickException;
  









  public void setAnimatedMouseCursor(String ref, int x, int y, int width, int height, int[] cursorDelays)
    throws SlickException
  {
    try
    {
      Cursor cursor = CursorLoader.get().getAnimatedCursor(ref, x, y, width, height, cursorDelays);
      setMouseCursor(cursor, x, y);
    } catch (IOException e) {
      throw new SlickException("Failed to set mouse cursor", e);
    } catch (LWJGLException e) {
      throw new SlickException("Failed to set mouse cursor", e);
    }
  }
  




  public abstract void setDefaultMouseCursor();
  




  public Input getInput()
  {
    return input;
  }
  




  public int getFPS()
  {
    return recordedFPS;
  }
  





  public abstract void setMouseGrabbed(boolean paramBoolean);
  





  public abstract boolean isMouseGrabbed();
  





  protected int getDelta()
  {
    long time = getTime();
    int delta = (int)(time - lastFrame);
    lastFrame = time;
    
    return delta;
  }
  


  protected void updateFPS()
  {
    if (getTime() - lastFPS > 1000L) {
      lastFPS = getTime();
      recordedFPS = fps;
      fps = 0;
    }
    fps += 1;
  }
  






  public void setMinimumLogicUpdateInterval(int interval)
  {
    minimumLogicInterval = interval;
  }
  






  public void setMaximumLogicUpdateInterval(int interval)
  {
    maximumLogicInterval = interval;
  }
  




  protected void updateAndRender(int delta)
    throws SlickException
  {
    if ((smoothDeltas) && 
      (getFPS() != 0)) {
      delta = 1000 / getFPS();
    }
    

    input.poll(width, height);
    
    Music.poll(delta);
    if (!paused) {
      storedDelta += delta;
      
      if (storedDelta >= minimumLogicInterval) {
        try {
          if (maximumLogicInterval != 0L) {
            long cycles = storedDelta / maximumLogicInterval;
            for (int i = 0; i < cycles; i++) {
              game.update(this, (int)maximumLogicInterval);
            }
            
            int remainder = (int)(storedDelta % maximumLogicInterval);
            if (remainder > minimumLogicInterval) {
              game.update(this, (int)(remainder % maximumLogicInterval));
              storedDelta = 0L;
            } else {
              storedDelta = remainder;
            }
          } else {
            game.update(this, (int)storedDelta);
            storedDelta = 0L;
          }
        }
        catch (Throwable e) {
          Log.error(e);
          throw new SlickException("Game.update() failure - check the game code.");
        }
      }
    } else {
      game.update(this, 0);
    }
    
    if ((hasFocus()) || (getAlwaysRender())) {
      if (clearEachFrame) {
        GL.glClear(16640);
      }
      
      GL.glLoadIdentity();
      
      graphics.resetTransform();
      graphics.resetFont();
      graphics.resetLineWidth();
      graphics.setAntiAlias(false);
      try {
        game.render(this, graphics);
      } catch (Throwable e) {
        Log.error(e);
        throw new SlickException("Game.render() failure - check the game code.");
      }
      graphics.resetTransform();
      
      if (showFPS) {
        defaultFont.drawString(10.0F, 10.0F, "FPS: " + recordedFPS);
      }
      
      GL.flush();
    }
    
    if (targetFPS != -1) {
      Display.sync(targetFPS);
    }
  }
  






  public void setUpdateOnlyWhenVisible(boolean updateOnlyWhenVisible) {}
  





  public boolean isUpdatingOnlyWhenVisible()
  {
    return true;
  }
  


  protected void initGL()
  {
    Log.info("Starting display " + width + "x" + height);
    GL.initDisplay(width, height);
    
    if (input == null) {
      input = new Input(height);
    }
    input.init(height);
    

    if ((game instanceof InputListener)) {
      input.removeListener((InputListener)game);
      input.addListener((InputListener)game);
    }
    
    if (graphics != null) {
      graphics.setDimensions(getWidth(), getHeight());
    }
    lastGame = game;
  }
  



  protected void initSystem()
    throws SlickException
  {
    initGL();
    setMusicVolume(1.0F);
    setSoundVolume(1.0F);
    
    graphics = new Graphics(width, height);
    defaultFont = graphics.getFont();
  }
  


  protected void enterOrtho()
  {
    enterOrtho(width, height);
  }
  




  public void setShowFPS(boolean show)
  {
    showFPS = show;
  }
  




  public boolean isShowingFPS()
  {
    return showFPS;
  }
  




  public void setTargetFrameRate(int fps)
  {
    targetFPS = fps;
  }
  





  public void setVSync(boolean vsync)
  {
    this.vsync = vsync;
    Display.setVSyncEnabled(vsync);
  }
  




  public boolean isVSyncRequested()
  {
    return vsync;
  }
  




  protected boolean running()
  {
    return running;
  }
  




  public void setVerbose(boolean verbose)
  {
    Log.setVerbose(verbose);
  }
  


  public void exit()
  {
    running = false;
  }
  





  public abstract boolean hasFocus();
  





  public Graphics getGraphics()
  {
    return graphics;
  }
  





  protected void enterOrtho(int xsize, int ysize)
  {
    GL.enterOrtho(xsize, ysize);
  }
}
