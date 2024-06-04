package org.spongycastle.jce.provider;

import java.util.Collection;
import org.spongycastle.util.CollectionStore;
import org.spongycastle.util.Selector;
import org.spongycastle.x509.X509CollectionStoreParameters;
import org.spongycastle.x509.X509StoreParameters;
import org.spongycastle.x509.X509StoreSpi;



















public class X509StoreCertPairCollection
  extends X509StoreSpi
{
  private CollectionStore _store;
  
  public X509StoreCertPairCollection() {}
  
  public void engineInit(X509StoreParameters params)
  {
    if (!(params instanceof X509CollectionStoreParameters))
    {


      throw new IllegalArgumentException("Initialization parameters must be an instance of " + X509CollectionStoreParameters.class.getName() + ".");
    }
    


    _store = new CollectionStore(((X509CollectionStoreParameters)params).getCollection());
  }
  











  public Collection engineGetMatches(Selector selector)
  {
    return _store.getMatches(selector);
  }
}
