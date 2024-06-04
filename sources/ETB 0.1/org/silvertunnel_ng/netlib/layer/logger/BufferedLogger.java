package org.silvertunnel_ng.netlib.layer.logger;

import java.util.logging.Level;
import org.silvertunnel_ng.netlib.util.LogHelper;
import org.slf4j.Logger;











































public class BufferedLogger
{
  public static final Level LOG_LEVEL_NULL = Level.OFF;
  public static final Level LOG_LEVEL_DEBUG = Level.FINE;
  public static final Level LOG_LEVEL_INFO = Level.INFO;
  
  private static final char SPECIAL_CHAR = '?';
  
  private final Logger summaryLog;
  private final Level summaryLogLevel;
  private final Logger detailLog;
  private final Level detailLogLevel;
  private final boolean logSingleBytes;
  private final String logMessagePrefix;
  private StringBuffer buffer = new StringBuffer();
  int byteCount = 0;
  










  public BufferedLogger(Logger summaryLog, Level summaryLogLevel, Logger detailLog, Level detailLogLevel, boolean logSingleBytes, String logMessagePrefix)
  {
    this.summaryLog = summaryLog;
    this.summaryLogLevel = summaryLogLevel;
    this.detailLog = detailLog;
    this.detailLogLevel = detailLogLevel;
    this.logSingleBytes = logSingleBytes;
    this.logMessagePrefix = logMessagePrefix;
  }
  





  public void log(byte b)
  {
    if ((logSingleBytes) && (LogHelper.isLoggable(detailLog, detailLogLevel)))
    {
      char c = (char)b;
      if ((c >= ' ') && (c <= ''))
      {
        logAndCount(c);
      }
      else
      {
        logAndCount('?');
        
        int i = b < 0 ? 256 + b : b;
        String hex = Integer.toHexString(i);
        if (hex.length() < 2)
        {
          logAndDoNotCount("0");
        }
        logAndDoNotCount(hex);
      }
    }
    else
    {
      byteCount += 1;
    }
  }
  









  public void log(byte[] bytes, int offset, int numOfBytes)
  {
    if ((logSingleBytes) && (LogHelper.isLoggable(detailLog, detailLogLevel)))
    {
      int len = bytes.length;
      for (int i = 0; i < numOfBytes; i++)
      {
        int idx = offset + i;
        if (idx < len)
        {
          log(bytes[idx]);
        }
      }
    }
    else
    {
      byteCount += numOfBytes;
    }
  }
  
  private void logAndCount(char c)
  {
    buffer.append(c);
    byteCount += 1;
  }
  
  private void logAndDoNotCount(String s)
  {
    buffer.append(s);
  }
  


  public void flush()
  {
    if (buffer.length() > 0)
    {
      if (LogHelper.isLoggable(detailLog, detailLogLevel))
      {
        String msg = byteCount + " bytes \"" + buffer.toString() + "\"";
        
        logDetailLine(msg);
      }
      byteCount = 0;
      buffer = new StringBuffer();
    }
    if (byteCount > 0)
    {
      logDetailLine(byteCount + " bytes");
      byteCount = 0;
      buffer = new StringBuffer();
    }
  }
  





  public void logSummaryLine(String msg)
  {
    LogHelper.logLine(summaryLog, summaryLogLevel, msg, false, logMessagePrefix);
  }
  





  public void logDetailLine(String msg)
  {
    LogHelper.logLine(detailLog, detailLogLevel, msg, false, logMessagePrefix);
  }
  
  public boolean isLogSingleBytesEnabled()
  {
    return logSingleBytes;
  }
}
