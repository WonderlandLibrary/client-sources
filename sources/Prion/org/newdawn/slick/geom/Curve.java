package org.newdawn.slick.geom;




public class Curve
  extends Shape
{
  private Vector2f p1;
  


  private Vector2f c1;
  


  private Vector2f c2;
  


  private Vector2f p2;
  


  private int segments;
  


  public Curve(Vector2f p1, Vector2f c1, Vector2f c2, Vector2f p2)
  {
    this(p1, c1, c2, p2, 20);
  }
  








  public Curve(Vector2f p1, Vector2f c1, Vector2f c2, Vector2f p2, int segments)
  {
    this.p1 = new Vector2f(p1);
    this.c1 = new Vector2f(c1);
    this.c2 = new Vector2f(c2);
    this.p2 = new Vector2f(p2);
    
    this.segments = segments;
    pointsDirty = true;
  }
  





  public Vector2f pointAt(float t)
  {
    float a = 1.0F - t;
    float b = t;
    
    float f1 = a * a * a;
    float f2 = 3.0F * a * a * b;
    float f3 = 3.0F * a * b * b;
    float f4 = b * b * b;
    
    float nx = p1.x * f1 + c1.x * f2 + c2.x * f3 + p2.x * f4;
    float ny = p1.y * f1 + c1.y * f2 + c2.y * f3 + p2.y * f4;
    
    return new Vector2f(nx, ny);
  }
  


  protected void createPoints()
  {
    float step = 1.0F / segments;
    points = new float[(segments + 1) * 2];
    for (int i = 0; i < segments + 1; i++) {
      float t = i * step;
      
      Vector2f p = pointAt(t);
      points[(i * 2)] = x;
      points[(i * 2 + 1)] = y;
    }
  }
  


  public Shape transform(Transform transform)
  {
    float[] pts = new float[8];
    float[] dest = new float[8];
    pts[0] = p1.x;pts[1] = p1.y;
    pts[2] = c1.x;pts[3] = c1.y;
    pts[4] = c2.x;pts[5] = c2.y;
    pts[6] = p2.x;pts[7] = p2.y;
    transform.transform(pts, 0, dest, 0, 4);
    
    return new Curve(new Vector2f(dest[0], dest[1]), new Vector2f(dest[2], dest[3]), 
      new Vector2f(dest[4], dest[5]), new Vector2f(dest[6], dest[7]));
  }
  




  public boolean closed()
  {
    return false;
  }
}
