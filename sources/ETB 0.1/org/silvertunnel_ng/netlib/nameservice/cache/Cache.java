package org.silvertunnel_ng.netlib.nameservice.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;







































public class Cache<K, V>
  implements Map<K, V>
{
  private final Map<K, CacheEntry<K, V>> storage;
  private final int timeToLiveSeconds;
  private final int maxElements;
  private static final int MIN_MAX_ELEMENTS = 1;
  
  public Cache(int maxElements, int timeToLiveSeconds)
  {
    if (timeToLiveSeconds < 0)
    {
      this.timeToLiveSeconds = Integer.MAX_VALUE;
    }
    else
    {
      this.timeToLiveSeconds = timeToLiveSeconds;
    }
    if (maxElements < 1)
    {
      throw new IllegalArgumentException("invalid maxElements=" + maxElements);
    }
    
    this.maxElements = maxElements;
    
    storage = new HashMap(maxElements);
  }
  

  public synchronized void clear()
  {
    storage.clear();
  }
  

  public synchronized boolean containsKey(Object key)
  {
    V v = get(key);
    return v != null;
  }
  

  public synchronized boolean containsValue(Object value)
  {
    return values().contains(value);
  }
  


  public synchronized Set<Map.Entry<K, V>> entrySet()
  {
    Set<Map.Entry<K, V>> entries = new HashSet(storage.size());
    
    for (K key : storage.keySet())
    {
      CacheEntry<K, V> cacheValue = (CacheEntry)storage.get(key);
      if (cacheValue != null)
      {
        entries.add(cacheValue);
      }
    }
    
    return entries;
  }
  

  public synchronized V get(Object key)
  {
    if (timeToLiveSeconds == 0)
    {
      return null;
    }
    
    CacheEntry<K, V> value = (CacheEntry)storage.get(key);
    if (value == null)
    {

      return null;
    }
    if (value.isExpired())
    {

      storage.remove(key);
      return null;
    }
    


    return value.getValue();
  }
  


  public synchronized boolean isEmpty()
  {
    return storage.isEmpty();
  }
  

  public synchronized Set<K> keySet()
  {
    return storage.keySet();
  }
  

  public synchronized V put(K key, V value)
  {
    if (timeToLiveSeconds == 0)
    {
      return null;
    }
    
    ensureThatAtLeastOneMoreEntryCanBePutted();
    
    CacheEntry<K, V> valueNew = new CacheEntry(key, value, timeToLiveSeconds);
    
    CacheEntry<K, V> valueOld = (CacheEntry)storage.put(key, valueNew);
    return valueOld == null ? null : valueOld.getValue();
  }
  

  public synchronized void putAll(Map<? extends K, ? extends V> m)
  {
    for (Map.Entry<? extends K, ? extends V> entry : m.entrySet())
    {
      put(entry.getKey(), entry.getValue());
    }
  }
  

  public synchronized V remove(Object key)
  {
    CacheEntry<K, V> v = (CacheEntry)storage.remove(key);
    return v == null ? null : v.getValue();
  }
  

  public synchronized int size()
  {
    removeExpiredEntries();
    return storage.size();
  }
  

  public synchronized Collection<V> values()
  {
    Collection<V> values = new ArrayList(storage.size());
    
    for (K key : storage.keySet())
    {
      CacheEntry<K, V> value = (CacheEntry)storage.get(key);
      if (value != null)
      {
        values.add(value.getValue());
      }
    }
    
    return values;
  }
  

  public String toString()
  {
    return "Cache(" + storage + ")";
  }
  




  private synchronized void ensureThatAtLeastOneMoreEntryCanBePutted()
  {
    if (storage.size() < maxElements)
    {
      return;
    }
    

    K remainingKey = removeExpiredEntries();
    
    if (storage.size() > maxElements - 1)
    {

      if (remainingKey != null)
      {

        storage.remove(remainingKey);

      }
      else
      {
        throw new IllegalStateException("no remainingKey found, but storage is not empty: " + storage);
      }
    }
  }
  






  private synchronized K removeExpiredEntries()
  {
    K remainingKey = null;
    

    Collection<K> keysToRemove = new ArrayList(storage.size());
    for (Map.Entry<K, CacheEntry<K, V>> entry : storage.entrySet())
    {
      if (((CacheEntry)entry.getValue()).isExpired())
      {
        keysToRemove.add(entry.getKey());
      }
      else
      {
        remainingKey = entry.getKey();
      }
    }
    

    for (K keyToRemove : keysToRemove)
    {
      storage.remove(keyToRemove);
    }
    
    return remainingKey;
  }
}
