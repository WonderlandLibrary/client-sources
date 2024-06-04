package org.spongycastle.asn1;

import java.io.IOException;
import java.util.Enumeration;












public class BERTaggedObject
  extends ASN1TaggedObject
{
  public BERTaggedObject(int tagNo, ASN1Encodable obj)
  {
    super(true, tagNo, obj);
  }
  








  public BERTaggedObject(boolean explicit, int tagNo, ASN1Encodable obj)
  {
    super(explicit, tagNo, obj);
  }
  





  public BERTaggedObject(int tagNo)
  {
    super(false, tagNo, new BERSequence());
  }
  
  boolean isConstructed()
  {
    if (!empty)
    {
      if (explicit)
      {
        return true;
      }
      

      ASN1Primitive primitive = obj.toASN1Primitive().toDERObject();
      
      return primitive.isConstructed();
    }
    


    return true;
  }
  

  int encodedLength()
    throws IOException
  {
    if (!empty)
    {
      ASN1Primitive primitive = obj.toASN1Primitive();
      int length = primitive.encodedLength();
      
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
    out.writeTag(160, tagNo);
    out.write(128);
    
    if (!empty)
    {
      if (!explicit)
      {
        Enumeration e;
        if ((obj instanceof ASN1OctetString)) {
          Enumeration e;
          if ((obj instanceof BEROctetString))
          {
            e = ((BEROctetString)obj).getObjects();
          }
          else
          {
            ASN1OctetString octs = (ASN1OctetString)obj;
            BEROctetString berO = new BEROctetString(octs.getOctets());
            e = berO.getObjects();
          }
        } else { Enumeration e;
          if ((obj instanceof ASN1Sequence))
          {
            e = ((ASN1Sequence)obj).getObjects();
          } else { Enumeration e;
            if ((obj instanceof ASN1Set))
            {
              e = ((ASN1Set)obj).getObjects();
            }
            else
            {
              throw new ASN1Exception("not implemented: " + obj.getClass().getName()); }
          } }
        Enumeration e;
        while (e.hasMoreElements())
        {
          out.writeObject((ASN1Encodable)e.nextElement());
        }
      }
      else
      {
        out.writeObject(obj);
      }
    }
    
    out.write(0);
    out.write(0);
  }
}
