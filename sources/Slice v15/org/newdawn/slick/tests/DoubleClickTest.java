package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;







public class DoubleClickTest
  extends BasicGame
{
  public DoubleClickTest()
  {
    super("Double Click Test");
  }
  

  private String message = "Click or Double Click";
  


  public void init(GameContainer container)
    throws SlickException
  {}
  


  public void update(GameContainer container, int delta)
    throws SlickException
  {}
  

  public void render(GameContainer container, Graphics g)
    throws SlickException
  {
    g.drawString(message, 100.0F, 100.0F);
  }
  



  public static void main(String[] argv)
  {
    try
    {
      AppGameContainer container = new AppGameContainer(new DoubleClickTest());
      container.setDisplayMode(800, 600, false);
      container.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }
  


  public void mouseClicked(int button, int x, int y, int clickCount)
  {
    if (clickCount == 1) {
      message = ("Single Click: " + button + " " + x + "," + y);
    }
    if (clickCount == 2) {
      message = ("Double Click: " + button + " " + x + "," + y);
    }
  }
}
