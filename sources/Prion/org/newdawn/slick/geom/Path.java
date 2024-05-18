package org.newdawn.slick.geom;

import java.util.ArrayList;






public class Path
  extends Shape
{
  private ArrayList localPoints = new ArrayList();
  
  private float cx;
  
  private float cy;
  
  private boolean closed;
  
  private ArrayList holes = new ArrayList();
  


  private ArrayList hole;
  



  public Path(float sx, float sy)
  {
    localPoints.add(new float[] { sx, sy });
    cx = sx;
    cy = sy;
    pointsDirty = true;
  }
  





  public void startHole(float sx, float sy)
  {
    hole = new ArrayList();
    holes.add(hole);
  }
  






  public void lineTo(float x, float y)
  {
    if (hole != null) {
      hole.add(new float[] { x, y });
    } else {
      localPoints.add(new float[] { x, y });
    }
    cx = x;
    cy = y;
    pointsDirty = true;
  }
  


  public void close()
  {
    closed = true;
  }
  









  public void curveTo(float x, float y, float cx1, float cy1, float cx2, float cy2)
  {
    curveTo(x, y, cx1, cy1, cx2, cy2, 10);
  }
  











  public void curveTo(float x, float y, float cx1, float cy1, float cx2, float cy2, int segments)
  {
    if ((cx == x) && (cy == y)) {
      return;
    }
    
    Curve curve = new Curve(new Vector2f(cx, cy), new Vector2f(cx1, cy1), new Vector2f(cx2, cy2), new Vector2f(x, y));
    float step = 1.0F / segments;
    
    for (int i = 1; i < segments + 1; i++) {
      float t = i * step;
      Vector2f p = curve.pointAt(t);
      if (hole != null) {
        hole.add(new float[] { x, y });
      } else {
        localPoints.add(new float[] { x, y });
      }
      cx = x;
      cy = y;
    }
    pointsDirty = true;
  }
  


  protected void createPoints()
  {
    points = new float[localPoints.size() * 2];
    for (int i = 0; i < localPoints.size(); i++) {
      float[] p = (float[])localPoints.get(i);
      points[(i * 2)] = p[0];
      points[(i * 2 + 1)] = p[1];
    }
  }
  


  public Shape transform(Transform transform)
  {
    Path p = new Path(cx, cy);
    localPoints = transform(localPoints, transform);
    for (int i = 0; i < holes.size(); i++) {
      holes.add(transform((ArrayList)holes.get(i), transform));
    }
    closed = closed;
    
    return p;
  }
  






  private ArrayList transform(ArrayList pts, Transform t)
  {
    float[] in = new float[pts.size() * 2];
    float[] out = new float[pts.size() * 2];
    
    for (int i = 0; i < pts.size(); i++) {
      in[(i * 2)] = ((float[])pts.get(i))[0];
      in[(i * 2 + 1)] = ((float[])pts.get(i))[1];
    }
    t.transform(in, 0, out, 0, pts.size());
    
    ArrayList outList = new ArrayList();
    for (int i = 0; i < pts.size(); i++) {
      outList.add(new float[] { out[(i * 2)], out[(i * 2 + 1)] });
    }
    
    return outList;
  }
  
































































  public boolean closed()
  {
    return closed;
  }
}
