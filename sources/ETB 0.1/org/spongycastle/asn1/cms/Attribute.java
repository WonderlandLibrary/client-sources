package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.DERSequence;




































public class Attribute
  extends ASN1Object
{
  private ASN1ObjectIdentifier attrType;
  private ASN1Set attrValues;
  
  public static Attribute getInstance(Object o)
  {
    if ((o instanceof Attribute))
    {
      return (Attribute)o;
    }
    
    if (o != null)
    {
      return new Attribute(ASN1Sequence.getInstance(o));
    }
    
    return null;
  }
  

  private Attribute(ASN1Sequence seq)
  {
    attrType = ((ASN1ObjectIdentifier)seq.getObjectAt(0));
    attrValues = ((ASN1Set)seq.getObjectAt(1));
  }
  


  public Attribute(ASN1ObjectIdentifier attrType, ASN1Set attrValues)
  {
    this.attrType = attrType;
    this.attrValues = attrValues;
  }
  
  public ASN1ObjectIdentifier getAttrType()
  {
    return attrType;
  }
  
  public ASN1Set getAttrValues()
  {
    return attrValues;
  }
  
  public ASN1Encodable[] getAttributeValues()
  {
    return attrValues.toArray();
  }
  



  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(attrType);
    v.add(attrValues);
    
    return new DERSequence(v);
  }
}
