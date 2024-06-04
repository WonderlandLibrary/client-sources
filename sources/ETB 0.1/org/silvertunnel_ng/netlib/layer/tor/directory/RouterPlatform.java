package org.silvertunnel_ng.netlib.layer.tor.directory;




























public class RouterPlatform
{
  private boolean originalTor = false;
  
  private int major = 0;
  
  private int minor = 0;
  
  private int micro = 0;
  
  private int patchlevel = 0;
  
  private String statusTag = "";
  
  private String os = "";
  
  private String extraInfo = "";
  
  private String dev = "";
  

  public RouterPlatform() {}
  

  public String toString()
  {
    if (originalTor)
    {
      StringBuilder builder = new StringBuilder();
      builder.append("Tor ");
      builder.append(major).append('.');
      builder.append(minor).append('.');
      builder.append(micro);
      if (patchlevel > 0)
      {
        builder.append('.').append(major);
      }
      if (!statusTag.isEmpty())
      {
        builder.append('-').append(statusTag);
      }
      if (!dev.isEmpty())
      {
        builder.append(' ').append(dev);
      }
      if (!os.isEmpty())
      {
        builder.append(" on ").append(os);
        if (!extraInfo.isEmpty())
        {
          builder.append(' ').append(extraInfo);
        }
      }
      return builder.toString();
    }
    

    return "unknown";
  }
}
