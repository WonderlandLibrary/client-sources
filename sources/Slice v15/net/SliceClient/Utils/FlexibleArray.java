package net.SliceClient.Utils;

import java.util.Iterator;

public class FlexibleArray<T>
  implements Iterable<T>
{
  private Object[] elements;
  
  public FlexibleArray(T[] array)
  {
    elements = array;
  }
  
  public FlexibleArray()
  {
    elements = new Object[0];
  }
  
  public void add(T t)
  {
    if (t != null)
    {
      Object[] array = new Object[size() + 1];
      for (int i = 0; i < array.length; i++) {
        if (i < size()) {
          array[i] = get(i);
        } else {
          array[i] = t;
        }
      }
      set(array);
    }
  }
  
  public void remove(T t)
  {
    if (contains(t))
    {
      Object[] array = new Object[size() - 1];
      boolean b = true;
      for (int i = 0; i < size(); i++) {
        if ((b) && (get(i).equals(t))) {
          b = false;
        } else {
          array[(b ? i : i - 1)] = get(i);
        }
      }
      set(array);
    }
  }
  
  public boolean contains(T t)
  {
    Object[] arrayOfObject;
    int j = (arrayOfObject = array()).length;
    for (int i = 0; i < j; i++)
    {
      Object entry = arrayOfObject[i];
      if (entry.equals(t)) {
        return true;
      }
    }
    return false;
  }
  
  private void set(Object[] array)
  {
    elements = array;
  }
  
  public void clear()
  {
    elements = new Object[0];
  }
  
  public T get(int index)
  {
    return array()[index];
  }
  
  public int size()
  {
    return array().length;
  }
  
  public Object[] array()
  {
    return elements;
  }
  
  public void setElements(T[] elements)
  {
    this.elements = elements;
  }
  
  public boolean isEmpty()
  {
    return size() == 0;
  }
  
  public Iterator<T> iterator()
  {
    new Iterator()
    {
      private int index = 0;
      
      public boolean hasNext()
      {
        return (index < size()) && (get(index) != null);
      }
      
      public T next()
      {
        return get(index++);
      }
      
      public void remove()
      {
        remove(get(index));
      }
    };
  }
}
