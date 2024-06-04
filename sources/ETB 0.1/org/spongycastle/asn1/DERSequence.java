package org.spongycastle.asn1;

import java.io.IOException;
import java.util.Enumeration;







public class DERSequence
  extends ASN1Sequence
{
  private int bodyLength = -1;
  





  public DERSequence() {}
  





  public DERSequence(ASN1Encodable obj)
  {
    super(obj);
  }
  





  public DERSequence(ASN1EncodableVector v)
  {
    super(v);
  }
  





  public DERSequence(ASN1Encodable[] array)
  {
    super(array);
  }
  
  private int getBodyLength()
    throws IOException
  {
    if (bodyLength < 0)
    {
      int length = 0;
      
      for (Enumeration e = getObjects(); e.hasMoreElements();)
      {
        Object obj = e.nextElement();
        
        length += ((ASN1Encodable)obj).toASN1Primitive().toDERObject().encodedLength();
      }
      
      bodyLength = length;
    }
    
    return bodyLength;
  }
  
  int encodedLength()
    throws IOException
  {
    int length = getBodyLength();
    
    return 1 + StreamUtil.calculateBodyLength(length) + length;
  }
  









  void encode(ASN1OutputStream out)
    throws IOException
  {
    ASN1OutputStream dOut = out.getDERSubStream();
    int length = getBodyLength();
    
    out.write(48);
    out.writeLength(length);
    
    for (Enumeration e = getObjects(); e.hasMoreElements();)
    {
      Object obj = e.nextElement();
      
      dOut.writeObject((ASN1Encodable)obj);
    }
  }
}
