package org.silvertunnel_ng.netlib.layer.logger;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;




























public class LoggingOutputStream
  extends FilterOutputStream
{
  private final BufferedLogger blog;
  
  protected LoggingOutputStream(OutputStream os, BufferedLogger bufferedLogger)
  {
    super(os);
    blog = bufferedLogger;
  }
  
  public void write(int b)
    throws IOException
  {
    boolean unknownThrowableIsOnTheWay = true;
    
    try
    {
      blog.log((byte)b);
      

      out.write(b);
      
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
  }
  



  public void write(byte[] b, int off, int len)
    throws IOException
  {
    boolean unknownThrowableIsOnTheWay = true;
    

    if ((off | len | b.length - (len + off) | off + len) < 0)
    {
      throw new IndexOutOfBoundsException();
    }
    

    try
    {
      for (int i = 0; i < len; i++)
      {
        blog.log(b[(off + i)]);
      }
      blog.flush();
      

      out.write(b, off, len);
      
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
  }
  

  public void flush()
    throws IOException
  {
    blog.flush();
    

    super.flush();
  }
  

  public void close()
    throws IOException
  {
    blog.flush();
    blog.logSummaryLine("stream closed");
    

    super.close();
  }
}
