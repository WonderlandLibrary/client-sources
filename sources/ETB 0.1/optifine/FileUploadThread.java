package optifine;

import java.util.Map;

public class FileUploadThread extends Thread
{
  private String urlString;
  private Map headers;
  private byte[] content;
  private IFileUploadListener listener;
  
  public FileUploadThread(String urlString, Map headers, byte[] content, IFileUploadListener listener)
  {
    this.urlString = urlString;
    this.headers = headers;
    this.content = content;
    this.listener = listener;
  }
  
  public void run()
  {
    try
    {
      HttpUtils.post(urlString, headers, content);
      listener.fileUploadFinished(urlString, content, null);
    }
    catch (Exception var2)
    {
      listener.fileUploadFinished(urlString, content, var2);
    }
  }
  
  public String getUrlString()
  {
    return urlString;
  }
  
  public byte[] getContent()
  {
    return content;
  }
  
  public IFileUploadListener getListener()
  {
    return listener;
  }
}
