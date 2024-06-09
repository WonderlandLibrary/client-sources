package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;





public class ImageReadTest
  extends BasicGame
{
  private Image image;
  private Color[] read = new Color[6];
  

  private Graphics g;
  

  public ImageReadTest()
  {
    super("Image Read Test");
  }
  

  public void init(GameContainer container)
    throws SlickException
  {
    image = new Image("testdata/testcard.png");
    read[0] = image.getColor(0, 0);
    read[1] = image.getColor(30, 40);
    read[2] = image.getColor(55, 70);
    read[3] = image.getColor(80, 90);
  }
  


  public void render(GameContainer container, Graphics g)
  {
    this.g = g;
    
    image.draw(100.0F, 100.0F);
    g.setColor(Color.white);
    g.drawString("Move mouse over test image", 200.0F, 20.0F);
    g.setColor(read[0]);
    g.drawString(read[0].toString(), 100.0F, 300.0F);
    g.setColor(read[1]);
    g.drawString(read[1].toString(), 150.0F, 320.0F);
    g.setColor(read[2]);
    g.drawString(read[2].toString(), 200.0F, 340.0F);
    g.setColor(read[3]);
    g.drawString(read[3].toString(), 250.0F, 360.0F);
    if (read[4] != null) {
      g.setColor(read[4]);
      g.drawString("On image: " + read[4].toString(), 100.0F, 250.0F);
    }
    if (read[5] != null) {
      g.setColor(Color.white);
      g.drawString("On screen: " + read[5].toString(), 100.0F, 270.0F);
    }
  }
  


  public void update(GameContainer container, int delta)
  {
    int mx = container.getInput().getMouseX();
    int my = container.getInput().getMouseY();
    
    if ((mx >= 100) && (my >= 100) && (mx < 200) && (my < 200)) {
      read[4] = image.getColor(mx - 100, my - 100);
    } else {
      read[4] = Color.black;
    }
    
    read[5] = g.getPixel(mx, my);
  }
  



  public static void main(String[] argv)
  {
    try
    {
      AppGameContainer container = new AppGameContainer(new ImageReadTest());
      container.setDisplayMode(800, 600, false);
      container.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }
}
