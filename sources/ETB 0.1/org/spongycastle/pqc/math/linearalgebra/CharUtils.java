package org.spongycastle.pqc.math.linearalgebra;








public final class CharUtils
{
  private CharUtils() {}
  






  public static char[] clone(char[] array)
  {
    char[] result = new char[array.length];
    System.arraycopy(array, 0, result, 0, array.length);
    return result;
  }
  






  public static byte[] toByteArray(char[] chars)
  {
    byte[] result = new byte[chars.length];
    for (int i = chars.length - 1; i >= 0; i--)
    {
      result[i] = ((byte)chars[i]);
    }
    return result;
  }
  








  public static byte[] toByteArrayForPBE(char[] chars)
  {
    byte[] out = new byte[chars.length];
    
    for (int i = 0; i < chars.length; i++)
    {
      out[i] = ((byte)chars[i]);
    }
    
    int length = out.length * 2;
    byte[] ret = new byte[length + 2];
    
    int j = 0;
    for (int i = 0; i < out.length; i++)
    {
      j = i * 2;
      ret[j] = 0;
      ret[(j + 1)] = out[i];
    }
    
    ret[length] = 0;
    ret[(length + 1)] = 0;
    
    return ret;
  }
  







  public static boolean equals(char[] left, char[] right)
  {
    if (left.length != right.length)
    {
      return false;
    }
    boolean result = true;
    for (int i = left.length - 1; i >= 0; i--)
    {
      result &= left[i] == right[i];
    }
    return result;
  }
}
