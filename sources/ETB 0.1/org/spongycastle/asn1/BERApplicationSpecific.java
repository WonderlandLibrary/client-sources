package org.spongycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;







public class BERApplicationSpecific
  extends ASN1ApplicationSpecific
{
  BERApplicationSpecific(boolean isConstructed, int tag, byte[] octets)
  {
    super(isConstructed, tag, octets);
  }
  








  public BERApplicationSpecific(int tag, ASN1Encodable object)
    throws IOException
  {
    this(true, tag, object);
  }
  










  public BERApplicationSpecific(boolean constructed, int tag, ASN1Encodable object)
    throws IOException
  {
    super((constructed) || (object.toASN1Primitive().isConstructed()), tag, getEncoding(constructed, object));
  }
  
  private static byte[] getEncoding(boolean explicit, ASN1Encodable object)
    throws IOException
  {
    byte[] data = object.toASN1Primitive().getEncoded("BER");
    
    if (explicit)
    {
      return data;
    }
    

    int lenBytes = getLengthOfHeader(data);
    byte[] tmp = new byte[data.length - lenBytes];
    System.arraycopy(data, lenBytes, tmp, 0, tmp.length);
    return tmp;
  }
  







  public BERApplicationSpecific(int tagNo, ASN1EncodableVector vec)
  {
    super(true, tagNo, getEncodedVector(vec));
  }
  
  private static byte[] getEncodedVector(ASN1EncodableVector vec)
  {
    ByteArrayOutputStream bOut = new ByteArrayOutputStream();
    
    for (int i = 0; i != vec.size(); i++)
    {
      try
      {
        bOut.write(((ASN1Object)vec.get(i)).getEncoded("BER"));
      }
      catch (IOException e)
      {
        throw new ASN1ParsingException("malformed object: " + e, e);
      }
    }
    return bOut.toByteArray();
  }
  


  void encode(ASN1OutputStream out)
    throws IOException
  {
    int classBits = 64;
    if (isConstructed)
    {
      classBits |= 0x20;
    }
    
    out.writeTag(classBits, tag);
    out.write(128);
    out.write(octets);
    out.write(0);
    out.write(0);
  }
}
