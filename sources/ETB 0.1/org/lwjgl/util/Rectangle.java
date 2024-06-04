package org.lwjgl.util;

import java.io.Serializable;










































public final class Rectangle
  implements ReadableRectangle, WritableRectangle, Serializable
{
  static final long serialVersionUID = 1L;
  private int x;
  private int y;
  private int width;
  private int height;
  
  public Rectangle() {}
  
  public Rectangle(int x, int y, int w, int h)
  {
    this.x = x;
    this.y = y;
    width = w;
    height = h;
  }
  

  public Rectangle(ReadablePoint p, ReadableDimension d)
  {
    x = p.getX();
    y = p.getY();
    width = d.getWidth();
    height = d.getHeight();
  }
  

  public Rectangle(ReadableRectangle r)
  {
    x = r.getX();
    y = r.getY();
    width = r.getWidth();
    height = r.getHeight();
  }
  
  public void setLocation(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  public void setLocation(ReadablePoint p) {
    x = p.getX();
    y = p.getY();
  }
  
  public void setSize(int w, int h) {
    width = w;
    height = h;
  }
  
  public void setSize(ReadableDimension d) {
    width = d.getWidth();
    height = d.getHeight();
  }
  
  public void setBounds(int x, int y, int w, int h) {
    this.x = x;
    this.y = y;
    width = w;
    height = h;
  }
  
  public void setBounds(ReadablePoint p, ReadableDimension d) {
    x = p.getX();
    y = p.getY();
    width = d.getWidth();
    height = d.getHeight();
  }
  
  public void setBounds(ReadableRectangle r) {
    x = r.getX();
    y = r.getY();
    width = r.getWidth();
    height = r.getHeight();
  }
  


  public void getBounds(WritableRectangle dest)
  {
    dest.setBounds(x, y, width, height);
  }
  


  public void getLocation(WritablePoint dest)
  {
    dest.setLocation(x, y);
  }
  


  public void getSize(WritableDimension dest)
  {
    dest.setSize(width, height);
  }
  




  public void translate(int x, int y)
  {
    this.x += x;
    this.y += y;
  }
  



  public void translate(ReadablePoint point)
  {
    x += point.getX();
    y += point.getY();
  }
  



  public void untranslate(ReadablePoint point)
  {
    x -= point.getX();
    y -= point.getY();
  }
  








  public boolean contains(ReadablePoint p)
  {
    return contains(p.getX(), p.getY());
  }
  










  public boolean contains(int X, int Y)
  {
    int w = width;
    int h = height;
    if ((w | h) < 0)
    {
      return false;
    }
    
    int x = this.x;
    int y = this.y;
    if ((X < x) || (Y < y)) {
      return false;
    }
    w += x;
    h += y;
    
    return ((w < x) || (w > X)) && ((h < y) || (h > Y));
  }
  







  public boolean contains(ReadableRectangle r)
  {
    return contains(r.getX(), r.getY(), r.getWidth(), r.getHeight());
  }
  













  public boolean contains(int X, int Y, int W, int H)
  {
    int w = width;
    int h = height;
    if ((w | h | W | H) < 0)
    {
      return false;
    }
    
    int x = this.x;
    int y = this.y;
    if ((X < x) || (Y < y)) {
      return false;
    }
    w += x;
    W += X;
    if (W <= X)
    {



      if ((w >= x) || (W > w)) {
        return false;
      }
      

    }
    else if ((w >= x) && (W > w)) {
      return false;
    }
    h += y;
    H += Y;
    if (H <= Y) {
      if ((h >= y) || (H > h)) {
        return false;
      }
    } else if ((h >= y) && (H > h)) {
      return false;
    }
    return true;
  }
  









  public boolean intersects(ReadableRectangle r)
  {
    int tw = width;
    int th = height;
    int rw = r.getWidth();
    int rh = r.getHeight();
    if ((rw <= 0) || (rh <= 0) || (tw <= 0) || (th <= 0)) {
      return false;
    }
    int tx = x;
    int ty = y;
    int rx = r.getX();
    int ry = r.getY();
    rw += rx;
    rh += ry;
    tw += tx;
    th += ty;
    
    return ((rw < rx) || (rw > tx)) && ((rh < ry) || (rh > ty)) && ((tw < tx) || (tw > rx)) && ((th < ty) || (th > ry));
  }
  












  public Rectangle intersection(ReadableRectangle r, Rectangle dest)
  {
    int tx1 = x;
    int ty1 = y;
    int rx1 = r.getX();
    int ry1 = r.getY();
    long tx2 = tx1;
    tx2 += width;
    long ty2 = ty1;
    ty2 += height;
    long rx2 = rx1;
    rx2 += r.getWidth();
    long ry2 = ry1;
    ry2 += r.getHeight();
    if (tx1 < rx1)
      tx1 = rx1;
    if (ty1 < ry1)
      ty1 = ry1;
    if (tx2 > rx2)
      tx2 = rx2;
    if (ty2 > ry2)
      ty2 = ry2;
    tx2 -= tx1;
    ty2 -= ty1;
    


    if (tx2 < -2147483648L)
      tx2 = -2147483648L;
    if (ty2 < -2147483648L)
      ty2 = -2147483648L;
    if (dest == null) {
      dest = new Rectangle(tx1, ty1, (int)tx2, (int)ty2);
    } else
      dest.setBounds(tx1, ty1, (int)tx2, (int)ty2);
    return dest;
  }
  










  public WritableRectangle union(ReadableRectangle r, WritableRectangle dest)
  {
    int x1 = Math.min(x, r.getX());
    int x2 = Math.max(x + width, r.getX() + r.getWidth());
    int y1 = Math.min(y, r.getY());
    int y2 = Math.max(y + height, r.getY() + r.getHeight());
    dest.setBounds(x1, y1, x2 - x1, y2 - y1);
    return dest;
  }
  

















  public void add(int newx, int newy)
  {
    int x1 = Math.min(x, newx);
    int x2 = Math.max(x + width, newx);
    int y1 = Math.min(y, newy);
    int y2 = Math.max(y + height, newy);
    x = x1;
    y = y1;
    width = (x2 - x1);
    height = (y2 - y1);
  }
  

















  public void add(ReadablePoint pt)
  {
    add(pt.getX(), pt.getY());
  }
  





  public void add(ReadableRectangle r)
  {
    int x1 = Math.min(x, r.getX());
    int x2 = Math.max(x + width, r.getX() + r.getWidth());
    int y1 = Math.min(y, r.getY());
    int y2 = Math.max(y + height, r.getY() + r.getHeight());
    x = x1;
    y = y1;
    width = (x2 - x1);
    height = (y2 - y1);
  }
  






















  public void grow(int h, int v)
  {
    x -= h;
    y -= v;
    width += h * 2;
    height += v * 2;
  }
  






  public boolean isEmpty()
  {
    return (width <= 0) || (height <= 0);
  }
  









  public boolean equals(Object obj)
  {
    if ((obj instanceof Rectangle)) {
      Rectangle r = (Rectangle)obj;
      return (x == x) && (y == y) && (width == width) && (height == height);
    }
    return super.equals(obj);
  }
  



  public String toString()
  {
    return getClass().getName() + "[x=" + x + ",y=" + y + ",width=" + width + ",height=" + height + "]";
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
  



  public int getX()
  {
    return x;
  }
  



  public void setX(int x)
  {
    this.x = x;
  }
  



  public int getY()
  {
    return y;
  }
  



  public void setY(int y)
  {
    this.y = y;
  }
}
