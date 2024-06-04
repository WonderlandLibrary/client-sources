package org.spongycastle.pqc.math.linearalgebra;




public abstract class Vector
{
  protected int length;
  



  public Vector() {}
  


  public final int getLength()
  {
    return length;
  }
  
  public abstract byte[] getEncoded();
  
  public abstract boolean isZero();
  
  public abstract Vector add(Vector paramVector);
  
  public abstract Vector multiply(Permutation paramPermutation);
  
  public abstract boolean equals(Object paramObject);
  
  public abstract int hashCode();
  
  public abstract String toString();
}
