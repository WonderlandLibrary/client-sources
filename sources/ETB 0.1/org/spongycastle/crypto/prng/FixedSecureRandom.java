package org.spongycastle.crypto.prng;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;





public class FixedSecureRandom
  extends SecureRandom
{
  private byte[] _data;
  private int _index;
  private int _intPad;
  
  public FixedSecureRandom(byte[] value)
  {
    this(false, new byte[][] { value });
  }
  

  public FixedSecureRandom(byte[][] values)
  {
    this(false, values);
  }
  






  public FixedSecureRandom(boolean intPad, byte[] value)
  {
    this(intPad, new byte[][] { value });
  }
  






  public FixedSecureRandom(boolean intPad, byte[][] values)
  {
    ByteArrayOutputStream bOut = new ByteArrayOutputStream();
    
    for (int i = 0; i != values.length; i++)
    {
      try
      {
        bOut.write(values[i]);
      }
      catch (IOException e)
      {
        throw new IllegalArgumentException("can't save value array.");
      }
    }
    
    _data = bOut.toByteArray();
    
    if (intPad)
    {
      _intPad = (_data.length % 4);
    }
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
    
    if (_intPad == 2)
    {
      _intPad -= 1;
    }
    else
    {
      val |= nextValue() << 8;
    }
    
    if (_intPad == 1)
    {
      _intPad -= 1;
    }
    else
    {
      val |= nextValue();
    }
    
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
}
