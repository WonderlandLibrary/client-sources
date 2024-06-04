package optifine;

import java.util.LinkedHashMap;
import java.util.Map;

public class HttpResponse
{
  private int status = 0;
  private String statusLine = null;
  private Map<String, String> headers = new LinkedHashMap();
  private byte[] body = null;
  
  public HttpResponse(int status, String statusLine, Map headers, byte[] body)
  {
    this.status = status;
    this.statusLine = statusLine;
    this.headers = headers;
    this.body = body;
  }
  
  public int getStatus()
  {
    return status;
  }
  
  public String getStatusLine()
  {
    return statusLine;
  }
  
  public Map getHeaders()
  {
    return headers;
  }
  
  public String getHeader(String key)
  {
    return (String)headers.get(key);
  }
  
  public byte[] getBody()
  {
    return body;
  }
}
