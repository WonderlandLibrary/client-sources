package org.silvertunnel_ng.netlib.adapter.url.impl.net.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;



























































public class MessageHeader
{
  private String[] keys;
  private String[] values;
  private int nkeys;
  
  public MessageHeader()
  {
    grow();
  }
  
  public MessageHeader(InputStream is) throws IOException
  {
    parseHeader(is);
  }
  



  public synchronized void reset()
  {
    keys = null;
    values = null;
    nkeys = 0;
    grow();
  }
  








  public synchronized String findValue(String key)
  {
    if (key == null)
    {
      int i = nkeys; do { i--; if (i < 0)
          break;
      } while (keys[i] != null);
      
      return values[i];

    }
    else
    {

      int i = nkeys; do { i--; if (i < 0)
          break;
      } while (!key.equalsIgnoreCase(keys[i]));
      
      return values[i];
    }
    

    return null;
  }
  





  public synchronized int getKeyPos(String key)
  {
    if (key != null)
    {
      int i = nkeys; do { i--; if (i < 0)
          break;
      } while (!key.equalsIgnoreCase(keys[i]));
      
      return i;
    }
    

    return -1;
  }
  
  public synchronized String getKey(int n)
  {
    if ((n < 0) || (n >= nkeys))
    {
      return null;
    }
    return keys[n];
  }
  
  public synchronized String getValue(int n)
  {
    if ((n < 0) || (n >= nkeys))
    {
      return null;
    }
    return values[n];
  }
  












  public synchronized String findNextValue(String key, String value)
  {
    boolean foundV = false;
    if (key == null)
    {
      int i = nkeys; for (;;) { i--; if (i < 0)
          break;
        if (keys[i] == null)
        {
          if (foundV)
          {
            return values[i];
          }
          if (value.equals(values[i]))
          {
            foundV = true;
          }
        }
      }
    }
    else
    {
      int i = nkeys; for (;;) { i--; if (i < 0)
          break;
        if (key.equalsIgnoreCase(keys[i]))
        {
          if (foundV)
          {
            return values[i];
          }
          if (value.equals(values[i]))
          {
            foundV = true;
          }
        }
      }
    }
    return null;
  }
  
  class HeaderIterator implements Iterator<String>
  {
    int index = 0;
    int next = -1;
    String key;
    boolean haveNext = false;
    Object lock;
    
    public HeaderIterator(String k, Object lock)
    {
      key = k;
      this.lock = lock;
    }
    

    public boolean hasNext()
    {
      synchronized (lock)
      {
        if (haveNext)
        {
          return true;
        }
        while (index < nkeys)
        {
          if (key.equalsIgnoreCase(keys[index]))
          {
            haveNext = true;
            next = (index++);
            return true;
          }
          index += 1;
        }
        return false;
      }
    }
    

    public String next()
    {
      synchronized (lock)
      {
        if (haveNext)
        {
          haveNext = false;
          return values[next];
        }
        if (hasNext())
        {
          return next();
        }
        

        throw new NoSuchElementException("No more elements");
      }
    }
    


    public void remove()
    {
      throw new UnsupportedOperationException("remove not allowed");
    }
  }
  




  public Iterator<String> multiValueIterator(String k)
  {
    return new HeaderIterator(k, this);
  }
  
  public synchronized Map<String, List<String>> getHeaders()
  {
    return getHeaders(null);
  }
  

  public synchronized Map<String, List<String>> getHeaders(String[] excludeList)
  {
    boolean skipIt = false;
    Map<String, List<String>> m = new HashMap();
    int i = nkeys; for (;;) { i--; if (i < 0)
        break;
      if (excludeList != null)
      {


        for (int j = 0; j < excludeList.length; j++)
        {
          if ((excludeList[j] != null) && 
            (excludeList[j].equalsIgnoreCase(keys[i])))
          {
            skipIt = true;
            break;
          }
        }
      }
      if (!skipIt)
      {
        List<String> l = (List)m.get(keys[i]);
        if (l == null)
        {
          l = new ArrayList();
          m.put(keys[i], l);
        }
        l.add(values[i]);

      }
      else
      {
        skipIt = false;
      }
    }
    
    for (String key : m.keySet())
    {
      m.put(key, Collections.unmodifiableList((List)m.get(key)));
    }
    
    return Collections.unmodifiableMap(m);
  }
  




  public synchronized void print(PrintStream p)
  {
    for (int i = 0; i < nkeys; i++)
    {
      if (keys[i] != null)
      {
        p.print(keys[i] + (values[i] != null ? ": " + values[i] : "") + "\r\n");
      }
    }
    
    p.print("\r\n");
    p.flush();
  }
  



  public synchronized void add(String k, String v)
  {
    grow();
    keys[nkeys] = k;
    values[nkeys] = v;
    nkeys += 1;
  }
  




  public synchronized void prepend(String k, String v)
  {
    grow();
    for (int i = nkeys; i > 0; i--)
    {
      keys[i] = keys[(i - 1)];
      values[i] = values[(i - 1)];
    }
    keys[0] = k;
    values[0] = v;
    nkeys += 1;
  }
  





