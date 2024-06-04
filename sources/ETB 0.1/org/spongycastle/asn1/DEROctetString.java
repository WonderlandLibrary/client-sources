package org.spongycastle.asn1;

import java.io.IOException;










public class DEROctetString
  extends ASN1OctetString
{
  public DEROctetString(byte[] string)
  {
    super(string);
  }
  






  public DEROctetString(ASN1Encodable obj)
    throws IOException
  {
    super(obj.toASN1Primitive().getEncoded("DER"));
  }
  
  boolean isConstructed()
  {
    return false;
  }
  
  int encodedLength()
  {
    return 1 + StreamUtil.calculateBodyLength(string.length) + string.length;
  }
  

  void encode(ASN1OutputStream out)
    throws IOException
  {
    out.writeEncoded(4, string);
  }
  


  static void encode(DEROutputStream derOut, byte[] bytes)
    throws IOException
  {
    derOut.writeEncoded(4, bytes);
  }
}
