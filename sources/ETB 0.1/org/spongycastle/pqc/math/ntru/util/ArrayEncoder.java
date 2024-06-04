package org.spongycastle.pqc.math.ntru.util;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import org.spongycastle.util.Arrays;
























public class ArrayEncoder
{
  private static final int[] COEFF1_TABLE = { 0, 0, 0, 1, 1, 1, -1, -1 };
  private static final int[] COEFF2_TABLE = { 0, 1, -1, 0, 1, -1, 0, 1 };
  



















  private static final int[] BIT1_TABLE = { 1, 1, 1, 0, 0, 0, 1, 0, 1 };
  private static final int[] BIT2_TABLE = { 1, 1, 1, 1, 0, 0, 0, 1, 0 };
  private static final int[] BIT3_TABLE = { 1, 0, 1, 0, 0, 1, 1, 1, 0 };
  




  public ArrayEncoder() {}
  



  public static byte[] encodeModQ(int[] a, int q)
  {
    int bitsPerCoeff = 31 - Integer.numberOfLeadingZeros(q);
    int numBits = a.length * bitsPerCoeff;
    int numBytes = (numBits + 7) / 8;
    byte[] data = new byte[numBytes];
    int bitIndex = 0;
    int byteIndex = 0;
    for (int i = 0; i < a.length; i++)
    {
      for (int j = 0; j < bitsPerCoeff; j++)
      {
        int currentBit = a[i] >> j & 0x1; int 
          tmp68_66 = byteIndex; byte[] tmp68_64 = data;tmp68_64[tmp68_66] = ((byte)(tmp68_64[tmp68_66] | currentBit << bitIndex));
        if (bitIndex == 7)
        {
          bitIndex = 0;
          byteIndex++;
        }
        else
        {
          bitIndex++;
        }
      }
    }
    return data;
  }
  










  public static int[] decodeModQ(byte[] data, int N, int q)
  {
    int[] coeffs = new int[N];
    int bitsPerCoeff = 31 - Integer.numberOfLeadingZeros(q);
    int numBits = N * bitsPerCoeff;
    int coeffIndex = 0;
    for (int bitIndex = 0; bitIndex < numBits; bitIndex++)
    {
      if ((bitIndex > 0) && (bitIndex % bitsPerCoeff == 0))
      {
        coeffIndex++;
      }
      int bit = getBit(data, bitIndex);
      coeffs[coeffIndex] += (bit << bitIndex % bitsPerCoeff);
    }
    return coeffs;
  }
  










  public static int[] decodeModQ(InputStream is, int N, int q)
    throws IOException
  {
    int qBits = 31 - Integer.numberOfLeadingZeros(q);
    int size = (N * qBits + 7) / 8;
    byte[] arr = Util.readFullLength(is, size);
    return decodeModQ(arr, N, q);
  }
  










  public static int[] decodeMod3Sves(byte[] data, int N)
  {
    int[] coeffs = new int[N];
    int coeffIndex = 0;
    for (int bitIndex = 0; bitIndex < data.length * 8;)
    {
      int bit1 = getBit(data, bitIndex++);
      int bit2 = getBit(data, bitIndex++);
      int bit3 = getBit(data, bitIndex++);
      int coeffTableIndex = bit1 * 4 + bit2 * 2 + bit3;
      coeffs[(coeffIndex++)] = COEFF1_TABLE[coeffTableIndex];
      coeffs[(coeffIndex++)] = COEFF2_TABLE[coeffTableIndex];
      
      if (coeffIndex > N - 2) {
        break;
      }
    }
    
    return coeffs;
  }
  









  public static byte[] encodeMod3Sves(int[] arr)
  {
    int numBits = (arr.length * 3 + 1) / 2;
    int numBytes = (numBits + 7) / 8;
    byte[] data = new byte[numBytes];
    int bitIndex = 0;
    int byteIndex = 0;
    for (int i = 0; i < arr.length / 2 * 2;)
    {
      int coeff1 = arr[(i++)] + 1;
      int coeff2 = arr[(i++)] + 1;
      if ((coeff1 == 0) && (coeff2 == 0))
      {
        throw new IllegalStateException("Illegal encoding!");
      }
      int bitTableIndex = coeff1 * 3 + coeff2;
      int[] bits = { BIT1_TABLE[bitTableIndex], BIT2_TABLE[bitTableIndex], BIT3_TABLE[bitTableIndex] };
      for (int j = 0; j < 3; j++)
      {
        int tmp136_134 = byteIndex; byte[] tmp136_133 = data;tmp136_133[tmp136_134] = ((byte)(tmp136_133[tmp136_134] | bits[j] << bitIndex));
        if (bitIndex == 7)
        {
          bitIndex = 0;
          byteIndex++;
        }
        else
        {
          bitIndex++;
        }
      }
    }
    return data;
  }
  





  public static byte[] encodeMod3Tight(int[] intArray)
  {
    BigInteger sum = BigInteger.ZERO;
    for (int i = intArray.length - 1; i >= 0; i--)
    {
      sum = sum.multiply(BigInteger.valueOf(3L));
      sum = sum.add(BigInteger.valueOf(intArray[i] + 1));
    }
    
    int size = (BigInteger.valueOf(3L).pow(intArray.length).bitLength() + 7) / 8;
    byte[] arr = sum.toByteArray();
    
    if (arr.length < size)
    {

      byte[] arr2 = new byte[size];
      System.arraycopy(arr, 0, arr2, size - arr.length, arr.length);
      return arr2;
    }
    
    if (arr.length > size)
    {

      arr = Arrays.copyOfRange(arr, 1, arr.length);
    }
    return arr;
  }
  







  public static int[] decodeMod3Tight(byte[] b, int N)
  {
    BigInteger sum = new BigInteger(1, b);
    int[] coeffs = new int[N];
    for (int i = 0; i < N; i++)
    {
      coeffs[i] = (sum.mod(BigInteger.valueOf(3L)).intValue() - 1);
      if (coeffs[i] > 1)
      {
        coeffs[i] -= 3;
      }
      sum = sum.divide(BigInteger.valueOf(3L));
    }
    return coeffs;
  }
  







  public static int[] decodeMod3Tight(InputStream is, int N)
    throws IOException
  {
    int size = (int)Math.ceil(N * Math.log(3.0D) / Math.log(2.0D) / 8.0D);
    byte[] arr = Util.readFullLength(is, size);
    return decodeMod3Tight(arr, N);
  }
  
  private static int getBit(byte[] arr, int bitIndex)
  {
    int byteIndex = bitIndex / 8;
    int arrElem = arr[byteIndex] & 0xFF;
    return arrElem >> bitIndex % 8 & 0x1;
  }
}
