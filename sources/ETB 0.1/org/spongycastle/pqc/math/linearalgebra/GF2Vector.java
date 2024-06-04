package org.spongycastle.pqc.math.linearalgebra;

import java.security.SecureRandom;


















public class GF2Vector
  extends Vector
{
  private int[] v;
  
  public GF2Vector(int length)
  {
    if (length < 0)
    {
      throw new ArithmeticException("Negative length.");
    }
    this.length = length;
    v = new int[length + 31 >> 5];
  }
  






  public GF2Vector(int length, SecureRandom sr)
  {
    this.length = length;
    
    int size = length + 31 >> 5;
    v = new int[size];
    

    for (int i = size - 1; i >= 0; i--)
    {
      v[i] = sr.nextInt();
    }
    

    int r = length & 0x1F;
    if (r != 0)
    {

      v[(size - 1)] &= (1 << r) - 1;
    }
  }
  








  public GF2Vector(int length, int t, SecureRandom sr)
  {
    if (t > length)
    {
      throw new ArithmeticException("The hamming weight is greater than the length of vector.");
    }
    
    this.length = length;
    
    int size = length + 31 >> 5;
    v = new int[size];
    
    int[] help = new int[length];
    for (int i = 0; i < length; i++)
    {
      help[i] = i;
    }
    
    int m = length;
    for (int i = 0; i < t; i++)
    {
      int j = RandUtils.nextInt(sr, m);
      setBit(help[j]);
      m--;
      help[j] = help[m];
    }
  }
  







  public GF2Vector(int length, int[] v)
  {
    if (length < 0)
    {
      throw new ArithmeticException("negative length");
    }
    this.length = length;
    
    int size = length + 31 >> 5;
    
    if (v.length != size)
    {
      throw new ArithmeticException("length mismatch");
    }
    
    this.v = IntUtils.clone(v);
    
    int r = length & 0x1F;
    if (r != 0)
    {

      this.v[(size - 1)] &= (1 << r) - 1;
    }
  }
  





  public GF2Vector(GF2Vector other)
  {
    length = length;
    v = IntUtils.clone(v);
  }
  








  protected GF2Vector(int[] v, int length)
  {
    this.v = v;
    this.length = length;
  }
  








  public static GF2Vector OS2VP(int length, byte[] encVec)
  {
    if (length < 0)
    {
      throw new ArithmeticException("negative length");
    }
    
    int byteLen = length + 7 >> 3;
    
    if (encVec.length > byteLen)
    {
      throw new ArithmeticException("length mismatch");
    }
    
    return new GF2Vector(length, LittleEndianConversions.toIntArray(encVec));
  }
  





  public byte[] getEncoded()
  {
    int byteLen = length + 7 >> 3;
    return LittleEndianConversions.toByteArray(v, byteLen);
  }
  



  public int[] getVecArray()
  {
    return v;
  }
  






  public int getHammingWeight()
  {
    int weight = 0;
    for (int i = 0; i < v.length; i++)
    {
      int e = v[i];
      for (int j = 0; j < 32; j++)
      {
        int b = e & 0x1;
        if (b != 0)
        {
          weight++;
        }
        e >>>= 1;
      }
    }
    return weight;
  }
  



  public boolean isZero()
  {
    for (int i = v.length - 1; i >= 0; i--)
    {
      if (v[i] != 0)
      {
        return false;
      }
    }
    return true;
  }
  






  public int getBit(int index)
  {
    if (index >= length)
    {
      throw new IndexOutOfBoundsException();
    }
    int q = index >> 5;
    int r = index & 0x1F;
    return (v[q] & 1 << r) >>> r;
  }
  






  public void setBit(int index)
  {
    if (index >= length)
    {
      throw new IndexOutOfBoundsException();
    }
    v[(index >> 5)] |= 1 << (index & 0x1F);
  }
  








  public Vector add(Vector other)
  {
    if (!(other instanceof GF2Vector))
    {
      throw new ArithmeticException("vector is not defined over GF(2)");
    }
    
    GF2Vector otherVec = (GF2Vector)other;
    if (length != length)
    {
      throw new ArithmeticException("length mismatch");
    }
    
    int[] vec = IntUtils.clone(v);
    
    for (int i = vec.length - 1; i >= 0; i--)
    {
      vec[i] ^= v[i];
    }
    
    return new GF2Vector(length, vec);
  }
  






  public Vector multiply(Permutation p)
  {
    int[] pVec = p.getVector();
    if (length != pVec.length)
    {
      throw new ArithmeticException("length mismatch");
    }
    
    GF2Vector result = new GF2Vector(length);
    
    for (int i = 0; i < pVec.length; i++)
    {
      int e = v[(pVec[i] >> 5)] & 1 << (pVec[i] & 0x1F);
      if (e != 0)
      {
        v[(i >> 5)] |= 1 << (i & 0x1F);
      }
    }
    
    return result;
  }
  








  public GF2Vector extractVector(int[] setJ)
  {
    int k = setJ.length;
    if (setJ[(k - 1)] > length)
    {
      throw new ArithmeticException("invalid index set");
    }
    
    GF2Vector result = new GF2Vector(k);
    
    for (int i = 0; i < k; i++)
    {
      int e = v[(setJ[i] >> 5)] & 1 << (setJ[i] & 0x1F);
      if (e != 0)
      {
        v[(i >> 5)] |= 1 << (i & 0x1F);
      }
    }
    
    return result;
  }
  








  public GF2Vector extractLeftVector(int k)
  {
    if (k > length)
    {
      throw new ArithmeticException("invalid length");
    }
    
    if (k == length)
    {
      return new GF2Vector(this);
    }
    
    GF2Vector result = new GF2Vector(k);
    
    int q = k >> 5;
    int r = k & 0x1F;
    
    System.arraycopy(v, 0, v, 0, q);
    if (r != 0)
    {
      v[q] &= (1 << r) - 1;
    }
    
    return result;
  }
  








  public GF2Vector extractRightVector(int k)
  {
    if (k > this.length)
    {
      throw new ArithmeticException("invalid length");
    }
    
    if (k == this.length)
    {
      return new GF2Vector(this);
    }
    
    GF2Vector result = new GF2Vector(k);
    
    int q = this.length - k >> 5;
    int r = this.length - k & 0x1F;
    int length = k + 31 >> 5;
    
    int ind = q;
    
    if (r != 0)
    {

      for (int i = 0; i < length - 1; i++)
      {
        v[i] = (v[(ind++)] >>> r | v[ind] << 32 - r);
      }
      
      v[(length - 1)] = (v[(ind++)] >>> r);
      if (ind < v.length)
      {
        v[(length - 1)] |= v[ind] << 32 - r;
      }
      
    }
    else
    {
      System.arraycopy(v, q, v, 0, length);
    }
    
    return result;
  }
  







  public GF2mVector toExtensionFieldVector(GF2mField field)
  {
    int m = field.getDegree();
    if (length % m != 0)
    {
      throw new ArithmeticException("conversion is impossible");
    }
    
    int t = length / m;
    int[] result = new int[t];
    int count = 0;
    for (int i = t - 1; i >= 0; i--)
    {
      for (int j = field.getDegree() - 1; j >= 0; j--)
      {
        int q = count >>> 5;
        int r = count & 0x1F;
        
        int e = v[q] >>> r & 0x1;
        if (e == 1)
        {
          result[i] ^= 1 << j;
        }
        count++;
      }
    }
    return new GF2mVector(field, result);
  }
  







  public boolean equals(Object other)
  {
    if (!(other instanceof GF2Vector))
    {
      return false;
    }
    GF2Vector otherVec = (GF2Vector)other;
    
    return (length == length) && (IntUtils.equals(v, v));
  }
  



  public int hashCode()
  {
    int hash = length;
    hash = hash * 31 + v.hashCode();
    return hash;
  }
  



  public String toString()
  {
    StringBuffer buf = new StringBuffer();
    for (int i = 0; i < length; i++)
    {
      if ((i != 0) && ((i & 0x1F) == 0))
      {
        buf.append(' ');
      }
      int q = i >> 5;
      int r = i & 0x1F;
      int bit = v[q] & 1 << r;
      if (bit == 0)
      {
        buf.append('0');
      }
      else
      {
        buf.append('1');
      }
    }
    return buf.toString();
  }
}
