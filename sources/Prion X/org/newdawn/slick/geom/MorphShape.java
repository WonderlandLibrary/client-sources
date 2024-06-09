package org.newdawn.slick.geom;

import java.util.ArrayList;





public class MorphShape
  extends Shape
{
  private ArrayList shapes = new ArrayList();
  

  private float offset;
  

  private Shape current;
  

  private Shape next;
  


  public MorphShape(Shape base)
  {
    shapes.add(base);
    float[] copy = points;
    points = new float[copy.length];
    
    current = base;
    next = base;
  }
  




  public void addShape(Shape shape)
  {
    if (points.length != points.length) {
      throw new RuntimeException("Attempt to morph between two shapes with different vertex counts");
    }
    
    Shape prev = (Shape)shapes.get(shapes.size() - 1);
    if (equalShapes(prev, shape)) {
      shapes.add(prev);
    } else {
      shapes.add(shape);
    }
    
    if (shapes.size() == 2) {
      next = ((Shape)shapes.get(1));
    }
  }
  






  private boolean equalShapes(Shape a, Shape b)
  {
    a.checkPoints();
    b.checkPoints();
    
    for (int i = 0; i < points.length; i++) {
      if (points[i] != points[i]) {
        return false;
      }
    }
    
    return true;
  }
  





  public void setMorphTime(float time)
  {
    int p = (int)time;
    int n = p + 1;
    float offset = time - p;
    
    p = rational(p);
    n = rational(n);
    
    setFrame(p, n, offset);
  }
  




  public void updateMorphTime(float delta)
  {
    offset += delta;
    if (offset < 0.0F) {
      int index = shapes.indexOf(current);
      if (index < 0) {
        index = shapes.size() - 1;
      }
      
      int nframe = rational(index + 1);
      setFrame(index, nframe, offset);
      offset += 1.0F;
    } else if (offset > 1.0F) {
      int index = shapes.indexOf(next);
      if (index < 1) {
        index = 0;
      }
      
      int nframe = rational(index + 1);
      setFrame(index, nframe, offset);
      offset -= 1.0F;
    } else {
      pointsDirty = true;
    }
  }
  




  public void setExternalFrame(Shape current)
  {
    this.current = current;
    next = ((Shape)shapes.get(0));
    offset = 0.0F;
  }
  





  private int rational(int n)
  {
    while (n >= shapes.size()) {
      n -= shapes.size();
    }
    while (n < 0) {
      n += shapes.size();
    }
    
    return n;
  }
  






  private void setFrame(int a, int b, float offset)
  {
    current = ((Shape)shapes.get(a));
    next = ((Shape)shapes.get(b));
    this.offset = offset;
    pointsDirty = true;
  }
  


  protected void createPoints()
  {
    if (current == next) {
      System.arraycopy(current.points, 0, points, 0, points.length);
      return;
    }
    
    float[] apoints = current.points;
    float[] bpoints = next.points;
    
    for (int i = 0; i < points.length; i++) {
      points[i] = (apoints[i] * (1.0F - offset));
      points[i] += bpoints[i] * offset;
    }
  }
  


  public Shape transform(Transform transform)
  {
    createPoints();
    Polygon poly = new Polygon(points);
    
    return poly;
  }
}
