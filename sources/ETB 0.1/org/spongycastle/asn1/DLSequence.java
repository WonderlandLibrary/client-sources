package org.spongycastle.asn1;

import java.io.IOException;
import java.util.Enumeration;




public class DLSequence
  extends ASN1Sequence
{
  private int bodyLength = -1;
  





  public DLSequence() {}
  





  public DLSequence(ASN1Encodable obj)
  {
    super(obj);
  }
  





  public DLSequence(ASN1EncodableVector v)
  {
    super(v);
  }
  





  public DLSequence(ASN1Encodable[] array)
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
        
        length += ((ASN1Encodable)obj).toASN1Primitive().toDLObject().encodedLength();
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
    ASN1OutputStream dOut = out.getDLSubStream();
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
