package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.SpriteSheetFont;
import org.newdawn.slick.util.Log;

public class SpriteSheetFontTest extends BasicGame
{
  private Font font;
  private static AppGameContainer container;
  
  public SpriteSheetFontTest()
  {
    super("SpriteSheetFont Test");
  }
  

  public void init(GameContainer container)
    throws SlickException
  {
    SpriteSheet sheet = new SpriteSheet("testdata/spriteSheetFont.png", 32, 32);
    font = new SpriteSheetFont(sheet, ' ');
  }
  


  public void render(GameContainer container, Graphics g)
  {
    g.setBackground(Color.gray);
    font.drawString(80.0F, 5.0F, "A FONT EXAMPLE", Color.red);
    font.drawString(100.0F, 50.0F, "A MORE COMPLETE LINE");
  }
  


  public void update(GameContainer container, int delta)
    throws SlickException
  {}
  


  public void keyPressed(int key, char c)
  {
    if (key == 1) {
      System.exit(0);
    }
    if (key == 57) {
      try {
        container.setDisplayMode(640, 480, false);
      } catch (SlickException e) {
        Log.error(e);
      }
    }
  }
  








  public static void main(String[] argv)
  {
    try
    {
      container = new AppGameContainer(new SpriteSheetFontTest());
      container.setDisplayMode(800, 600, false);
      container.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }
}
