package org.spongycastle.asn1;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;

class StreamUtil
{
  private static final long MAX_MEMORY = Runtime.getRuntime().maxMemory();
  


  StreamUtil() {}
  


  static int findLimit(InputStream in)
  {
    if ((in instanceof LimitedInputStream))
    {
      return ((LimitedInputStream)in).getRemaining();
    }
    if ((in instanceof ASN1InputStream))
    {
      return ((ASN1InputStream)in).getLimit();
    }
    if ((in instanceof ByteArrayInputStream))
    {
      return ((ByteArrayInputStream)in).available();
    }
    if ((in instanceof FileInputStream))
    {
      try
      {
        FileChannel channel = ((FileInputStream)in).getChannel();
        long size = channel != null ? channel.size() : 2147483647L;
        
        if (size < 2147483647L)
        {
          return (int)size;
        }
      }
      catch (IOException localIOException) {}
    }
    



    if (MAX_MEMORY > 2147483647L)
    {
      return Integer.MAX_VALUE;
    }
    
    return (int)MAX_MEMORY;
  }
  

  static int calculateBodyLength(int length)
  {
    int count = 1;
    
    if (length > 127)
    {
      int size = 1;
      int val = length;
      
      while (val >>>= 8 != 0)
      {
        size++;
      }
      
      for (int i = (size - 1) * 8; i >= 0; i -= 8)
      {
        count++;
      }
    }
    
    return count;
  }
  
  static int calculateTagLength(int tagNo)
    throws IOException
  {
    int length = 1;
    
    if (tagNo >= 31)
    {
      if (tagNo < 128)
      {
        length++;
      }
      else
      {
        byte[] stack = new byte[5];
        int pos = stack.length;
        
        stack[(--pos)] = ((byte)(tagNo & 0x7F));
        
        do
        {
          tagNo >>= 7;
          stack[(--pos)] = ((byte)(tagNo & 0x7F | 0x80));
        }
        while (tagNo > 127);
        
        length += stack.length - pos;
      }
    }
    
    return length;
  }
}
