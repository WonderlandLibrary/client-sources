package org.silvertunnel_ng.netlib.layer.tor.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



























public final class Util
{
  private static final Logger LOG = LoggerFactory.getLogger(Util.class);
  
  public static final String MYNAME = "silvertunnel-ng-org-Netlib";
  
  public static final String UTF8 = "UTF-8";
  
  private static final String UTC_TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";
  
  private static DateFormat utcTimestampDateFormat;
  

  public Util() {}
  

  private static synchronized void initUtcTimestampIfNeeded()
  {
    if (utcTimestampDateFormat == null)
    {

      utcTimestampDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      utcTimestampDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }
  }
  







  public static Date parseUtcTimestamp(String timestampStr)
  {
    Calendar calendar = Calendar.getInstance(UTC_TZ);
    calendar.clear();
    calendar.setTimeZone(UTC_TZ);
    int year = Integer.parseInt(timestampStr.substring(0, 4));
    int month = Integer.parseInt(timestampStr.substring(5, 7));
    int day = Integer.parseInt(timestampStr.substring(8, 10));
    int hour = Integer.parseInt(timestampStr.substring(11, 13));
    int minute = Integer.parseInt(timestampStr.substring(14, 16));
    int second = Integer.parseInt(timestampStr.substring(17));
    calendar.set(year, month - 1, day, hour, minute, second);
    return calendar.getTime();
  }
  







  public static Long parseUtcTimestampAsLong(String timestampStr)
  {
    return Long.valueOf(parseUtcTimestamp(timestampStr).getTime());
  }
  
  private static final TimeZone UTC_TZ = TimeZone.getTimeZone("UTC");
  







  public static String formatUtcTimestamp(Date timestamp)
  {
    try
    {
      synchronized (Util.class)
      {
        initUtcTimestampIfNeeded();
        

        return utcTimestampDateFormat.format(timestamp);
      }
      



      return null;
    }
    catch (Exception e)
    {
      LOG.debug("Exception while formatting timestamp={}", timestamp, e);
    }
  }
  








  public static String formatUtcTimestamp(Long timestamp)
  {
    try
    {
      synchronized (Util.class)
      {
        initUtcTimestampIfNeeded();
        

        return utcTimestampDateFormat.format(timestamp);
      }
      



      return null;
    }
    catch (Exception e)
    {
      LOG.debug("Exception while formatting timestamp={}", timestamp, e);
    }
  }
}
