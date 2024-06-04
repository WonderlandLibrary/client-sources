package org.silvertunnel_ng.netlib.layer.logger;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import org.silvertunnel_ng.netlib.api.NetAddress;
import org.silvertunnel_ng.netlib.api.NetAddressNameService;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.api.NetLayerStatus;
import org.silvertunnel_ng.netlib.api.NetServerSocket;
import org.silvertunnel_ng.netlib.api.NetSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

























public class LoggingNetLayer
  implements NetLayer
{
  private static Logger defaultLog = LoggerFactory.getLogger(LoggingNetLayer.class);
  
  private final NetLayer lowerNetLayer;
  
  private final Logger summaryLog;
  
  private final Level summaryLogLevel;
  private final Logger detailLog;
  private final Level detailLogLevel;
  private final boolean logContent;
  private final String topDownLoggingPrefix;
  private final String bottomUpLoggingPrefix;
  private static long connectionAttemptsGlobalCounter = 0L;
  

  private long connectionAttemptsCounter = 0L;
  




  private long connectionEstablisedCounter = 0L;
  









  public LoggingNetLayer(NetLayer lowerNetLayer, String loggingPrefix)
  {
    this(lowerNetLayer, LoggerFactory.getLogger(lowerNetLayer.getClass()), Level.FINE, 
      LoggerFactory.getLogger(lowerNetLayer.getClass()), Level.FINER, true, "v [down] " + loggingPrefix + ": ", "^ [up]   " + loggingPrefix + ": ");
  }
  

















  public LoggingNetLayer(NetLayer lowerNetLayer, Logger summaryLog, Level summaryLogLevel, Logger detailLog, Level detailLogLevel, boolean logContent, String topDownLoggingPrefix, String bottomUpLoggingPrefix)
  {
    this.lowerNetLayer = lowerNetLayer;
    this.summaryLog = summaryLog;
    this.summaryLogLevel = summaryLogLevel;
    this.detailLog = detailLog;
    this.detailLogLevel = detailLogLevel;
    this.logContent = logContent;
    this.topDownLoggingPrefix = topDownLoggingPrefix;
    this.bottomUpLoggingPrefix = bottomUpLoggingPrefix;
  }
  





  public NetSocket createNetSocket(Map<String, Object> localProperties, NetAddress localAddress, NetAddress remoteAddress)
    throws IOException
  {
    String connectionIdStr = "<conn=" + getNextConnectionAttemptGlobalCounter() + "> ";
    synchronized (this)
    {
      connectionAttemptsCounter += 1L;
    }
    BufferedLogger tmpLog = new BufferedLogger(summaryLog, summaryLogLevel, detailLog, detailLogLevel, logContent, connectionIdStr + topDownLoggingPrefix);
    

    tmpLog.logSummaryLine("createNetSocket with localProperties=" + localProperties + ", localAddress=" + localAddress + ", remoteAddress=" + remoteAddress);
    



    NetSocket lowerLayerSocket = lowerNetLayer.createNetSocket(localProperties, localAddress, remoteAddress);
    
    NetSocket result = new LoggingNetSocket(lowerLayerSocket, summaryLog, summaryLogLevel, detailLog, detailLogLevel, logContent, connectionIdStr + topDownLoggingPrefix, connectionIdStr + bottomUpLoggingPrefix);
    


    tmpLog.logSummaryLine("createNetSocket was successful for lowerLayerSocket=" + lowerLayerSocket);
    


    synchronized (this)
    {
      connectionEstablisedCounter += 1L;
    }
    
    return result;
  }
  





  public NetServerSocket createNetServerSocket(Map<String, Object> properties, NetAddress localListenAddress)
    throws IOException
  {
    String connectionIdStr = "<server-conn=" + getNextConnectionAttemptGlobalCounter() + "> ";
    BufferedLogger tmpLog = new BufferedLogger(summaryLog, summaryLogLevel, detailLog, detailLogLevel, logContent, connectionIdStr + topDownLoggingPrefix);
    

    tmpLog.logSummaryLine("createNetSocket with properties=" + properties + ", localListenAddress=" + localListenAddress);
    

    return lowerNetLayer.createNetServerSocket(properties, localListenAddress);
  }
  






  protected static synchronized long getNextConnectionAttemptGlobalCounter()
  {
    connectionAttemptsGlobalCounter += 1L;
    if (connectionAttemptsGlobalCounter < 0L)
    {
      connectionAttemptsGlobalCounter = 1L;
    }
    return connectionAttemptsGlobalCounter;
  }
  


  public NetLayerStatus getStatus()
  {
    return lowerNetLayer.getStatus();
  }
  


  public void waitUntilReady()
  {
    lowerNetLayer.waitUntilReady();
  }
  

  public void clear()
    throws IOException
  {
    lowerNetLayer.clear();
  }
  


  public NetAddressNameService getNetAddressNameService()
  {
    return lowerNetLayer.getNetAddressNameService();
  }
  







  public static synchronized long getConnectionAttemptsGlobalCounter()
  {
    return connectionAttemptsGlobalCounter;
  }
  



  public synchronized long getConnectionAttemptsCounter()
  {
    return connectionAttemptsCounter;
  }
  




  public synchronized long getConnectionEstablisedCounter()
  {
    return connectionEstablisedCounter;
  }
}
