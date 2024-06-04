package org.lwjgl.util.vector;

import java.io.Serializable;
import java.nio.FloatBuffer;























public class Matrix4f
  extends Matrix
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public float m00;
  public float m01;
  public float m02;
  public float m03;
  public float m10;
  public float m11;
  public float m12;
  public float m13;
  public float m20;
  public float m21;
  public float m22;
  public float m23;
  public float m30;
  public float m31;
  public float m32;
  public float m33;
  
  public Matrix4f()
  {
    setIdentity();
  }
  
  public Matrix4f(Matrix4f src)
  {
    load(src);
  }
  


  public String toString()
  {
    StringBuilder buf = new StringBuilder();
    buf.append(m00).append(' ').append(m10).append(' ').append(m20).append(' ').append(m30).append('\n');
    buf.append(m01).append(' ').append(m11).append(' ').append(m21).append(' ').append(m31).append('\n');
    buf.append(m02).append(' ').append(m12).append(' ').append(m22).append(' ').append(m32).append('\n');
    buf.append(m03).append(' ').append(m13).append(' ').append(m23).append(' ').append(m33).append('\n');
    return buf.toString();
  }
  



  public Matrix setIdentity()
  {
    return setIdentity(this);
  }
  




  public static Matrix4f setIdentity(Matrix4f m)
  {
    m00 = 1.0F;
    m01 = 0.0F;
    m02 = 0.0F;
    m03 = 0.0F;
    m10 = 0.0F;
    m11 = 1.0F;
    m12 = 0.0F;
    m13 = 0.0F;
    m20 = 0.0F;
    m21 = 0.0F;
    m22 = 1.0F;
    m23 = 0.0F;
    m30 = 0.0F;
    m31 = 0.0F;
    m32 = 0.0F;
    m33 = 1.0F;
    
    return m;
  }
  



  public Matrix setZero()
  {
    return setZero(this);
  }
  




  public static Matrix4f setZero(Matrix4f m)
  {
    m00 = 0.0F;
    m01 = 0.0F;
    m02 = 0.0F;
    m03 = 0.0F;
    m10 = 0.0F;
    m11 = 0.0F;
    m12 = 0.0F;
    m13 = 0.0F;
    m20 = 0.0F;
    m21 = 0.0F;
    m22 = 0.0F;
    m23 = 0.0F;
    m30 = 0.0F;
    m31 = 0.0F;
    m32 = 0.0F;
    m33 = 0.0F;
    
    return m;
  }
  




  public Matrix4f load(Matrix4f src)
  {
    return load(src, this);
  }
  





  public static Matrix4f load(Matrix4f src, Matrix4f dest)
  {
    if (dest == null)
      dest = new Matrix4f();
    m00 = m00;
    m01 = m01;
    m02 = m02;
    m03 = m03;
    m10 = m10;
    m11 = m11;
    m12 = m12;
    m13 = m13;
    m20 = m20;
    m21 = m21;
    m22 = m22;
    m23 = m23;
    m30 = m30;
    m31 = m31;
    m32 = m32;
    m33 = m33;
    
    return dest;
  }
  







  public Matrix load(FloatBuffer buf)
  {
    m00 = buf.get();
    m01 = buf.get();
    m02 = buf.get();
    m03 = buf.get();
    m10 = buf.get();
    m11 = buf.get();
    m12 = buf.get();
    m13 = buf.get();
    m20 = buf.get();
    m21 = buf.get();
    m22 = buf.get();
    m23 = buf.get();
    m30 = buf.get();
    m31 = buf.get();
    m32 = buf.get();
    m33 = buf.get();
    
    return this;
  }
  







  public Matrix loadTranspose(FloatBuffer buf)
  {
    m00 = buf.get();
    m10 = buf.get();
    m20 = buf.get();
    m30 = buf.get();
    m01 = buf.get();
    m11 = buf.get();
    m21 = buf.get();
    m31 = buf.get();
    m02 = buf.get();
    m12 = buf.get();
    m22 = buf.get();
    m32 = buf.get();
    m03 = buf.get();
    m13 = buf.get();
    m23 = buf.get();
    m33 = buf.get();
    
    return this;
  }
  




  public Matrix store(FloatBuffer buf)
  {
    buf.put(m00);
    buf.put(m01);
    buf.put(m02);
    buf.put(m03);
    buf.put(m10);
    buf.put(m11);
    buf.put(m12);
    buf.put(m13);
    buf.put(m20);
    buf.put(m21);
    buf.put(m22);
    buf.put(m23);
    buf.put(m30);
    buf.put(m31);
    buf.put(m32);
    buf.put(m33);
    return this;
  }
  




  public Matrix storeTranspose(FloatBuffer buf)
  {
    buf.put(m00);
    buf.put(m10);
    buf.put(m20);
    buf.put(m30);
    buf.put(m01);
    buf.put(m11);
    buf.put(m21);
    buf.put(m31);
    buf.put(m02);
    buf.put(m12);
    buf.put(m22);
    buf.put(m32);
    buf.put(m03);
    buf.put(m13);
    buf.put(m23);
    buf.put(m33);
    return this;
  }
  




  public Matrix store3f(FloatBuffer buf)
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
  






  public static Matrix4f add(Matrix4f left, Matrix4f right, Matrix4f dest)
  {
    if (dest == null) {
      dest = new Matrix4f();
    }
    m00 += m00;
    m01 += m01;
    m02 += m02;
    m03 += m03;
    m10 += m10;
    m11 += m11;
    m12 += m12;
    m13 += m13;
    m20 += m20;
    m21 += m21;
    m22 += m22;
    m23 += m23;
    m30 += m30;
    m31 += m31;
    m32 += m32;
    m33 += m33;
    
    return dest;
  }
  






  public static Matrix4f sub(Matrix4f left, Matrix4f right, Matrix4f dest)
  {
    if (dest == null) {
      dest = new Matrix4f();
    }
    m00 -= m00;
    m01 -= m01;
    m02 -= m02;
    m03 -= m03;
    m10 -= m10;
    m11 -= m11;
    m12 -= m12;
    m13 -= m13;
    m20 -= m20;
    m21 -= m21;
    m22 -= m22;
    m23 -= m23;
    m30 -= m30;
    m31 -= m31;
    m32 -= m32;
    m33 -= m33;
    
    return dest;
  }
  






  public static Matrix4f mul(Matrix4f left, Matrix4f right, Matrix4f dest)
  {
    if (dest == null) {
      dest = new Matrix4f();
    }
    float m00 = left.m00 * m00 + left.m10 * m01 + left.m20 * m02 + left.m30 * m03;
    float m01 = left.m01 * m00 + left.m11 * m01 + left.m21 * m02 + left.m31 * m03;
    float m02 = left.m02 * m00 + left.m12 * m01 + left.m22 * m02 + left.m32 * m03;
    float m03 = left.m03 * m00 + left.m13 * m01 + left.m23 * m02 + left.m33 * m03;
    float m10 = left.m00 * m10 + left.m10 * m11 + left.m20 * m12 + left.m30 * m13;
    float m11 = left.m01 * m10 + left.m11 * m11 + left.m21 * m12 + left.m31 * m13;
    float m12 = left.m02 * m10 + left.m12 * m11 + left.m22 * m12 + left.m32 * m13;
    float m13 = left.m03 * m10 + left.m13 * m11 + left.m23 * m12 + left.m33 * m13;
    float m20 = left.m00 * m20 + left.m10 * m21 + left.m20 * m22 + left.m30 * m23;
    float m21 = left.m01 * m20 + left.m11 * m21 + left.m21 * m22 + left.m31 * m23;
    float m22 = left.m02 * m20 + left.m12 * m21 + left.m22 * m22 + left.m32 * m23;
    float m23 = left.m03 * m20 + left.m13 * m21 + left.m23 * m22 + left.m33 * m23;
    float m30 = left.m00 * m30 + left.m10 * m31 + left.m20 * m32 + left.m30 * m33;
    float m31 = left.m01 * m30 + left.m11 * m31 + left.m21 * m32 + left.m31 * m33;
    float m32 = left.m02 * m30 + left.m12 * m31 + left.m22 * m32 + left.m32 * m33;
    float m33 = left.m03 * m30 + left.m13 * m31 + left.m23 * m32 + left.m33 * m33;
    
    m00 = m00;
    m01 = m01;
    m02 = m02;
    m03 = m03;
    m10 = m10;
    m11 = m11;
    m12 = m12;
    m13 = m13;
    m20 = m20;
    m21 = m21;
    m22 = m22;
    m23 = m23;
    m30 = m30;
    m31 = m31;
    m32 = m32;
    m33 = m33;
    
    return dest;
  }
  







  public static Vector4f transform(Matrix4f left, Vector4f right, Vector4f dest)
  {
    if (dest == null) {
      dest = new Vector4f();
    }
    float x = m00 * x + m10 * y + m20 * z + m30 * w;
    float y = m01 * x + m11 * y + m21 * z + m31 * w;
    float z = m02 * x + m12 * y + m22 * z + m32 * w;
    float w = m03 * x + m13 * y + m23 * z + m33 * w;
    
    x = x;
    y = y;
    z = z;
    w = w;
    
    return dest;
  }
  



  public Matrix transpose()
  {
    return transpose(this);
  }
  




  public Matrix4f translate(Vector2f vec)
  {
    return translate(vec, this);
  }
  




  public Matrix4f translate(Vector3f vec)
  {
    return translate(vec, this);
  }
  




  public Matrix4f scale(Vector3f vec)
  {
    return scale(vec, this, this);
  }
  






  public static Matrix4f scale(Vector3f vec, Matrix4f src, Matrix4f dest)
  {
    if (dest == null)
      dest = new Matrix4f();
    m00 *= x;
    m01 *= x;
    m02 *= x;
    m03 *= x;
    m10 *= y;
    m11 *= y;
    m12 *= y;
    m13 *= y;
    m20 *= z;
    m21 *= z;
    m22 *= z;
    m23 *= z;
    return dest;
  }
  





  public Matrix4f rotate(float angle, Vector3f axis)
  {
    return rotate(angle, axis, this);
  }
  






  public Matrix4f rotate(float angle, Vector3f axis, Matrix4f dest)
  {
    return rotate(angle, axis, this, dest);
  }
  








  public static Matrix4f rotate(float angle, Vector3f axis, Matrix4f src, Matrix4f dest)
  {
    if (dest == null)
      dest = new Matrix4f();
    float c = (float)Math.cos(angle);
    float s = (float)Math.sin(angle);
    float oneminusc = 1.0F - c;
    float xy = x * y;
    float yz = y * z;
    float xz = x * z;
    float xs = x * s;
    float ys = y * s;
    float zs = z * s;
    
    float f00 = x * x * oneminusc + c;
    float f01 = xy * oneminusc + zs;
    float f02 = xz * oneminusc - ys;
    
    float f10 = xy * oneminusc - zs;
    float f11 = y * y * oneminusc + c;
    float f12 = yz * oneminusc + xs;
    
    float f20 = xz * oneminusc + ys;
    float f21 = yz * oneminusc - xs;
    float f22 = z * z * oneminusc + c;
    
    float t00 = m00 * f00 + m10 * f01 + m20 * f02;
    float t01 = m01 * f00 + m11 * f01 + m21 * f02;
    float t02 = m02 * f00 + m12 * f01 + m22 * f02;
    float t03 = m03 * f00 + m13 * f01 + m23 * f02;
    float t10 = m00 * f10 + m10 * f11 + m20 * f12;
    float t11 = m01 * f10 + m11 * f11 + m21 * f12;
    float t12 = m02 * f10 + m12 * f11 + m22 * f12;
    float t13 = m03 * f10 + m13 * f11 + m23 * f12;
    m20 = (m00 * f20 + m10 * f21 + m20 * f22);
    m21 = (m01 * f20 + m11 * f21 + m21 * f22);
    m22 = (m02 * f20 + m12 * f21 + m22 * f22);
    m23 = (m03 * f20 + m13 * f21 + m23 * f22);
    m00 = t00;
    m01 = t01;
    m02 = t02;
    m03 = t03;
    m10 = t10;
    m11 = t11;
    m12 = t12;
    m13 = t13;
    return dest;
  }
  





  public Matrix4f translate(Vector3f vec, Matrix4f dest)
  {
    return translate(vec, this, dest);
  }
  






  public static Matrix4f translate(Vector3f vec, Matrix4f src, Matrix4f dest)
  {
    if (dest == null) {
      dest = new Matrix4f();
    }
    m30 += m00 * x + m10 * y + m20 * z;
    m31 += m01 * x + m11 * y + m21 * z;
    m32 += m02 * x + m12 * y + m22 * z;
    m33 += m03 * x + m13 * y + m23 * z;
    
    return dest;
  }
  





  public Matrix4f translate(Vector2f vec, Matrix4f dest)
  {
    return translate(vec, this, dest);
  }
  






  public static Matrix4f translate(Vector2f vec, Matrix4f src, Matrix4f dest)
  {
    if (dest == null) {
      dest = new Matrix4f();
    }
    m30 += m00 * x + m10 * y;
    m31 += m01 * x + m11 * y;
    m32 += m02 * x + m12 * y;
    m33 += m03 * x + m13 * y;
    
    return dest;
  }
  




  public Matrix4f transpose(Matrix4f dest)
  {
    return transpose(this, dest);
  }
  





  public static Matrix4f transpose(Matrix4f src, Matrix4f dest)
  {
    if (dest == null)
      dest = new Matrix4f();
    float m00 = src.m00;
    float m01 = src.m10;
    float m02 = src.m20;
    float m03 = src.m30;
    float m10 = src.m01;
    float m11 = src.m11;
    float m12 = src.m21;
    float m13 = src.m31;
    float m20 = src.m02;
    float m21 = src.m12;
    float m22 = src.m22;
    float m23 = src.m32;
    float m30 = src.m03;
    float m31 = src.m13;
    float m32 = src.m23;
    float m33 = src.m33;
    
    m00 = m00;
    m01 = m01;
    m02 = m02;
    m03 = m03;
    m10 = m10;
    m11 = m11;
    m12 = m12;
    m13 = m13;
    m20 = m20;
    m21 = m21;
    m22 = m22;
    m23 = m23;
    m30 = m30;
    m31 = m31;
    m32 = m32;
    m33 = m33;
    
    return dest;
  }
  


  public float determinant()
  {
    float f = m00 * (m11 * m22 * m33 + m12 * m23 * m31 + m13 * m21 * m32 - m13 * m22 * m31 - m11 * m23 * m32 - m12 * m21 * m33);
    




    f -= m01 * (m10 * m22 * m33 + m12 * m23 * m30 + m13 * m20 * m32 - m13 * m22 * m30 - m10 * m23 * m32 - m12 * m20 * m33);
    



    f += m02 * (m10 * m21 * m33 + m11 * m23 * m30 + m13 * m20 * m31 - m13 * m21 * m30 - m10 * m23 * m31 - m11 * m20 * m33);
    



    f -= m03 * (m10 * m21 * m32 + m11 * m22 * m30 + m12 * m20 * m31 - m12 * m21 * m30 - m10 * m22 * m31 - m11 * m20 * m32);
    



    return f;
  }
  







  private static float determinant3x3(float t00, float t01, float t02, float t10, float t11, float t12, float t20, float t21, float t22)
  {
    return t00 * (t11 * t22 - t12 * t21) + t01 * (t12 * t20 - t10 * t22) + t02 * (t10 * t21 - t11 * t20);
  }
  





  public Matrix invert()
  {
    return invert(this, this);
  }
  





  public static Matrix4f invert(Matrix4f src, Matrix4f dest)
  {
    float determinant = src.determinant();
    
    if (determinant != 0.0F)
    {





      if (dest == null)
        dest = new Matrix4f();
      float determinant_inv = 1.0F / determinant;
      

      float t00 = determinant3x3(m11, m12, m13, m21, m22, m23, m31, m32, m33);
      float t01 = -determinant3x3(m10, m12, m13, m20, m22, m23, m30, m32, m33);
      float t02 = determinant3x3(m10, m11, m13, m20, m21, m23, m30, m31, m33);
      float t03 = -determinant3x3(m10, m11, m12, m20, m21, m22, m30, m31, m32);
      
      float t10 = -determinant3x3(m01, m02, m03, m21, m22, m23, m31, m32, m33);
      float t11 = determinant3x3(m00, m02, m03, m20, m22, m23, m30, m32, m33);
      float t12 = -determinant3x3(m00, m01, m03, m20, m21, m23, m30, m31, m33);
      float t13 = determinant3x3(m00, m01, m02, m20, m21, m22, m30, m31, m32);
      
      float t20 = determinant3x3(m01, m02, m03, m11, m12, m13, m31, m32, m33);
      float t21 = -determinant3x3(m00, m02, m03, m10, m12, m13, m30, m32, m33);
      float t22 = determinant3x3(m00, m01, m03, m10, m11, m13, m30, m31, m33);
      float t23 = -determinant3x3(m00, m01, m02, m10, m11, m12, m30, m31, m32);
      
      float t30 = -determinant3x3(m01, m02, m03, m11, m12, m13, m21, m22, m23);
      float t31 = determinant3x3(m00, m02, m03, m10, m12, m13, m20, m22, m23);
      float t32 = -determinant3x3(m00, m01, m03, m10, m11, m13, m20, m21, m23);
      float t33 = determinant3x3(m00, m01, m02, m10, m11, m12, m20, m21, m22);
      

      m00 = (t00 * determinant_inv);
      m11 = (t11 * determinant_inv);
      m22 = (t22 * determinant_inv);
      m33 = (t33 * determinant_inv);
      m01 = (t10 * determinant_inv);
      m10 = (t01 * determinant_inv);
      m20 = (t02 * determinant_inv);
      m02 = (t20 * determinant_inv);
      m12 = (t21 * determinant_inv);
      m21 = (t12 * determinant_inv);
      m03 = (t30 * determinant_inv);
      m30 = (t03 * determinant_inv);
      m13 = (t31 * determinant_inv);
      m31 = (t13 * determinant_inv);
      m32 = (t23 * determinant_inv);
      m23 = (t32 * determinant_inv);
      return dest;
    }
    return null;
  }
  



  public Matrix negate()
  {
    return negate(this);
  }
  




  public Matrix4f negate(Matrix4f dest)
  {
    return negate(this, dest);
  }
  





  public static Matrix4f negate(Matrix4f src, Matrix4f dest)
  {
    if (dest == null) {
      dest = new Matrix4f();
    }
    m00 = (-m00);
    m01 = (-m01);
    m02 = (-m02);
    m03 = (-m03);
    m10 = (-m10);
    m11 = (-m11);
    m12 = (-m12);
    m13 = (-m13);
    m20 = (-m20);
    m21 = (-m21);
    m22 = (-m22);
    m23 = (-m23);
    m30 = (-m30);
    m31 = (-m31);
    m32 = (-m32);
    m33 = (-m33);
    
    return dest;
  }
}
