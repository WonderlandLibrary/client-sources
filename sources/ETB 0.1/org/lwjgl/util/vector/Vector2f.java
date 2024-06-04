package org.lwjgl.util.vector;

import java.io.Serializable;
import java.nio.FloatBuffer;
















































public class Vector2f
  extends Vector
  implements Serializable, ReadableVector2f, WritableVector2f
{
  private static final long serialVersionUID = 1L;
  public float x;
  public float y;
  
  public Vector2f() {}
  
  public Vector2f(ReadableVector2f src)
  {
    set(src);
  }
  


  public Vector2f(float x, float y)
  {
    set(x, y);
  }
  


  public void set(float x, float y)
  {
    this.x = x;
    this.y = y;
  }
  




  public Vector2f set(ReadableVector2f src)
  {
    x = src.getX();
    y = src.getY();
    return this;
  }
  


  public float lengthSquared()
  {
    return x * x + y * y;
  }
  





  public Vector2f translate(float x, float y)
  {
    this.x += x;
    this.y += y;
    return this;
  }
  



  public Vector negate()
  {
    x = (-x);
    y = (-y);
    return this;
  }
  




  public Vector2f negate(Vector2f dest)
  {
    if (dest == null)
      dest = new Vector2f();
    x = (-x);
    y = (-y);
    return dest;
  }
  





  public Vector2f normalise(Vector2f dest)
  {
    float l = length();
    
    if (dest == null) {
      dest = new Vector2f(x / l, y / l);
    } else {
      dest.set(x / l, y / l);
    }
    return dest;
  }
  






  public static float dot(Vector2f left, Vector2f right)
  {
    return x * x + y * y;
  }
  







  public static float angle(Vector2f a, Vector2f b)
  {
    float dls = dot(a, b) / (a.length() * b.length());
    if (dls < -1.0F) {
      dls = -1.0F;
    } else if (dls > 1.0F)
      dls = 1.0F;
    return (float)Math.acos(dls);
  }
  







  public static Vector2f add(Vector2f left, Vector2f right, Vector2f dest)
  {
    if (dest == null) {
      return new Vector2f(x + x, y + y);
    }
    dest.set(x + x, y + y);
    return dest;
  }
  








  public static Vector2f sub(Vector2f left, Vector2f right, Vector2f dest)
  {
    if (dest == null) {
      return new Vector2f(x - x, y - y);
    }
    dest.set(x - x, y - y);
    return dest;
  }
  





  public Vector store(FloatBuffer buf)
  {
    buf.put(x);
    buf.put(y);
    return this;
  }
  




  public Vector load(FloatBuffer buf)
  {
    x = buf.get();
    y = buf.get();
    return this;
  }
  



  public Vector scale(float scale)
  {
    x *= scale;
    y *= scale;
    
    return this;
  }
  


  public String toString()
  {
    StringBuilder sb = new StringBuilder(64);
    
    sb.append("Vector2f[");
    sb.append(x);
    sb.append(", ");
    sb.append(y);
    sb.append(']');
    return sb.toString();
  }
  


  public final float getX()
  {
    return x;
  }
  


  public final float getY()
  {
    return y;
  }
  



  public final void setX(float x)
  {
    this.x = x;
  }
  



  public final void setY(float y)
  {
    this.y = y;
  }
  
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Vector2f other = (Vector2f)obj;
    
    if ((x == x) && (y == y)) { return true;
    }
    return false;
  }
}
