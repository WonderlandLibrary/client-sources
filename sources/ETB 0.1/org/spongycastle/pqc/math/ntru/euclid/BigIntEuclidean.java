package org.spongycastle.pqc.math.ntru.euclid;

import java.math.BigInteger;












public class BigIntEuclidean
{
  public BigInteger x;
  public BigInteger y;
  public BigInteger gcd;
  
  private BigIntEuclidean() {}
  
  public static BigIntEuclidean calculate(BigInteger a, BigInteger b)
  {
    BigInteger x = BigInteger.ZERO;
    BigInteger lastx = BigInteger.ONE;
    BigInteger y = BigInteger.ONE;
    BigInteger lasty = BigInteger.ZERO;
    while (!b.equals(BigInteger.ZERO))
    {
      BigInteger[] quotientAndRemainder = a.divideAndRemainder(b);
      BigInteger quotient = quotientAndRemainder[0];
      
      BigInteger temp = a;
      a = b;
      b = quotientAndRemainder[1];
      
      temp = x;
      x = lastx.subtract(quotient.multiply(x));
      lastx = temp;
      
      temp = y;
      y = lasty.subtract(quotient.multiply(y));
      lasty = temp;
    }
    
    BigIntEuclidean result = new BigIntEuclidean();
    x = lastx;
    y = lasty;
    gcd = a;
    return result;
  }
}
