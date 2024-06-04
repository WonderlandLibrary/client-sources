package org.spongycastle.pqc.crypto.gmss.util;

import java.io.PrintStream;






public class GMSSUtil
{
  public GMSSUtil() {}
  
  public byte[] intToBytesLittleEndian(int value)
  {
    byte[] bytes = new byte[4];
    
    bytes[0] = ((byte)(value & 0xFF));
    bytes[1] = ((byte)(value >> 8 & 0xFF));
    bytes[2] = ((byte)(value >> 16 & 0xFF));
    bytes[3] = ((byte)(value >> 24 & 0xFF));
    return bytes;
  }
  








  public int bytesToIntLittleEndian(byte[] bytes)
  {
    return bytes[0] & 0xFF | (bytes[1] & 0xFF) << 8 | (bytes[2] & 0xFF) << 16 | (bytes[3] & 0xFF) << 24;
  }
  









  public int bytesToIntLittleEndian(byte[] bytes, int offset)
  {
    return bytes[(offset++)] & 0xFF | (bytes[(offset++)] & 0xFF) << 8 | (bytes[(offset++)] & 0xFF) << 16 | (bytes[offset] & 0xFF) << 24;
  }
  









  public byte[] concatenateArray(byte[][] arraycp)
  {
    byte[] dest = new byte[arraycp.length * arraycp[0].length];
    int indx = 0;
    for (int i = 0; i < arraycp.length; i++)
    {
      System.arraycopy(arraycp[i], 0, dest, indx, arraycp[i].length);
      indx += arraycp[i].length;
    }
    return dest;
  }
  






  public void printArray(String text, byte[][] array)
  {
    System.out.println(text);
    int counter = 0;
    for (int i = 0; i < array.length; i++)
    {
      for (int j = 0; j < array[0].length; j++)
      {
        System.out.println(counter + "; " + array[i][j]);
        counter++;
      }
    }
  }
  






  public void printArray(String text, byte[] array)
  {
    System.out.println(text);
    int counter = 0;
    for (int i = 0; i < array.length; i++)
    {
      System.out.println(counter + "; " + array[i]);
      counter++;
    }
  }
  







  public boolean testPowerOfTwo(int testValue)
  {
    int a = 1;
    while (a < testValue)
    {
      a <<= 1;
    }
    if (testValue == a)
    {
      return true;
    }
    
    return false;
  }
  








  public int getLog(int intValue)
  {
    int log = 1;
    int i = 2;
    while (i < intValue)
    {
      i <<= 1;
      log++;
    }
    return log;
  }
}
