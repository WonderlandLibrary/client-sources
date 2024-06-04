package org.silvertunnel_ng.netlib.nameservice.cache;

import java.util.Date;
import java.util.Map.Entry;





























class CacheEntry<K, V>
  implements Map.Entry<K, V>
{
  private K key;
  private V value;
  private Date expires;
  
  public CacheEntry(K key, V value, int timeToLiveSeconds)
  {
    this.key = key;
    this.value = value;
    expires = new Date(System.currentTimeMillis() + 1000L * timeToLiveSeconds);
  }
  






  public CacheEntry(V value, Date expires)
  {
    this.value = value;
    this.expires = expires;
  }
  
  public boolean isExpired()
  {
    if (expires == null)
    {
      return false;
    }
    

    return expires.before(new Date());
  }
  



  public boolean equals(Object o)
  {
    if ((o == null) || (!(o instanceof CacheEntry)))
    {
      return false;
    }
    
    CacheEntry<K, V> e1 = this;
    CacheEntry<K, V> e2 = (CacheEntry)o;
    
    if (e1.getKey() == null ? e2.getKey() == null : e1.getKey().equals(e2
      .getKey())) {}
    
    return e1.getValue() == null ? e2.getValue() == null : e1.getValue().equals(e2.getValue());
  }
  

  public int hashCode()
  {
    return key.hashCode();
  }
  

  public String toString()
  {
    return "(" + key + "," + value + "," + expires + ")";
  }
  

  public V setValue(V value)
  {
    V oldValue = this.value;
    this.value = value;
    return oldValue;
  }
  





  public K getKey()
  {
    return key;
  }
  

  public V getValue()
  {
    return value;
  }
  
  public Date getExpires()
  {
    return expires;
  }
  
  public void setExpires(Date expires)
  {
    this.expires = expires;
  }
}
