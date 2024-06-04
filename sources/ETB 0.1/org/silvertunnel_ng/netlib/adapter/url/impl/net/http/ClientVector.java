package org.silvertunnel_ng.netlib.adapter.url.impl.net.http;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Stack;

































































































































































































































































































class ClientVector
  extends Stack<KeepAliveEntry>
{
  private static final long serialVersionUID = -8680532108106489459L;
  int nap;
  
  ClientVector(int nap)
  {
    this.nap = nap;
  }
  
  synchronized HttpClient get()
  {
    if (empty())
    {
      return null;
    }
    


    HttpClient hc = null;
    long currentTime = System.currentTimeMillis();
    do
    {
      KeepAliveEntry e = (KeepAliveEntry)pop();
      if (currentTime - idleStartTime > nap)
      {
        hc.closeServer();
      }
      else
      {
        hc = hc;
      }
      
    } while ((hc == null) && (!empty()));
    return hc;
  }
  


  synchronized void put(HttpClient h)
  {
    if (size() > KeepAliveCache.getMaxConnections())
    {
      h.closeServer();
    }
    else
    {
      push(new KeepAliveEntry(h, System.currentTimeMillis()));
    }
  }
  



  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    throw new NotSerializableException();
  }
  
  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    throw new NotSerializableException();
  }
  




  public int hashCode()
  {
    int prime = 31;
    int result = super.hashCode();
    result = 31 * result + nap;
    return result;
  }
  




  public boolean equals(Object obj)
  {
    if (this == obj)
    {
      return true;
    }
    if (!super.equals(obj))
    {
      return false;
    }
    if (!(obj instanceof ClientVector))
    {
      return false;
    }
    ClientVector other = (ClientVector)obj;
    if (nap != nap)
    {
      return false;
    }
    return true;
  }
}
