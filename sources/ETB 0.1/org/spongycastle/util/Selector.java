package org.spongycastle.util;

public abstract interface Selector<T>
  extends Cloneable
{
  public abstract boolean match(T paramT);
  
  public abstract Object clone();
}
