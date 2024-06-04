package org.silvertunnel_ng.netlib.tool;

import java.io.IOException;
import java.io.InputStream;



























public class DynByteBuffer
{
  private static final int DEFAULT_START_SIZE = 10000;
  private static final int DEFAULT_INC_SIZE = 10000;
  private boolean copyOnWrite = false;
  private transient int length;
  private transient byte[] buffer;
  
  public DynByteBuffer()
  {
    setSize(10000L);
  }
  






  public DynByteBuffer(byte[] data)
  {
    setBuffer(data);
  }
  






  public DynByteBuffer(InputStream inputStream)
    throws IOException
  {
    setSize(10000L);
    init();
    append(inputStream);
    crrPosRead = 0;
  }
  







  public DynByteBuffer(byte[] data, boolean copyOnWrite)
  {
    setBuffer(data, copyOnWrite);
  }
  








  public DynByteBuffer(byte[] data, boolean copyOnWrite, int initialOffset)
  {
    setBuffer(data, copyOnWrite);
    resetPosition(initialOffset);
  }
  






  public DynByteBuffer(long initSize)
  {
    if (initSize <= 0L)
    {
      setSize(10000L);
    }
    else
    {
      setSize(initSize);
    }
  }
  




  private void setSize(long size)
  {
    if (length == 0)
    {
      buffer = new byte[size > 2147483647L ? Integer.MAX_VALUE : (int)size];
    }
    else
    {
      byte[] newBuffer = new byte[size > 2147483647L ? Integer.MAX_VALUE : (int)size];
      System.arraycopy(buffer, 0, newBuffer, 0, length);
      buffer = newBuffer;
    }
  }
  




  private void increaseSize()
  {
    increaseSize(10000);
  }
  




  private void increaseSize(int size)
  {
    setSize(buffer.length + size);
  }
  













  public final void append(boolean value)
  {
    copyOnWrite();
    if (length + 1 >= buffer.length)
    {
      increaseSize();
    }
    buffer[length] = ((byte)(value ? 1 : 0));
    length += 1;
  }
  


  private void copyOnWrite()
  {
    if (copyOnWrite)
    {
      synchronized (buffer)
      {
        byte[] tmpBuffer = new byte[buffer.length];
        System.arraycopy(buffer, 0, tmpBuffer, 0, buffer.length);
        buffer = tmpBuffer;
        copyOnWrite = false;
      }
    }
  }
  



  public final void append(byte value)
  {
    copyOnWrite();
    if (length + 1 >= buffer.length)
    {
      increaseSize();
    }
    buffer[length] = value;
    length += 1;
  }
  








  public final void append(byte[] array, boolean saveLength)
  {
    copyOnWrite();
    if (array == null)
    {
      if (saveLength)
      {
        append(0);
      }
    }
    else
    {
      if (saveLength)
      {
        append(array.length);
      }
      append(array, 0, array.length);
    }
  }
  








  public final void append(byte[] array, int offset)
  {
    copyOnWrite();
    append(array, offset, array.length - offset);
  }
  










  public final void append(byte[] array, int offset, int length)
  {
    copyOnWrite();
    if (offset + length > array.length)
    {
      throw new ArrayIndexOutOfBoundsException(length + offset);
    }
    if (this.length + length >= buffer.length)
    {
      if (length > 10000)
      {
        increaseSize(length + 10000);

      }
      else
      {
        increaseSize();
      }
    }
    System.arraycopy(array, offset, buffer, this.length, length);
    this.length += length;
  }
  



  public final byte[] toArray()
  {
    return toArray(0);
  }
  





  public final byte[] toArray(int offset)
  {
    byte[] tmp = new byte[length - offset];
    System.arraycopy(buffer, offset, tmp, 0, length - offset);
    return tmp;
  }
  



  public final void init()
  {
    length = 0;
  }
  




  public final void append(int value)
  {
    copyOnWrite();
    append(ByteUtils.intToBytes(value), false);
  }
  




  public final void append(float value)
  {
    copyOnWrite();
    append(ByteUtils.intToBytes(Float.floatToIntBits(value)), false);
  }
  




  public final void append(double value)
  {
    copyOnWrite();
    append(ByteUtils.longToBytes(Double.doubleToLongBits(value)), false);
  }
  




