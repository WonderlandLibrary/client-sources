package org.silvertunnel_ng.netlib.layer.control;





public class ControlParameters
{
  public static final long DEFAULT_CONNECT_TIMEOUT_MILLIS = 120000L;
  



  public static final long UNLIMITED_TIMOUT_MILLIS = 0L;
  



  public static final long LONG_CONNECT_TIMEOUT_MILLIS = 300000L;
  


  public static final long DEFAULT_OVERALL_TIMEOUT_MILLIS = 3600000L;
  


  public static final long DEFAULT_MAX_FILETRANSFER_BYTES = 52428800L;
  


  public static final long UNLIMITED_MAX_FILETRANSFER_BYTES = 0L;
  


  public static final long DEFAUT_THROUGPUT_TIMEFRAME_MIN_BYTES = 61440L;
  


  public static final long UNLIMITED_THROUGPUT_TIMEFRAME_MIN_BYTES = 0L;
  


  public static final long DEFAUT_THROUGPUT_TIMEFRAME_MILLIS = 60000L;
  


  private long connectTimeoutMillis;
  


  private long overallTimeoutMillis;
  


  private long inputMaxBytes;
  


  private long throughputTimeframeMinBytes;
  


  private long throughputTimeframeMillis;
  



  protected ControlParameters() {}
  



  public static ControlParameters createTypicalFileTransferParameters()
  {
    ControlParameters p = new ControlParameters();
    
    connectTimeoutMillis = 120000L;
    overallTimeoutMillis = 3600000L;
    inputMaxBytes = 52428800L;
    throughputTimeframeMinBytes = 61440L;
    throughputTimeframeMillis = 60000L;
    
    return p;
  }
  




  public static ControlParameters createTypicalStreamingParameters()
  {
    ControlParameters p = new ControlParameters();
    
    connectTimeoutMillis = 120000L;
    overallTimeoutMillis = 0L;
    inputMaxBytes = 0L;
    throughputTimeframeMinBytes = 61440L;
    throughputTimeframeMillis = 60000L;
    
    return p;
  }
  



  public static ControlParameters createUnlimitedParameters()
  {
    ControlParameters p = new ControlParameters();
    
    connectTimeoutMillis = 0L;
    overallTimeoutMillis = 0L;
    inputMaxBytes = 0L;
    throughputTimeframeMinBytes = 0L;
    throughputTimeframeMillis = 60000L;
    
    return p;
  }
  




  public long getConnectTimeoutMillis()
  {
    return connectTimeoutMillis;
  }
  
  public void setConnectTimeoutMillis(long connectTimeoutMillis)
  {
    this.connectTimeoutMillis = connectTimeoutMillis;
  }
  
  public long getOverallTimeoutMillis()
  {
    return overallTimeoutMillis;
  }
  
  public void setOverallTimeoutMillis(long overallTimeoutMillis)
  {
    this.overallTimeoutMillis = overallTimeoutMillis;
  }
  
  public long getInputMaxBytes()
  {
    return inputMaxBytes;
  }
  
  public void setInputMaxBytes(long inputMaxBytes)
  {
    this.inputMaxBytes = inputMaxBytes;
  }
  
  public long getThroughputTimeframeMillis()
  {
    return throughputTimeframeMillis;
  }
  
  public void setThroughputTimeframeMillis(long throughputTimeframeMillis)
  {
    this.throughputTimeframeMillis = throughputTimeframeMillis;
  }
  
  public long getThroughputTimeframeMinBytes()
  {
    return throughputTimeframeMinBytes;
  }
  
  public void setThroughputTimeframeMinBytes(long throughputTimeframeMinBytes)
  {
    this.throughputTimeframeMinBytes = throughputTimeframeMinBytes;
  }
}
