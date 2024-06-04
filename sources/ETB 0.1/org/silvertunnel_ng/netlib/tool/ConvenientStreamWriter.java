package org.silvertunnel_ng.netlib.tool;

import java.io.IOException;
import java.io.OutputStream;



















public class ConvenientStreamWriter
{
  private OutputStream outputStream;
  
  public ConvenientStreamWriter(OutputStream outputStream)
  {
    this.outputStream = outputStream;
  }
  
  public void writeBoolean(boolean value) throws IOException {
    outputStream.write(value ? 1 : 0);
  }
  
  public void writeByte(byte value) throws IOException {
    outputStream.write(new byte[] { value });
  }
  
  public void writeInt(int value) throws IOException {
    outputStream.write(ByteUtils.intToBytes(value));
  }
  
  public void writeLong(long value) throws IOException {
    outputStream.write(ByteUtils.longToBytes(value));
  }
  
  public void writeFloat(float value) throws IOException {
    writeByteArray(ByteUtils.intToBytes(Float.floatToIntBits(value)), false);
  }
  
  public void writeByteArray(byte[] value, boolean saveLen) throws IOException {
    if (saveLen) {
      writeInt(value.length);
    }
    outputStream.write(value);
  }
  
  public void writeString(String value) throws IOException {
    if ((value == null) || (value.length() == 0)) {
      writeInt(0);
    } else {
      writeByteArray(value.getBytes(), true);
    }
  }
}
