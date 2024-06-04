package org.silvertunnel_ng.netlib.layer.logger;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;



























public class LoggingInputStream
  extends FilterInputStream
{
  private final BufferedLogger blog;
  
  protected LoggingInputStream(InputStream is, BufferedLogger bufferedLogger)
  {
    super(is);
    blog = bufferedLogger;
  }
  
  public int read()
    throws IOException
  {
    boolean unknownThrowableIsOnTheWay = true;
    int result = 0;
    
    try
    {
      result = in.read();
      unknownThrowableIsOnTheWay = false;

    }
    catch (IOException e)
    {
      unknownThrowableIsOnTheWay = false;
      blog.flush();
      blog.logSummaryLine("throwable detected1: " + e);
      throw e;
    }
    catch (RuntimeException e)
    {
      unknownThrowableIsOnTheWay = false;
      blog.flush();
      blog.logSummaryLine("throwable detected2: " + e);
      throw e;
    }
    finally
    {
      if (unknownThrowableIsOnTheWay)
      {

        blog.flush();
        blog.logSummaryLine("throwable detected4");
      }
    }
    

    blog.log((byte)result);
    blog.flush();
    

    return result;
  }
  
  public int read(byte[] b, int off, int len)
    throws IOException
  {
    boolean unknownThrowableIsOnTheWay = true;
    int numOfBytes = 0;
    try
    {
      numOfBytes = in.read(b, off, len);
      unknownThrowableIsOnTheWay = false;

    }
    catch (IOException e)
    {
      unknownThrowableIsOnTheWay = false;
      blog.flush();
      blog.logSummaryLine("throwable detected: " + e);
      throw e;
    }
    catch (RuntimeException e)
    {
      unknownThrowableIsOnTheWay = false;
      blog.flush();
      blog.logSummaryLine("throwable detected: " + e);
      throw e;
    }
    finally
    {
      if (unknownThrowableIsOnTheWay)
      {

        blog.flush();
        blog.logSummaryLine("throwable detected");
      }
    }
    

    blog.log(b, off, numOfBytes);
    blog.flush();
    

    return numOfBytes;
  }
  

  public void close()
    throws IOException
  {
    blog.flush();
    blog.logSummaryLine("stream closed");
    

    super.close();
  }
}
