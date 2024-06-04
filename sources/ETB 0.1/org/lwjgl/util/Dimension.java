package org.lwjgl.util;

import java.io.Serializable;













































public final class Dimension
  implements Serializable, ReadableDimension, WritableDimension
{
  static final long serialVersionUID = 1L;
  private int width;
  private int height;
  
  public Dimension() {}
  
  public Dimension(int w, int h)
  {
    width = w;
    height = h;
  }
  


  public Dimension(ReadableDimension d)
  {
    setSize(d);
  }
  
  public void setSize(int w, int h) {
    width = w;
    height = h;
  }
  
  public void setSize(ReadableDimension d) {
    width = d.getWidth();
    height = d.getHeight();
  }
  


  public void getSize(WritableDimension dest)
  {
    dest.setSize(this);
  }
  


  public boolean equals(Object obj)
  {
    if ((obj instanceof ReadableDimension)) {
      ReadableDimension d = (ReadableDimension)obj;
      return (width == d.getWidth()) && (height == d.getHeight());
    }
    return false;
  }
  




  public int hashCode()
  {
    int sum = width + height;
    return sum * (sum + 1) / 2 + width;
  }
  










  public String toString()
  {
    return getClass().getName() + "[width=" + width + ",height=" + height + "]";
  }
  



  public int getHeight()
  {
    return height;
  }
  



  public void setHeight(int height)
  {
    this.height = height;
  }
  



  public int getWidth()
  {
    return width;
  }
  



  public void setWidth(int width)
  {
    this.width = width;
  }
}
