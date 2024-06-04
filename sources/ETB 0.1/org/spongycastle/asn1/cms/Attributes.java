package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.DLSet;
















public class Attributes
  extends ASN1Object
{
  private ASN1Set attributes;
  
  private Attributes(ASN1Set set)
  {
    attributes = set;
  }
  
  public Attributes(ASN1EncodableVector v)
  {
    attributes = new DLSet(v);
  }
  













  public static Attributes getInstance(Object obj)
  {
    if ((obj instanceof Attributes))
    {
      return (Attributes)obj;
    }
    if (obj != null)
    {
      return new Attributes(ASN1Set.getInstance(obj));
    }
    
    return null;
  }
  
  public Attribute[] getAttributes()
  {
    Attribute[] rv = new Attribute[attributes.size()];
    
    for (int i = 0; i != rv.length; i++)
    {
      rv[i] = Attribute.getInstance(attributes.getObjectAt(i));
    }
    
    return rv;
  }
  



  public ASN1Primitive toASN1Primitive()
  {
    return attributes;
  }
}
