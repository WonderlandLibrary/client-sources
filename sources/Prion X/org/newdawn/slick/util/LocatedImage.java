package org.newdawn.slick.util;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;










public class LocatedImage
{
  private Image image;
  private int x;
  private int y;
  private Color filter = Color.white;
  


  private float width;
  


  private float height;
  


  public LocatedImage(Image image, int x, int y)
  {
    this.image = image;
    this.x = x;
    this.y = y;
    width = image.getWidth();
    height = image.getHeight();
  }
  




  public float getHeight()
  {
    return height;
  }
  




  public float getWidth()
  {
    return width;
  }
  




  public void setHeight(float height)
  {
    this.height = height;
  }
  




  public void setWidth(float width)
  {
    this.width = width;
  }
  




  public void setColor(Color c)
  {
    filter = c;
  }
  




  public Color getColor()
  {
    return filter;
  }
  




  public void setX(int x)
  {
    this.x = x;
  }
  




  public void setY(int y)
  {
    this.y = y;
  }
  




  public int getX()
  {
    return x;
  }
  




  public int getY()
  {
    return y;
  }
  


  public void draw()
  {
    image.draw(x, y, width, height, filter);
  }
}
