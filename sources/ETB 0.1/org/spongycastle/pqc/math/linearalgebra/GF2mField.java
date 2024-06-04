package org.spongycastle.pqc.math.linearalgebra;

import java.security.SecureRandom;



















public class GF2mField
{
  private int degree = 0;
  


  private int polynomial;
  



  public GF2mField(int degree)
  {
    if (degree >= 32)
    {
      throw new IllegalArgumentException(" Error: the degree of field is too large ");
    }
    
    if (degree < 1)
    {
      throw new IllegalArgumentException(" Error: the degree of field is non-positive ");
    }
    
    this.degree = degree;
    polynomial = PolynomialRingGF2.getIrreduciblePolynomial(degree);
  }
  






  public GF2mField(int degree, int poly)
  {
    if (degree != PolynomialRingGF2.degree(poly))
    {
      throw new IllegalArgumentException(" Error: the degree is not correct");
    }
    
    if (!PolynomialRingGF2.isIrreducible(poly))
    {
      throw new IllegalArgumentException(" Error: given polynomial is reducible");
    }
    
    this.degree = degree;
    polynomial = poly;
  }
  

  public GF2mField(byte[] enc)
  {
    if (enc.length != 4)
    {
      throw new IllegalArgumentException("byte array is not an encoded finite field");
    }
    
    polynomial = LittleEndianConversions.OS2IP(enc);
    if (!PolynomialRingGF2.isIrreducible(polynomial))
    {
      throw new IllegalArgumentException("byte array is not an encoded finite field");
    }
    

    degree = PolynomialRingGF2.degree(polynomial);
  }
  
  public GF2mField(GF2mField field)
  {
    degree = degree;
    polynomial = polynomial;
  }
  





  public int getDegree()
  {
    return degree;
  }
  





  public int getPolynomial()
  {
    return polynomial;
  }
  





  public byte[] getEncoded()
  {
    return LittleEndianConversions.I2OSP(polynomial);
  }
  







  public int add(int a, int b)
  {
    return a ^ b;
  }
  







  public int mult(int a, int b)
  {
    return PolynomialRingGF2.modMultiply(a, b, polynomial);
  }
  







  public int exp(int a, int k)
  {
    if (k == 0)
    {
      return 1;
    }
    if (a == 0)
    {
      return 0;
    }
    if (a == 1)
    {
      return 1;
    }
    int result = 1;
    if (k < 0)
    {
      a = inverse(a);
      k = -k;
    }
    while (k != 0)
    {
      if ((k & 0x1) == 1)
      {
        result = mult(result, a);
      }
      a = mult(a, a);
      k >>>= 1;
    }
    return result;
  }
  






  public int inverse(int a)
  {
    int d = (1 << degree) - 2;
    
    return exp(a, d);
  }
  






  public int sqRoot(int a)
  {
    for (int i = 1; i < degree; i++)
    {
      a = mult(a, a);
    }
    return a;
  }
  






  public int getRandomElement(SecureRandom sr)
  {
    int result = RandUtils.nextInt(sr, 1 << degree);
    return result;
  }
  





  public int getRandomNonZeroElement()
  {
    return getRandomNonZeroElement(new SecureRandom());
  }
  






  public int getRandomNonZeroElement(SecureRandom sr)
  {
    int controltime = 1048576;
    int count = 0;
    int result = RandUtils.nextInt(sr, 1 << degree);
    while ((result == 0) && (count < controltime))
    {
      result = RandUtils.nextInt(sr, 1 << degree);
      count++;
    }
    if (count == controltime)
    {
      result = 1;
    }
    return result;
  }
  




  public boolean isElementOfThisField(int e)
  {
    if (degree == 31)
    {
      return e >= 0;
    }
    return (e >= 0) && (e < 1 << degree);
  }
  



  public String elementToStr(int a)
  {
    String s = "";
    for (int i = 0; i < degree; i++)
    {
      if (((byte)a & 0x1) == 0)
      {
        s = "0" + s;
      }
      else
      {
        s = "1" + s;
      }
      a >>>= 1;
    }
    return s;
  }
  








  public boolean equals(Object other)
  {
    if ((other == null) || (!(other instanceof GF2mField)))
    {
      return false;
    }
    
    GF2mField otherField = (GF2mField)other;
    
    if ((degree == degree) && (polynomial == polynomial))
    {

      return true;
    }
    
    return false;
  }
  
  public int hashCode()
  {
    return polynomial;
  }
  






  public String toString()
  {
    String str = "Finite Field GF(2^" + degree + ") = GF(2)[X]/<" + polyToString(polynomial) + "> ";
    return str;
  }
  
  private static String polyToString(int p)
  {
    String str = "";
    if (p == 0)
    {
      str = "0";
    }
    else
    {
      byte b = (byte)(p & 0x1);
      if (b == 1)
      {
        str = "1";
      }
      p >>>= 1;
      int i = 1;
      while (p != 0)
      {
        b = (byte)(p & 0x1);
        if (b == 1)
        {
          str = str + "+x^" + i;
        }
        p >>>= 1;
        i++;
      }
    }
    return str;
  }
}
