package org.lwjgl.util.vector;

import java.io.Serializable;
import java.nio.FloatBuffer;











































public class Matrix3f
  extends Matrix
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public float m00;
  public float m01;
  public float m02;
  public float m10;
  public float m11;
  public float m12;
  public float m20;
  public float m21;
  public float m22;
  
  public Matrix3f()
  {
    setIdentity();
  }
  




  public Matrix3f load(Matrix3f src)
  {
    return load(src, this);
  }
  





  public static Matrix3f load(Matrix3f src, Matrix3f dest)
  {
    if (dest == null) {
      dest = new Matrix3f();
    }
    m00 = m00;
    m10 = m10;
    m20 = m20;
    m01 = m01;
    m11 = m11;
    m21 = m21;
    m02 = m02;
    m12 = m12;
    m22 = m22;
    
    return dest;
  }
  







  public Matrix load(FloatBuffer buf)
  {
    m00 = buf.get();
    m01 = buf.get();
    m02 = buf.get();
    m10 = buf.get();
    m11 = buf.get();
    m12 = buf.get();
    m20 = buf.get();
    m21 = buf.get();
    m22 = buf.get();
    
    return this;
  }
  







  public Matrix loadTranspose(FloatBuffer buf)
  {
    m00 = buf.get();
    m10 = buf.get();
    m20 = buf.get();
    m01 = buf.get();
    m11 = buf.get();
    m21 = buf.get();
    m02 = buf.get();
    m12 = buf.get();
    m22 = buf.get();
    
    return this;
  }
  




  public Matrix store(FloatBuffer buf)
  {
    buf.put(m00);
    buf.put(m01);
    buf.put(m02);
    buf.put(m10);
    buf.put(m11);
    buf.put(m12);
    buf.put(m20);
    buf.put(m21);
    buf.put(m22);
    return this;
  }
  




  public Matrix storeTranspose(FloatBuffer buf)
  {
    buf.put(m00);
    buf.put(m10);
    buf.put(m20);
    buf.put(m01);
    buf.put(m11);
    buf.put(m21);
    buf.put(m02);
    buf.put(m12);
    buf.put(m22);
    return this;
  }
  






  public static Matrix3f add(Matrix3f left, Matrix3f right, Matrix3f dest)
  {
    if (dest == null) {
      dest = new Matrix3f();
    }
    m00 += m00;
    m01 += m01;
    m02 += m02;
    m10 += m10;
    m11 += m11;
    m12 += m12;
    m20 += m20;
    m21 += m21;
    m22 += m22;
    
    return dest;
  }
  






  public static Matrix3f sub(Matrix3f left, Matrix3f right, Matrix3f dest)
  {
    if (dest == null) {
      dest = new Matrix3f();
    }
    m00 -= m00;
    m01 -= m01;
    m02 -= m02;
    m10 -= m10;
    m11 -= m11;
    m12 -= m12;
    m20 -= m20;
    m21 -= m21;
    m22 -= m22;
    
    return dest;
  }
  






  public static Matrix3f mul(Matrix3f left, Matrix3f right, Matrix3f dest)
  {
    if (dest == null) {
      dest = new Matrix3f();
    }
    float m00 = left.m00 * m00 + left.m10 * m01 + left.m20 * m02;
    
    float m01 = left.m01 * m00 + left.m11 * m01 + left.m21 * m02;
    
    float m02 = left.m02 * m00 + left.m12 * m01 + left.m22 * m02;
    
    float m10 = left.m00 * m10 + left.m10 * m11 + left.m20 * m12;
    
    float m11 = left.m01 * m10 + left.m11 * m11 + left.m21 * m12;
    
    float m12 = left.m02 * m10 + left.m12 * m11 + left.m22 * m12;
    
    float m20 = left.m00 * m20 + left.m10 * m21 + left.m20 * m22;
    
    float m21 = left.m01 * m20 + left.m11 * m21 + left.m21 * m22;
    
    float m22 = left.m02 * m20 + left.m12 * m21 + left.m22 * m22;
    

    m00 = m00;
    m01 = m01;
    m02 = m02;
    m10 = m10;
    m11 = m11;
    m12 = m12;
    m20 = m20;
    m21 = m21;
    m22 = m22;
    
    return dest;
  }
  







  public static Vector3f transform(Matrix3f left, Vector3f right, Vector3f dest)
  {
    if (dest == null) {
      dest = new Vector3f();
    }
    float x = m00 * x + m10 * y + m20 * z;
    float y = m01 * x + m11 * y + m21 * z;
    float z = m02 * x + m12 * y + m22 * z;
    
    x = x;
    y = y;
    z = z;
    
    return dest;
  }
  



  public Matrix transpose()
  {
    return transpose(this, this);
  }
  




  public Matrix3f transpose(Matrix3f dest)
  {
    return transpose(this, dest);
  }
  





  public static Matrix3f transpose(Matrix3f src, Matrix3f dest)
  {
    if (dest == null)
      dest = new Matrix3f();
    float m00 = src.m00;
    float m01 = src.m10;
    float m02 = src.m20;
    float m10 = src.m01;
    float m11 = src.m11;
    float m12 = src.m21;
    float m20 = src.m02;
    float m21 = src.m12;
    float m22 = src.m22;
    
    m00 = m00;
    m01 = m01;
    m02 = m02;
    m10 = m10;
    m11 = m11;
    m12 = m12;
    m20 = m20;
    m21 = m21;
    m22 = m22;
    return dest;
  }
  


  public float determinant()
  {
    float f = m00 * (m11 * m22 - m12 * m21) + m01 * (m12 * m20 - m10 * m22) + m02 * (m10 * m21 - m11 * m20);
    


    return f;
  }
  


  public String toString()
  {
    StringBuilder buf = new StringBuilder();
    buf.append(m00).append(' ').append(m10).append(' ').append(m20).append(' ').append('\n');
    buf.append(m01).append(' ').append(m11).append(' ').append(m21).append(' ').append('\n');
    buf.append(m02).append(' ').append(m12).append(' ').append(m22).append(' ').append('\n');
    return buf.toString();
  }
  



  public Matrix invert()
  {
    return invert(this, this);
  }
  





  public static Matrix3f invert(Matrix3f src, Matrix3f dest)
  {
    float determinant = src.determinant();
    
    if (determinant != 0.0F) {
      if (dest == null) {
        dest = new Matrix3f();
      }
      






      float determinant_inv = 1.0F / determinant;
      

      float t00 = m11 * m22 - m12 * m21;
      float t01 = -m10 * m22 + m12 * m20;
      float t02 = m10 * m21 - m11 * m20;
      float t10 = -m01 * m22 + m02 * m21;
      float t11 = m00 * m22 - m02 * m20;
      float t12 = -m00 * m21 + m01 * m20;
      float t20 = m01 * m12 - m02 * m11;
      float t21 = -m00 * m12 + m02 * m10;
      float t22 = m00 * m11 - m01 * m10;
      
      m00 = (t00 * determinant_inv);
      m11 = (t11 * determinant_inv);
      m22 = (t22 * determinant_inv);
      m01 = (t10 * determinant_inv);
      m10 = (t01 * determinant_inv);
      m20 = (t02 * determinant_inv);
      m02 = (t20 * determinant_inv);
      m12 = (t21 * determinant_inv);
      m21 = (t12 * determinant_inv);
      return dest;
    }
    return null;
  }
  




  public Matrix negate()
  {
    return negate(this);
  }
  




  public Matrix3f negate(Matrix3f dest)
  {
    return negate(this, dest);
  }
  





  public static Matrix3f negate(Matrix3f src, Matrix3f dest)
  {
    if (dest == null) {
      dest = new Matrix3f();
    }
    m00 = (-m00);
    m01 = (-m02);
    m02 = (-m01);
    m10 = (-m10);
    m11 = (-m12);
    m12 = (-m11);
    m20 = (-m20);
    m21 = (-m22);
    m22 = (-m21);
    return dest;
  }
  



  public Matrix setIdentity()
  {
    return setIdentity(this);
  }
  




  public static Matrix3f setIdentity(Matrix3f m)
  {
    m00 = 1.0F;
    m01 = 0.0F;
    m02 = 0.0F;
    m10 = 0.0F;
    m11 = 1.0F;
    m12 = 0.0F;
    m20 = 0.0F;
    m21 = 0.0F;
    m22 = 1.0F;
    return m;
  }
  



  public Matrix setZero()
  {
    return setZero(this);
  }
  




  public static Matrix3f setZero(Matrix3f m)
  {
    m00 = 0.0F;
    m01 = 0.0F;
    m02 = 0.0F;
    m10 = 0.0F;
    m11 = 0.0F;
    m12 = 0.0F;
    m20 = 0.0F;
    m21 = 0.0F;
    m22 = 0.0F;
    return m;
  }
}
