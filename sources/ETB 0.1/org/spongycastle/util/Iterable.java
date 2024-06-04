package org.spongycastle.util;

import java.util.Iterator;

public abstract interface Iterable<T>
  extends java.lang.Iterable<T>
{
  public abstract Iterator<T> iterator();
}
