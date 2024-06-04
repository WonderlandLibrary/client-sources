package org.spongycastle.math.field;

public abstract interface ExtensionField
  extends FiniteField
{
  public abstract FiniteField getSubfield();
  
  public abstract int getDegree();
}
