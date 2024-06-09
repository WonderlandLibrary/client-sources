package org.newdawn.slick.geom;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;





































public class MannTriangulator
  implements Triangulator
{
  private static final double EPSILON = 1.0E-5D;
  protected PointBag contour;
  protected PointBag holes;
  private PointBag nextFreePointBag;
  private Point nextFreePoint;
  private List triangles = new ArrayList();
  
  public MannTriangulator()
  {
    contour = getPointBag();
  }
  


  public void addPolyPoint(float x, float y)
  {
    addPoint(new Vector2f(x, y));
  }
  


  public void reset()
  {
    while (holes != null) {
      holes = freePointBag(holes);
    }
    
    contour.clear();
    holes = null;
  }
  


  public void startHole()
  {
    PointBag newHole = getPointBag();
    next = holes;
    holes = newHole;
  }
  




  private void addPoint(Vector2f pt)
  {
    if (holes == null) {
      Point p = getPoint(pt);
      contour.add(p);
    } else {
      Point p = getPoint(pt);
      holes.add(p);
    }
  }
  






  private Vector2f[] triangulate(Vector2f[] result)
  {
    contour.computeAngles();
    for (PointBag hole = holes; hole != null; hole = next) {
      hole.computeAngles();
    }
    
    label243:
    while (holes != null) {
      Point pHole = holes.first;
      do {
        if (angle <= 0.0D) {
          Point pContour = contour.first;
          do {
            if ((pHole.isInfront(pContour)) && 
              (pContour.isInfront(pHole)))
            {
              if (!contour.doesIntersectSegment(pt, pt)) {
                PointBag hole = holes;
                

                while (!hole.doesIntersectSegment(pt, pt))
                {

                  if ((hole = next) == null)
                  {
                    Point newPtContour = getPoint(pt);
                    pContour.insertAfter(newPtContour);
                    
                    Point newPtHole = getPoint(pt);
                    pHole.insertBefore(newPtHole);
                    
                    next = pHole;
                    prev = pContour;
                    
                    next = newPtContour;
                    prev = newPtHole;
                    
                    pContour.computeAngle();
                    pHole.computeAngle();
                    newPtContour.computeAngle();
                    newPtHole.computeAngle();
                    

                    holes.first = null;
                    break label243;
                  } }
              } }
          } while ((pContour = next) != contour.first);
        }
      } while ((pHole = next) != holes.first);
      

      holes = freePointBag(holes);
    }
    

    int numTriangles = contour.countPoints() - 2;
    int neededSpace = numTriangles * 3 + 1;
    if (result.length < neededSpace) {
      result = (Vector2f[])Array.newInstance(result.getClass()
        .getComponentType(), neededSpace);
    }
    

    int idx = 0;
    for (;;) {
      Point pContour = contour.first;
      
      if (pContour == null) {
        break;
      }
      
      if (next == prev) {
        break;
      }
      do
      {
        if (angle > 0.0D) {
          Point prev = prev;
          Point next = next;
          
          if (((next == prev) || ((prev.isInfront(next)) && (next.isInfront(prev)))) && 
            (!contour.doesIntersectSegment(pt, pt))) {
            result[(idx++)] = pt;
            result[(idx++)] = pt;
            result[(idx++)] = pt;
            break;
          }
          
        }
      } while ((pContour = next) != contour.first);
      

      Point prev = prev;
      Point next = next;
      
      contour.first = prev;
      pContour.unlink();
      freePoint(pContour);
      
      next.computeAngle();
      prev.computeAngle();
    }
    

    result[idx] = null;
    

    contour.clear();
    

    return result;
  }
  




  private PointBag getPointBag()
  {
    PointBag pb = nextFreePointBag;
    if (pb != null) {
      nextFreePointBag = next;
      next = null;
      return pb;
    }
    return new PointBag();
  }
  





  private PointBag freePointBag(PointBag pb)
  {
    PointBag next = next;
    pb.clear();
    next = nextFreePointBag;
    nextFreePointBag = pb;
    return next;
  }
  





  private Point getPoint(Vector2f pt)
  {
    Point p = nextFreePoint;
    if (p != null) {
      nextFreePoint = next;
      
      next = null;
      prev = null;
      pt = pt;
      return p;
    }
    return new Point(pt);
  }
  




  private void freePoint(Point p)
  {
    next = nextFreePoint;
    nextFreePoint = p;
  }
  




  private void freePoints(Point head)
  {
    prev.next = nextFreePoint;
    prev = null;
    nextFreePoint = head;
  }
  


  private static class Point
    implements Serializable
  {
    protected Vector2f pt;
    

    protected Point prev;
    

    protected Point next;
    

    protected double nx;
    

    protected double ny;
    

    protected double angle;
    
    protected double dist;
    

    public Point(Vector2f pt)
    {
      this.pt = pt;
    }
    


    public void unlink()
    {
      prev.next = next;
      next.prev = prev;
      next = null;
      prev = null;
    }
    




    public void insertBefore(Point p)
    {
      prev.next = p;
      prev = prev;
      next = this;
      prev = p;
    }
    




    public void insertAfter(Point p)
    {
      next.prev = p;
      prev = this;
      next = next;
      next = p;
    }
    






    private double hypot(double x, double y)
    {
      return Math.sqrt(x * x + y * y);
    }
    


    public void computeAngle()
    {
      if (prev.pt.equals(pt)) {
        pt.x += 0.01F;
      }
      double dx1 = pt.x - prev.pt.x;
      double dy1 = pt.y - prev.pt.y;
      double len1 = hypot(dx1, dy1);
      dx1 /= len1;
      dy1 /= len1;
      
      if (next.pt.equals(pt)) {
        pt.y += 0.01F;
      }
      double dx2 = next.pt.x - pt.x;
      double dy2 = next.pt.y - pt.y;
      double len2 = hypot(dx2, dy2);
      dx2 /= len2;
      dy2 /= len2;
      
      double nx1 = -dy1;
      double ny1 = dx1;
      
      nx = ((nx1 - dy2) * 0.5D);
      ny = ((ny1 + dx2) * 0.5D);
      
      if (nx * nx + ny * ny < 1.0E-5D) {
        nx = dx1;
        ny = dy2;
        angle = 1.0D;
        if (dx1 * dx2 + dy1 * dy2 > 0.0D) {
          nx = (-dx1);
          ny = (-dy1);
        }
      } else {
        angle = (nx * dx2 + ny * dy2);
      }
    }
    





    public double getAngle(Point p)
    {
      double dx = pt.x - pt.x;
      double dy = pt.y - pt.y;
      double dlen = hypot(dx, dy);
      
      return (nx * dx + ny * dy) / dlen;
    }
    




    public boolean isConcave()
    {
      return angle < 0.0D;
    }
    








    public boolean isInfront(double dx, double dy)
    {
      boolean sidePrev = (prev.pt.y - pt.y) * dx + (pt.x - prev.pt.x) * 
        dy >= 0.0D;
      boolean sideNext = (pt.y - next.pt.y) * dx + (next.pt.x - pt.x) * 
        dy >= 0.0D;
      
      return angle < 0.0D ? sidePrev | sideNext : sidePrev & sideNext;
    }
    





    public boolean isInfront(Point p)
    {
      return isInfront(pt.x - pt.x, pt.y - pt.y);
    }
  }
  


  protected class PointBag
    implements Serializable
  {
    protected MannTriangulator.Point first;
    
    protected PointBag next;
    

    protected PointBag() {}
    

    public void clear()
    {
      if (first != null) {
        MannTriangulator.this.freePoints(first);
        first = null;
      }
    }
    




    public void add(MannTriangulator.Point p)
    {
      if (first != null) {
        first.insertBefore(p);
      } else {
        first = p;
        next = p;
        prev = p;
      }
    }
    


    public void computeAngles()
    {
      if (first == null) {
        return;
      }
      
      MannTriangulator.Point p = first;
      do {
        p.computeAngle();
      } while ((p = next) != first);
    }
    







    public boolean doesIntersectSegment(Vector2f v1, Vector2f v2)
    {
      double dxA = x - x;
      double dyA = y - y;
      
      MannTriangulator.Point p = first;
      for (;;) { MannTriangulator.Point n = next;
        if ((pt != v1) && (pt != v1) && (pt != v2) && (pt != v2)) {
          double dxB = pt.x - pt.x;
          double dyB = pt.y - pt.y;
          double d = dxA * dyB - dyA * dxB;
          
          if (Math.abs(d) > 1.0E-5D) {
            double tmp1 = pt.x - x;
            double tmp2 = pt.y - y;
            double tA = (dyB * tmp1 - dxB * tmp2) / d;
            double tB = (dyA * tmp1 - dxA * tmp2) / d;
            
            if ((tA >= 0.0D) && (tA <= 1.0D) && (tB >= 0.0D) && (tB <= 1.0D)) {
              return true;
            }
          }
        }
        
        if (n == first) {
          return false;
        }
        p = n;
      }
    }
    




    public int countPoints()
    {
      if (first == null) {
        return 0;
      }
      
      int count = 0;
      MannTriangulator.Point p = first;
      do {
        count++;
      } while ((p = next) != first);
      return count;
    }
    





    public boolean contains(Vector2f point)
    {
      if (first == null) {
        return false;
      }
      
      if (first.prev.pt.equals(point)) {
        return true;
      }
      if (first.pt.equals(point)) {
        return true;
      }
      return false;
    }
  }
  
  public boolean triangulate() {
    Vector2f[] temp = triangulate(new Vector2f[0]);
    
    for (int i = 0; i < temp.length; i++) {
      if (temp[i] == null) {
        break;
      }
      triangles.add(temp[i]);
    }
    

    return true;
  }
  


  public int getTriangleCount()
  {
    return triangles.size() / 3;
  }
  


  public float[] getTrianglePoint(int tri, int i)
  {
    Vector2f pt = (Vector2f)triangles.get(tri * 3 + i);
    
    return new float[] { x, y };
  }
}
