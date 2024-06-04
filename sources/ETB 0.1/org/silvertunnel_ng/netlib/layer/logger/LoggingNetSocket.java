package org.silvertunnel_ng.netlib.layer.logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import org.silvertunnel_ng.netlib.api.NetSocket;
import org.slf4j.Logger;




























public class LoggingNetSocket
  implements NetSocket
{
  private final NetSocket lowerLayerSocket;
  private final Logger summaryLog;
  private final Level summaryLogLevel;
  private final Logger detailLog;
  private final Level detailLogLevel;
  private final boolean logContent;
  private final String topDownLoggingPrefix;
  private final String bottomUpLoggingPrefix;
  private InputStream in;
  private OutputStream out;
  
  public LoggingNetSocket(NetSocket lowerLayerSocket, Logger summaryLog, Level summaryLogLevel, Logger detailLog, Level detailLogLevel, boolean logContent, String topDownLoggingPrefix, String bottomUpLoggingPrefix)
  {
    this.lowerLayerSocket = lowerLayerSocket;
    this.summaryLog = summaryLog;
    this.summaryLogLevel = summaryLogLevel;
    this.detailLog = detailLog;
    this.detailLogLevel = detailLogLevel;
    this.logContent = logContent;
    this.topDownLoggingPrefix = topDownLoggingPrefix;
    this.bottomUpLoggingPrefix = bottomUpLoggingPrefix;
  }
  
  public void close()
    throws IOException
  {
    lowerLayerSocket.close();
  }
  
  public InputStream getInputStream()
    throws IOException
  {
    if (in == null)
    {
      BufferedLogger bufferedLogger = new BufferedLogger(summaryLog, summaryLogLevel, detailLog, detailLogLevel, logContent, bottomUpLoggingPrefix);
      

      in = new LoggingInputStream(lowerLayerSocket.getInputStream(), bufferedLogger);
    }
    
    return in;
  }
  
  public OutputStream getOutputStream()
    throws IOException
  {
    if (out == null)
    {
      BufferedLogger bufferedLogger = new BufferedLogger(summaryLog, summaryLogLevel, detailLog, detailLogLevel, logContent, topDownLoggingPrefix);
      

      out = new LoggingOutputStream(lowerLayerSocket.getOutputStream(), bufferedLogger);
    }
    
    return out;
  }
  

  public String toString()
  {
    return "LoggingNetSocket(" + lowerLayerSocket + ")";
  }
}
