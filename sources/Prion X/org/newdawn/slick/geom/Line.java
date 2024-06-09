package org.newdawn.slick.geom;




public class Line
  extends Shape
{
  private Vector2f start;
  

  private Vector2f end;
  

  private Vector2f vec;
  

  private float lenSquared;
  

  private Vector2f loc = new Vector2f(0.0F, 0.0F);
  
  private Vector2f v = new Vector2f(0.0F, 0.0F);
  
  private Vector2f v2 = new Vector2f(0.0F, 0.0F);
  
  private Vector2f proj = new Vector2f(0.0F, 0.0F);
  

  private Vector2f closest = new Vector2f(0.0F, 0.0F);
  
  private Vector2f other = new Vector2f(0.0F, 0.0F);
  

  private boolean outerEdge = true;
  
  private boolean innerEdge = true;
  











  public Line(float x, float y, boolean inner, boolean outer)
  {
    this(0.0F, 0.0F, x, y);
  }
  







  public Line(float x, float y)
  {
    this(x, y, true, true);
  }
  











  public Line(float x1, float y1, float x2, float y2)
  {
    this(new Vector2f(x1, y1), new Vector2f(x2, y2));
  }
  













  public Line(float x1, float y1, float dx, float dy, boolean dummy)
  {
    this(new Vector2f(x1, y1), new Vector2f(x1 + dx, y1 + dy));
  }
  









  public Line(float[] start, float[] end)
  {
    set(start, end);
  }
  









  public Line(Vector2f start, Vector2f end)
  {
    set(start, end);
  }
  







  public void set(float[] start, float[] end)
  {
    set(start[0], start[1], end[0], end[1]);
  }
  




  public Vector2f getStart()
  {
    return start;
  }
  




  public Vector2f getEnd()
  {
    return end;
  }
  




  public float length()
  {
    return vec.length();
  }
  




  public float lengthSquared()
  {
    return vec.lengthSquared();
  }
  







  public void set(Vector2f start, Vector2f end)
  {
    pointsDirty = true;
    if (this.start == null) {
      this.start = new Vector2f();
    }
    this.start.set(start);
    
    if (this.end == null) {
      this.end = new Vector2f();
    }
    this.end.set(end);
    
    vec = new Vector2f(end);
    vec.sub(start);
    
    lenSquared = vec.lengthSquared();
  }
  











  public void set(float sx, float sy, float ex, float ey)
  {
    pointsDirty = true;
    start.set(sx, sy);
    end.set(ex, ey);
    float dx = ex - sx;
    float dy = ey - sy;
    vec.set(dx, dy);
    
    lenSquared = (dx * dx + dy * dy);
  }
  




  public float getDX()
  {
    return end.getX() - start.getX();
  }
  




  public float getDY()
  {
    return end.getY() - start.getY();
  }
  


  public float getX()
  {
    return getX1();
  }
  


  public float getY()
  {
    return getY1();
  }
  




  public float getX1()
  {
    return start.getX();
  }
  




  public float getY1()
  {
    return start.getY();
  }
  




  public float getX2()
  {
    return end.getX();
  }
  




  public float getY2()
  {
    return end.getY();
  }
  






  public float distance(Vector2f point)
  {
    return (float)Math.sqrt(distanceSquared(point));
  }
  






  public boolean on(Vector2f point)
  {
    getClosestPoint(point, closest);
    
    return point.equals(closest);
  }
  






  public float distanceSquared(Vector2f point)
  {
    getClosestPoint(point, closest);
    closest.sub(point);
    
    float result = closest.lengthSquared();
    
    return result;
  }
  







  public void getClosestPoint(Vector2f point, Vector2f result)
  {
    loc.set(point);
    loc.sub(start);
    
    float projDistance = vec.dot(loc);
    
    projDistance /= vec.lengthSquared();
    
    if (projDistance < 0.0F) {
      result.set(start);
      return;
    }
    if (projDistance > 1.0F) {
      result.set(end);
      return;
    }
    
    x = (start.getX() + projDistance * vec.getX());
    y = (start.getY() + projDistance * vec.getY());
  }
  


  public String toString()
  {
    return "[Line " + start + "," + end + "]";
  }
  






  public Vector2f intersect(Line other)
  {
    return intersect(other, false);
  }
  








  public Vector2f intersect(Line other, boolean limit)
  {
    Vector2f temp = new Vector2f();
    
    if (!intersect(other, limit, temp)) {
      return null;
    }
    
    return temp;
  }
  










  public boolean intersect(Line other, boolean limit, Vector2f result)
  {
    float dx1 = end.getX() - start.getX();
    float dx2 = end.getX() - start.getX();
    float dy1 = end.getY() - start.getY();
    float dy2 = end.getY() - start.getY();
    float denom = dy2 * dx1 - dx2 * dy1;
    
    if (denom == 0.0F) {
      return false;
    }
    
    float ua = dx2 * (start.getY() - start.getY()) - 
      dy2 * (start.getX() - start.getX());
    ua /= denom;
    float ub = dx1 * (start.getY() - start.getY()) - 
      dy1 * (start.getX() - start.getX());
    ub /= denom;
    
    if ((limit) && ((ua < 0.0F) || (ua > 1.0F) || (ub < 0.0F) || (ub > 1.0F))) {
      return false;
    }
    
    float u = ua;
    
    float ix = start.getX() + u * (end.getX() - start.getX());
    float iy = start.getY() + u * (end.getY() - start.getY());
    
    result.set(ix, iy);
    return true;
  }
  


  protected void createPoints()
  {
    points = new float[4];
    points[0] = getX1();
    points[1] = getY1();
    points[2] = getX2();
    points[3] = getY2();
  }
  


  public Shape transform(Transform transform)
  {
    float[] temp = new float[4];
    createPoints();
    transform.transform(points, 0, temp, 0, 2);
    
    return new Line(temp[0], temp[1], temp[2], temp[3]);
  }
  


  public boolean closed()
  {
    return false;
  }
  



  public boolean intersects(Shape shape)
  {
    if ((shape instanceof Circle))
    {
      return shape.intersects(this);
    }
    return super.intersects(shape);
  }
}
