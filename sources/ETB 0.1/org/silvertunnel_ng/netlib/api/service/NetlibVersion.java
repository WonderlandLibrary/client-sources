package org.silvertunnel_ng.netlib.api.service;

import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;












































public final class NetlibVersion
{
  private static final Logger LOG = LoggerFactory.getLogger(NetlibVersion.class);
  
  private static final Logger LOGNETLIB = LoggerFactory.getLogger("netlib");
  
  private static NetlibVersion instance;
  
  private static final String VERSION_PROPERTIES = "/org/silvertunnel_ng/netlib/version.properties";
  
  private String netlibVersionInfo = "unknown";
  
  private static NetlibVersion info = getInstance();
  







  public static NetlibVersion getInstance()
  {
    if (instance == null)
    {

      synchronized (NetlibVersion.class)
      {
        if (instance == null)
        {

          instance = new NetlibVersion();
        }
      }
    }
    
    return instance;
  }
  






  private NetlibVersion()
  {
    try
    {
      InputStream in = getClass().getResourceAsStream("/org/silvertunnel_ng/netlib/version.properties");
      Properties props = new Properties();
      props.load(in);
      netlibVersionInfo = props.getProperty("netlib.version.info");
    }
    catch (Exception e)
    {
      LOG.error("error while initializing NetlibStartInfo", e);
    }
    

    LOGNETLIB.info("Welcome to silvertunnel-ng.org Netlib (version " + netlibVersionInfo + ")");
  }
  



  public String getNetlibVersionInfo()
  {
    return netlibVersionInfo;
  }
}
