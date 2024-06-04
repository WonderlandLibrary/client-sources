package org.spongycastle.pqc.math.ntru.polynomial;

import org.spongycastle.util.Arrays;











public class LongPolynomial2
{
  private long[] coeffs;
  private int numCoeffs;
  
  public LongPolynomial2(IntegerPolynomial p)
  {
    numCoeffs = coeffs.length;
    coeffs = new long[(numCoeffs + 1) / 2];
    int idx = 0;
    for (int pIdx = 0; pIdx < numCoeffs;)
    {
      int c0 = coeffs[(pIdx++)];
      while (c0 < 0)
      {
        c0 += 2048;
      }
      long c1 = pIdx < numCoeffs ? coeffs[(pIdx++)] : 0L;
      while (c1 < 0L)
      {
        c1 += 2048L;
      }
      coeffs[idx] = (c0 + (c1 << 24));
      idx++;
    }
  }
  
  private LongPolynomial2(long[] coeffs)
  {
    this.coeffs = coeffs;
  }
  
  private LongPolynomial2(int N)
  {
    coeffs = new long[N];
  }
  



  public LongPolynomial2 mult(LongPolynomial2 poly2)
  {
    int N = coeffs.length;
    if ((coeffs.length != N) || (numCoeffs != numCoeffs))
    {
      throw new IllegalArgumentException("Number of coefficients must be the same");
    }
    
    LongPolynomial2 c = multRecursive(poly2);
    
    if (coeffs.length > N)
    {
      if (numCoeffs % 2 == 0)
      {
        for (int k = N; k < coeffs.length; k++)
        {
          coeffs[(k - N)] = (coeffs[(k - N)] + coeffs[k] & 0x7FF0007FF);
        }
        coeffs = Arrays.copyOf(coeffs, N);
      }
      else
      {
        for (int k = N; k < coeffs.length; k++)
        {
          coeffs[(k - N)] += (coeffs[(k - 1)] >> 24);
          coeffs[(k - N)] += ((coeffs[k] & 0x7FF) << 24);
          coeffs[(k - N)] &= 0x7FF0007FF;
        }
        coeffs = Arrays.copyOf(coeffs, N);
        coeffs[(coeffs.length - 1)] &= 0x7FF;
      }
    }
    
    c = new LongPolynomial2(coeffs);
    numCoeffs = numCoeffs;
    return c;
  }
  
  public IntegerPolynomial toIntegerPolynomial()
  {
    int[] intCoeffs = new int[numCoeffs];
    int uIdx = 0;
    for (int i = 0; i < coeffs.length; i++)
    {
      intCoeffs[(uIdx++)] = ((int)(coeffs[i] & 0x7FF));
      if (uIdx < numCoeffs)
      {
        intCoeffs[(uIdx++)] = ((int)(coeffs[i] >> 24 & 0x7FF));
      }
    }
    return new IntegerPolynomial(intCoeffs);
  }
  



  private LongPolynomial2 multRecursive(LongPolynomial2 poly2)
  {
    long[] a = coeffs;
    long[] b = coeffs;
    
    int n = coeffs.length;
    if (n <= 32)
    {
      int cn = 2 * n;
      LongPolynomial2 c = new LongPolynomial2(new long[cn]);
      for (int k = 0; k < cn; k++)
      {
        for (int i = Math.max(0, k - n + 1); i <= Math.min(k, n - 1); i++)
        {
          long c0 = a[(k - i)] * b[i];
          long cu = c0 & 34342961152L + (c0 & 0x7FF);
          long co = c0 >>> 48 & 0x7FF;
          
          coeffs[k] = (coeffs[k] + cu & 0x7FF0007FF);
          coeffs[(k + 1)] = (coeffs[(k + 1)] + co & 0x7FF0007FF);
        }
      }
      return c;
    }
    

    int n1 = n / 2;
    
    LongPolynomial2 a1 = new LongPolynomial2(Arrays.copyOf(a, n1));
    LongPolynomial2 a2 = new LongPolynomial2(Arrays.copyOfRange(a, n1, n));
    LongPolynomial2 b1 = new LongPolynomial2(Arrays.copyOf(b, n1));
    LongPolynomial2 b2 = new LongPolynomial2(Arrays.copyOfRange(b, n1, n));
    
    LongPolynomial2 A = (LongPolynomial2)a1.clone();
    A.add(a2);
    LongPolynomial2 B = (LongPolynomial2)b1.clone();
    B.add(b2);
    
    LongPolynomial2 c1 = a1.multRecursive(b1);
    LongPolynomial2 c2 = a2.multRecursive(b2);
    LongPolynomial2 c3 = A.multRecursive(B);
    c3.sub(c1);
    c3.sub(c2);
    
    LongPolynomial2 c = new LongPolynomial2(2 * n);
    for (int i = 0; i < coeffs.length; i++)
    {
      coeffs[i] &= 0x7FF0007FF;
    }
    for (int i = 0; i < coeffs.length; i++)
    {
      coeffs[(n1 + i)] = (coeffs[(n1 + i)] + coeffs[i] & 0x7FF0007FF);
    }
    for (int i = 0; i < coeffs.length; i++)
    {
      coeffs[(2 * n1 + i)] = (coeffs[(2 * n1 + i)] + coeffs[i] & 0x7FF0007FF);
    }
    return c;
  }
  






  private void add(LongPolynomial2 b)
  {
    if (coeffs.length > coeffs.length)
    {
      coeffs = Arrays.copyOf(coeffs, coeffs.length);
    }
    for (int i = 0; i < coeffs.length; i++)
    {
      coeffs[i] = (coeffs[i] + coeffs[i] & 0x7FF0007FF);
    }
  }
  





  private void sub(LongPolynomial2 b)
  {
    if (coeffs.length > coeffs.length)
    {
      coeffs = Arrays.copyOf(coeffs, coeffs.length);
    }
    for (int i = 0; i < coeffs.length; i++)
    {
      coeffs[i] = (140737496743936L + coeffs[i] - coeffs[i] & 0x7FF0007FF);
    }
  }
  







  public void subAnd(LongPolynomial2 b, int mask)
  {
    long longMask = (mask << 24) + mask;
    for (int i = 0; i < coeffs.length; i++)
    {
      coeffs[i] = (140737496743936L + coeffs[i] - coeffs[i] & longMask);
    }
  }
  






  public void mult2And(int mask)
  {
    long longMask = (mask << 24) + mask;
    for (int i = 0; i < coeffs.length; i++)
    {
      coeffs[i] = (coeffs[i] << 1 & longMask);
    }
  }
  
  public Object clone()
  {
    LongPolynomial2 p = new LongPolynomial2((long[])coeffs.clone());
    numCoeffs = numCoeffs;
    return p;
  }
  
  public boolean equals(Object obj)
  {
    if ((obj instanceof LongPolynomial2))
    {
      return Arrays.areEqual(coeffs, coeffs);
    }
    

    return false;
  }
}
