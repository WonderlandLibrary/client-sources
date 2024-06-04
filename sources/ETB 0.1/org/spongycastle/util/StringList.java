package org.spongycastle.util;

public abstract interface StringList
  extends Iterable<String>
{
  public abstract boolean add(String paramString);
  
  public abstract String get(int paramInt);
  
  public abstract int size();
  
  public abstract String[] toStringArray();
  
  public abstract String[] toStringArray(int paramInt1, int paramInt2);
}
