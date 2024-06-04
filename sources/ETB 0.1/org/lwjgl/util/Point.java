package org.lwjgl.util;

import java.io.Serializable;













































public final class Point
  implements ReadablePoint, WritablePoint, Serializable
{
  static final long serialVersionUID = 1L;
  private int x;
  private int y;
  
  public Point() {}
  
  public Point(int x, int y)
  {
    setLocation(x, y);
  }
  


  public Point(ReadablePoint p)
  {
    setLocation(p);
  }
  
  public void setLocation(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  public void setLocation(ReadablePoint p) {
    x = p.getX();
    y = p.getY();
  }
  
  public void setX(int x) {
    this.x = x;
  }
  
  public void setY(int y) {
    this.y = y;
  }
  




  public void translate(int dx, int dy)
  {
    x += dx;
    y += dy;
  }
  



  public void translate(ReadablePoint p)
  {
    x += p.getX();
    y += p.getY();
  }
  



  public void untranslate(ReadablePoint p)
  {
    x -= p.getX();
    y -= p.getY();
  }
  










  public boolean equals(Object obj)
  {
    if ((obj instanceof Point)) {
      Point pt = (Point)obj;
      return (x == x) && (y == y);
    }
    return super.equals(obj);
  }
  








  public String toString()
  {
    return getClass().getName() + "[x=" + x + ",y=" + y + "]";
  }
  




  public int hashCode()
  {
    int sum = x + y;
    return sum * (sum + 1) / 2 + x;
  }
  
  public int getX() {
    return x;
  }
  
  public int getY() {
    return y;
  }
  
  public void getLocation(WritablePoint dest) {
    dest.setLocation(x, y);
  }
}
