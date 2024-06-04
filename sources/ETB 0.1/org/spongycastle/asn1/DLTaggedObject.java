package org.spongycastle.asn1;

import java.io.IOException;






public class DLTaggedObject
  extends ASN1TaggedObject
{
  private static final byte[] ZERO_BYTES = new byte[0];
  








  public DLTaggedObject(boolean explicit, int tagNo, ASN1Encodable obj)
  {
    super(explicit, tagNo, obj);
  }
  
  boolean isConstructed()
  {
    if (!empty)
    {
      if (explicit)
      {
        return true;
      }
      

      ASN1Primitive primitive = obj.toASN1Primitive().toDLObject();
      
      return primitive.isConstructed();
    }
    


    return true;
  }
  

  int encodedLength()
    throws IOException
  {
    if (!empty)
    {
      int length = obj.toASN1Primitive().toDLObject().encodedLength();
      
      if (explicit)
      {
        return StreamUtil.calculateTagLength(tagNo) + StreamUtil.calculateBodyLength(length) + length;
      }
      


      length -= 1;
      
      return StreamUtil.calculateTagLength(tagNo) + length;
    }
    


    return StreamUtil.calculateTagLength(tagNo) + 1;
  }
  


  void encode(ASN1OutputStream out)
    throws IOException
  {
    if (!empty)
    {
      ASN1Primitive primitive = obj.toASN1Primitive().toDLObject();
      
      if (explicit)
      {
        out.writeTag(160, tagNo);
        out.writeLength(primitive.encodedLength());
        out.writeObject(primitive);
      }
      else
      {
        int flags;
        
        int flags;
        
        if (primitive.isConstructed())
        {
          flags = 160;
        }
        else
        {
          flags = 128;
        }
        
        out.writeTag(flags, tagNo);
        out.writeImplicitObject(primitive);
      }
    }
    else
    {
      out.writeEncoded(160, tagNo, ZERO_BYTES);
    }
  }
}
