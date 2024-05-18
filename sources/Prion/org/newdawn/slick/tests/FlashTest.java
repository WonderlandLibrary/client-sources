package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;










public class FlashTest
  extends BasicGame
{
  private Image image;
  private boolean flash;
  private GameContainer container;
  
  public FlashTest()
  {
    super("Flash Test");
  }
  

  public void init(GameContainer container)
    throws SlickException
  {
    this.container = container;
    
    image = new Image("testdata/logo.tga");
  }
  


  public void render(GameContainer container, Graphics g)
  {
    g.drawString("Press space to toggle", 10.0F, 50.0F);
    if (flash) {
      image.draw(100.0F, 100.0F);
    } else {
      image.drawFlash(100.0F, 100.0F, image.getWidth(), image.getHeight(), new Color(1.0F, 0.0F, 1.0F, 1.0F));
    }
  }
  




  public void update(GameContainer container, int delta) {}
  



  public static void main(String[] argv)
  {
    try
    {
      AppGameContainer container = new AppGameContainer(new FlashTest());
      container.setDisplayMode(800, 600, false);
      container.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }
  


  public void keyPressed(int key, char c)
  {
    if (key == 57) {
      flash = (!flash);
    }
    if (key == 1) {
      container.exit();
    }
  }
}
