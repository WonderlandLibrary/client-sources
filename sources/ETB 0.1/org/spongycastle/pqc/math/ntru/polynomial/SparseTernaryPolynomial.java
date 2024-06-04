package org.spongycastle.pqc.math.ntru.polynomial;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.pqc.math.ntru.util.ArrayEncoder;
import org.spongycastle.pqc.math.ntru.util.Util;
import org.spongycastle.util.Arrays;
















public class SparseTernaryPolynomial
  implements TernaryPolynomial
{
  private static final int BITS_PER_INDEX = 11;
  private int N;
  private int[] ones;
  private int[] negOnes;
  
  SparseTernaryPolynomial(int N, int[] ones, int[] negOnes)
  {
    this.N = N;
    this.ones = ones;
    this.negOnes = negOnes;
  }
  






  public SparseTernaryPolynomial(IntegerPolynomial intPoly)
  {
    this(coeffs);
  }
  





  public SparseTernaryPolynomial(int[] coeffs)
  {
    N = coeffs.length;
    ones = new int[N];
    negOnes = new int[N];
    int onesIdx = 0;
    int negOnesIdx = 0;
    for (int i = 0; i < N; i++)
    {
      int c = coeffs[i];
      switch (c)
      {
      case 1: 
        ones[(onesIdx++)] = i;
        break;
      case -1: 
        negOnes[(negOnesIdx++)] = i;
        break;
      case 0: 
        break;
      default: 
        throw new IllegalArgumentException("Illegal value: " + c + ", must be one of {-1, 0, 1}");
      }
    }
    ones = Arrays.copyOf(ones, onesIdx);
    negOnes = Arrays.copyOf(negOnes, negOnesIdx);
  }
  










  public static SparseTernaryPolynomial fromBinary(InputStream is, int N, int numOnes, int numNegOnes)
    throws IOException
  {
    int maxIndex = 2048;
    int bitsPerIndex = 32 - Integer.numberOfLeadingZeros(maxIndex - 1);
    
    int data1Len = (numOnes * bitsPerIndex + 7) / 8;
    byte[] data1 = Util.readFullLength(is, data1Len);
    int[] ones = ArrayEncoder.decodeModQ(data1, numOnes, maxIndex);
    
    int data2Len = (numNegOnes * bitsPerIndex + 7) / 8;
    byte[] data2 = Util.readFullLength(is, data2Len);
    int[] negOnes = ArrayEncoder.decodeModQ(data2, numNegOnes, maxIndex);
    
    return new SparseTernaryPolynomial(N, ones, negOnes);
  }
  








  public static SparseTernaryPolynomial generateRandom(int N, int numOnes, int numNegOnes, SecureRandom random)
  {
    int[] coeffs = Util.generateRandomTernary(N, numOnes, numNegOnes, random);
    return new SparseTernaryPolynomial(coeffs);
  }
  
  public IntegerPolynomial mult(IntegerPolynomial poly2)
  {
    int[] b = coeffs;
    if (b.length != N)
    {
      throw new IllegalArgumentException("Number of coefficients must be the same");
    }
    
    int[] c = new int[N];
    for (int idx = 0; idx != ones.length; idx++)
    {
      int i = ones[idx];
      int j = N - 1 - i;
      for (int k = N - 1; k >= 0; k--)
      {
        c[k] += b[j];
        j--;
        if (j < 0)
        {
          j = N - 1;
        }
      }
    }
    
    for (int idx = 0; idx != negOnes.length; idx++)
    {
      int i = negOnes[idx];
      int j = N - 1 - i;
      for (int k = N - 1; k >= 0; k--)
      {
        c[k] -= b[j];
        j--;
        if (j < 0)
        {
          j = N - 1;
        }
      }
    }
    
    return new IntegerPolynomial(c);
  }
  
  public IntegerPolynomial mult(IntegerPolynomial poly2, int modulus)
  {
    IntegerPolynomial c = mult(poly2);
    c.mod(modulus);
    return c;
  }
  
  public BigIntPolynomial mult(BigIntPolynomial poly2)
  {
    BigInteger[] b = coeffs;
    if (b.length != N)
    {
      throw new IllegalArgumentException("Number of coefficients must be the same");
    }
    
    BigInteger[] c = new BigInteger[N];
    for (int i = 0; i < N; i++)
    {
      c[i] = BigInteger.ZERO;
    }
    
    for (int idx = 0; idx != ones.length; idx++)
    {
      int i = ones[idx];
      int j = N - 1 - i;
      for (int k = N - 1; k >= 0; k--)
      {
        c[k] = c[k].add(b[j]);
        j--;
        if (j < 0)
        {
          j = N - 1;
        }
      }
    }
    
    for (int idx = 0; idx != negOnes.length; idx++)
    {
      int i = negOnes[idx];
      int j = N - 1 - i;
      for (int k = N - 1; k >= 0; k--)
      {
        c[k] = c[k].subtract(b[j]);
        j--;
        if (j < 0)
        {
          j = N - 1;
        }
      }
    }
    
    return new BigIntPolynomial(c);
  }
  
  public int[] getOnes()
  {
    return ones;
  }
  
  public int[] getNegOnes()
  {
    return negOnes;
  }
  





  public byte[] toBinary()
  {
    int maxIndex = 2048;
    byte[] bin1 = ArrayEncoder.encodeModQ(ones, maxIndex);
    byte[] bin2 = ArrayEncoder.encodeModQ(negOnes, maxIndex);
    
    byte[] bin = Arrays.copyOf(bin1, bin1.length + bin2.length);
    System.arraycopy(bin2, 0, bin, bin1.length, bin2.length);
    return bin;
  }
  
  public IntegerPolynomial toIntegerPolynomial()
  {
    int[] coeffs = new int[N];
    for (int idx = 0; idx != ones.length; idx++)
    {
      int i = ones[idx];
      coeffs[i] = 1;
    }
    for (int idx = 0; idx != negOnes.length; idx++)
    {
      int i = negOnes[idx];
      coeffs[i] = -1;
    }
    return new IntegerPolynomial(coeffs);
  }
  
  public int size()
  {
    return N;
  }
  
  public void clear()
  {
    for (int i = 0; i < ones.length; i++)
    {
      ones[i] = 0;
    }
    for (int i = 0; i < negOnes.length; i++)
    {
      negOnes[i] = 0;
    }
  }
  
  public int hashCode()
  {
    int prime = 31;
    int result = 1;
    result = 31 * result + N;
    result = 31 * result + Arrays.hashCode(negOnes);
    result = 31 * result + Arrays.hashCode(ones);
    return result;
  }
  
  public boolean equals(Object obj)
  {
    if (this == obj)
    {
      return true;
    }
    if (obj == null)
    {
      return false;
    }
    if (getClass() != obj.getClass())
    {
      return false;
    }
    SparseTernaryPolynomial other = (SparseTernaryPolynomial)obj;
    if (N != N)
    {
      return false;
    }
    if (!Arrays.areEqual(negOnes, negOnes))
    {
      return false;
    }
    if (!Arrays.areEqual(ones, ones))
    {
      return false;
    }
    return true;
  }
}
