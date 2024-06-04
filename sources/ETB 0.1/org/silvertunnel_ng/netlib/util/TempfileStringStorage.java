package org.silvertunnel_ng.netlib.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.silvertunnel_ng.netlib.layer.tor.common.TorConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

























public final class TempfileStringStorage
  implements StringStorage
{
  private static final Logger LOG = LoggerFactory.getLogger(TempfileStringStorage.class);
  
  private static final Pattern KEY_PATTERN = Pattern.compile("[a-z0-9\\_\\-\\.]+");
  
  static
  {
    LOG.debug("TempfileStringStorage directory={}", TorConfig.getTempDirectory());
  }
  
  private static TempfileStringStorage instance = new TempfileStringStorage();
  



  public static TempfileStringStorage getInstance()
  {
    return instance;
  }
  
















  public synchronized void put(String key, String value)
    throws IllegalArgumentException
  {
    if (key == null)
    {
      throw new IllegalArgumentException("key=null");
    }
    if (!KEY_PATTERN.matcher(key).matches())
    {
      throw new IllegalArgumentException("invalid characters in key=" + key);
    }
    
    if (value == null)
    {
      throw new IllegalArgumentException("value=null");
    }
    

    try
    {
      FileUtil.writeFile(getTempfileFile(key), value);
    }
    catch (Exception e)
    {
      LOG.warn("could not write value for key=" + key, e);
    }
  }
  









  public synchronized String get(String key)
  {
    if (key == null)
    {
      throw new IllegalArgumentException("key=null");
    }
    if (!KEY_PATTERN.matcher(key).matches())
    {
      throw new IllegalArgumentException("invalid characters in key=" + key);
    }
    


    try
    {
      return FileUtil.readFile(getTempfileFile(key));
    }
    catch (FileNotFoundException e)
    {
      return null;
    }
    catch (Exception e)
    {
      LOG.warn("could not read value for key=" + key, e); }
    return null;
  }
  









  public static File getTempfileFile(String key)
    throws IOException
  {
    String prefix = "st-" + key;
    



    return new File(TorConfig.getTempDirectory(), prefix);
  }
  
  protected TempfileStringStorage() {}
}
