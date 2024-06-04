package org.silvertunnel_ng.netlib.nameservice.logger;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.logging.Level;
import org.silvertunnel_ng.netlib.api.NetAddress;
import org.silvertunnel_ng.netlib.api.NetAddressNameService;
import org.silvertunnel_ng.netlib.util.LogHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

























public class LoggingNetAddressNameService
  implements NetAddressNameService
{
  private static Logger defaultLog = LoggerFactory.getLogger(LoggingNetAddressNameService.class);
  

  private final NetAddressNameService lowerNetAddressNameService;
  

  private final Logger log;
  

  private final Level logLevel;
  

  private final String loggingPrefix;
  

  public LoggingNetAddressNameService(NetAddressNameService lowerNetAddressNameService, Level logLevel)
  {
    this(lowerNetAddressNameService, logLevel, null);
  }
  











  public LoggingNetAddressNameService(NetAddressNameService lowerNetAddressNameService, Level logLevel, String loggingPrefix)
  {
    this(lowerNetAddressNameService, defaultLog, logLevel, loggingPrefix);
  }
  












  public LoggingNetAddressNameService(NetAddressNameService lowerNetAddressNameService, Logger log, Level logLevel, String loggingPrefix)
  {
    this.lowerNetAddressNameService = lowerNetAddressNameService;
    this.log = log;
    this.logLevel = logLevel;
    this.loggingPrefix = (loggingPrefix != null ? loggingPrefix + ": " : "");
  }
  



  public NetAddress[] getAddressesByName(String name)
    throws UnknownHostException
  {
    boolean normalEnd = false;
    boolean unknownHostExceptionEnd = false;
    
    try
    {
      LogHelper.logLine(log, logLevel, "getAddresses(name=\"" + name + "\") called", false, loggingPrefix);
      



      NetAddress[] result = lowerNetAddressNameService.getAddressesByName(name);
      

      String resultStr = result == null ? null : Arrays.toString(result);
      LogHelper.logLine(log, logLevel, "  getAddresses(name=\"" + name + "\") result=" + resultStr, false, loggingPrefix);
      
      normalEnd = true;
      
      return result;

    }
    catch (UnknownHostException e)
    {
      LogHelper.logLine(log, logLevel, " getAddresses(name=\"" + name + "\") throws " + e
        .toString(), false, loggingPrefix);
      unknownHostExceptionEnd = true;
      throw e;

    }
    finally
    {
      if ((!normalEnd) && (!unknownHostExceptionEnd))
      {
        LogHelper.logLine(log, logLevel, "  getAddresses(name=\"" + name + "\") throws UNCATHCHED EXCEPTION", false, loggingPrefix);
      }
    }
  }
  



  public String[] getNamesByAddress(NetAddress address)
    throws UnknownHostException
  {
    boolean normalEnd = false;
    boolean unknownHostExceptionEnd = false;
    
    try
    {
      LogHelper.logLine(log, logLevel, "getNames(address=\"" + address + "\") called", false, loggingPrefix);
      


      String[] result = lowerNetAddressNameService.getNamesByAddress(address);
      
      String resultStr = result == null ? null : Arrays.toString(result);
      LogHelper.logLine(log, logLevel, " getNames(address=\"" + address + "\") result=" + resultStr, false, loggingPrefix);
      
      normalEnd = true;
      
      return result;

    }
    catch (UnknownHostException e)
    {
      LogHelper.logLine(log, logLevel, "  getNames(address=\"" + address + "\") throws " + e
        .toString(), false, loggingPrefix);
      unknownHostExceptionEnd = true;
      throw e;

    }
    finally
    {
      if ((!normalEnd) && (!unknownHostExceptionEnd))
      {
        LogHelper.logLine(log, logLevel, "  getNames(address=\"" + address + "\") throws UNCATHCHED EXCEPTION", false, loggingPrefix);
      }
    }
  }
}
