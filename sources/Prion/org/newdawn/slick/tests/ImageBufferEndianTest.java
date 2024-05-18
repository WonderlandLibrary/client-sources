package org.newdawn.slick.tests;

import java.nio.ByteOrder;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.ImageBuffer;
import org.newdawn.slick.SlickException;











public class ImageBufferEndianTest
  extends BasicGame
{
  private ImageBuffer redImageBuffer;
  private ImageBuffer blueImageBuffer;
  private Image fromRed;
  private Image fromBlue;
  private String endian;
  
  public ImageBufferEndianTest()
  {
    super("ImageBuffer Endian Test");
  }
  



  public static void main(String[] args)
  {
    try
    {
      AppGameContainer container = new AppGameContainer(new ImageBufferEndianTest());
      container.setDisplayMode(800, 600, false);
      container.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }
  


  public void render(GameContainer container, Graphics g)
    throws SlickException
  {
    g.setColor(Color.white);
    g.drawString("Endianness is " + endian, 10.0F, 100.0F);
    
    g.drawString("Image below should be red", 10.0F, 200.0F);
    g.drawImage(fromRed, 10.0F, 220.0F);
    g.drawString("Image below should be blue", 410.0F, 200.0F);
    g.drawImage(fromBlue, 410.0F, 220.0F);
  }
  



  public void init(GameContainer container)
    throws SlickException
  {
    if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) {
      endian = "Big endian";
    } else if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
      endian = "Little endian";
    } else {
      endian = "no idea";
    }
    redImageBuffer = new ImageBuffer(100, 100);
    fillImageBufferWithColor(redImageBuffer, Color.red, 100, 100);
    
    blueImageBuffer = new ImageBuffer(100, 100);
    fillImageBufferWithColor(blueImageBuffer, Color.blue, 100, 100);
    
    fromRed = redImageBuffer.getImage();
    fromBlue = blueImageBuffer.getImage();
  }
  







  private void fillImageBufferWithColor(ImageBuffer buffer, Color c, int width, int height)
  {
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        buffer.setRGBA(x, y, c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
      }
    }
  }
  
  public void update(GameContainer container, int delta)
    throws SlickException
  {}
}