  public final void append(long value)
  {
    copyOnWrite();
    append(ByteUtils.longToBytes(value), false);
  }
  




  public final void append(Long value)
  {
    copyOnWrite();
    append(ByteUtils.longToBytes(value.longValue()), false);
  }
  




  public final void append(String value)
  {
    copyOnWrite();
    if ((value == null) || (value.isEmpty()))
    {
      append(0);
      return;
    }
    append(value.getBytes(), true);
  }
  



  public final int getSize()
  {
    return buffer.length;
  }
  



  public final int getLength()
  {
    return length;
  }
  



  public final boolean isEmpty()
  {
    return getSize() == 0;
  }
  

  private transient int crrPosRead = 0;
  



  public final void resetPosition()
  {
    resetPosition(0);
  }
  




  public final void resetPosition(int pos)
  {
    crrPosRead = pos;
  }
  



  public final void clear()
  {
    resetPosition();
    length = 0;
  }
  


  public final int getNextInt()
  {
    if (crrPosRead >= length)
    {
      throw new ArrayIndexOutOfBoundsException(crrPosRead);
    }
    int tmp = ByteUtils.bytesToInt(buffer, crrPosRead);
    crrPosRead += 4;
    return tmp;
  }
  



  public final float getNextFloat()
  {
    return Float.intBitsToFloat(getNextInt());
  }
  



  public final double getNextDouble()
  {
    return Double.longBitsToDouble(getNextLong());
  }
  



  public final long getNextLong()
  {
    if (crrPosRead >= length)
    {
      throw new ArrayIndexOutOfBoundsException(crrPosRead);
    }
    long tmp = ByteUtils.bytesToLong(buffer, crrPosRead);
    crrPosRead += 8;
    return tmp;
  }
  



  public final byte getNextByte()
  {
    if (crrPosRead >= length)
    {
      throw new ArrayIndexOutOfBoundsException(crrPosRead);
    }
    byte tmp = buffer[crrPosRead];
    crrPosRead += 1;
    return tmp;
  }
  



  public final int getNextByteAsInt()
  {
    if (crrPosRead >= length)
    {
      throw new ArrayIndexOutOfBoundsException(crrPosRead);
    }
    byte tmp = buffer[crrPosRead];
    crrPosRead += 1;
    return tmp & 0xFF;
  }
  



  public final boolean getNextBoolean()
  {
    if (crrPosRead >= length)
    {
      throw new ArrayIndexOutOfBoundsException(crrPosRead);
    }
    byte tmp = getNextByte();
    return tmp == 1;
  }
  




  public final byte[] getNextByteArray()
  {
    if (crrPosRead >= length)
    {
      throw new ArrayIndexOutOfBoundsException(crrPosRead);
    }
    int len = getNextInt();
    return getNextByteArray(len);
  }
  



  public final String getNextString()
  {
    if (crrPosRead >= length)
    {
      throw new ArrayIndexOutOfBoundsException(crrPosRead);
    }
    byte[] tmp = getNextByteArray();
    if ((tmp == null) || (tmp.length == 0))
    {
      return null;
    }
    return new String(tmp);
  }
  







  public final byte[] getNextByteArray(int length)
  {
    if (length == 0)
    {
      return null;
    }
    if (crrPosRead >= this.length)
    {
      throw new ArrayIndexOutOfBoundsException(crrPosRead);
    }
    byte[] tmp = new byte[length];
    System.arraycopy(buffer, crrPosRead, tmp, 0, length);
    crrPosRead += length;
    return tmp;
  }
  




  public final void setBuffer(byte[] data)
  {
    setBuffer(data, false);
  }
  



  public final void append(InputStream inputStream)
    throws IOException
  {
    byte[] buf = new byte['ࠀ'];
    int count;
    while ((count = inputStream.read(buf)) > 0)
    {
      append(buf, 0, count);
    }
    inputStream.close();
  }
  





  public final synchronized void setBuffer(byte[] data, boolean copyOnWrite)
  {
    if (!copyOnWrite)
    {
      buffer = new byte[data.length];
      System.arraycopy(data, 0, buffer, 0, data.length);
    }
    else
    {
      buffer = data;
      this.copyOnWrite = copyOnWrite;
    }
    length = data.length;
    crrPosRead = 0;
  }
}
