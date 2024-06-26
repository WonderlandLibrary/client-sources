package net.minecraft.src;

public class FileDownloadThread extends Thread
{
  private String urlString = null;
  private IFileDownloadListener listener = null;
  
  public FileDownloadThread(String urlString, IFileDownloadListener listener)
  {
    this.urlString = urlString;
    this.listener = listener;
  }
  
  public void run()
  {
    try
    {
      byte[] e = HttpUtils.get(urlString);
      listener.fileDownloadFinished(urlString, e, null);
    }
    catch (Exception var2)
    {
      listener.fileDownloadFinished(urlString, null, var2);
    }
  }
  
  public String getUrlString()
  {
    return urlString;
  }
  
  public IFileDownloadListener getListener()
  {
    return listener;
  }
}
