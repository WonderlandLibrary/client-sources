package org.silvertunnel_ng.netlib.layer.tor.util;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;









public class ASN1Parser
{
  private static final int ASN1_TAG_SEQUENCE = 16;
  private static final int ASN1_TAG_INTEGER = 2;
  private static final int ASN1_TAG_BITSTRING = 3;
  public ASN1Parser() {}
  
  static abstract interface ASN1Object {}
  
  static class ASN1Sequence
    implements ASN1Parser.ASN1Object
  {
    private final List<ASN1Parser.ASN1Object> items;
    
    ASN1Sequence(List<ASN1Parser.ASN1Object> items)
    {
      this.items = items;
    }
    
    List<ASN1Parser.ASN1Object> getItems()
    {
      return items;
    }
  }
  
  static class ASN1Integer implements ASN1Parser.ASN1Object
  {
    final BigInteger value;
    
    ASN1Integer(BigInteger value)
    {
      this.value = value;
    }
    
    BigInteger getValue()
    {
      return value;
    }
  }
  
  static class ASN1BitString implements ASN1Parser.ASN1Object
  {
    final byte[] bytes;
    
    ASN1BitString(byte[] bytes)
    {
      this.bytes = bytes;
    }
    
    byte[] getBytes()
    {
      return bytes;
    }
  }
  
  static class ASN1Blob
    extends ASN1Parser.ASN1BitString
  {
    ASN1Blob(byte[] bytes)
    {
      super();
    }
  }
  
  ASN1Object parseASN1(ByteBuffer data)
  {
    int typeOctet = data.get() & 0xFF;
    int tag = typeOctet & 0x1F;
    ByteBuffer objectBuffer = getObjectBuffer(data);
    
    switch (tag)
    {
    case 16: 
      return parseASN1Sequence(objectBuffer);
    case 2: 
      return parseASN1Integer(objectBuffer);
    case 3: 
      return parseASN1BitString(objectBuffer);
    }
    return createBlob(objectBuffer);
  }
  








  ByteBuffer getObjectBuffer(ByteBuffer data)
  {
    int length = parseASN1Length(data);
    if (length > data.remaining())
    {
      throw new IllegalArgumentException();
    }
    ByteBuffer objectBuffer = data.slice();
    objectBuffer.limit(length);
    data.position(data.position() + length);
    return objectBuffer;
  }
  
  int parseASN1Length(ByteBuffer data)
  {
    int firstOctet = data.get() & 0xFF;
    if (firstOctet < 128)
    {
      return firstOctet;
    }
    return parseASN1LengthLong(firstOctet & 0x7F, data);
  }
  
  int parseASN1LengthLong(int lengthOctets, ByteBuffer data)
  {
    if ((lengthOctets == 0) || (lengthOctets > 3))
    {

      throw new IllegalArgumentException();
    }
    int length = 0;
    for (int i = 0; i < lengthOctets; i++)
    {
      length <<= 8;
      length |= data.get() & 0xFF;
    }
    return length;
  }
  
  ASN1Sequence parseASN1Sequence(ByteBuffer data)
  {
    List<ASN1Object> obs = new ArrayList();
    while (data.hasRemaining())
    {
      obs.add(parseASN1(data));
    }
    return new ASN1Sequence(obs);
  }
  
  ASN1Integer parseASN1Integer(ByteBuffer data)
  {
    return new ASN1Integer(new BigInteger(getRemainingBytes(data)));
  }
  
  ASN1BitString parseASN1BitString(ByteBuffer data)
  {
    int unusedBits = data.get() & 0xFF;
    if (unusedBits != 0)
    {
      throw new IllegalArgumentException();
    }
    return new ASN1BitString(getRemainingBytes(data));
  }
  
  ASN1Blob createBlob(ByteBuffer data)
  {
    return new ASN1Blob(getRemainingBytes(data));
  }
  
  private byte[] getRemainingBytes(ByteBuffer data)
  {
    byte[] bs = new byte[data.remaining()];
    data.get(bs);
    return bs;
  }
}
