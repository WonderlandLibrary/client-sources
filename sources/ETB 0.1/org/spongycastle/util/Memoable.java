package org.spongycastle.util;

public abstract interface Memoable
{
  public abstract Memoable copy();
  
  public abstract void reset(Memoable paramMemoable);
}
