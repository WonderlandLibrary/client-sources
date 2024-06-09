package org.newdawn.slick.tests;

import java.io.PrintStream;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;













public class ImageTest
  extends BasicGame
{
  private Image tga;
  private Image scaleMe;
  private Image scaled;
  private Image gif;
  private Image image;
  private Image subImage;
  private Image rotImage;
  private float rot;
  public static boolean exitMe = true;
  


  public ImageTest()
  {
    super("Image Test");
  }
  

  public void init(GameContainer container)
    throws SlickException
  {
    image = (this.tga = new Image("testdata/logo.png"));
    rotImage = new Image("testdata/logo.png");
    rotImage = rotImage.getScaledCopy(rotImage.getWidth() / 2, rotImage.getHeight() / 2);
    

    scaleMe = new Image("testdata/logo.tga", true, 2);
    gif = new Image("testdata/logo.gif");
    gif.destroy();
    gif = new Image("testdata/logo.gif");
    scaled = gif.getScaledCopy(120, 120);
    subImage = image.getSubImage(200, 0, 70, 260);
    rot = 0.0F;
    
    if (exitMe) {
      container.exit();
    }
    
    Image test = tga.getSubImage(50, 50, 50, 50);
    System.out.println(test.getColor(50, 50));
  }
  


  public void render(GameContainer container, Graphics g)
  {
    g.drawRect(0.0F, 0.0F, image.getWidth(), image.getHeight());
    image.draw(0.0F, 0.0F);
    image.draw(500.0F, 0.0F, 200.0F, 100.0F);
    scaleMe.draw(500.0F, 100.0F, 200.0F, 100.0F);
    scaled.draw(400.0F, 500.0F);
    Image flipped = scaled.getFlippedCopy(true, false);
    flipped.draw(520.0F, 500.0F);
    Image flipped2 = flipped.getFlippedCopy(false, true);
    flipped2.draw(520.0F, 380.0F);
    Image flipped3 = flipped2.getFlippedCopy(true, false);
    flipped3.draw(400.0F, 380.0F);
    
    for (int i = 0; i < 3; i++) {
      subImage.draw(200 + i * 30, 300.0F);
    }
    
    g.translate(500.0F, 200.0F);
    g.rotate(50.0F, 50.0F, rot);
    g.scale(0.3F, 0.3F);
    image.draw();
    g.resetTransform();
    
    rotImage.setRotation(rot);
    rotImage.draw(100.0F, 200.0F);
  }
  


  public void update(GameContainer container, int delta)
  {
    rot += delta * 0.1F;
    if (rot > 360.0F) {
      rot -= 360.0F;
    }
  }
  




  public static void main(String[] argv)
  {
    boolean sharedContextTest = false;
    try
    {
      exitMe = false;
      if (sharedContextTest) {
        GameContainer.enableSharedContext();
        exitMe = true;
      }
      
      AppGameContainer container = new AppGameContainer(new ImageTest());
      container.setForceExit(!sharedContextTest);
      container.setDisplayMode(800, 600, false);
      container.start();
      
      if (sharedContextTest) {
        System.out.println("Exit first instance");
        exitMe = false;
        container = new AppGameContainer(new ImageTest());
        container.setDisplayMode(800, 600, false);
        container.start();
      }
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }
  



  public void keyPressed(int key, char c)
  {
    if (key == 57) {
      if (image == gif) {
        image = tga;
      } else {
        image = gif;
      }
    }
  }
}
