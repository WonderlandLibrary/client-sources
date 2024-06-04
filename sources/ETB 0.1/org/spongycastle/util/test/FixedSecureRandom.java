package org.spongycastle.util.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.Provider;
import java.security.SecureRandom;
import org.spongycastle.util.Pack;
import org.spongycastle.util.encoders.Hex;




public class FixedSecureRandom
  extends SecureRandom
{
  private static BigInteger REGULAR = new BigInteger("01020304ffffffff0506070811111111", 16);
  private static BigInteger ANDROID = new BigInteger("1111111105060708ffffffff01020304", 16);
  private static BigInteger CLASSPATH = new BigInteger("3020104ffffffff05060708111111", 16);
  private static final boolean isAndroidStyle;
  private static final boolean isClasspathStyle;
  private static final boolean isRegularStyle;
  private byte[] _data;
  private int _index;
  
  static {
    BigInteger check1 = new BigInteger(128, new RandomChecker());
    BigInteger check2 = new BigInteger(120, new RandomChecker());
    
    isAndroidStyle = check1.equals(ANDROID);
    isRegularStyle = check1.equals(REGULAR);
    isClasspathStyle = check2.equals(CLASSPATH);
  }
  



  public static class Source
  {
    byte[] data;
    



    Source(byte[] data)
    {
      this.data = data;
    }
  }
  



  public static class Data
    extends FixedSecureRandom.Source
  {
    public Data(byte[] data)
    {
      super();
    }
  }
  




  public static class BigInteger
    extends FixedSecureRandom.Source
  {
    public BigInteger(byte[] data)
    {
      super();
    }
    
    public BigInteger(int bitLength, byte[] data)
    {
      super();
    }
    
    public BigInteger(String hexData)
    {
      this(Hex.decode(hexData));
    }
    
    public BigInteger(int bitLength, String hexData)
    {
      super();
    }
  }
  
  public FixedSecureRandom(byte[] value)
  {
    this(new Source[] { new Data(value) });
  }
  

  public FixedSecureRandom(byte[][] values)
  {
    this(buildDataArray(values));
  }
  
  private static Data[] buildDataArray(byte[][] values)
  {
    Data[] res = new Data[values.length];
    
    for (int i = 0; i != values.length; i++)
    {
      res[i] = new Data(values[i]);
    }
    
    return res;
  }
  

  public FixedSecureRandom(Source[] sources)
  {
    super(null, new DummyProvider());
    
    ByteArrayOutputStream bOut = new ByteArrayOutputStream();
    
    if (isRegularStyle)
    {
      if (isClasspathStyle)
      {
        for (int i = 0; i != sources.length; i++)
        {
          try
          {
            if ((sources[i] instanceof BigInteger))
            {
              byte[] data = data;
              int len = data.length - data.length % 4;
              for (int w = data.length - len - 1; w >= 0; w--)
              {
                bOut.write(data[w]);
              }
              for (int w = data.length - len; w < data.length; w += 4)
              {
                bOut.write(data, w, 4);
              }
            }
            else
            {
              bOut.write(data);
            }
          }
          catch (IOException e)
          {
            throw new IllegalArgumentException("can't save value source.");
          }
          
        }
        
      } else {
        for (int i = 0; i != sources.length; i++)
        {
          try
          {
            bOut.write(data);
          }
          catch (IOException e)
          {
            throw new IllegalArgumentException("can't save value source.");
          }
        }
      }
    }
    else if (isAndroidStyle)
    {
      for (int i = 0; i != sources.length; i++)
      {
        try
        {
          if ((sources[i] instanceof BigInteger))
          {
            byte[] data = data;
            int len = data.length - data.length % 4;
            for (int w = 0; w < len; w += 4)
            {
              bOut.write(data, data.length - (w + 4), 4);
            }
            if (data.length - len != 0)
            {
              for (int w = 0; w != 4 - (data.length - len); w++)
              {
                bOut.write(0);
              }
            }
            for (int w = 0; w != data.length - len; w++)
            {
              bOut.write(data[(len + w)]);
            }
          }
          else
          {
            bOut.write(data);
          }
        }
        catch (IOException e)
        {
          throw new IllegalArgumentException("can't save value source.");
        }
        
      }
      
    } else {
      throw new IllegalStateException("Unrecognized BigInteger implementation");
    }
    
    _data = bOut.toByteArray();
  }
  
  public void nextBytes(byte[] bytes)
  {
    System.arraycopy(_data, _index, bytes, 0, bytes.length);
    
    _index += bytes.length;
  }
  
  public byte[] generateSeed(int numBytes)
  {
    byte[] bytes = new byte[numBytes];
    
    nextBytes(bytes);
    
    return bytes;
  }
  




  public int nextInt()
  {
    int val = 0;
    
    val |= nextValue() << 24;
    val |= nextValue() << 16;
    val |= nextValue() << 8;
    val |= nextValue();
    
    return val;
  }
  




  public long nextLong()
  {
    long val = 0L;
    
    val |= nextValue() << 56;
    val |= nextValue() << 48;
    val |= nextValue() << 40;
    val |= nextValue() << 32;
    val |= nextValue() << 24;
    val |= nextValue() << 16;
    val |= nextValue() << 8;
    val |= nextValue();
    
    return val;
  }
  
  public boolean isExhausted()
  {
    return _index == _data.length;
  }
  
  private int nextValue()
  {
    return _data[(_index++)] & 0xFF;
  }
  
  private static class RandomChecker
    extends SecureRandom
  {
    RandomChecker()
    {
      super(new FixedSecureRandom.DummyProvider());
    }
    
    byte[] data = Hex.decode("01020304ffffffff0506070811111111");
    int index = 0;
    
    public void nextBytes(byte[] bytes)
    {
      System.arraycopy(data, index, bytes, 0, bytes.length);
      
      index += bytes.length;
    }
  }
  
  private static byte[] expandToBitLength(int bitLength, byte[] v)
  {
    if ((bitLength + 7) / 8 > v.length)
    {
      byte[] tmp = new byte[(bitLength + 7) / 8];
      
      System.arraycopy(v, 0, tmp, tmp.length - v.length, v.length);
      if (isAndroidStyle)
      {
        if (bitLength % 8 != 0)
        {
          int i = Pack.bigEndianToInt(tmp, 0);
          Pack.intToBigEndian(i << 8 - bitLength % 8, tmp, 0);
        }
      }
      
      return tmp;
    }
    

    if ((isAndroidStyle) && (bitLength < v.length * 8))
    {
      if (bitLength % 8 != 0)
      {
        int i = Pack.bigEndianToInt(v, 0);
        Pack.intToBigEndian(i << 8 - bitLength % 8, v, 0);
      }
    }
    

    return v;
  }
  
  private static class DummyProvider
    extends Provider
  {
    DummyProvider()
    {
      super(1.0D, "BCFIPS Fixed Secure Random Provider");
    }
  }
}
