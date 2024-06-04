package org.silvertunnel_ng.netlib.tool;

import java.io.IOException;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





















public class ConvenientStreamReader
{
  private static final Logger LOG = LoggerFactory.getLogger(ConvenientStreamReader.class);
  private InputStream inputStream;
  
  public ConvenientStreamReader(InputStream inputStream) throws IOException {
    this.inputStream = inputStream;
  }
  




  public byte readByte()
    throws IOException
  {
    byte[] result = new byte[1];
    inputStream.read(result);
    return result[0];
  }
  





  public byte[] readByteArray(int len)
    throws IOException
  {
    if (len > 200000000) throw new IOException("lenght seems to be to big! len = " + len);
    byte[] result = new byte[len];
    inputStream.read(result);
    return result;
  }
  





  public byte[] readByteArray()
    throws IOException
  {
    int len = readInt();
    if (len == 0) { return null;
    }
    return readByteArray(len);
  }
  




  public int readInt()
    throws IOException
  {
    byte[] result = new byte[4];
    inputStream.read(result);
    return ByteUtils.bytesToInt(result, 0);
  }
  




  public boolean readBoolean()
    throws IOException
  {
    return readByte() == 1;
  }
  




  public float readFloat()
    throws IOException
  {
    return Float.intBitsToFloat(readInt());
  }
  




  public long readLong()
    throws IOException
  {
    byte[] result = new byte[8];
    inputStream.read(result);
    return ByteUtils.bytesToLong(result);
  }
  




  public String readString()
    throws IOException
  {
    byte[] tmp = readByteArray();
    if ((tmp == null) || (tmp.length == 0)) {
      return null;
    }
    return new String(tmp);
  }
}
