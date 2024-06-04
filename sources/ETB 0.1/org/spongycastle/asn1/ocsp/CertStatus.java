package org.spongycastle.asn1.ocsp;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DERTaggedObject;




public class CertStatus
  extends ASN1Object
  implements ASN1Choice
{
  private int tagNo;
  private ASN1Encodable value;
  
  public CertStatus()
  {
    tagNo = 0;
    value = DERNull.INSTANCE;
  }
  

  public CertStatus(RevokedInfo info)
  {
    tagNo = 1;
    value = info;
  }
  


  public CertStatus(int tagNo, ASN1Encodable value)
  {
    this.tagNo = tagNo;
    this.value = value;
  }
  

  private CertStatus(ASN1TaggedObject choice)
  {
    tagNo = choice.getTagNo();
    
    switch (choice.getTagNo())
    {
    case 0: 
      value = DERNull.INSTANCE;
      break;
    case 1: 
      value = RevokedInfo.getInstance(choice, false);
      break;
    case 2: 
      value = DERNull.INSTANCE;
      break;
    default: 
      throw new IllegalArgumentException("Unknown tag encountered: " + choice.getTagNo());
    }
    
  }
  
  public static CertStatus getInstance(Object obj)
  {
    if ((obj == null) || ((obj instanceof CertStatus)))
    {
      return (CertStatus)obj;
    }
    if ((obj instanceof ASN1TaggedObject))
    {
      return new CertStatus((ASN1TaggedObject)obj);
    }
    
    throw new IllegalArgumentException("unknown object in factory: " + obj.getClass().getName());
  }
  


  public static CertStatus getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    return getInstance(obj.getObject());
  }
  
  public int getTagNo()
  {
    return tagNo;
  }
  
  public ASN1Encodable getStatus()
  {
    return value;
  }
  









  public ASN1Primitive toASN1Primitive()
  {
    return new DERTaggedObject(false, tagNo, value);
  }
}
