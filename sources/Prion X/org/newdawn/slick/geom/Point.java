package org.newdawn.slick.geom;














public class Point
  extends Shape
{
  public Point(float x, float y)
  {
    this.x = x;
    this.y = y;
    checkPoints();
  }
  



  public Shape transform(Transform transform)
  {
    float[] result = new float[points.length];
    transform.transform(points, 0, result, 0, points.length / 2);
    
    return new Point(points[0], points[1]);
  }
  



  protected void createPoints()
  {
    points = new float[2];
    points[0] = getX();
    points[1] = getY();
    
    maxX = x;
    maxY = y;
    minX = x;
    minY = y;
    
    findCenter();
    calculateRadius();
  }
  



  protected void findCenter()
  {
    center = new float[2];
    center[0] = points[0];
    center[1] = points[1];
  }
  



  protected void calculateRadius()
  {
    boundingCircleRadius = 0.0F;
  }
}
