package org.silvertunnel_ng.netlib.util;

import java.util.logging.Level;
import org.slf4j.Logger;




















public final class LogHelper
{
  private LogHelper() {}
  
  public static boolean isLoggable(Logger logger, Level level)
  {
    if (level == Level.INFO)
    {
      return logger.isInfoEnabled();
    }
    if (level == Level.SEVERE)
    {
      return logger.isErrorEnabled();
    }
    if (level == Level.WARNING)
    {
      return logger.isWarnEnabled();
    }
    if ((level == Level.FINE) || (level == Level.FINER) || (level == Level.FINEST))
    {
      return logger.isDebugEnabled();
    }
    return false;
  }
  













  public static void logLine(Logger logger, Level level, String msg, boolean withStackTrace, String logMessagePrefix)
  {
    String finalMsg = logMessagePrefix + msg;
    if (level == Level.INFO)
    {
      if (withStackTrace)
      {
        logger.info(finalMsg, new Throwable());
      }
      else
      {
        logger.info(finalMsg);
      }
    }
    else if (level == Level.SEVERE)
    {
      if (withStackTrace)
      {
        logger.error(finalMsg, new Throwable());
      }
      else
      {
        logger.error(finalMsg);
      }
    }
    else if (level == Level.WARNING)
    {
      if (withStackTrace)
      {
        logger.warn(finalMsg, new Throwable());
      }
      else
      {
        logger.warn(finalMsg);
      }
    }
    else if ((level == Level.FINE) || (level == Level.FINER) || (level == Level.FINEST))
    {
      if (withStackTrace)
      {
        logger.debug(finalMsg, new Throwable());
      }
      else
      {
        logger.debug(finalMsg);
      }
    }
  }
}
