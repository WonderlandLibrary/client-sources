package org.spongycastle.asn1.cmc;

import java.io.IOException;
import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.crmf.CertReqMsg;
















public class TaggedRequest
  extends ASN1Object
  implements ASN1Choice
{
  public static final int TCR = 0;
  public static final int CRM = 1;
  public static final int ORM = 2;
  private final int tagNo;
  private final ASN1Encodable value;
  
  public TaggedRequest(TaggedCertificationRequest tcr)
  {
    tagNo = 0;
    value = tcr;
  }
  
  public TaggedRequest(CertReqMsg crm)
  {
    tagNo = 1;
    value = crm;
  }
  
  private TaggedRequest(ASN1Sequence orm)
  {
    tagNo = 2;
    value = orm;
  }
  
  public static TaggedRequest getInstance(Object obj)
  {
    if ((obj instanceof TaggedRequest))
    {
      return (TaggedRequest)obj;
    }
    
    if (obj != null)
    {
      if ((obj instanceof ASN1Encodable))
      {
        ASN1TaggedObject asn1Prim = ASN1TaggedObject.getInstance(((ASN1Encodable)obj).toASN1Primitive());
        
        switch (asn1Prim.getTagNo())
        {
        case 0: 
          return new TaggedRequest(TaggedCertificationRequest.getInstance(asn1Prim, false));
        case 1: 
          return new TaggedRequest(CertReqMsg.getInstance(asn1Prim, false));
        case 2: 
          return new TaggedRequest(ASN1Sequence.getInstance(asn1Prim, false));
        }
        throw new IllegalArgumentException("unknown tag in getInstance(): " + asn1Prim.getTagNo());
      }
      
      if ((obj instanceof byte[]))
      {
        try
        {
          return getInstance(ASN1Primitive.fromByteArray((byte[])obj));
        }
        catch (IOException e)
        {
          throw new IllegalArgumentException("unknown encoding in getInstance()");
        }
      }
      throw new IllegalArgumentException("unknown object in getInstance(): " + obj.getClass().getName());
    }
    
    return null;
  }
  
  public int getTagNo()
  {
    return tagNo;
  }
  
  public ASN1Encodable getValue()
  {
    return value;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return new DERTaggedObject(false, tagNo, value);
  }
}
