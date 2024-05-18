package org.newdawn.slick.geom;

import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.util.FastTrig;
























public class RoundedRectangle
  extends Rectangle
{
  public static final int TOP_LEFT = 1;
  public static final int TOP_RIGHT = 2;
  public static final int BOTTOM_RIGHT = 4;
  public static final int BOTTOM_LEFT = 8;
  public static final int ALL = 15;
  private static final int DEFAULT_SEGMENT_COUNT = 25;
  private float cornerRadius;
  private int segmentCount;
  private int cornerFlags;
  
  public RoundedRectangle(float x, float y, float width, float height, float cornerRadius)
  {
    this(x, y, width, height, cornerRadius, 25);
  }
  









  public RoundedRectangle(float x, float y, float width, float height, float cornerRadius, int segmentCount)
  {
    this(x, y, width, height, cornerRadius, segmentCount, 15);
  }
  











  public RoundedRectangle(float x, float y, float width, float height, float cornerRadius, int segmentCount, int cornerFlags)
  {
    super(x, y, width, height);
    
    if (cornerRadius < 0.0F) {
      throw new IllegalArgumentException("corner radius must be >= 0");
    }
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.cornerRadius = cornerRadius;
    this.segmentCount = segmentCount;
    pointsDirty = true;
    this.cornerFlags = cornerFlags;
  }
  




  public float getCornerRadius()
  {
    return cornerRadius;
  }
  




  public void setCornerRadius(float cornerRadius)
  {
    if ((cornerRadius >= 0.0F) && 
      (cornerRadius != this.cornerRadius)) {
      this.cornerRadius = cornerRadius;
      pointsDirty = true;
    }
  }
  





  public float getHeight()
  {
    return height;
  }
  




  public void setHeight(float height)
  {
    if (this.height != height) {
      this.height = height;
      pointsDirty = true;
    }
  }
  




  public float getWidth()
  {
    return width;
  }
  




  public void setWidth(float width)
  {
    if (width != this.width) {
      this.width = width;
      pointsDirty = true;
    }
  }
  
  protected void createPoints() {
    maxX = (x + width);
    maxY = (y + height);
    minX = x;
    minY = y;
    float useWidth = width - 1.0F;
    float useHeight = height - 1.0F;
    if (cornerRadius == 0.0F) {
      points = new float[8];
      
      points[0] = x;
      points[1] = y;
      
      points[2] = (x + useWidth);
      points[3] = y;
      
      points[4] = (x + useWidth);
      points[5] = (y + useHeight);
      
      points[6] = x;
      points[7] = (y + useHeight);
    }
    else {
      float doubleRadius = cornerRadius * 2.0F;
      if (doubleRadius > useWidth) {
        doubleRadius = useWidth;
        cornerRadius = (doubleRadius / 2.0F);
      }
      if (doubleRadius > useHeight) {
        doubleRadius = useHeight;
        cornerRadius = (doubleRadius / 2.0F);
      }
      
      ArrayList tempPoints = new ArrayList();
      



      if ((cornerFlags & 0x1) != 0) {
        tempPoints.addAll(createPoints(segmentCount, cornerRadius, x + cornerRadius, y + cornerRadius, 180.0F, 270.0F));
      } else {
        tempPoints.add(new Float(x));
        tempPoints.add(new Float(y));
      }
      

      if ((cornerFlags & 0x2) != 0) {
        tempPoints.addAll(createPoints(segmentCount, cornerRadius, x + useWidth - cornerRadius, y + cornerRadius, 270.0F, 360.0F));
      } else {
        tempPoints.add(new Float(x + useWidth));
        tempPoints.add(new Float(y));
      }
      

      if ((cornerFlags & 0x4) != 0) {
        tempPoints.addAll(createPoints(segmentCount, cornerRadius, x + useWidth - cornerRadius, y + useHeight - cornerRadius, 0.0F, 90.0F));
      } else {
        tempPoints.add(new Float(x + useWidth));
        tempPoints.add(new Float(y + useHeight));
      }
      

      if ((cornerFlags & 0x8) != 0) {
        tempPoints.addAll(createPoints(segmentCount, cornerRadius, x + cornerRadius, y + useHeight - cornerRadius, 90.0F, 180.0F));
      } else {
        tempPoints.add(new Float(x));
        tempPoints.add(new Float(y + useHeight));
      }
      
      points = new float[tempPoints.size()];
      for (int i = 0; i < tempPoints.size(); i++) {
        points[i] = ((Float)tempPoints.get(i)).floatValue();
      }
    }
    
    findCenter();
    calculateRadius();
  }
  










  private List createPoints(int numberOfSegments, float radius, float cx, float cy, float start, float end)
  {
    ArrayList tempPoints = new ArrayList();
    
    int step = 360 / numberOfSegments;
    
    for (float a = start; a <= end + step; a += step) {
      float ang = a;
      if (ang > end) {
        ang = end;
      }
      float x = (float)(cx + FastTrig.cos(Math.toRadians(ang)) * radius);
      float y = (float)(cy + FastTrig.sin(Math.toRadians(ang)) * radius);
      
      tempPoints.add(new Float(x));
      tempPoints.add(new Float(y));
    }
    
    return tempPoints;
  }
  





  public Shape transform(Transform transform)
  {
    checkPoints();
    
    Polygon resultPolygon = new Polygon();
    
    float[] result = new float[points.length];
    transform.transform(points, 0, result, 0, points.length / 2);
    points = result;
    resultPolygon.findCenter();
    
    return resultPolygon;
  }
}
