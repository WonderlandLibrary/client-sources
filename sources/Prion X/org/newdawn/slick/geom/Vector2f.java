package org.newdawn.slick.geom;

import java.io.Serializable;
import org.newdawn.slick.util.FastTrig;

















public class Vector2f
  implements Serializable
{
  private static final long serialVersionUID = 1339934L;
  public float x;
  public float y;
  
  public strictfp Vector2f() {}
  
  public strictfp Vector2f(float[] coords)
  {
    x = coords[0];
    y = coords[1];
  }
  




  public strictfp Vector2f(double theta)
  {
    x = 1.0F;
    y = 0.0F;
    setTheta(theta);
  }
  






  public strictfp void setTheta(double theta)
  {
    if ((theta < -360.0D) || (theta > 360.0D)) {
      theta %= 360.0D;
    }
    if (theta < 0.0D) {
      theta += 360.0D;
    }
    double oldTheta = getTheta();
    if ((theta < -360.0D) || (theta > 360.0D)) {
      oldTheta %= 360.0D;
    }
    if (theta < 0.0D) {
      oldTheta += 360.0D;
    }
    
    float len = length();
    x = (len * (float)FastTrig.cos(StrictMath.toRadians(theta)));
    y = (len * (float)FastTrig.sin(StrictMath.toRadians(theta)));
  }
  












  public strictfp Vector2f add(double theta)
  {
    setTheta(getTheta() + theta);
    
    return this;
  }
  





  public strictfp Vector2f sub(double theta)
  {
    setTheta(getTheta() - theta);
    
    return this;
  }
  




  public strictfp double getTheta()
  {
    double theta = StrictMath.toDegrees(StrictMath.atan2(y, x));
    if ((theta < -360.0D) || (theta > 360.0D)) {
      theta %= 360.0D;
    }
    if (theta < 0.0D) {
      theta += 360.0D;
    }
    
    return theta;
  }
  




  public strictfp float getX()
  {
    return x;
  }
  




  public strictfp float getY()
  {
    return y;
  }
  




  public strictfp Vector2f(Vector2f other)
  {
    this(other.getX(), other.getY());
  }
  





  public strictfp Vector2f(float x, float y)
  {
    this.x = x;
    this.y = y;
  }
  




  public strictfp void set(Vector2f other)
  {
    set(other.getX(), other.getY());
  }
  





  public strictfp float dot(Vector2f other)
  {
    return x * other.getX() + y * other.getY();
  }
  






  public strictfp Vector2f set(float x, float y)
  {
    this.x = x;
    this.y = y;
    
    return this;
  }
  




  public strictfp Vector2f getPerpendicular()
  {
    return new Vector2f(-y, x);
  }
  





  public strictfp Vector2f set(float[] pt)
  {
    return set(pt[0], pt[1]);
  }
  




  public strictfp Vector2f negate()
  {
    return new Vector2f(-x, -y);
  }
  




  public strictfp Vector2f negateLocal()
  {
    x = (-x);
    y = (-y);
    
    return this;
  }
  






  public strictfp Vector2f add(Vector2f v)
  {
    x += v.getX();
    y += v.getY();
    
    return this;
  }
  






  public strictfp Vector2f sub(Vector2f v)
  {
    x -= v.getX();
    y -= v.getY();
    
    return this;
  }
  






  public strictfp Vector2f scale(float a)
  {
    x *= a;
    y *= a;
    
    return this;
  }
  




  public strictfp Vector2f normalise()
  {
    float l = length();
    
    if (l == 0.0F) {
      return this;
    }
    
    x /= l;
    y /= l;
    return this;
  }
  




  public strictfp Vector2f getNormal()
  {
    Vector2f cp = copy();
    cp.normalise();
    return cp;
  }
  




  public strictfp float lengthSquared()
  {
    return x * x + y * y;
  }
  





  public strictfp float length()
  {
    return (float)Math.sqrt(lengthSquared());
  }
  





  public strictfp void projectOntoUnit(Vector2f b, Vector2f result)
  {
    float dp = b.dot(this);
    
    x = (dp * b.getX());
    y = (dp * b.getY());
  }
  





  public strictfp Vector2f copy()
  {
    return new Vector2f(x, y);
  }
  


  public strictfp String toString()
  {
    return "[Vector2f " + x + "," + y + " (" + length() + ")]";
  }
  





  public strictfp float distance(Vector2f other)
  {
    return (float)Math.sqrt(distanceSquared(other));
  }
  







  public strictfp float distanceSquared(Vector2f other)
  {
    float dx = other.getX() - getX();
    float dy = other.getY() - getY();
    
    return dx * dx + dy * dy;
  }
  


  public strictfp int hashCode()
  {
    return 997 * (int)x ^ 991 * (int)y;
  }
  


  public strictfp boolean equals(Object other)
  {
    if ((other instanceof Vector2f)) {
      Vector2f o = (Vector2f)other;
      return (x == x) && (y == y);
    }
    
    return false;
  }
}
