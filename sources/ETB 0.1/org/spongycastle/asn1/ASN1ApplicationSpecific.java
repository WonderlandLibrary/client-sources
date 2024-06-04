package org.spongycastle.asn1;

import java.io.IOException;
import org.spongycastle.util.Arrays;








public abstract class ASN1ApplicationSpecific
  extends ASN1Primitive
{
  protected final boolean isConstructed;
  protected final int tag;
  protected final byte[] octets;
  
  ASN1ApplicationSpecific(boolean isConstructed, int tag, byte[] octets)
  {
    this.isConstructed = isConstructed;
    this.tag = tag;
    this.octets = Arrays.clone(octets);
  }
  






  public static ASN1ApplicationSpecific getInstance(Object obj)
  {
    if ((obj == null) || ((obj instanceof ASN1ApplicationSpecific)))
    {
      return (ASN1ApplicationSpecific)obj;
    }
    if ((obj instanceof byte[]))
    {
      try
      {
        return getInstance(ASN1Primitive.fromByteArray((byte[])obj));
      }
      catch (IOException e)
      {
        throw new IllegalArgumentException("Failed to construct object from byte[]: " + e.getMessage());
      }
    }
    
    throw new IllegalArgumentException("unknown object in getInstance: " + obj.getClass().getName());
  }
  
  protected static int getLengthOfHeader(byte[] data)
  {
    int length = data[1] & 0xFF;
    
    if (length == 128)
    {
      return 2;
    }
    
    if (length > 127)
    {
      int size = length & 0x7F;
      

      if (size > 4)
      {
        throw new IllegalStateException("DER length more than 4 bytes: " + size);
      }
      
      return size + 2;
    }
    
    return 2;
  }
  





  public boolean isConstructed()
  {
    return isConstructed;
  }
  





  public byte[] getContents()
  {
    return Arrays.clone(octets);
  }
  





  public int getApplicationTag()
  {
    return tag;
  }
  






  public ASN1Primitive getObject()
    throws IOException
  {
    return ASN1Primitive.fromByteArray(getContents());
  }
  







  public ASN1Primitive getObject(int derTagNo)
    throws IOException
  {
    if (derTagNo >= 31)
    {
      throw new IOException("unsupported tag number");
    }
    
    byte[] orig = getEncoded();
    byte[] tmp = replaceTagNumber(derTagNo, orig);
    
    if ((orig[0] & 0x20) != 0)
    {
      int tmp39_38 = 0; byte[] tmp39_37 = tmp;tmp39_37[tmp39_38] = ((byte)(tmp39_37[tmp39_38] | 0x20));
    }
    
    return ASN1Primitive.fromByteArray(tmp);
  }
  
  int encodedLength()
    throws IOException
  {
    return StreamUtil.calculateTagLength(tag) + StreamUtil.calculateBodyLength(octets.length) + octets.length;
  }
  


  void encode(ASN1OutputStream out)
    throws IOException
  {
    int classBits = 64;
    if (isConstructed)
    {
      classBits |= 0x20;
    }
    
    out.writeEncoded(classBits, tag, octets);
  }
  

  boolean asn1Equals(ASN1Primitive o)
  {
    if (!(o instanceof ASN1ApplicationSpecific))
    {
      return false;
    }
    
    ASN1ApplicationSpecific other = (ASN1ApplicationSpecific)o;
    
    if ((isConstructed == isConstructed) && (tag == tag)) {} return 
    
      Arrays.areEqual(octets, octets);
  }
  
  public int hashCode()
  {
    return (isConstructed ? 1 : 0) ^ tag ^ Arrays.hashCode(octets);
  }
  
  private byte[] replaceTagNumber(int newTag, byte[] input)
    throws IOException
  {
    int tagNo = input[0] & 0x1F;
    int index = 1;
    


    if (tagNo == 31)
    {
      tagNo = 0;
      
      int b = input[(index++)] & 0xFF;
      


      if ((b & 0x7F) == 0)
      {
        throw new ASN1ParsingException("corrupted stream - invalid high tag number found");
      }
      
      while ((b >= 0) && ((b & 0x80) != 0))
      {
        tagNo |= b & 0x7F;
        tagNo <<= 7;
        b = input[(index++)] & 0xFF;
      }
    }
    


    byte[] tmp = new byte[input.length - index + 1];
    
    System.arraycopy(input, index, tmp, 1, tmp.length - 1);
    
    tmp[0] = ((byte)newTag);
    
    return tmp;
  }
}
