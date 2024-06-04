package org.silvertunnel_ng.netlib.adapter.url.impl.net.http;

import java.net.URL;


























public class ProgressSource
{
  private final URL url;
  private final String method;
  private String contentType;
  
  public static enum State
  {
    NEW,  CONNECTED,  UPDATE,  DELETE;
    



    private State() {}
  }
  


  private long progress = 0L;
  
  private long lastProgress = 0L;
  
  private long expected = -1L;
  
  private State state;
  
  private boolean connected = false;
  
  private int threshold = 8192;
  






  public ProgressSource(URL url, String method)
  {
    this(url, method, -1L);
  }
  



  public ProgressSource(URL url, String method, long expected)
  {
    this.url = url;
    this.method = method;
    contentType = "content/unknown";
    progress = 0L;
    lastProgress = 0L;
    this.expected = expected;
    state = State.NEW;
    


    threshold = 8192;
  }
  
  public boolean connected()
  {
    if (!connected)
    {
      connected = true;
      state = State.CONNECTED;
      return false;
    }
    return true;
  }
  



  public void close()
  {
    state = State.DELETE;
  }
  



  public URL getURL()
  {
    return url;
  }
  



  public String getMethod()
  {
    return method;
  }
  



  public String getContentType()
  {
    return contentType;
  }
  

  public void setContentType(String ct)
  {
    contentType = ct;
  }
  



  public long getProgress()
  {
    return progress;
  }
  



  public long getExpected()
  {
    return expected;
  }
  



  public State getState()
  {
    return state;
  }
  





  public void beginTracking() {}
  





  public void finishTracking() {}
  





  public void updateProgress(long latestProgress, long expectedProgress)
  {
    lastProgress = progress;
    progress = latestProgress;
    expected = expectedProgress;
    
    if (!connected())
    {
      state = State.CONNECTED;
    }
    else
    {
      state = State.UPDATE;
    }
    

























    if (expected != -1L)
    {
      if ((progress >= expected) && (progress != 0L))
      {
        close();
      }
    }
  }
  
  public Object clone()
    throws CloneNotSupportedException
  {
    return super.clone();
  }
  

  public String toString()
  {
    return getClass().getName() + "[url=" + url + ", method=" + method + ", state=" + state + ", content-type=" + contentType + ", progress=" + progress + ", expected=" + expected + "]";
  }
}
