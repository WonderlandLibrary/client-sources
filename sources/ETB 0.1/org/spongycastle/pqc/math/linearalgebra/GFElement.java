package org.spongycastle.pqc.math.linearalgebra;

import java.math.BigInteger;

public abstract interface GFElement
{
  public abstract Object clone();
  
  public abstract boolean equals(Object paramObject);
  
  public abstract int hashCode();
  
  public abstract boolean isZero();
  
  public abstract boolean isOne();
  
  public abstract GFElement add(GFElement paramGFElement)
    throws RuntimeException;
  
  public abstract void addToThis(GFElement paramGFElement)
    throws RuntimeException;
  
  public abstract GFElement subtract(GFElement paramGFElement)
    throws RuntimeException;
  
  public abstract void subtractFromThis(GFElement paramGFElement);
  
  public abstract GFElement multiply(GFElement paramGFElement)
    throws RuntimeException;
  
  public abstract void multiplyThisBy(GFElement paramGFElement)
    throws RuntimeException;
  
  public abstract GFElement invert()
    throws ArithmeticException;
  
  public abstract BigInteger toFlexiBigInt();
  
  public abstract byte[] toByteArray();
  
  public abstract String toString();
  
  public abstract String toString(int paramInt);
}
