package org.newdawn.slick.geom;

import java.util.ArrayList;







public class BasicTriangulator
  implements Triangulator
{
  private static final float EPSILON = 1.0E-10F;
  private PointList poly = new PointList();
  
  private PointList tris = new PointList();
  



  private boolean tried;
  



  public BasicTriangulator() {}
  



  public void addPolyPoint(float x, float y)
  {
    Point p = new Point(x, y);
    if (!poly.contains(p)) {
      poly.add(p);
    }
  }
  




  public int getPolyPointCount()
  {
    return poly.size();
  }
  





  public float[] getPolyPoint(int index)
  {
    return new float[] { poly.get(index).x, poly.get(index).y };
  }
  




  public boolean triangulate()
  {
    tried = true;
    
    boolean worked = process(poly, tris);
    return worked;
  }
  




  public int getTriangleCount()
  {
    if (!tried) {
      throw new RuntimeException("Call triangulate() before accessing triangles");
    }
    return tris.size() / 3;
  }
  







  public float[] getTrianglePoint(int tri, int i)
  {
    if (!tried) {
      throw new RuntimeException("Call triangulate() before accessing triangles");
    }
    
    return tris.get(tri * 3 + i).toArray();
  }
  







  private float area(PointList contour)
  {
    int n = contour.size();
    
    float A = 0.0F;
    
    int p = n - 1; for (int q = 0; q < n; p = q++) {
      Point contourP = contour.get(p);
      Point contourQ = contour.get(q);
      

      A = A + (contourP.getX() * contourQ.getY() - contourQ.getX() * contourP.getY());
    }
    return A * 0.5F;
  }
  

















  private boolean insideTriangle(float Ax, float Ay, float Bx, float By, float Cx, float Cy, float Px, float Py)
  {
    float ax = Cx - Bx;
    float ay = Cy - By;
    float bx = Ax - Cx;
    float by = Ay - Cy;
    float cx = Bx - Ax;
    float cy = By - Ay;
    float apx = Px - Ax;
    float apy = Py - Ay;
    float bpx = Px - Bx;
    float bpy = Py - By;
    float cpx = Px - Cx;
    float cpy = Py - Cy;
    
    float aCROSSbp = ax * bpy - ay * bpx;
    float cCROSSap = cx * apy - cy * apx;
    float bCROSScp = bx * cpy - by * cpx;
    
    return (aCROSSbp >= 0.0F) && (bCROSScp >= 0.0F) && (cCROSSap >= 0.0F);
  }
  















  private boolean snip(PointList contour, int u, int v, int w, int n, int[] V)
  {
    float Ax = contour.get(V[u]).getX();
    float Ay = contour.get(V[u]).getY();
    
    float Bx = contour.get(V[v]).getX();
    float By = contour.get(V[v]).getY();
    
    float Cx = contour.get(V[w]).getX();
    float Cy = contour.get(V[w]).getY();
    
    if (1.0E-10F > (Bx - Ax) * (Cy - Ay) - (By - Ay) * (Cx - Ax)) {
      return false;
    }
    
    for (int p = 0; p < n; p++) {
      if ((p != u) && (p != v) && (p != w))
      {


        float Px = contour.get(V[p]).getX();
        float Py = contour.get(V[p]).getY();
        
        if (insideTriangle(Ax, Ay, Bx, By, Cx, Cy, Px, Py)) {
          return false;
        }
      }
    }
    return true;
  }
  







  private boolean process(PointList contour, PointList result)
  {
    result.clear();
    


    int n = contour.size();
    if (n < 3) {
      return false;
    }
    int[] V = new int[n];
    


    if (0.0F < area(contour)) {
      for (int v = 0; v < n; v++)
        V[v] = v;
    } else {
      for (int v = 0; v < n; v++) {
        V[v] = (n - 1 - v);
      }
    }
    int nv = n;
    

    int count = 2 * nv;
    
    int m = 0; for (int v = nv - 1; nv > 2;)
    {
      if (count-- <= 0)
      {
        return false;
      }
      

      int u = v;
      if (nv <= u)
        u = 0;
      v = u + 1;
      if (nv <= v)
        v = 0;
      int w = v + 1;
      if (nv <= w) {
        w = 0;
      }
      if (snip(contour, u, v, w, nv, V))
      {


        int a = V[u];
        int b = V[v];
        int c = V[w];
        

        result.add(contour.get(a));
        result.add(contour.get(b));
        result.add(contour.get(c));
        
        m++;
        

        int s = v; for (int t = v + 1; t < nv; t++) {
          V[s] = V[t];s++;
        }
        nv--;
        

        count = 2 * nv;
      }
    }
    
    return true;
  }
  


  public void startHole() {}
  


  private class Point
  {
    private float x;
    

    private float y;
    

    private float[] array;
    


    public Point(float x, float y)
    {
      this.x = x;
      this.y = y;
      array = new float[] { x, y };
    }
    




    public float getX()
    {
      return x;
    }
    




    public float getY()
    {
      return y;
    }
    




    public float[] toArray()
    {
      return array;
    }
    


    public int hashCode()
    {
      return (int)(x * y * 31.0F);
    }
    


    public boolean equals(Object other)
    {
      if ((other instanceof Point)) {
        Point p = (Point)other;
        return (x == x) && (y == y);
      }
      
      return false;
    }
  }
  





  private class PointList
  {
    private ArrayList points = new ArrayList();
    





    public PointList() {}
    




    public boolean contains(BasicTriangulator.Point p)
    {
      return points.contains(p);
    }
    




    public void add(BasicTriangulator.Point point)
    {
      points.add(point);
    }
    




    public void remove(BasicTriangulator.Point point)
    {
      points.remove(point);
    }
    




    public int size()
    {
      return points.size();
    }
    





    public BasicTriangulator.Point get(int i)
    {
      return (BasicTriangulator.Point)points.get(i);
    }
    


    public void clear()
    {
      points.clear();
    }
  }
}
