package org.silvertunnel_ng.netlib.util;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


























public class PropertiesUtil
{
  private static final Logger LOG = LoggerFactory.getLogger(PropertiesUtil.class);
  



  private static final String LIST_SEPARATOR = ",";
  



  public PropertiesUtil() {}
  



  public static Object getAsObject(Map<String, Object> properties, String key, Object defaultValue)
  {
    if (properties == null)
    {
      return defaultValue;
    }
    
    return properties.get(key);
  }
  













  public static String[] getAsStringArray(Map<String, Object> properties, String key, String[] defaultValue)
  {
    if (properties == null)
    {
      return defaultValue;
    }
    
    Object value = properties.get(key);
    

    if (value == null)
    {
      return defaultValue;
    }
    if ((value instanceof String[]))
    {
      return (String[])value;
    }
    return value.toString().split(",");
  }
  












  public static String getAsString(Map<String, Object> properties, String key, String defaultValue)
  {
    if (properties == null)
    {
      return defaultValue;
    }
    
    Object value = properties.get(key);
    

    if (value == null)
    {
      return defaultValue;
    }
    return value.toString();
  }
  












  public static Long getAsLong(Map<String, Object> properties, String key, Long defaultValue)
  {
    if (properties == null)
    {
      return defaultValue;
    }
    
    Object obj = properties.get(key);
    if ((obj instanceof Long))
    {
      return (Long)obj;
    }
    if ((obj instanceof Integer))
    {
      return Long.valueOf(((Integer)obj).longValue());
    }
    if ((obj instanceof Short))
    {
      return Long.valueOf(((Short)obj).longValue());
    }
    if ((obj instanceof Byte))
    {
      return Long.valueOf(((Byte)obj).longValue());
    }
    if ((obj instanceof String))
    {
      try
      {
        String s = (String)obj;
        return Long.valueOf(s);

      }
      catch (Exception e)
      {
        LOG.debug("got Exception : {}", e.getMessage(), e);
      }
    }
    
    return defaultValue;
  }
  












  public static Integer getAsInteger(Map<String, Object> properties, String key, Integer defaultValue)
  {
    if (properties == null)
    {
      return defaultValue;
    }
    
    Object obj = properties.get(key);
    if ((obj instanceof Integer))
    {
      return (Integer)obj;
    }
    if ((obj instanceof Long))
    {
      return Integer.valueOf(((Long)obj).intValue());
    }
    if ((obj instanceof Short))
    {
      return Integer.valueOf(((Short)obj).intValue());
    }
    if ((obj instanceof Byte))
    {
      return Integer.valueOf(((Byte)obj).intValue());
    }
    if ((obj instanceof String))
    {
      try
      {
        String s = (String)obj;
        return Integer.valueOf(s);

      }
      catch (Exception e)
      {
        LOG.debug("got Exception : {}", e.getMessage(), e);
      }
    }
    
    return defaultValue;
  }
}
