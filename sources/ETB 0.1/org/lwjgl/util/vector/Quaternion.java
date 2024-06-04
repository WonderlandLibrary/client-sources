package org.lwjgl.util.vector;

import java.nio.FloatBuffer;







































public class Quaternion
  extends Vector
  implements ReadableVector4f
{
  private static final long serialVersionUID = 1L;
  public float x;
  public float y;
  public float z;
  public float w;
  
  public Quaternion()
  {
    setIdentity();
  }
  




  public Quaternion(ReadableVector4f src)
  {
    set(src);
  }
  



  public Quaternion(float x, float y, float z, float w)
  {
    set(x, y, z, w);
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
  





  public void set(float x, float y, float z, float w)
  {
    this.x = x;
    this.y = y;
    this.z = z;
    this.w = w;
  }
  






  public Quaternion set(ReadableVector4f src)
  {
    x = src.getX();
    y = src.getY();
    z = src.getZ();
    w = src.getW();
    return this;
  }
  



  public Quaternion setIdentity()
  {
    return setIdentity(this);
  }
  




  public static Quaternion setIdentity(Quaternion q)
  {
    x = 0.0F;
    y = 0.0F;
    z = 0.0F;
    w = 1.0F;
    return q;
  }
  


  public float lengthSquared()
  {
    return x * x + y * y + z * z + w * w;
  }
  









  public static Quaternion normalise(Quaternion src, Quaternion dest)
  {
    float inv_l = 1.0F / src.length();
    
    if (dest == null) {
      dest = new Quaternion();
    }
    dest.set(x * inv_l, y * inv_l, z * inv_l, w * inv_l);
    
    return dest;
  }
  







  public Quaternion normalise(Quaternion dest)
  {
    return normalise(this, dest);
  }
  








  public static float dot(Quaternion left, Quaternion right)
  {
    return x * x + y * y + z * z + w * w;
  }
  







  public Quaternion negate(Quaternion dest)
  {
    return negate(this, dest);
  }
  








  public static Quaternion negate(Quaternion src, Quaternion dest)
  {
    if (dest == null) {
      dest = new Quaternion();
    }
    x = (-x);
    y = (-y);
    z = (-z);
    w = w;
    
    return dest;
  }
  


  public Vector negate()
  {
    return negate(this, this);
  }
  


  public Vector load(FloatBuffer buf)
  {
    x = buf.get();
    y = buf.get();
    z = buf.get();
    w = buf.get();
    return this;
  }
  




  public Vector scale(float scale)
  {
    return scale(scale, this, this);
  }
  






  public static Quaternion scale(float scale, Quaternion src, Quaternion dest)
  {
    if (dest == null)
      dest = new Quaternion();
    x *= scale;
    y *= scale;
    z *= scale;
    w *= scale;
    return dest;
  }
  


  public Vector store(FloatBuffer buf)
  {
    buf.put(x);
    buf.put(y);
    buf.put(z);
    buf.put(w);
    
    return this;
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
  




  public void setW(float w)
  {
    this.w = w;
  }
  




  public float getW()
  {
    return w;
  }
  
  public String toString() {
    return "Quaternion: " + x + " " + y + " " + z + " " + w;
  }
  










  public static Quaternion mul(Quaternion left, Quaternion right, Quaternion dest)
  {
    if (dest == null)
      dest = new Quaternion();
    dest.set(x * w + w * x + y * z - z * y, y * w + w * y + z * x - x * z, z * w + w * z + x * y - y * x, w * w - x * x - y * y - z * z);
    




    return dest;
  }
  











  public static Quaternion mulInverse(Quaternion left, Quaternion right, Quaternion dest)
  {
    float n = right.lengthSquared();
    
    n = n == 0.0D ? n : 1.0F / n;
    
    if (dest == null)
      dest = new Quaternion();
    dest.set((x * w - w * x - y * z + z * y) * n, (y * w - w * y - z * x + x * z) * n, (z * w - w * z - x * y + y * x) * n, (w * w + x * x + y * y + z * z) * n);
    









    return dest;
  }
  






  public final void setFromAxisAngle(Vector4f a1)
  {
    x = x;
    y = y;
    z = z;
    float n = (float)Math.sqrt(x * x + y * y + z * z);
    
    float s = (float)(Math.sin(0.5D * w) / n);
    x *= s;
    y *= s;
    z *= s;
    w = ((float)Math.cos(0.5D * w));
  }
  







  public final Quaternion setFromMatrix(Matrix4f m)
  {
    return setFromMatrix(m, this);
  }
  









  public static Quaternion setFromMatrix(Matrix4f m, Quaternion q)
  {
    return q.setFromMat(m00, m01, m02, m10, m11, m12, m20, m21, m22);
  }
  







  public final Quaternion setFromMatrix(Matrix3f m)
  {
    return setFromMatrix(m, this);
  }
  









  public static Quaternion setFromMatrix(Matrix3f m, Quaternion q)
  {
    return q.setFromMat(m00, m01, m02, m10, m11, m12, m20, m21, m22);
  }
  






  private Quaternion setFromMat(float m00, float m01, float m02, float m10, float m11, float m12, float m20, float m21, float m22)
  {
    float tr = m00 + m11 + m22;
    if (tr >= 0.0D) {
      float s = (float)Math.sqrt(tr + 1.0D);
      w = (s * 0.5F);
      s = 0.5F / s;
      x = ((m21 - m12) * s);
      y = ((m02 - m20) * s);
      z = ((m10 - m01) * s);
    } else {
      float max = Math.max(Math.max(m00, m11), m22);
      if (max == m00) {
        float s = (float)Math.sqrt(m00 - (m11 + m22) + 1.0D);
        x = (s * 0.5F);
        s = 0.5F / s;
        y = ((m01 + m10) * s);
        z = ((m20 + m02) * s);
        w = ((m21 - m12) * s);
      } else if (max == m11) {
        float s = (float)Math.sqrt(m11 - (m22 + m00) + 1.0D);
        y = (s * 0.5F);
        s = 0.5F / s;
        z = ((m12 + m21) * s);
        x = ((m01 + m10) * s);
        w = ((m02 - m20) * s);
      } else {
        float s = (float)Math.sqrt(m22 - (m00 + m11) + 1.0D);
        z = (s * 0.5F);
        s = 0.5F / s;
        x = ((m20 + m02) * s);
        y = ((m12 + m21) * s);
        w = ((m10 - m01) * s);
      }
    }
    return this;
  }
}
