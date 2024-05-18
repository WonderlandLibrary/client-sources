package org.newdawn.slick.tests;

import java.io.IOException;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;















public class DeferredLoadingTest
  extends BasicGame
{
  private Music music;
  private Sound sound;
  private Image image;
  private Font font;
  private DeferredResource nextResource;
  private boolean started;
  
  public DeferredLoadingTest()
  {
    super("Deferred Loading Test");
  }
  

  public void init(GameContainer container)
    throws SlickException
  {
    LoadingList.setDeferredLoading(true);
    
    new Sound("testdata/cbrown01.wav");
    new Sound("testdata/engine.wav");
    sound = new Sound("testdata/restart.ogg");
    new Music("testdata/testloop.ogg");
    music = new Music("testdata/SMB-X.XM");
    
    new Image("testdata/cursor.png");
    new Image("testdata/cursor.tga");
    new Image("testdata/cursor.png");
    new Image("testdata/cursor.png");
    new Image("testdata/dungeontiles.gif");
    new Image("testdata/logo.gif");
    image = new Image("testdata/logo.tga");
    new Image("testdata/logo.png");
    new Image("testdata/rocket.png");
    new Image("testdata/testpack.png");
    
    font = new AngelCodeFont("testdata/demo.fnt", "testdata/demo_00.tga");
  }
  


  public void render(GameContainer container, Graphics g)
  {
    if (nextResource != null) {
      g.drawString("Loading: " + nextResource.getDescription(), 100.0F, 100.0F);
    }
    
    int total = LoadingList.get().getTotalResources();
    int loaded = LoadingList.get().getTotalResources() - LoadingList.get().getRemainingResources();
    
    float bar = loaded / total;
    g.fillRect(100.0F, 150.0F, loaded * 40, 20.0F);
    g.drawRect(100.0F, 150.0F, total * 40, 20.0F);
    
    if (started) {
      image.draw(100.0F, 200.0F);
      font.drawString(100.0F, 500.0F, "LOADING COMPLETE");
    }
  }
  

  public void update(GameContainer container, int delta)
    throws SlickException
  {
    if (nextResource != null) {
      try {
        nextResource.load();
        try {
          Thread.sleep(50L);
        }
        catch (Exception localException) {}
        

        nextResource = null;
      }
      catch (IOException e)
      {
        throw new SlickException("Failed to load: " + nextResource.getDescription(), e);
      }
    }
    


    if (LoadingList.get().getRemainingResources() > 0) {
      nextResource = LoadingList.get().getNext();
    }
    else if (!started) {
      started = true;
      music.loop();
      sound.play();
    }
  }
  




  public static void main(String[] argv)
  {
    try
    {
      AppGameContainer container = new AppGameContainer(new DeferredLoadingTest());
      container.setDisplayMode(800, 600, false);
      container.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }
  
  public void keyPressed(int key, char c) {}
}
