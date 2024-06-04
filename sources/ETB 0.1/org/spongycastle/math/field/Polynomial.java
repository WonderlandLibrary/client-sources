package org.spongycastle.math.field;

public abstract interface Polynomial
{
  public abstract int getDegree();
  
  public abstract int[] getExponentsPresent();
}
