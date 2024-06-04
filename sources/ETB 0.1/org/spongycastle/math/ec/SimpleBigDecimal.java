package org.spongycastle.math.ec;

import java.math.BigInteger;
























class SimpleBigDecimal
{
  private static final long serialVersionUID = 1L;
  private final BigInteger bigInt;
  private final int scale;
  
  public static SimpleBigDecimal getInstance(BigInteger value, int scale)
  {
    return new SimpleBigDecimal(value.shiftLeft(scale), scale);
  }
  







  public SimpleBigDecimal(BigInteger bigInt, int scale)
  {
    if (scale < 0)
    {
      throw new IllegalArgumentException("scale may not be negative");
    }
    
    this.bigInt = bigInt;
    this.scale = scale;
  }
  
  private void checkScale(SimpleBigDecimal b)
  {
    if (scale != scale)
    {
      throw new IllegalArgumentException("Only SimpleBigDecimal of same scale allowed in arithmetic operations");
    }
  }
  

  public SimpleBigDecimal adjustScale(int newScale)
  {
    if (newScale < 0)
    {
      throw new IllegalArgumentException("scale may not be negative");
    }
    
    if (newScale == scale)
    {
      return this;
    }
    
    return new SimpleBigDecimal(bigInt.shiftLeft(newScale - scale), newScale);
  }
  

  public SimpleBigDecimal add(SimpleBigDecimal b)
  {
    checkScale(b);
    return new SimpleBigDecimal(bigInt.add(bigInt), scale);
  }
  
  public SimpleBigDecimal add(BigInteger b)
  {
    return new SimpleBigDecimal(bigInt.add(b.shiftLeft(scale)), scale);
  }
  
  public SimpleBigDecimal negate()
  {
    return new SimpleBigDecimal(bigInt.negate(), scale);
  }
  
  public SimpleBigDecimal subtract(SimpleBigDecimal b)
  {
    return add(b.negate());
  }
  
  public SimpleBigDecimal subtract(BigInteger b)
  {
    return new SimpleBigDecimal(bigInt.subtract(b.shiftLeft(scale)), scale);
  }
  

  public SimpleBigDecimal multiply(SimpleBigDecimal b)
  {
    checkScale(b);
    return new SimpleBigDecimal(bigInt.multiply(bigInt), scale + scale);
  }
  
  public SimpleBigDecimal multiply(BigInteger b)
  {
    return new SimpleBigDecimal(bigInt.multiply(b), scale);
  }
  
  public SimpleBigDecimal divide(SimpleBigDecimal b)
  {
    checkScale(b);
    BigInteger dividend = bigInt.shiftLeft(scale);
    return new SimpleBigDecimal(dividend.divide(bigInt), scale);
  }
  
  public SimpleBigDecimal divide(BigInteger b)
  {
    return new SimpleBigDecimal(bigInt.divide(b), scale);
  }
  
  public SimpleBigDecimal shiftLeft(int n)
  {
    return new SimpleBigDecimal(bigInt.shiftLeft(n), scale);
  }
  
  public int compareTo(SimpleBigDecimal val)
  {
    checkScale(val);
    return bigInt.compareTo(bigInt);
  }
  
  public int compareTo(BigInteger val)
  {
    return bigInt.compareTo(val.shiftLeft(scale));
  }
  
  public BigInteger floor()
  {
    return bigInt.shiftRight(scale);
  }
  
  public BigInteger round()
  {
    SimpleBigDecimal oneHalf = new SimpleBigDecimal(ECConstants.ONE, 1);
    return add(oneHalf.adjustScale(scale)).floor();
  }
  
  public int intValue()
  {
    return floor().intValue();
  }
  
  public long longValue()
  {
    return floor().longValue();
  }
  










  public int getScale()
  {
    return scale;
  }
  
  public String toString()
  {
    if (scale == 0)
    {
      return bigInt.toString();
    }
    
    BigInteger floorBigInt = floor();
    
    BigInteger fract = bigInt.subtract(floorBigInt.shiftLeft(scale));
    if (bigInt.signum() == -1)
    {
      fract = ECConstants.ONE.shiftLeft(scale).subtract(fract);
    }
    
    if ((floorBigInt.signum() == -1) && (!fract.equals(ECConstants.ZERO)))
    {
      floorBigInt = floorBigInt.add(ECConstants.ONE);
    }
    String leftOfPoint = floorBigInt.toString();
    
    char[] fractCharArr = new char[scale];
    String fractStr = fract.toString(2);
    int fractLen = fractStr.length();
    int zeroes = scale - fractLen;
    for (int i = 0; i < zeroes; i++)
    {
      fractCharArr[i] = '0';
    }
    for (int j = 0; j < fractLen; j++)
    {
      fractCharArr[(zeroes + j)] = fractStr.charAt(j);
    }
    String rightOfPoint = new String(fractCharArr);
    
    StringBuffer sb = new StringBuffer(leftOfPoint);
    sb.append(".");
    sb.append(rightOfPoint);
    
    return sb.toString();
  }
  
  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }
    
    if (!(o instanceof SimpleBigDecimal))
    {
      return false;
    }
    
    SimpleBigDecimal other = (SimpleBigDecimal)o;
    return (bigInt.equals(bigInt)) && (scale == scale);
  }
  
  public int hashCode()
  {
    return bigInt.hashCode() ^ scale;
  }
}
