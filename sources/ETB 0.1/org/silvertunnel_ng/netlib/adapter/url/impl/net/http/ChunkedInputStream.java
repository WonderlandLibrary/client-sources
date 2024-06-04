package org.silvertunnel_ng.netlib.adapter.url.impl.net.http;

import java.io.IOException;
import java.io.InputStream;
































































public class ChunkedInputStream
  extends InputStream
  implements Hurryable
{
  private InputStream in;
  private final HttpClient hc;
  private final MessageHeader responses;
  private int chunkSize;
  private int chunkRead;
  private byte[] chunkData = new byte['က'];
  





  private int chunkPos;
  





  private int chunkCount;
  




  private byte[] rawData = new byte[32];
  




  private int rawPos;
  




  private int rawCount;
  




  private boolean error;
  




  private boolean closed;
  




  static final int STATE_AWAITING_CHUNK_HEADER = 1;
  




  static final int STATE_READING_CHUNK = 2;
  



  static final int STATE_AWAITING_CHUNK_EOL = 3;
  



  static final int STATE_AWAITING_TRAILERS = 4;
  



  static final int STATE_DONE = 5;
  



  private int state;
  




  private void ensureOpen()
    throws IOException
  {
    if (closed)
    {
      throw new IOException("stream is closed");
    }
  }
  






  private void ensureRawAvailable(int size)
  {
    if (rawCount + size > rawData.length)
    {
      int used = rawCount - rawPos;
      if (used + size > rawData.length)
      {
        byte[] tmp = new byte[used + size];
        if (used > 0)
        {
          System.arraycopy(rawData, rawPos, tmp, 0, used);
        }
        rawData = tmp;


      }
      else if (used > 0)
      {
        System.arraycopy(rawData, rawPos, rawData, 0, used);
      }
      
      rawCount = used;
      rawPos = 0;
    }
  }
  







  private void closeUnderlying()
    throws IOException
  {
    if (in == null)
    {
      return;
    }
    
    if ((!error) && (state == 5))
    {
      hc.finished();


    }
    else if (!hurry())
    {
      hc.closeServer();
    }
    

    in = null;
  }
  








  private int fastRead(byte[] b, int off, int len)
    throws IOException
  {
    int remaining = chunkSize - chunkRead;
    int cnt = remaining < len ? remaining : len;
    if (cnt > 0)
    {

      try
      {
        nread = in.read(b, off, cnt);
      }
      catch (IOException e) {
        int nread;
        error = true;
        throw e; }
      int nread;
      if (nread > 0)
      {
        chunkRead += nread;
        if (chunkRead >= chunkSize)
        {
          state = 3;
        }
        return nread;
      }
      error = true;
      throw new IOException("Premature EOF");
    }
    

    return 0;
  }
  
















  private void processRaw()
    throws IOException
  {
    while (state != 5)
    {

      switch (state)
      {









      case 1: 
        int pos = rawPos;
        while (pos < rawCount)
        {
          if (rawData[pos] == 10) {
            break;
          }
          
          pos++;
        }
        if (pos >= rawCount)
        {
          return;
        }
        




        String header = new String(rawData, rawPos, pos - rawPos + 1, "US-ASCII");
        
        for (int i = 0; i < header.length(); i++)
        {
          if (Character.digit(header.charAt(i), 16) == -1) {
            break;
          }
        }
        

        try
        {
          chunkSize = Integer.parseInt(header.substring(0, i), 16);
        }
        catch (NumberFormatException e)
        {
          error = true;
          throw new IOException("Bogus chunk size");
        }
        




        rawPos = (pos + 1);
        chunkRead = 0;
        



        if (chunkSize > 0)
        {
          state = 2;
        }
        else
        {
          state = 4;
        }
        break;
      






      case 2: 
        if (rawPos >= rawCount)
        {
          return;
        }
        




        int copyLen = Math.min(chunkSize - chunkRead, rawCount - rawPos);
        




        if (chunkData.length < chunkCount + copyLen)
        {
          int cnt = chunkCount - chunkPos;
          if (chunkData.length < cnt + copyLen)
          {
            byte[] tmp = new byte[cnt + copyLen];
            System.arraycopy(chunkData, chunkPos, tmp, 0, cnt);
            chunkData = tmp;
          }
          else
          {
            System.arraycopy(chunkData, chunkPos, chunkData, 0, cnt);
          }
          
          chunkPos = 0;
          chunkCount = cnt;
        }
        




        System.arraycopy(rawData, rawPos, chunkData, chunkCount, copyLen);
        
        rawPos += copyLen;
        chunkCount += copyLen;
        chunkRead += copyLen;
        




        if (chunkSize - chunkRead <= 0)
        {
          state = 3;
        } else {
          return;
        }
        






        break;
      case 3: 
        if (rawPos + 1 >= rawCount)
        {
          return;
        }
        
        if (rawData[rawPos] != 13)
        {
          error = true;
          throw new IOException("missing CR");
        }
        if (rawData[(rawPos + 1)] != 10)
        {
          error = true;
          throw new IOException("missing LF");
        }
        rawPos += 2;
        



        state = 1;
        break;
      








      case 4: 
        int pos = rawPos;
        while (pos < rawCount)
        {
          if (rawData[pos] == 10) {
            break;
          }
          
          pos++;
        }
        if (pos >= rawCount)
        {
          return;
        }
        
        if (pos == rawPos)
        {
          error = true;
          throw new IOException("LF should be proceeded by CR");
        }
        if (rawData[(pos - 1)] != 13)
        {
          error = true;
          throw new IOException("LF should be proceeded by CR");
        }
        



        if (pos == rawPos + 1)
        {

          state = 5;
          closeUnderlying();
          
          return;
        }
        




        String trailer = new String(rawData, rawPos, pos - rawPos, "US-ASCII");
        
        int i = trailer.indexOf(':');
        if (i == -1)
        {
          throw new IOException("Malformed tailer - format should be key:value");
        }
        
        String key = trailer.substring(0, i).trim();
        
        String value = trailer.substring(i + 1, trailer.length()).trim();
        
        responses.add(key, value);
        



        rawPos = (pos + 1);
      }
      
    }
  }
  











  private int readAheadNonBlocking()
    throws IOException
  {
    int avail = in.available();
    if (avail > 0)
    {


      ensureRawAvailable(avail);
      

      try
      {
        nread = in.read(rawData, rawCount, avail);
      }
      catch (IOException e) {
        int nread;
        error = true;
        throw e; }
      int nread;
      if (nread < 0)
      {
        error = true;
        return -1;
      }
      rawCount += nread;
      



      processRaw();
    }
    



    return chunkCount - chunkPos;
  }
  







  private int readAheadBlocking()
    throws IOException
  {
    do
    {
      if (state == 5)
      {
        return -1;
      }
      





      ensureRawAvailable(32);
      
      try
      {
        nread = in.read(rawData, rawCount, rawData.length - rawCount);
      }
      catch (IOException e) {
        int nread;
        error = true;
        throw e;
      }
      


      int nread;
      

      if (nread < 0)
      {
        error = true;
        throw new IOException("Premature EOF");
      }
      



      rawCount += nread;
      processRaw();

    }
    while (chunkCount <= 0);
    



    return chunkCount - chunkPos;
  }
  









  private int readAhead(boolean allowBlocking)
    throws IOException
  {
    if (state == 5)
    {
      return -1;
    }
    



    if (chunkPos >= chunkCount)
    {
      chunkCount = 0;
      chunkPos = 0;
    }
    



    if (allowBlocking)
    {
      return readAheadBlocking();
    }
    

    return readAheadNonBlocking();
  }
  















  public ChunkedInputStream(InputStream in, HttpClient hc, MessageHeader responses)
    throws IOException
  {
    this.in = in;
    this.responses = responses;
    this.hc = hc;
    




    state = 1;
  }
  










  public synchronized int read()
    throws IOException
  {
    ensureOpen();
    if (chunkPos >= chunkCount)
    {
      if (readAhead(true) <= 0)
      {
        return -1;
      }
    }
    return chunkData[(chunkPos++)] & 0xFF;
  }
  















  public synchronized int read(byte[] b, int off, int len)
    throws IOException
  {
    ensureOpen();
    if ((off < 0) || (off > b.length) || (len < 0) || (off + len > b.length) || (off + len < 0))
    {

      throw new IndexOutOfBoundsException();
    }
    if (len == 0)
    {
      return 0;
    }
    
    int avail = chunkCount - chunkPos;
    if (avail <= 0)
    {




      if (state == 2)
      {
        return fastRead(b, off, len);
      }
      




      avail = readAhead(true);
      if (avail < 0)
      {
        return -1;
      }
    }
    int cnt = avail < len ? avail : len;
    System.arraycopy(chunkData, chunkPos, b, off, cnt);
    chunkPos += cnt;
    
    return cnt;
  }
  










  public synchronized int available()
    throws IOException
  {
    ensureOpen();
    
    int avail = chunkCount - chunkPos;
    if (avail > 0)
    {
      return avail;
    }
    
    avail = readAhead(false);
    
    if (avail < 0)
    {
      return 0;
    }
    

    return avail;
  }
  












  public synchronized void close()
    throws IOException
  {
    if (closed)
    {
      return;
    }
    closeUnderlying();
    closed = true;
  }
  









  public synchronized boolean hurry()
  {
    if ((in == null) || (error))
    {
      return false;
    }
    
    try
    {
      readAhead(false);
    }
    catch (Exception e)
    {
      return false;
    }
    
    if (error)
    {
      return false;
    }
    
    return state == 5;
  }
}
