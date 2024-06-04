package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERTaggedObject;


public class AttCertIssuer
  extends ASN1Object
  implements ASN1Choice
{
  ASN1Encodable obj;
  ASN1Primitive choiceObj;
  
  public static AttCertIssuer getInstance(Object obj)
  {
    if ((obj == null) || ((obj instanceof AttCertIssuer)))
    {
      return (AttCertIssuer)obj;
    }
    if ((obj instanceof V2Form))
    {
      return new AttCertIssuer(V2Form.getInstance(obj));
    }
    if ((obj instanceof GeneralNames))
    {
      return new AttCertIssuer((GeneralNames)obj);
    }
    if ((obj instanceof ASN1TaggedObject))
    {
      return new AttCertIssuer(V2Form.getInstance((ASN1TaggedObject)obj, false));
    }
    if ((obj instanceof ASN1Sequence))
    {
      return new AttCertIssuer(GeneralNames.getInstance(obj));
    }
    
    throw new IllegalArgumentException("unknown object in factory: " + obj.getClass().getName());
  }
  


  public static AttCertIssuer getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    return getInstance(obj.getObject());
  }
  







  public AttCertIssuer(GeneralNames names)
  {
    obj = names;
    choiceObj = obj.toASN1Primitive();
  }
  

  public AttCertIssuer(V2Form v2Form)
  {
    obj = v2Form;
    choiceObj = new DERTaggedObject(false, 0, obj);
  }
  
  public ASN1Encodable getIssuer()
  {
    return obj;
  }
  










  public ASN1Primitive toASN1Primitive()
  {
    return choiceObj;
  }
}
