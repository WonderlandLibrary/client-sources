package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.BigImage;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;






public class BigSpriteSheetTest
  extends BasicGame
{
  private Image original;
  private SpriteSheet bigSheet;
  private boolean oldMethod = true;
  


  public BigSpriteSheetTest()
  {
    super("Big SpriteSheet Test");
  }
  

  public void init(GameContainer container)
    throws SlickException
  {
    original = new BigImage("testdata/bigimage.tga", 2, 256);
    bigSheet = new SpriteSheet(original, 16, 16);
  }
  


  public void render(GameContainer container, Graphics g)
  {
    if (oldMethod) {
      for (int x = 0; x < 43; x++) {
        for (int y = 0; y < 27; y++) {
          bigSheet.getSprite(x, y).draw(10 + x * 18, 50 + y * 18);
        }
      }
    } else {
      bigSheet.startUse();
      for (int x = 0; x < 43; x++) {
        for (int y = 0; y < 27; y++) {
          bigSheet.renderInUse(10 + x * 18, 50 + y * 18, x, y);
        }
      }
      bigSheet.endUse();
    }
    
    g.drawString("Press space to toggle rendering method", 10.0F, 30.0F);
    
    container.getDefaultFont().drawString(10.0F, 100.0F, "TEST");
  }
  



  public static void main(String[] argv)
  {
    try
    {
      AppGameContainer container = new AppGameContainer(new BigSpriteSheetTest());
      container.setDisplayMode(800, 600, false);
      container.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }
  

  public void update(GameContainer container, int delta)
    throws SlickException
  {
    if (container.getInput().isKeyPressed(57)) {
      oldMethod = (!oldMethod);
    }
  }
}
