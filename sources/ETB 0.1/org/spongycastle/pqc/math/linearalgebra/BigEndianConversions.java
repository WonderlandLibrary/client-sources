package org.spongycastle.pqc.math.linearalgebra;












public final class BigEndianConversions
{
  private BigEndianConversions() {}
  










  public static byte[] I2OSP(int x)
  {
    byte[] result = new byte[4];
    result[0] = ((byte)(x >>> 24));
    result[1] = ((byte)(x >>> 16));
    result[2] = ((byte)(x >>> 8));
    result[3] = ((byte)x);
    return result;
  }
  












  public static byte[] I2OSP(int x, int oLen)
    throws ArithmeticException
  {
    if (x < 0)
    {
      return null;
    }
    int octL = IntegerFunctions.ceilLog256(x);
    if (octL > oLen)
    {
      throw new ArithmeticException("Cannot encode given integer into specified number of octets.");
    }
    
    byte[] result = new byte[oLen];
    for (int i = oLen - 1; i >= oLen - octL; i--)
    {
      result[i] = ((byte)(x >>> 8 * (oLen - 1 - i)));
    }
    return result;
  }
  








  public static void I2OSP(int input, byte[] output, int outOff)
  {
    output[(outOff++)] = ((byte)(input >>> 24));
    output[(outOff++)] = ((byte)(input >>> 16));
    output[(outOff++)] = ((byte)(input >>> 8));
    output[outOff] = ((byte)input);
  }
  







  public static byte[] I2OSP(long input)
  {
    byte[] output = new byte[8];
    output[0] = ((byte)(int)(input >>> 56));
    output[1] = ((byte)(int)(input >>> 48));
    output[2] = ((byte)(int)(input >>> 40));
    output[3] = ((byte)(int)(input >>> 32));
    output[4] = ((byte)(int)(input >>> 24));
    output[5] = ((byte)(int)(input >>> 16));
    output[6] = ((byte)(int)(input >>> 8));
    output[7] = ((byte)(int)input);
    return output;
  }
  








  public static void I2OSP(long input, byte[] output, int outOff)
  {
    output[(outOff++)] = ((byte)(int)(input >>> 56));
    output[(outOff++)] = ((byte)(int)(input >>> 48));
    output[(outOff++)] = ((byte)(int)(input >>> 40));
    output[(outOff++)] = ((byte)(int)(input >>> 32));
    output[(outOff++)] = ((byte)(int)(input >>> 24));
    output[(outOff++)] = ((byte)(int)(input >>> 16));
    output[(outOff++)] = ((byte)(int)(input >>> 8));
    output[outOff] = ((byte)(int)input);
  }
  











  public static void I2OSP(int input, byte[] output, int outOff, int length)
  {
    for (int i = length - 1; i >= 0; i--)
    {
      output[(outOff + i)] = ((byte)(input >>> 8 * (length - 1 - i)));
    }
  }
  










  public static int OS2IP(byte[] input)
  {
    if (input.length > 4)
    {
      throw new ArithmeticException("invalid input length");
    }
    if (input.length == 0)
    {
      return 0;
    }
    int result = 0;
    for (int j = 0; j < input.length; j++)
    {
      result |= (input[j] & 0xFF) << 8 * (input.length - 1 - j);
    }
    return result;
  }
  








  public static int OS2IP(byte[] input, int inOff)
  {
    int result = (input[(inOff++)] & 0xFF) << 24;
    result |= (input[(inOff++)] & 0xFF) << 16;
    result |= (input[(inOff++)] & 0xFF) << 8;
    result |= input[inOff] & 0xFF;
    return result;
  }
  












  public static int OS2IP(byte[] input, int inOff, int inLen)
  {
    if ((input.length == 0) || (input.length < inOff + inLen - 1))
    {
      return 0;
    }
    int result = 0;
    for (int j = 0; j < inLen; j++)
    {
      result |= (input[(inOff + j)] & 0xFF) << 8 * (inLen - j - 1);
    }
    return result;
  }
  








  public static long OS2LIP(byte[] input, int inOff)
  {
    long result = (input[(inOff++)] & 0xFF) << 56;
    result |= (input[(inOff++)] & 0xFF) << 48;
    result |= (input[(inOff++)] & 0xFF) << 40;
    result |= (input[(inOff++)] & 0xFF) << 32;
    result |= (input[(inOff++)] & 0xFF) << 24;
    result |= (input[(inOff++)] & 0xFF) << 16;
    result |= (input[(inOff++)] & 0xFF) << 8;
    result |= input[inOff] & 0xFF;
    return result;
  }
  






  public static byte[] toByteArray(int[] input)
  {
    byte[] result = new byte[input.length << 2];
    for (int i = 0; i < input.length; i++)
    {
      I2OSP(input[i], result, i << 2);
    }
    return result;
  }
  









  public static byte[] toByteArray(int[] input, int length)
  {
    int intLen = input.length;
    byte[] result = new byte[length];
    int index = 0;
    for (int i = 0; i <= intLen - 2; index += 4)
    {
      I2OSP(input[i], result, index);i++;
    }
    I2OSP(input[(intLen - 1)], result, index, length - index);
    return result;
  }
  






  public static int[] toIntArray(byte[] input)
  {
    int intLen = (input.length + 3) / 4;
    int lastLen = input.length & 0x3;
    int[] result = new int[intLen];
    
    int index = 0;
    for (int i = 0; i <= intLen - 2; index += 4)
    {
      result[i] = OS2IP(input, index);i++;
    }
    if (lastLen != 0)
    {
      result[(intLen - 1)] = OS2IP(input, index, lastLen);
    }
    else
    {
      result[(intLen - 1)] = OS2IP(input, index);
    }
    
    return result;
  }
}
