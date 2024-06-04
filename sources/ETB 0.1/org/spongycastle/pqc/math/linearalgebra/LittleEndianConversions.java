package org.spongycastle.pqc.math.linearalgebra;












public final class LittleEndianConversions
{
  private LittleEndianConversions() {}
  










  public static int OS2IP(byte[] input)
  {
    return input[0] & 0xFF | (input[1] & 0xFF) << 8 | (input[2] & 0xFF) << 16 | (input[3] & 0xFF) << 24;
  }
  









  public static int OS2IP(byte[] input, int inOff)
  {
    int result = input[(inOff++)] & 0xFF;
    result |= (input[(inOff++)] & 0xFF) << 8;
    result |= (input[(inOff++)] & 0xFF) << 16;
    result |= (input[inOff] & 0xFF) << 24;
    return result;
  }
  









  public static int OS2IP(byte[] input, int inOff, int inLen)
  {
    int result = 0;
    for (int i = inLen - 1; i >= 0; i--)
    {
      result |= (input[(inOff + i)] & 0xFF) << 8 * i;
    }
    return result;
  }
  








  public static long OS2LIP(byte[] input, int inOff)
  {
    long result = input[(inOff++)] & 0xFF;
    result |= (input[(inOff++)] & 0xFF) << 8;
    result |= (input[(inOff++)] & 0xFF) << 16;
    result |= (input[(inOff++)] & 0xFF) << 24;
    result |= (input[(inOff++)] & 0xFF) << 32;
    result |= (input[(inOff++)] & 0xFF) << 40;
    result |= (input[(inOff++)] & 0xFF) << 48;
    result |= (input[(inOff++)] & 0xFF) << 56;
    return result;
  }
  






  public static byte[] I2OSP(int x)
  {
    byte[] result = new byte[4];
    result[0] = ((byte)x);
    result[1] = ((byte)(x >>> 8));
    result[2] = ((byte)(x >>> 16));
    result[3] = ((byte)(x >>> 24));
    return result;
  }
  







  public static void I2OSP(int value, byte[] output, int outOff)
  {
    output[(outOff++)] = ((byte)value);
    output[(outOff++)] = ((byte)(value >>> 8));
    output[(outOff++)] = ((byte)(value >>> 16));
    output[(outOff++)] = ((byte)(value >>> 24));
  }
  










  public static void I2OSP(int value, byte[] output, int outOff, int outLen)
  {
    for (int i = outLen - 1; i >= 0; i--)
    {
      output[(outOff + i)] = ((byte)(value >>> 8 * i));
    }
  }
  






  public static byte[] I2OSP(long input)
  {
    byte[] output = new byte[8];
    output[0] = ((byte)(int)input);
    output[1] = ((byte)(int)(input >>> 8));
    output[2] = ((byte)(int)(input >>> 16));
    output[3] = ((byte)(int)(input >>> 24));
    output[4] = ((byte)(int)(input >>> 32));
    output[5] = ((byte)(int)(input >>> 40));
    output[6] = ((byte)(int)(input >>> 48));
    output[7] = ((byte)(int)(input >>> 56));
    return output;
  }
  







  public static void I2OSP(long input, byte[] output, int outOff)
  {
    output[(outOff++)] = ((byte)(int)input);
    output[(outOff++)] = ((byte)(int)(input >>> 8));
    output[(outOff++)] = ((byte)(int)(input >>> 16));
    output[(outOff++)] = ((byte)(int)(input >>> 24));
    output[(outOff++)] = ((byte)(int)(input >>> 32));
    output[(outOff++)] = ((byte)(int)(input >>> 40));
    output[(outOff++)] = ((byte)(int)(input >>> 48));
    output[outOff] = ((byte)(int)(input >>> 56));
  }
  









  public static byte[] toByteArray(int[] input, int outLen)
  {
    int intLen = input.length;
    byte[] result = new byte[outLen];
    int index = 0;
    for (int i = 0; i <= intLen - 2; index += 4)
    {
      I2OSP(input[i], result, index);i++;
    }
    I2OSP(input[(intLen - 1)], result, index, outLen - index);
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
