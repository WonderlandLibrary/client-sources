package org.spongycastle.pqc.math.ntru.polynomial;

public abstract interface Polynomial
{
  public abstract IntegerPolynomial mult(IntegerPolynomial paramIntegerPolynomial);
  
  public abstract IntegerPolynomial mult(IntegerPolynomial paramIntegerPolynomial, int paramInt);
  
  public abstract IntegerPolynomial toIntegerPolynomial();
  
  public abstract BigIntPolynomial mult(BigIntPolynomial paramBigIntPolynomial);
}
