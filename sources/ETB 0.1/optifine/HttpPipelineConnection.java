package optifine;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Proxy;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpPipelineConnection
{
  private String host;
  private int port;
  private Proxy proxy;
  private List<HttpPipelineRequest> listRequests;
  private List<HttpPipelineRequest> listRequestsSend;
  private Socket socket;
  private InputStream inputStream;
  private OutputStream outputStream;
  private HttpPipelineSender httpPipelineSender;
  private HttpPipelineReceiver httpPipelineReceiver;
  private int countRequests;
  private boolean responseReceived;
  private long keepaliveTimeoutMs;
  private int keepaliveMaxCount;
  private long timeLastActivityMs;
  private boolean terminated;
  private static final String LF = "\n";
  public static final int TIMEOUT_CONNECT_MS = 5000;
  public static final int TIMEOUT_READ_MS = 5000;
  private static final Pattern patternFullUrl = Pattern.compile("^[a-zA-Z]+://.*");
  
  public HttpPipelineConnection(String host, int port)
  {
    this(host, port, Proxy.NO_PROXY);
  }
  
  public HttpPipelineConnection(String host, int port, Proxy proxy)
  {
    this.host = null;
    this.port = 0;
    this.proxy = Proxy.NO_PROXY;
    listRequests = new LinkedList();
    listRequestsSend = new LinkedList();
    socket = null;
    inputStream = null;
    outputStream = null;
    httpPipelineSender = null;
    httpPipelineReceiver = null;
    countRequests = 0;
    responseReceived = false;
    keepaliveTimeoutMs = 5000L;
    keepaliveMaxCount = 1000;
    timeLastActivityMs = System.currentTimeMillis();
    terminated = false;
    this.host = host;
    this.port = port;
    this.proxy = proxy;
    httpPipelineSender = new HttpPipelineSender(this);
    httpPipelineSender.start();
    httpPipelineReceiver = new HttpPipelineReceiver(this);
    httpPipelineReceiver.start();
  }
  
  public synchronized boolean addRequest(HttpPipelineRequest pr)
  {
    if (isClosed())
    {
      return false;
    }
    

    addRequest(pr, listRequests);
    addRequest(pr, listRequestsSend);
    countRequests += 1;
    return true;
  }
  

  private void addRequest(HttpPipelineRequest pr, List<HttpPipelineRequest> list)
  {
    list.add(pr);
    notifyAll();
  }
  
  public synchronized void setSocket(Socket s) throws IOException
  {
    if (!terminated)
    {
      if (socket != null)
      {
        throw new IllegalArgumentException("Already connected");
      }
      

      socket = s;
      socket.setTcpNoDelay(true);
      inputStream = socket.getInputStream();
      outputStream = new BufferedOutputStream(socket.getOutputStream());
      onActivity();
      notifyAll();
    }
  }
  
  public synchronized OutputStream getOutputStream()
    throws IOException, InterruptedException
  {
    while (outputStream == null)
    {
      checkTimeout();
      wait(1000L);
    }
    
    return outputStream;
  }
  
  public synchronized InputStream getInputStream() throws IOException, InterruptedException
  {
    while (inputStream == null)
    {
      checkTimeout();
      wait(1000L);
    }
    
    return inputStream;
  }
  
  public synchronized HttpPipelineRequest getNextRequestSend() throws InterruptedException, IOException
  {
    if ((listRequestsSend.size() <= 0) && (outputStream != null))
    {
      outputStream.flush();
    }
    
    return getNextRequest(listRequestsSend, true);
  }
  
  public synchronized HttpPipelineRequest getNextRequestReceive() throws InterruptedException
  {
    return getNextRequest(listRequests, false);
  }
  
  private HttpPipelineRequest getNextRequest(List<HttpPipelineRequest> list, boolean remove) throws InterruptedException
  {
    while (list.size() <= 0)
    {
      checkTimeout();
      wait(1000L);
    }
    
    onActivity();
    
    if (remove)
    {
      return (HttpPipelineRequest)list.remove(0);
    }
    

    return (HttpPipelineRequest)list.get(0);
  }
  

  private void checkTimeout()
  {
    if (socket != null)
    {
      long timeoutMs = keepaliveTimeoutMs;
      
      if (listRequests.size() > 0)
      {
        timeoutMs = 5000L;
      }
      
      long timeNowMs = System.currentTimeMillis();
      
      if (timeNowMs > timeLastActivityMs + timeoutMs)
      {
        terminate(new InterruptedException("Timeout " + timeoutMs));
      }
    }
  }
  
  private void onActivity()
  {
    timeLastActivityMs = System.currentTimeMillis();
  }
  
  public synchronized void onRequestSent(HttpPipelineRequest pr)
  {
    if (!terminated)
    {
      onActivity();
    }
  }
  
  public synchronized void onResponseReceived(HttpPipelineRequest pr, HttpResponse resp)
  {
    if (!terminated)
    {
      responseReceived = true;
      onActivity();
      
      if ((listRequests.size() > 0) && (listRequests.get(0) == pr))
      {
        listRequests.remove(0);
        pr.setClosed(true);
        String location = resp.getHeader("Location");
        
        if ((resp.getStatus() / 100 == 3) && (location != null) && (pr.getHttpRequest().getRedirects() < 5))
        {
          try
          {
            location = normalizeUrl(location, pr.getHttpRequest());
            HttpRequest listener1 = HttpPipeline.makeRequest(location, pr.getHttpRequest().getProxy());
            listener1.setRedirects(pr.getHttpRequest().getRedirects() + 1);
            HttpPipelineRequest hpr2 = new HttpPipelineRequest(listener1, pr.getHttpListener());
            HttpPipeline.addRequest(hpr2);
          }
          catch (IOException var6)
          {
            pr.getHttpListener().failed(pr.getHttpRequest(), var6);
          }
        }
        else
        {
          HttpListener listener = pr.getHttpListener();
          listener.finished(pr.getHttpRequest(), resp);
        }
        
        checkResponseHeader(resp);
      }
      else
      {
        throw new IllegalArgumentException("Response out of order: " + pr);
      }
    }
  }
  
  private String normalizeUrl(String url, HttpRequest hr)
  {
    if (patternFullUrl.matcher(url).matches())
    {
      return url;
    }
    if (url.startsWith("//"))
    {
      return "http:" + url;
    }
    

    String server = hr.getHost();
    
    if (hr.getPort() != 80)
    {
      server = server + ":" + hr.getPort();
    }
    
    if (url.startsWith("/"))
    {
      return "http://" + server + url;
    }
    

    String file = hr.getFile();
    int pos = file.lastIndexOf("/");
    return "http://" + server + "/" + url;
  }
  


  private void checkResponseHeader(HttpResponse resp)
  {
    String connStr = resp.getHeader("Connection");
    
    if ((connStr != null) && (!connStr.toLowerCase().equals("keep-alive")))
    {
      terminate(new java.io.EOFException("Connection not keep-alive"));
    }
    
    String keepAliveStr = resp.getHeader("Keep-Alive");
    
    if (keepAliveStr != null)
    {
      String[] parts = Config.tokenize(keepAliveStr, ",;");
      
      for (int i = 0; i < parts.length; i++)
      {
        String part = parts[i];
        String[] tokens = split(part, '=');
        
        if (tokens.length >= 2)
        {


          if (tokens[0].equals("timeout"))
          {
            int max = Config.parseInt(tokens[1], -1);
            
            if (max > 0)
            {
              keepaliveTimeoutMs = (max * 1000);
            }
          }
          
          if (tokens[0].equals("max"))
          {
            int max = Config.parseInt(tokens[1], -1);
            
            if (max > 0)
            {
              keepaliveMaxCount = max;
            }
          }
        }
      }
    }
  }
  
  private String[] split(String str, char separator)
  {
    int pos = str.indexOf(separator);
    
    if (pos < 0)
    {
      return new String[] { str };
    }
    

    String str1 = str.substring(0, pos);
    String str2 = str.substring(pos + 1);
    return new String[] { str1, str2 };
  }
  

  public synchronized void onExceptionSend(HttpPipelineRequest pr, Exception e)
  {
    terminate(e);
  }
  
  public synchronized void onExceptionReceive(HttpPipelineRequest pr, Exception e)
  {
    terminate(e);
  }
  
  private synchronized void terminate(Exception e)
  {
    if (!terminated)
    {
      terminated = true;
      terminateRequests(e);
      
      if (httpPipelineSender != null)
      {
        httpPipelineSender.interrupt();
      }
      
      if (httpPipelineReceiver != null)
      {
        httpPipelineReceiver.interrupt();
      }
      
      try
      {
        if (socket != null)
        {
          socket.close();
        }
      }
      catch (IOException localIOException) {}
      



      socket = null;
      inputStream = null;
      outputStream = null;
    }
  }
  
  private void terminateRequests(Exception e)
  {
    if (listRequests.size() > 0)
    {


      if (!responseReceived)
      {
        HttpPipelineRequest pr = (HttpPipelineRequest)listRequests.remove(0);
        pr.getHttpListener().failed(pr.getHttpRequest(), e);
        pr.setClosed(true);
      }
      
      while (listRequests.size() > 0)
      {
        HttpPipelineRequest pr = (HttpPipelineRequest)listRequests.remove(0);
        HttpPipeline.addRequest(pr);
      }
    }
  }
  
  public synchronized boolean isClosed()
  {
    return terminated;
  }
  
  public int getCountRequests()
  {
    return countRequests;
  }
  
  public synchronized boolean hasActiveRequests()
  {
    return listRequests.size() > 0;
  }
  
  public String getHost()
  {
    return host;
  }
  
  public int getPort()
  {
    return port;
  }
  
  public Proxy getProxy()
  {
    return proxy;
  }
}