  public synchronized void set(int i, String k, String v)
  {
    grow();
    if (i < 0)
    {
      return;
    }
    if (i >= nkeys)
    {
      add(k, v);
    }
    else
    {
      keys[i] = k;
      values[i] = v;
    }
  }
  


  private void grow()
  {
    if ((keys == null) || (nkeys >= keys.length))
    {
      String[] nk = new String[nkeys + 4];
      String[] nv = new String[nkeys + 4];
      if (keys != null)
      {
        System.arraycopy(keys, 0, nk, 0, nkeys);
      }
      if (values != null)
      {
        System.arraycopy(values, 0, nv, 0, nkeys);
      }
      keys = nk;
      values = nv;
    }
  }
  








  public synchronized void remove(String k)
  {
    if (k == null)
    {
      for (int i = 0; i < nkeys; i++)
      {
        while ((keys[i] == null) && (i < nkeys))
        {
          for (int j = i; j < nkeys - 1; j++)
          {
            keys[j] = keys[(j + 1)];
            values[j] = values[(j + 1)];
          }
          nkeys -= 1;
        }
        
      }
      
    } else {
      for (int i = 0; i < nkeys; i++)
      {
        while ((k.equalsIgnoreCase(keys[i])) && (i < nkeys))
        {
          for (int j = i; j < nkeys - 1; j++)
          {
            keys[j] = keys[(j + 1)];
            values[j] = values[(j + 1)];
          }
          nkeys -= 1;
        }
      }
    }
  }
  





  public synchronized void set(String key, String value)
  {
    int i = nkeys; do { i--; if (i < 0)
        break;
    } while (!key.equalsIgnoreCase(keys[i]));
    
    values[i] = value;
    return;
    

    add(key, value);
  }
  




  public synchronized void setIfNotSet(String key, String value)
  {
    if (findValue(key) == null)
    {
      add(key, value);
    }
  }
  




  public static String canonicalID(String id)
  {
    if (id == null)
    {
      return "";
    }
    int st = 0;
    int len = id.length();
    boolean substr = false;
    int c;
    while ((st < len) && (((c = id.charAt(st)) == '<') || (c <= 32)))
    {
      st++;
      substr = true; }
    int c;
    while ((st < len) && (((c = id.charAt(len - 1)) == '>') || (c <= 32)))
    {
      len--;
      substr = true;
    }
    return substr ? id.substring(st, len) : id;
  }
  
  public void parseHeader(InputStream is)
    throws IOException
  {
    synchronized (this)
    {
      nkeys = 0;
    }
    mergeHeader(is);
  }
  
  public void mergeHeader(InputStream is)
    throws IOException
  {
    if (is == null)
    {
      return;
    }
    char[] s = new char[10];
    int firstc = is.read();
    while ((firstc != 10) && (firstc != 13) && (firstc >= 0))
    {
      int len = 0;
      int keyend = -1;
      
      boolean inKey = firstc > 32;
      s[(len++)] = ((char)firstc);
      
      int c;
      while ((c = is.read()) >= 0)
      {
        switch (c)
        {
        case 58: 
          if ((inKey) && (len > 0))
          {
            keyend = len;
          }
          inKey = false;
          break;
        case 9: 
          c = 32;
        case 32: 
          inKey = false;
          break;
        case 10: 
        case 13: 
          firstc = is.read();
          if ((c == 13) && (firstc == 10))
          {
            firstc = is.read();
            if (firstc == 13)
            {
              firstc = is.read();
            }
          }
          if ((firstc == 10) || (firstc == 13) || (firstc > 32)) {
            break label252;
          }
          


          c = 32;
        }
        
        if (len >= s.length)
        {
          char[] ns = new char[s.length * 2];
          System.arraycopy(s, 0, ns, 0, len);
          s = ns;
        }
        s[(len++)] = ((char)c);
      }
      firstc = -1;
      label252:
      while ((len > 0) && (s[(len - 1)] <= ' '))
      {
        len--;
      }
      String k;
      if (keyend <= 0)
      {
        String k = null;
        keyend = 0;
      }
      else
      {
        k = String.copyValueOf(s, 0, keyend);
        if ((keyend < len) && (s[keyend] == ':'))
        {
          keyend++;
        }
        while ((keyend < len) && (s[keyend] <= ' '))
        {
          keyend++; }
      }
      String v;
      String v;
      if (keyend >= len)
      {
        v = "";
      }
      else
      {
        v = String.copyValueOf(s, keyend, len - keyend);
      }
      add(k, v);
    }
  }
  

  public synchronized String toString()
  {
    StringBuffer result = new StringBuffer(super.toString()).append(nkeys).append(" pairs: ");
    for (int i = 0; (i < keys.length) && (i < nkeys); i++)
    {
      result.append('{').append(keys[i]).append(": ").append(values[i]).append('}');
    }
    return result.toString();
  }
}
