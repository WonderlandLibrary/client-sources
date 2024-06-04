package org.silvertunnel_ng.netlib.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




























public final class SystemPropertiesHelper
{
  private static final Logger LOG = LoggerFactory.getLogger(SystemPropertiesHelper.class);
  




  private SystemPropertiesHelper() {}
  



  public static int getSystemProperty(String key, int defaultValue)
  {
    String value = System.getProperty(key);
    if (value == null)
    {
      return defaultValue;
    }
    try
    {
      return Integer.parseInt(value);
    }
    catch (Exception e) {}
    

    return defaultValue;
  }
  











  public static boolean getSystemProperty(String key, boolean defaultValue)
  {
    String value = System.getProperty(key);
    if (value == null)
    {
      return defaultValue;
    }
    try
    {
      int tmp = Integer.parseInt(value);
      if ((tmp == 0) || (tmp == 1))
      {
        return tmp == 1;
      }
      

      LOG.warn("incorrect value (" + value + ") for " + key + " possible values are : 0,1,true,false,y,n");
      
      return defaultValue;

    }
    catch (Exception e)
    {
      value = value.toLowerCase();
      if (value.charAt(0) == 'f')
      {
        return false;
      }
      if (value.charAt(0) == 'n')
      {
        return false;
      }
      if (value.charAt(0) == 't')
      {
        return true;
      }
      if (value.charAt(0) == 'y')
      {
        return true;
      }
      LOG.warn("incorrect value (" + value + ") for " + key + " possible values are : 0,1,true,false,y,n");
    }
    return defaultValue;
  }
}
