package org.spongycastle.x509;

import java.util.ArrayList;
import java.util.Collection;

















public class X509CollectionStoreParameters
  implements X509StoreParameters
{
  private Collection collection;
  
  public X509CollectionStoreParameters(Collection collection)
  {
    if (collection == null)
    {
      throw new NullPointerException("collection cannot be null");
    }
    this.collection = collection;
  }
  






  public Object clone()
  {
    return new X509CollectionStoreParameters(collection);
  }
  





  public Collection getCollection()
  {
    return new ArrayList(collection);
  }
  





  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append("X509CollectionStoreParameters: [\n");
    sb.append("  collection: " + collection + "\n");
    sb.append("]");
    return sb.toString();
  }
}
