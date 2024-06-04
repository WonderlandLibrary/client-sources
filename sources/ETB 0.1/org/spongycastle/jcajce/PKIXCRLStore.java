package org.spongycastle.jcajce;

import java.security.cert.CRL;
import java.util.Collection;
import org.spongycastle.util.Selector;
import org.spongycastle.util.Store;
import org.spongycastle.util.StoreException;

public abstract interface PKIXCRLStore<T extends CRL>
  extends Store<T>
{
  public abstract Collection<T> getMatches(Selector<T> paramSelector)
    throws StoreException;
}
