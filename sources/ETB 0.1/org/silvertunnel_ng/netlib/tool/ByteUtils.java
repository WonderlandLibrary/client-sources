package org.silvertunnel_ng.netlib.tool;

import java.nio.ByteBuffer;



























public final class ByteUtils
{
  private static ByteBuffer longBuffer = ByteBuffer.allocate(8);
  


  private static ByteBuffer intBuffer = ByteBuffer.allocate(4);
  





  private ByteUtils() {}
  





  public static byte[] longToBytes(long value)
  {
    synchronized (longBuffer)
    {
      longBuffer.clear();
      longBuffer.putLong(value);
      return longBuffer.array();
    }
  }
  







  public static long bytesToLong(byte[] value)
  {
    return bytesToLong(value, 0);
  }
  









  public static long bytesToLong(byte[] value, int offset)
  {
    synchronized (longBuffer)
    {
      longBuffer.clear();
      longBuffer.put(value, offset, 8);
      longBuffer.flip();
      return longBuffer.getLong();
    }
  }
  







  public static byte[] intToBytes(int value)
  {
    synchronized (intBuffer)
    {
      intBuffer.clear();
      intBuffer.putInt(value);
      return intBuffer.array();
    }
  }
  









  public static int bytesToInt(byte[] value, int offset)
  {
    synchronized (intBuffer)
    {
      intBuffer.clear();
      intBuffer.put(value, offset, 4);
      intBuffer.flip();
      return intBuffer.getInt();
    }
  }
  






  public static Boolean[] getBooleansFromByte(byte data)
  {
    int tmp = data & 0xFF;
    Boolean[] result = new Boolean[4];
    result[0] = ((tmp & 0x3) == 2 ? null : Boolean.valueOf((tmp & 0x3) == 1 ? 1 : false));
    result[1] = ((tmp & 0xC) == 8 ? null : Boolean.valueOf((tmp & 0xC) == 4 ? 1 : false));
    result[2] = ((tmp & 0x30) == 32 ? null : Boolean.valueOf((tmp & 0x30) == 16 ? 1 : false));
    result[3] = ((tmp & 0xC0) == 128 ? null : Boolean.valueOf((tmp & 0xC0) == 64 ? 1 : false));
    return result;
  }
  











  public static byte getByteFromBooleans(Boolean value, Boolean... optValues)
  {
    int result = 0;
    if (value == null)
    {
      result += 2;
    }
    else if (value.booleanValue())
    {
      result++;
    }
    if ((optValues.length < 1) || (optValues[0] == null))
    {
      result += 8;
    }
    else if (optValues[0].booleanValue())
    {
      result += 4;
    }
    if ((optValues.length < 2) || (optValues[1] == null))
    {
      result += 32;
    }
    else if (optValues[1].booleanValue())
    {
      result += 16;
    }
    if ((optValues.length < 3) || (optValues[2] == null))
    {
      result += 128;
    }
    else if (optValues[2].booleanValue())
    {
      result += 64;
    }
    return (byte)result;
  }
}
