package optifine;

public class HttpPipelineRequest
{
  private HttpRequest httpRequest = null;
  private HttpListener httpListener = null;
  private boolean closed = false;
  
  public HttpPipelineRequest(HttpRequest httpRequest, HttpListener httpListener)
  {
    this.httpRequest = httpRequest;
    this.httpListener = httpListener;
  }
  
  public HttpRequest getHttpRequest()
  {
    return httpRequest;
  }
  
  public HttpListener getHttpListener()
  {
    return httpListener;
  }
  
  public boolean isClosed()
  {
    return closed;
  }
  
  public void setClosed(boolean closed)
  {
    this.closed = closed;
  }
}
