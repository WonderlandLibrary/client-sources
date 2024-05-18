package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.BigImage;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;













public class BigImageTest
  extends BasicGame
{
  private Image original;
  private Image image;
  private Image imageX;
  private Image imageY;
  private Image sub;
  private Image scaledSub;
  private float x;
  private float y;
  private float ang = 30.0F;
  

  private SpriteSheet bigSheet;
  

  public BigImageTest()
  {
    super("Big Image Test");
  }
  


  public void init(GameContainer container)
    throws SlickException
  {
    original = (this.image = new BigImage("testdata/bigimage.tga", 2, 512));
    sub = image.getSubImage(210, 210, 200, 130);
    scaledSub = sub.getScaledCopy(2.0F);
    image = image.getScaledCopy(0.3F);
    imageX = image.getFlippedCopy(true, false);
    imageY = imageX.getFlippedCopy(true, true);
    
    bigSheet = new SpriteSheet(original, 16, 16);
  }
  


  public void render(GameContainer container, Graphics g)
  {
    original.draw(0.0F, 0.0F, new Color(1.0F, 1.0F, 1.0F, 0.4F));
    
    image.draw(x, y);
    imageX.draw(x + 400.0F, y);
    imageY.draw(x, y + 300.0F);
    scaledSub.draw(x + 300.0F, y + 300.0F);
    
    bigSheet.getSprite(7, 5).draw(50.0F, 10.0F);
    g.setColor(Color.white);
    g.drawRect(50.0F, 10.0F, 64.0F, 64.0F);
    g.rotate(x + 400.0F, y + 165.0F, ang);
    g.drawImage(sub, x + 300.0F, y + 100.0F);
  }
  



  public static void main(String[] argv)
  {
    try
    {
      AppGameContainer container = new AppGameContainer(new BigImageTest());
      container.setDisplayMode(800, 600, false);
      container.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }
  

  public void update(GameContainer container, int delta)
    throws SlickException
  {
    ang += delta * 0.1F;
    
    if (container.getInput().isKeyDown(203)) {
      x -= delta * 0.1F;
    }
    if (container.getInput().isKeyDown(205)) {
      x += delta * 0.1F;
    }
    if (container.getInput().isKeyDown(200)) {
      y -= delta * 0.1F;
    }
    if (container.getInput().isKeyDown(208)) {
      y += delta * 0.1F;
    }
  }
}
