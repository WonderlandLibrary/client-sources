package org.spongycastle.math.field;

import org.spongycastle.util.Arrays;

class GF2Polynomial implements Polynomial
{
  protected final int[] exponents;
  
  GF2Polynomial(int[] exponents)
  {
    this.exponents = Arrays.clone(exponents);
  }
  
  public int getDegree()
  {
    return exponents[(exponents.length - 1)];
  }
  
  public int[] getExponentsPresent()
  {
    return Arrays.clone(exponents);
  }
  
  public boolean equals(Object obj)
  {
    if (this == obj)
    {
      return true;
    }
    if (!(obj instanceof GF2Polynomial))
    {
      return false;
    }
    GF2Polynomial other = (GF2Polynomial)obj;
    return Arrays.areEqual(exponents, exponents);
  }
  
  public int hashCode()
  {
    return Arrays.hashCode(exponents);
  }
}
