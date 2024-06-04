package org.spongycastle.x509;

import java.util.Collection;
import org.spongycastle.util.Selector;

public abstract class X509StoreSpi
{
  public X509StoreSpi() {}
  
  public abstract void engineInit(X509StoreParameters paramX509StoreParameters);
  
  public abstract Collection engineGetMatches(Selector paramSelector);
}
