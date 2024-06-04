package org.spongycastle.pqc.math.ntru.polynomial;

public abstract interface TernaryPolynomial
  extends Polynomial
{
  public abstract IntegerPolynomial mult(IntegerPolynomial paramIntegerPolynomial);
  
  public abstract int[] getOnes();
  
  public abstract int[] getNegOnes();
  
  public abstract int size();
  
  public abstract void clear();
}
