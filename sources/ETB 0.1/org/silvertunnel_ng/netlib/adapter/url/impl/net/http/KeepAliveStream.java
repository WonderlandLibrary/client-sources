package org.silvertunnel_ng.netlib.adapter.url.impl.net.http;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;


































public class KeepAliveStream
  extends MeteredStream
  implements Hurryable
{
  HttpClient hc;
  boolean hurried;
  protected boolean queuedForCleanup = false;
  
  private static KeepAliveStreamCleaner queue = new KeepAliveStreamCleaner();
  private static Thread cleanerThread = null;
  

  private static boolean startCleanupThread;
  


  public KeepAliveStream(InputStream is, ProgressSource pi, long expected, HttpClient hc)
  {
    super(is, pi, expected);
    this.hc = hc;
  }
  




  public void close()
    throws IOException
  {
    if (closed)
    {
      return;
    }
    

    if (queuedForCleanup)
    {
      return;
    }
    







    try
    {
      if (expected > count)
      {
        long nskip = expected - count;
        if (nskip <= available())
        {
          long n = 0L;
          while (n < nskip)
          {
            nskip -= n;
            n = skip(nskip);
          }
        }
        else if ((expected <= KeepAliveStreamCleaner.MAX_DATA_REMAINING) && (!hurried))
        {




          queueForCleanup(new MyKeepAliveCleanerEntry(this, hc));
        }
        else
        {
          hc.closeServer();
        }
      }
      if ((!closed) && (!hurried) && (!queuedForCleanup))
      {
        hc.finished();
      }
    }
    finally
    {
      if (pi != null)
      {
        pi.finishTracking();
      }
      
      if (!queuedForCleanup)
      {


        in = null;
        hc = null;
        closed = true;
      }
    }
  }
  



  public boolean markSupported()
  {
    return false;
  }
  


  public void mark(int limit) {}
  

  public void reset()
    throws IOException
  {
    throw new IOException("mark/reset not supported");
  }
  


  public synchronized boolean hurry()
  {
    try
    {
      if ((closed) || (count >= expected))
      {
        return false;
      }
      if (in.available() < expected - count)
      {

        return false;
      }
      





      int size = (int)(expected - count);
      byte[] buf = new byte[size];
      DataInputStream dis = new DataInputStream(in);
      dis.readFully(buf);
      in = new ByteArrayInputStream(buf);
      hurried = true;
      return true;
    }
    catch (IOException e) {}
    


    return false;
  }
  


  private static synchronized void queueForCleanup(MyKeepAliveCleanerEntry kace)
  {
    if ((queue != null) && (!kace.getQueuedForCleanup()))
    {
      if (!queue.offer(kace))
      {
        kace.getHttpClient().closeServer();
        return;
      }
      
      kace.setQueuedForCleanup();
    }
    
    startCleanupThread = cleanerThread == null;
    if (!startCleanupThread)
    {
      if (!cleanerThread.isAlive())
      {
        startCleanupThread = true;
      }
    }
    
    if (startCleanupThread)
    {

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
          
          KeepAliveStream.access$002(new Thread(grp, KeepAliveStream.queue, "Keep-Alive-SocketCleaner"));
          
          KeepAliveStream.cleanerThread.setDaemon(true);
          KeepAliveStream.cleanerThread.setPriority(8);
          KeepAliveStream.cleanerThread.start();
          return null;
        }
      });
    }
  }
  
  protected long remainingToRead()
  {
    return expected - count;
  }
  
  protected void setClosed()
  {
    in = null;
    hc = null;
    closed = true;
  }
}
