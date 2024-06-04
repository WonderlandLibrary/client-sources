package org.silvertunnel_ng.netlib.adapter.url.impl.net.http;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




































public class NetProperties
{
  private static final Logger LOG = LoggerFactory.getLogger(NetProperties.class);
  private static Properties props = new Properties();
  
  static {
    AccessController.doPrivileged(new PrivilegedAction()
    {

      public Void run()
      {
        NetProperties.access$000();
        return null;
      }
    });
  }
  








  private static void loadDefaultProperties()
  {
    String fname = System.getProperty("java.home");
    if (fname == null)
    {
      throw new Error("Can't find java.home ??");
    }
    try
    {
      File f = new File(fname, "lib");
      f = new File(f, "net.properties");
      fname = f.getCanonicalPath();
      InputStream in = new FileInputStream(fname);
      BufferedInputStream bin = new BufferedInputStream(in);
      props.load(bin);
      bin.close();

    }
    catch (Exception e)
    {

      LOG.debug("got Exception : {}", e.getMessage(), e);
    }
  }
  














  public static String get(String key)
  {
    String def = props.getProperty(key);
    try
    {
      return System.getProperty(key, def);
    }
    catch (IllegalArgumentException e)
    {
      LOG.debug("got IllegalArgumentException : {}", e.getMessage(), e);
    }
    catch (NullPointerException e)
    {
      LOG.debug("got NullPointerException : {}", e.getMessage(), e);
    }
    return null;
  }
  
















  public static Integer getInteger(String key, int defval)
  {
    String val = null;
    
    try
    {
      val = System.getProperty(key, props.getProperty(key));
    }
    catch (IllegalArgumentException e)
    {
      LOG.debug("got IllegalArgumentException : {}", e.getMessage(), e);
    }
    catch (NullPointerException e)
    {
      LOG.debug("got NullPointerException : {}", e.getMessage(), e);
    }
    
    if (val != null)
    {
      try
      {
        return Integer.decode(val);
      }
      catch (NumberFormatException ex)
      {
        LOG.debug("got NumberFormatException : {}", ex.getMessage(), ex);
      }
    }
    return Integer.valueOf(defval);
  }
  














  public static Boolean getBoolean(String key)
  {
    String val = null;
    
    try
    {
      val = System.getProperty(key, props.getProperty(key));
    }
    catch (IllegalArgumentException e)
    {
      LOG.debug("got IllegalArgumentException : {}", e.getMessage(), e);
    }
    catch (NullPointerException e)
    {
      LOG.debug("got NullPointerException : {}", e.getMessage(), e);
    }
    
    if (val != null)
    {
      try
      {
        return Boolean.valueOf(val);
      }
      catch (NumberFormatException ex)
      {
        LOG.debug("got NumberFormatException : {}", ex.getMessage(), ex);
      }
    }
    return null;
  }
  
  private NetProperties() {}
}
