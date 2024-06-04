package org.lwjgl.opencl;

import java.util.Iterator;

















final class FastLongMap<V>
  implements Iterable<Entry<V>>
{
  private Entry[] table;
  private int size;
  private int mask;
  private int capacity;
  private int threshold;
  
  FastLongMap()
  {
    this(16, 0.75F);
  }
  
  FastLongMap(int initialCapacity)
  {
    this(initialCapacity, 0.75F);
  }
  
  FastLongMap(int initialCapacity, float loadFactor) {
    if (initialCapacity > 1073741824) throw new IllegalArgumentException("initialCapacity is too large.");
    if (initialCapacity < 0) throw new IllegalArgumentException("initialCapacity must be greater than zero.");
    if (loadFactor <= 0.0F) throw new IllegalArgumentException("initialCapacity must be greater than zero.");
    capacity = 1;
    while (capacity < initialCapacity)
      capacity <<= 1;
    threshold = ((int)(capacity * loadFactor));
    table = new Entry[capacity];
    mask = (capacity - 1);
  }
  
  private int index(long key) {
    return index(key, mask);
  }
  
  private static int index(long key, int mask) {
    int hash = (int)(key ^ key >>> 32);
    return hash & mask;
  }
  
  public V put(long key, V value) {
    Entry<V>[] table = this.table;
    int index = index(key);
    

    for (Entry<V> e = table[index]; e != null; e = next) {
      if (key == key) {
        V oldValue = value;
        value = value;
        return oldValue;
      }
    }
    table[index] = new Entry(key, value, table[index]);
    
    if (size++ >= threshold) {
      rehash(table);
    }
    return null;
  }
  
  private void rehash(Entry<V>[] table) {
    int newCapacity = 2 * capacity;
    int newMask = newCapacity - 1;
    
    Entry<V>[] newTable = new Entry[newCapacity];
    
    for (int i = 0; i < table.length; i++) {
      Entry<V> e = table[i];
      if (e != null) {
        do {
          Entry<V> next = next;
          int index = index(key, newMask);
          next = newTable[index];
          newTable[index] = e;
          e = next;
        } while (e != null);
      }
    }
    this.table = newTable;
    capacity = newCapacity;
    mask = newMask;
    threshold *= 2;
  }
  
  public V get(long key) {
    int index = index(key);
    for (Entry<V> e = table[index]; e != null; e = next)
      if (key == key) return value;
    return null;
  }
  
  public boolean containsValue(Object value) {
    Entry<V>[] table = this.table;
    for (int i = table.length - 1; i >= 0; i--)
      for (Entry<V> e = table[i]; e != null; e = next)
        if (value.equals(value)) return true;
    return false;
  }
  
  public boolean containsKey(long key) {
    int index = index(key);
    for (Entry<V> e = table[index]; e != null; e = next)
      if (key == key) return true;
    return false;
  }
  
  public V remove(long key) {
    int index = index(key);
    
    Entry<V> prev = table[index];
    Entry<V> e = prev;
    while (e != null) {
      Entry<V> next = next;
      if (key == key) {
        size -= 1;
        if (prev == e) {
          table[index] = next;
        } else
          next = next;
        return value;
      }
      prev = e;
      e = next;
    }
    return null;
  }
  
  public int size() {
    return size;
  }
  
  public boolean isEmpty() {
    return size == 0;
  }
  
  public void clear() {
    Entry<V>[] table = this.table;
    for (int index = table.length - 1; index >= 0; index--)
      table[index] = null;
    size = 0;
  }
  
  public FastLongMap<V>.EntryIterator iterator() {
    return new EntryIterator();
  }
  
  public class EntryIterator implements Iterator<FastLongMap.Entry<V>>
  {
    private int nextIndex;
    private FastLongMap.Entry<V> current;
    
    EntryIterator() {
      reset();
    }
    
    public void reset() {
      current = null;
      
      FastLongMap.Entry<V>[] table = FastLongMap.this.table;
      
      for (int i = table.length - 1; i >= 0; i--)
        if (table[i] != null) break;
      nextIndex = i;
    }
    
    public boolean hasNext() {
      if (nextIndex >= 0) return true;
      FastLongMap.Entry e = current;
      return (e != null) && (next != null);
    }
    
    public FastLongMap.Entry<V> next()
    {
      FastLongMap.Entry<V> e = current;
      if (e != null) {
        e = next;
        if (e != null) {
          current = e;
          return e;
        }
      }
      
      FastLongMap.Entry<V>[] table = FastLongMap.this.table;
      int i = nextIndex;
      e = this.current = table[i];
      for (;;) { i--; if (i >= 0)
          if (table[i] != null) break; }
      nextIndex = i;
      return e;
    }
    
    public void remove() {
      remove(current.key);
    }
  }
  
  static final class Entry<T>
  {
    final long key;
    T value;
    Entry<T> next;
    
    Entry(long key, T value, Entry<T> next) {
      this.key = key;
      this.value = value;
      this.next = next;
    }
    
    public long getKey() {
      return key;
    }
    
    public T getValue() {
      return value;
    }
  }
}
