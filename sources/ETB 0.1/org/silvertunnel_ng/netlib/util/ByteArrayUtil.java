package org.silvertunnel_ng.netlib.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




























public final class ByteArrayUtil
{
  private static final Logger LOG = LoggerFactory.getLogger(ByteArrayUtil.class);
  

  private static final char SPECIAL_CHAR = '?';
  


  public ByteArrayUtil() {}
  

  public static String showAsString(byte[] byteArray)
  {
    StringBuffer result = new StringBuffer(byteArray.length);
    for (int i = 0; i < byteArray.length; i++)
    {
      result.append(asChar(byteArray[i]));
    }
    return result.toString();
  }
  






  public static String showAsStringDetails(byte[] byteArray)
  {
    StringBuffer result = new StringBuffer(byteArray.length);
    for (int i = 0; i < byteArray.length; i++)
    {
      result.append(asCharDetail(byteArray[i]));
    }
    return result.toString();
  }
  



  public static byte[] getByteArray(int... bytes)
  {
    byte[] result = new byte[bytes.length];
    for (int i = 0; i < bytes.length; i++)
    {
      result[i] = ((byte)bytes[i]);
    }
    return result;
  }
  

















  public static byte[] getByteArray(String prefixStr, int numOfBytesInTheMiddle, String suffixStr)
  {
    try
    {
      byte[] prefix = prefixStr.getBytes("UTF-8");
      byte[] suffix = suffixStr.getBytes("UTF-8");
      byte[] middle = new byte[numOfBytesInTheMiddle];
      for (int i = 0; i < middle.length; i++)
      {
        middle[i] = ((byte)i);
      }
      

      byte[] result = new byte[prefix.length + middle.length + suffix.length];
      System.arraycopy(prefix, 0, result, 0, prefix.length);
      System.arraycopy(middle, 0, result, prefix.length, middle.length);
      System.arraycopy(suffix, 0, result, prefix.length + middle.length, suffix.length);
      return result;

    }
    catch (UnsupportedEncodingException e)
    {
      LOG.error("", e);
    }
    return new byte[0];
  }
  




  public static char asChar(byte b)
  {
    if ((b < 32) || ((b & 0xFF) > Byte.MAX_VALUE))
    {
      return '?';
    }
    

    return (char)b;
  }
  








  public static String asCharDetail(byte b)
  {
    if ((b < 32) || ((b & 0xFF) > Byte.MAX_VALUE))
    {

      String hex = Integer.toHexString(b & 0xFF);
      if (hex.length() < 2)
      {
        return "?0" + hex;
      }
      

      return '?' + hex;
    }
    


    return Character.toString((char)b);
  }
  








  public static byte[] readDataFromInputStream(int maxResultSize, InputStream is)
    throws IOException
  {
    byte[] tempResultBuffer = new byte[maxResultSize];
    
    int len = 0;
    

    while (len < tempResultBuffer.length)
    {



      int lastLen = is.read(tempResultBuffer, len, tempResultBuffer.length - len);
      
      if (lastLen < 0) {
        break;
      }
      

      len += lastLen;
    }
    


    byte[] result = new byte[len];
    System.arraycopy(tempResultBuffer, 0, result, 0, len);
    
    return result;
  }
  






  public static byte[] concatByteArrays(byte[]... input)
  {
    int len = 0;
    for (int i = 0; i < input.length; i++)
    {
      len += input[i].length;
    }
    byte[] result = new byte[len];
    

    int pos = 0;
    for (int i = 0; i < input.length; i++)
    {
      System.arraycopy(input[i], 0, result, pos, input[i].length);
      pos += input[i].length;
    }
    
    return result;
  }
}
