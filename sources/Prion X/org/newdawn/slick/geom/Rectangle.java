package org.newdawn.slick.geom;





public class Rectangle
  extends Shape
{
  protected float width;
  



  protected float height;
  




  public Rectangle(float x, float y, float width, float height)
  {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    maxX = (x + width);
    maxY = (y + height);
    checkPoints();
  }
  






  public boolean contains(float xp, float yp)
  {
    if (xp <= getX()) {
      return false;
    }
    if (yp <= getY()) {
      return false;
    }
    if (xp >= maxX) {
      return false;
    }
    if (yp >= maxY) {
      return false;
    }
    
    return true;
  }
  




  public void setBounds(Rectangle other)
  {
    setBounds(other.getX(), other.getY(), other.getWidth(), other.getHeight());
  }
  







  public void setBounds(float x, float y, float width, float height)
  {
    setX(x);
    setY(y);
    setSize(width, height);
  }
  





  public void setSize(float width, float height)
  {
    setWidth(width);
    setHeight(height);
  }
  





  public float getWidth()
  {
    return width;
  }
  




  public float getHeight()
  {
    return height;
  }
  






  public void grow(float h, float v)
  {
    setX(getX() - h);
    setY(getY() - v);
    setWidth(getWidth() + h * 2.0F);
    setHeight(getHeight() + v * 2.0F);
  }
  





  public void scaleGrow(float h, float v)
  {
    grow(getWidth() * (h - 1.0F), getHeight() * (v - 1.0F));
  }
  




  public void setWidth(float width)
  {
    if (width != this.width) {
      pointsDirty = true;
      this.width = width;
      maxX = (x + width);
    }
  }
  




  public void setHeight(float height)
  {
    if (height != this.height) {
      pointsDirty = true;
      this.height = height;
      maxY = (y + height);
    }
  }
  





  public boolean intersects(Shape shape)
  {
    if ((shape instanceof Rectangle)) {
      Rectangle other = (Rectangle)shape;
      if ((x > x + width) || (x + width < x)) {
        return false;
      }
      if ((y > y + height) || (y + height < y)) {
        return false;
      }
      return true;
    }
    if ((shape instanceof Circle)) {
      return intersects((Circle)shape);
    }
    
    return super.intersects(shape);
  }
  
  protected void createPoints()
  {
    float useWidth = width;
    float useHeight = height;
    points = new float[8];
    
    points[0] = x;
    points[1] = y;
    
    points[2] = (x + useWidth);
    points[3] = y;
    
    points[4] = (x + useWidth);
    points[5] = (y + useHeight);
    
    points[6] = x;
    points[7] = (y + useHeight);
    
    maxX = points[2];
    maxY = points[5];
    minX = points[0];
    minY = points[1];
    
    findCenter();
    calculateRadius();
  }
  





  private boolean intersects(Circle other)
  {
    return other.intersects(this);
  }
  


  public String toString()
  {
    return "[Rectangle " + width + "x" + height + "]";
  }
  
















  public static boolean contains(float xp, float yp, float xr, float yr, float widthr, float heightr)
  {
    return (xp >= xr) && (yp >= yr) && (xp <= xr + widthr) && (
      yp <= yr + heightr);
  }
  






  public Shape transform(Transform transform)
  {
    checkPoints();
    
    Polygon resultPolygon = new Polygon();
    
    float[] result = new float[points.length];
    transform.transform(points, 0, result, 0, points.length / 2);
    points = result;
    resultPolygon.findCenter();
    resultPolygon.checkPoints();
    
    return resultPolygon;
  }
}
