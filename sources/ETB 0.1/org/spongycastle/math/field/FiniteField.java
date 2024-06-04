package org.spongycastle.math.field;

import java.math.BigInteger;

public abstract interface FiniteField
{
  public abstract BigInteger getCharacteristic();
  
  public abstract int getDimension();
}
