package org.spongycastle.util;

import java.util.Collection;

public abstract interface Store<T>
{
  public abstract Collection<T> getMatches(Selector<T> paramSelector)
    throws StoreException;
}
