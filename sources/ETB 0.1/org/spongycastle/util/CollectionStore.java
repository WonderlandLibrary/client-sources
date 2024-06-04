package org.spongycastle.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;










public class CollectionStore<T>
  implements Store<T>, Iterable<T>
{
  private Collection<T> _local;
  
  public CollectionStore(Collection<T> collection)
  {
    _local = new ArrayList(collection);
  }
  






  public Collection<T> getMatches(Selector<T> selector)
  {
    if (selector == null)
    {
      return new ArrayList(_local);
    }
    

    List<T> col = new ArrayList();
    Iterator<T> iter = _local.iterator();
    
    while (iter.hasNext())
    {
      T obj = iter.next();
      
      if (selector.match(obj))
      {
        col.add(obj);
      }
    }
    
    return col;
  }
  

  public Iterator<T> iterator()
  {
    return getMatches(null).iterator();
  }
}
