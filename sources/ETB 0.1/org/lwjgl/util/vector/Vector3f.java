package org.lwjgl.util.vector;

import java.io.Serializable;
import java.nio.FloatBuffer;















































public class Vector3f
  extends Vector
  implements Serializable, ReadableVector3f, WritableVector3f
{
  private static final long serialVersionUID = 1L;
  public float x;
  public float y;
  public float z;
  
  public Vector3f() {}
  
  public Vector3f(ReadableVector3f src)
  {
    set(src);
  }
  


  public Vector3f(float x, float y, float z)
  {
    set(x, y, z);
  }
  


  public void set(float x, float y)
  {
    this.x = x;
    this.y = y;
  }
  


  public void set(float x, float y, float z)
  {
    this.x = x;
    this.y = y;
    this.z = z;
  }
  




  public Vector3f set(ReadableVector3f src)
  {
    x = src.getX();
    y = src.getY();
    z = src.getZ();
    return this;
  }
  


  public float lengthSquared()
  {
    return x * x + y * y + z * z;
  }
  





  public Vector3f translate(float x, float y, float z)
  {
    this.x += x;
    this.y += y;
    this.z += z;
    return this;
  }
  







  public static Vector3f add(Vector3f left, Vector3f right, Vector3f dest)
  {
    if (dest == null) {
      return new Vector3f(x + x, y + y, z + z);
    }
    dest.set(x + x, y + y, z + z);
    return dest;
  }
  








  public static Vector3f sub(Vector3f left, Vector3f right, Vector3f dest)
  {
    if (dest == null) {
      return new Vector3f(x - x, y - y, z - z);
    }
    dest.set(x - x, y - y, z - z);
    return dest;
  }
  













  public static Vector3f cross(Vector3f left, Vector3f right, Vector3f dest)
  {
    if (dest == null) {
      dest = new Vector3f();
    }
    dest.set(y * z - z * y, x * z - z * x, x * y - y * x);
    




    return dest;
  }
  





  public Vector negate()
  {
    x = (-x);
    y = (-y);
    z = (-z);
    return this;
  }
  




  public Vector3f negate(Vector3f dest)
  {
    if (dest == null)
      dest = new Vector3f();
    x = (-x);
    y = (-y);
    z = (-z);
    return dest;
  }
  





  public Vector3f normalise(Vector3f dest)
  {
    float l = length();
    
    if (dest == null) {
      dest = new Vector3f(x / l, y / l, z / l);
    } else {
      dest.set(x / l, y / l, z / l);
    }
    return dest;
  }
  






  public static float dot(Vector3f left, Vector3f right)
  {
    return x * x + y * y + z * z;
  }
  





  public static float angle(Vector3f a, Vector3f b)
  {
    float dls = dot(a, b) / (a.length() * b.length());
    if (dls < -1.0F) {
      dls = -1.0F;
    } else if (dls > 1.0F)
      dls = 1.0F;
    return (float)Math.acos(dls);
  }
  


  public Vector load(FloatBuffer buf)
  {
    x = buf.get();
    y = buf.get();
    z = buf.get();
    return this;
  }
  



  public Vector scale(float scale)
  {
    x *= scale;
    y *= scale;
    z *= scale;
    
    return this;
  }
  




  public Vector store(FloatBuffer buf)
  {
    buf.put(x);
    buf.put(y);
    buf.put(z);
    
    return this;
  }
  


  public String toString()
  {
    StringBuilder sb = new StringBuilder(64);
    
    sb.append("Vector3f[");
    sb.append(x);
    sb.append(", ");
    sb.append(y);
    sb.append(", ");
    sb.append(z);
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
  



  public void setZ(float z)
  {
    this.z = z;
  }
  


  public float getZ()
  {
    return z;
  }
  
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Vector3f other = (Vector3f)obj;
    
    if ((x == x) && (y == y) && (z == z)) { return true;
    }
    return false;
  }
}
