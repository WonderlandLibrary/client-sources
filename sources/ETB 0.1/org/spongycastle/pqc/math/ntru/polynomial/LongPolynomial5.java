package org.spongycastle.pqc.math.ntru.polynomial;

import org.spongycastle.util.Arrays;











public class LongPolynomial5
{
  private long[] coeffs;
  private int numCoeffs;
  
  public LongPolynomial5(IntegerPolynomial p)
  {
    numCoeffs = coeffs.length;
    
    coeffs = new long[(numCoeffs + 4) / 5];
    int cIdx = 0;
    int shift = 0;
    for (int i = 0; i < numCoeffs; i++)
    {
      coeffs[cIdx] |= coeffs[i] << shift;
      shift += 12;
      if (shift >= 60)
      {
        shift = 0;
        cIdx++;
      }
    }
  }
  
  private LongPolynomial5(long[] coeffs, int numCoeffs)
  {
    this.coeffs = coeffs;
    this.numCoeffs = numCoeffs;
  }
  



  public LongPolynomial5 mult(TernaryPolynomial poly2)
  {
    long[][] prod = new long[5][coeffs.length + (poly2.size() + 4) / 5 - 1];
    

    int[] ones = poly2.getOnes();
    for (int idx = 0; idx != ones.length; idx++)
    {
      int pIdx = ones[idx];
      int cIdx = pIdx / 5;
      int m = pIdx - cIdx * 5;
      for (int i = 0; i < coeffs.length; i++)
      {
        prod[m][cIdx] = (prod[m][cIdx] + coeffs[i] & 0x7FF7FF7FF7FF7FF);
        cIdx++;
      }
    }
    

    int[] negOnes = poly2.getNegOnes();
    for (int idx = 0; idx != negOnes.length; idx++)
    {
      int pIdx = negOnes[idx];
      int cIdx = pIdx / 5;
      int m = pIdx - cIdx * 5;
      for (int i = 0; i < coeffs.length; i++)
      {
        prod[m][cIdx] = (576601524159907840L + prod[m][cIdx] - coeffs[i] & 0x7FF7FF7FF7FF7FF);
        cIdx++;
      }
    }
    

    long[] cCoeffs = Arrays.copyOf(prod[0], prod[0].length + 1);
    for (int m = 1; m <= 4; m++)
    {
      int shift = m * 12;
      int shift60 = 60 - shift;
      long mask = (1L << shift60) - 1L;
      int pLen = prod[m].length;
      for (int i = 0; i < pLen; i++)
      {

        long upper = prod[m][i] >> shift60;
        long lower = prod[m][i] & mask;
        
        cCoeffs[i] = (cCoeffs[i] + (lower << shift) & 0x7FF7FF7FF7FF7FF);
        int nextIdx = i + 1;
        cCoeffs[nextIdx] = (cCoeffs[nextIdx] + upper & 0x7FF7FF7FF7FF7FF);
      }
    }
    

    int shift = 12 * (numCoeffs % 5);
    for (int cIdx = coeffs.length - 1; cIdx < cCoeffs.length; cIdx++) {
      int newIdx;
      long iCoeff;
      int newIdx;
      if (cIdx == coeffs.length - 1)
      {
        long iCoeff = numCoeffs == 5 ? 0L : cCoeffs[cIdx] >> shift;
        newIdx = 0;
      }
      else
      {
        iCoeff = cCoeffs[cIdx];
        newIdx = cIdx * 5 - numCoeffs;
      }
      
      int base = newIdx / 5;
      int m = newIdx - base * 5;
      long lower = iCoeff << 12 * m;
      long upper = iCoeff >> 12 * (5 - m);
      cCoeffs[base] = (cCoeffs[base] + lower & 0x7FF7FF7FF7FF7FF);
      int base1 = base + 1;
      if (base1 < coeffs.length)
      {
        cCoeffs[base1] = (cCoeffs[base1] + upper & 0x7FF7FF7FF7FF7FF);
      }
    }
    
    return new LongPolynomial5(cCoeffs, numCoeffs);
  }
  
  public IntegerPolynomial toIntegerPolynomial()
  {
    int[] intCoeffs = new int[numCoeffs];
    int cIdx = 0;
    int shift = 0;
    for (int i = 0; i < numCoeffs; i++)
    {
      intCoeffs[i] = ((int)(coeffs[cIdx] >> shift & 0x7FF));
      shift += 12;
      if (shift >= 60)
      {
        shift = 0;
        cIdx++;
      }
    }
    return new IntegerPolynomial(intCoeffs);
  }
}
