package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;










public class ImageCornerTest
  extends BasicGame
{
  private Image image;
  private Image[] images;
  private int width;
  private int height;
  
  public ImageCornerTest()
  {
    super("Image Corner Test");
  }
  

  public void init(GameContainer container)
    throws SlickException
  {
    image = new Image("testdata/logo.png");
    
    width = (image.getWidth() / 3);
    height = (image.getHeight() / 3);
    
    images = new Image[] {
      image.getSubImage(0, 0, width, height), image.getSubImage(width, 0, width, height), image.getSubImage(width * 2, 0, width, height), 
      image.getSubImage(0, height, width, height), image.getSubImage(width, height, width, height), image.getSubImage(width * 2, height, width, height), 
      image.getSubImage(0, height * 2, width, height), image.getSubImage(width, height * 2, width, height), image.getSubImage(width * 2, height * 2, width, height) };
    

    images[0].setColor(2, 0.0F, 1.0F, 1.0F, 1.0F);
    images[1].setColor(3, 0.0F, 1.0F, 1.0F, 1.0F);
    images[1].setColor(2, 0.0F, 1.0F, 1.0F, 1.0F);
    images[2].setColor(3, 0.0F, 1.0F, 1.0F, 1.0F);
    images[3].setColor(1, 0.0F, 1.0F, 1.0F, 1.0F);
    images[3].setColor(2, 0.0F, 1.0F, 1.0F, 1.0F);
    
    images[4].setColor(1, 0.0F, 1.0F, 1.0F, 1.0F);
    images[4].setColor(0, 0.0F, 1.0F, 1.0F, 1.0F);
    images[4].setColor(3, 0.0F, 1.0F, 1.0F, 1.0F);
    images[4].setColor(2, 0.0F, 1.0F, 1.0F, 1.0F);
    images[5].setColor(0, 0.0F, 1.0F, 1.0F, 1.0F);
    images[5].setColor(3, 0.0F, 1.0F, 1.0F, 1.0F);
    
    images[6].setColor(1, 0.0F, 1.0F, 1.0F, 1.0F);
    images[7].setColor(1, 0.0F, 1.0F, 1.0F, 1.0F);
    images[7].setColor(0, 0.0F, 1.0F, 1.0F, 1.0F);
    images[8].setColor(0, 0.0F, 1.0F, 1.0F, 1.0F);
  }
  


  public void render(GameContainer container, Graphics g)
  {
    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        images[(x + y * 3)].draw(100 + x * width, 100 + y * height);
      }
    }
  }
  




  public static void main(String[] argv)
  {
    boolean sharedContextTest = false;
    try
    {
      AppGameContainer container = new AppGameContainer(new ImageCornerTest());
      container.setDisplayMode(800, 600, false);
      container.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }
  
  public void update(GameContainer container, int delta)
    throws SlickException
  {}
}
