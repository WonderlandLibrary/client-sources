package org.silvertunnel_ng.netlib.adapter.url.impl.net.http;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




























public class KeepAliveCache
  extends ConcurrentHashMap<KeepAliveKey, ClientVector>
  implements Runnable
{
  private static final Logger LOG = LoggerFactory.getLogger(KeepAliveCache.class);
  


  private static final long serialVersionUID = -2937172892064557949L;
  


  static final int MAX_CONNECTIONS = 5;
  

  static int result = -1;
  static final int LIFETIME = 5000;
  
  static int getMaxConnections() {
    if (result == -1)
    {
      try
      {
        result = Integer.parseInt(System.getProperty("http.maxConnections"));
      }
      catch (Exception e)
      {
        LOG.debug("got Exception : {}", e.getMessage(), e);
      }
      if (result <= 0)
      {
        result = 5;
      }
    }
    return result;
  }
  


  private Thread keepAliveTimer = null;
  







  public KeepAliveCache() {}
  







  public synchronized void put(URL url, Object obj, HttpClient http)
  {
    boolean startThread = keepAliveTimer == null;
    if (!startThread)
    {
      if (!keepAliveTimer.isAlive())
      {
        startThread = true;
      }
    }
    if (startThread)
    {
      clear();
      






      final KeepAliveCache cache = this;
      
      AccessController.doPrivileged(new PrivilegedAction()
      {



        public Void run()
        {


          ThreadGroup grp = Thread.currentThread().getThreadGroup();
          ThreadGroup parent = null;
          while ((parent = grp.getParent()) != null)
          {
            grp = parent;
          }
          
          keepAliveTimer = new Thread(grp, cache, "Keep-Alive-Timer");
          
          keepAliveTimer.setDaemon(true);
          keepAliveTimer.setPriority(8);
          keepAliveTimer.start();
          return null;
        }
      });
    }
    
    KeepAliveKey key = new KeepAliveKey(url, obj);
    ClientVector v = (ClientVector)super.get(key);
    
    if (v == null)
    {
      int keepAliveTimeout = http.getKeepAliveTimeout();
      v = new ClientVector(keepAliveTimeout > 0 ? keepAliveTimeout * 1000 : 5000);
      
      v.put(http);
      super.put(key, v);
    }
    else
    {
      v.put(http);
    }
  }
  

  public synchronized void remove(HttpClient h, Object obj)
  {
    KeepAliveKey key = new KeepAliveKey(url, obj);
    ClientVector v = (ClientVector)super.get(key);
    if (v != null)
    {
      v.remove(h);
      if (v.empty())
      {
        removeVector(key);
      }
    }
  }
  




  synchronized void removeVector(KeepAliveKey k)
  {
    super.remove(k);
  }
  




  public synchronized HttpClient get(URL url, Object obj)
  {
    KeepAliveKey key = new KeepAliveKey(url, obj);
    ClientVector v = (ClientVector)super.get(key);
    if (v == null)
    {
      return null;
    }
    return v.get();
  }
  






  public void run()
  {
    do
    {
      try
      {
        Thread.sleep(5000L);
      }
      catch (InterruptedException e)
      {
        LOG.debug("got IterruptedException : {}", e.getMessage(), e);
      }
      synchronized (this)
      {










        long currentTime = System.currentTimeMillis();
        
        ArrayList<KeepAliveKey> keysToRemove = new ArrayList();
        
        for (KeepAliveKey key : keySet())
        {
          ClientVector v = (ClientVector)get(key);
          synchronized (v)
          {


            for (int i = 0; i < v.size(); i++)
            {
              KeepAliveEntry e = (KeepAliveEntry)v.elementAt(i);
              if (currentTime - idleStartTime <= nap)
                break;
              HttpClient h = hc;
              h.closeServer();
            }
            




            v.subList(0, i).clear();
            
            if (v.size() == 0)
            {
              keysToRemove.add(key);
            }
          }
        }
        
        for (KeepAliveKey key : keysToRemove)
        {
          removeVector(key);
        }
        
      }
    } while (size() > 0);
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
}
